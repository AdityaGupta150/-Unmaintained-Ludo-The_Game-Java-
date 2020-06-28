package in.ag15;

import java.io.IOException;
import java.util.Scanner;

import in.ag15.CustomExceptions.endApplication;
import in.ag15.enums.Direction;

public class Main {
	static final Scanner scan = new Scanner(System.in);
	static int numGamePlays = 0;

	public static void main(final String[] args) throws IOException {
		final Game game = new Game();
		game.intro();
		game.updateDisplay();

		System.out.println("Enter anything");
		int a = System.in.read();
		if( a=='5' )	return;

		short playConsent = 1;

		//GamePlay starts
		
		try{
			do{
				if( !game.InitGame(playConsent) ){
					System.err.println("Couldn't initiate the game... Exiting");
					return;
				}

				game.play();
				System.out.println("Enter 1 if you want to play again and reset\nEnter 2 if you want to play with same players\nAnything else to say GoodBye :-)\nYour Consent : ");
				playConsent = Main.scan.nextShort();

			}while ( playConsent == 1 || playConsent == 2 );
			System.out.println("Khelne ke liye Dhanyawaad :-D");

			throw new endApplication("Just to hide the warning, that try block never throws it");
		}
		catch(final endApplication e){
			System.err.println("That was the " + numGamePlays+1 + "th run");
			System.err.println(e);
			return;
		}
		catch(final Exception e){
			System.err.println(e);
			return;
		}

	}
}
