package in.ag15;

import in.ag15.enums.BoxType;
import in.ag15.enums.Colour;

import java.awt.Point;
import java.util.ArrayList;

class Ludo_Box {
	
	private final ArrayList<Ludo_Goti> inBoxGotis = new ArrayList<Ludo_Goti>();
	final Point coords;
	//PUBLIC
	BoxType box_type;
	StringBuilder content;

	static public void sort(StringBuilder str) {
		for (int i = 1; i < str.length(); i++) {
			if (str.charAt(i - 1) > str.charAt(i)) {
				char tmp = str.charAt(i);
				str.setCharAt(i, str.charAt(i - 1));
				str.setCharAt(i - 1, tmp);
			}
		}
	}

	private Boolean remGoti(Ludo_Goti goti) {
		char colourChar = keywords.colourCodes.get(goti.getColour());

		//!Removing goti from the dataStructures is done by the next 3lines in the if block
		//Logically Removing the goti now
		this.inBoxGotis.remove(goti);

		//! Removing the goti from display(content)

		int loc = this.content.length();
		for (int i = 0; i < this.content.length(); i++) {
			if (this.content.charAt(i) == colourChar) {
				loc = i;
				break;
			}
		}
		if (loc == this.content.length()) {
			System.err.println("Goti to remove not present!");
			return false;
		}

		//Expects sanitized string
		if (loc + 1 == this.content.length()) content.deleteCharAt(loc);
		else if (this.content.charAt(loc + 1) >= 48 && this.content.charAt(loc + 1) <= 57)    //ie. there are multiple gotis
		{
			char tmp = this.content.charAt(loc + 1);
			if (tmp > '2')
				this.content.setCharAt(loc + 1, --tmp);
			else {
				this.content.deleteCharAt(loc + 1);
			}
		} else this.content.deleteCharAt(loc);

		if (this.isEmpty()) {
			if (box_type == BoxType.STOP) {
				this.content.delete(0, this.content.length());
				this.content.append("X");
			}
		}

		return true;
	}

	Byte addGoti(Ludo_Goti goti) {
		Colour gotiColour = goti.getColour();
		char colourChar = keywords.colourCodes.get(goti.getColour());

		if(goti.getCoords().x == 0 && goti.getCoords().y == 0 ) return -1;
		
		if( this.box_type == BoxType.STOP && this.isEmpty() ){
			this.content.delete(0, this.content.length());
		}
		this.content.append(colourChar);
		this.reformatContent();
	
		goti.currCoord = this.coords;
		this.inBoxGotis.add( goti );
	
		if( this.areOpponentsPresent(gotiColour) && this.box_type == BoxType.NORMAL ) return 0;
	
		return 1;
	}

	void reformatContent() {
		sort(content);
		for (int i = 0; i < this.content.length()-1; i++) {
			int j = i + 1;
			int count = 1;
			while (content.charAt(j) == content.charAt(i)) {
				++count;
			}
			if (count > 1) {
				content.insert(i, count);
			}
		}
	}

	Boolean isPresent(Colour colour) {
		for (Ludo_Goti ludo_Goti : inBoxGotis) {
			if (ludo_Goti.getColour() == colour) {
				return true;
			}
		}
		return false;
	}

	Boolean areOpponentsPresent(Colour colour) {
		for (Ludo_Goti ludo_Goti : inBoxGotis) {
			if (ludo_Goti.getColour() != colour) {
				return true;
			}
		}
		return false;
	}

	Boolean isEmpty() {
		return this.inBoxGotis.isEmpty();
	}

	public Ludo_Box(Point coords) {
		this(coords, BoxType.NORMAL);
	}

	public Ludo_Box(Point coords, BoxType box_type) {
		this.coords = coords;
		this.box_type = box_type;
		this.content = new StringBuilder();
	}

}