package in.ag15;

import in.ag15.enums.Colour;
import in.ag15.enums.Direction;

import java.awt.Point;

public class Ludo_Goti{

	private final Colour gotiColour;
	Direction currDir;
	Point currCoord;

	Point getCoords(){ return currCoord; }
	Direction getDir(){ return currDir; }
	Colour getColour(){ return gotiColour; }

	Ludo_Goti(Colour gotiColour, Point currCoord) {
		this(gotiColour, Ludo_Coords.StartPos.get(gotiColour).getValue() , currCoord);
	}
	Ludo_Goti( Colour gotiColour, Direction currDir, Point currCoord ){
		this.gotiColour = gotiColour;
		this.currDir = currDir;
		this.currCoord = currCoord;		
	}
	
}