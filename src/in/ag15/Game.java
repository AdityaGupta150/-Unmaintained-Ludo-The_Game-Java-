package in.ag15;

import in.ag15.enums.*;
import javafx.util.Pair;

import java.awt.Point;
import java.io.IOException;
import java.util.*;

class Game {

	private static final Scanner scanner = new Scanner(System.in);
	private static Random die = new Random();
	private static int numGameRuns;
	// Map shortcutsMap; // Map<String, funcPointer?>
	Map<Colour, Player> coloursMap;
	Map<Player, Pair<String, Colour>> playerMap = new HashMap<>();
	Map<Player, RobotKind> robotPlayer = new HashMap<>();
	// STATIC UTILITY END
	private final Player currPlayer;
	private final Colour currColour;
	private final int gotiPerUser;

	private final Ludo_Box[][] board; // Array<Array<LudoBoxType.>>
	private final Map<Colour, ArrayList<Point>> lockedPositions; // Map<Colour, Array<Coordinates>(4)>
	private Map<Colour, Ludo_Goti> movingGotis; // Map<Colour, Ludo_Goti>
	private Map<Colour, Integer> numFinishedGotis = new HashMap<>(); // Map<Colour, UInt>

	/*
	 * @brief 1st in this order is the one at bottom left, and next ones
	 * anti-clockwise to this
	 */
	private final Colour[] colourOrder; // Array<Colour>

	static{
		numGameRuns = 0;
	}

	Game() {

		++Game.numGameRuns;
		this.colourOrder = new Colour[]{ Colour.LAAL, Colour.NEELA, Colour.PEELA, Colour.HARA };

		gotiPerUser = 4;
	
		this.board = new Ludo_Box[15][15];
		for (int i = 0; i < 15; ++i)
		{
			for (int j = 0; j < 15; ++j)
			{
				board[i][j] = new Ludo_Box(new Point(i,j));
			}
		}
	
		//! Marking the LockRooms and the HomePath
		int i,j;
		for (i = 0; i < 6; i++){
			for (j = 0; j < 6; j++){
				board[i][j].box_type = BoxType.UNUSABLE;
				board[7][j].box_type = BoxType.HOME_PATH;
				board[7][14-j].box_type = BoxType.HOME_PATH;
			}
			board[i][7].box_type = BoxType.HOME_PATH;
			board[14-i][7].box_type = BoxType.HOME_PATH;
		}
		board[0][7].box_type = board[14][7].box_type = BoxType.NORMAL;
		board[7][0].box_type = board[7][14].box_type = BoxType.NORMAL;
		for (i = 0; i < 6; i++)	for (j = 9; j < 15; j++)	board[i][j].box_type = BoxType.UNUSABLE;
		for (i = 9; i < 15; i++)	for (j = 0; j < 6; j++)	board[i][j].box_type = BoxType.UNUSABLE;
		for (i = 9; i < 15; i++)	for (j = 9; j < 15; j++)	board[i][j].box_type = BoxType.UNUSABLE;
		//! Marking the HomeArea
		for (i = 6; i < 9; i++)	for (j = 6; j < 9; j++)	board[i][j].box_type = BoxType.UNUSABLE;
		//! Marking the Stops
		for (final Point p : Ludo_Coords.Stops) {
			this.getBox(p).box_type = BoxType.STOP;
		}
		board[6][7].box_type = board[8][7].box_type = board[7][6].box_type = board[7][8].box_type = BoxType.HOME_END;
	
		//! Storing references in order, starting from the starting positon of that colour, then anti-clockwise
		this.lockedPositions = Map.of(
			Colour.LAAL, new ArrayList<Point>(){{ addAll(Arrays.asList( new Point(13,4), new Point(13,1), new Point(10,1), new Point(10,4))); }},
			Colour.HARA, new ArrayList<Point>(){{ addAll(Arrays.asList( new Point(4,1), new Point(1,1), new Point(1,4), new Point(4,4))); }},
			Colour.PEELA, new ArrayList<Point>(){{ addAll(Arrays.asList( new Point(1,10), new Point(1,13), new Point(4,13), new Point(4,10))); }},
			Colour.NEELA, new ArrayList<Point>(){{ addAll(Arrays.asList( new Point(10,13), new Point(13,13), new Point(13,10), new Point(10,10))); }}
		);

			//! Marking the LockedPositions
		for (final Colour lockColour : this.lockedPositions.keySet())
		{
			for( final Point coord : this.lockedPositions.get(lockColour) ) this.getBox(coord).box_type = BoxType.LOCK;
		}
	
		this.coloursMap = new HashMap<>();

		this.currPlayer = Player.Player1;
		this.currColour = Colour.LAAL;
	
		for (final Point stop : Ludo_Coords.Stops)
		{
			this.getBox(stop).content.replace(0, this.getBox(stop).content.length(), "X");
		}
	}

	// STATIC UTILITY START
	void rolldie(final ArrayList<Integer> dieNumbers) {
		final ArrayList<Integer> tmpVec = new ArrayList<>();
		final ArrayList<Integer> sixPos = new ArrayList<>(); // used in 'Cleaning cases of 3 sixes'

		int dieNum = (Game.die.nextInt() % 6 + 6)%6 + 1; // ! (i%n +n)%n to get a positive value
		if (dieNum != 6) { // To prevent cases like, dieNumbers = {4, 6} (Think! It IS possible in the
			// do-while)
			dieNumbers.add(dieNum);
			return;
		}
		do {
			dieNum = (Game.die.nextInt() % 6 + 6)%6 + 1;
			tmpVec.add(dieNum);
		} while (dieNum == 6);
		tmpVec.add(dieNum); // To insert the last left non-6

		// ! Main logic is above only, below are some cleansing and optimisations
		for (int i = 0; i < tmpVec.size(); ++i) {
			if (tmpVec.get(i) == 6)
				sixPos.add(i);
		}
		while (sixPos.size() > 3) { // ie. more than 3 sixes existing
			tmpVec.remove(sixPos.get(sixPos.size() - 1));
			tmpVec.remove(sixPos.get(sixPos.size() - 2));
			tmpVec.remove(sixPos.get(sixPos.size() - 3));
			for (int i = 0; i < 3; i++)
				sixPos.remove(sixPos.size() - 1);
		}

		dieNumbers.addAll(tmpVec);
	}

	private int getNumLocks(final Colour gotiColour) {
		int num = 0;
		for (final Point boxCoord : this.lockedPositions.get(gotiColour)) {
			if (!this.getBox(boxCoord).isEmpty() && this.getBox(boxCoord).box_type == BoxType.LOCK) {
				++num;
			}
		}
		return num;
	}

	private Boolean unlockGoti() {
		// TODO
		return true;
	}

	private Boolean lockGoti(final Ludo_Goti goti) {
		// TODO
		return true;
	}

	private Byte moveGoti(final Ludo_Goti goti, final int dist) {
		// TODO
		return 0;
	}

	private Byte moveGoti(final Ludo_Goti goti, final _moveData data) {
		// TODO
		return 0;
	}

	private void handleMoveVal(final Byte moveVal, final ArrayList<Integer> dieNumbers) throws IOException {
		if (moveVal == 1 || moveVal == 2) {
			System.out.println("\nPress enter to roll the die once more...");
			System.in.read();
			this.rolldie(dieNumbers);
		}
	}

	private void autoMove() {
		// TODO
	}

	// Not implementing 'friend' gotis now, not in this atleast
	private Boolean attack(final Ludo_Goti goti) {
		// TODO
		return true;
	}

	void intro() {
		Pair<Integer, Integer> dimension = new Pair<>(0,0);
	
		Util.place_v_center("[NOTICE] Please ensure window is at least 62*31");
		System.out.println();
	
		do{
			dimension = Util.getTerminalDimen();

		}while( dimension.getKey() < 62 || dimension.getValue() < 31 );
	
		BoardPrinter.titleBar(dimension.getKey());
	
		Util.place_v_center(dimension.getValue() - 7, "");
		Util.align_text_center(dimension.getKey(), "Enter names of the Players (Leave empty if not playing, or type \"ROBOT\") : ");
		
		String playerName;
		Colour colour;
		Player p = Player.Player1;
		int numRobots = 0;
		final int numThinkers = 0;
	
		colour = colourOrder[0];	
		
		while(true){
			
			Util.place_center(dimension.getKey(), p.toString() + " - ");

			System.out.println("Enter player name : ");
			playerName = Game.scanner.nextLine();
			playerName = playerName.trim();
			if( !playerName.isEmpty() ){
				if( Util.icompare( playerName, keywords.robo ) ){
					++numRobots;
					this.playerMap.put(p, new Pair<String,enums.Colour>("ROBOT " + Integer.toString(numRobots), colour));
					this.robotPlayer.put(p, RobotKind.randomRobo);
				}
				else{
					this.playerMap.put(p, new Pair<String,enums.Colour>(playerName, colour));
				}
			}

			if( p.equals(Player.Player1) ){	p = Player.Player2; colour = Colour.HARA; }
			else if( p.equals(Player.Player2) ){	p = Player.Player3; colour = Colour.PEELA; }
			else if( p.equals(Player.Player3) ){	p = Player.Player4; colour = Colour.NEELA; }
			else break;
			
		}

		//Setting the board, on basis of given players
		for (Player player : this.playerMap.keySet()) {
			final Colour gotiColour = this.playerMap.get(player).getValue();
			for (Point coordPoint : this.lockedPositions.get(gotiColour)) {
				this.getBox(coordPoint).addGoti(new Ludo_Goti(gotiColour, this.getEmptyLock(gotiColour)));
				this.getBox(coordPoint).box_type = BoxType.LOCK;
			}
			this.coloursMap.put(gotiColour, player);
			this.numFinishedGotis.put(gotiColour, 0);
		}
	
		for (Point coordPoint : Ludo_Coords.Stops) {
			this.getBox(coordPoint).content.replace(0, this.getBox(coordPoint).content.length(), "X");
		}
	
	}

//Public section

	private void endGame() {
		// TODO
	}

	_moveData isMovePossible(final Ludo_Goti goti, final int dist) {
		// TODO
		return null;
	}

	Boolean isGameFinished() {
		// TODO
		return null;
	}

	Boolean isPlayerPlaying(final Player p) {
		// TODO
		return null;
	}

	Boolean isValid(final Point coord) {
		if (coord.x >= 0 && coord.x < 15) {
			return coord.y >= 0 && coord.y < 15;
		}
		return false;
	}

	Boolean isValid(final Ludo_Goti goti) throws Exception {
		if (goti.getColour() == this.currColour && this.isValid(goti.getCoords()))
			return true;
		throw new Exception("Goti invalid");
	}

	Point getEmptyLock(final Colour gotiColour) {
		for (final Point lockCoord : this.lockedPositions.get(gotiColour))
		{
			if(this.getBox(lockCoord).isEmpty() && this.getBox(lockCoord).box_type == BoxType.LOCK){
				return this.getBox(lockCoord).coords;
			}
		}
		return null;
	}

	void play() {
	}

	// fun settings(): Unit 	//Later if possible
	Ludo_Box getBox(final Point p) {
		if( this.isValid(p) ){
			return this.board[p.x][p.y];
		}
		return null;
	}

	public void updateDisplay() {
		int boxlen = 0;
		Pair<Integer, Integer> dimension = new Pair<>(0,0);

		do {
			dimension = Util.getTerminalDimen();
			boxlen = (2 * Math.min(dimension.getKey(), dimension.getValue()) - 32) / 15;

		} while (Math.min(dimension.getKey(), dimension.getValue()) < 32);

		BoardPrinter.titleBar(dimension.getKey());
		Util.place_center(dimension.getKey() - 15 * (boxlen + 1) + 3 - 4);

		System.out.print("  ");
		for (int i = 0; i < 10; i++) {
			for (int j = boxlen; j-- > 0; )
				System.out.print(' ');
			System.out.print(i);
		}
		for (int i = 10; i < 15; i++) {
			for (int j = boxlen - 1; j-- > 0; )
				System.out.print(' ');
			System.out.print(i);
		}
		System.out.print("\n  ");
		Util.place_center(dimension.getKey() - 15 * (boxlen + 1) + 3 - 4);

		for (int i = 0; i < (boxlen + 1) * 15 + 1; i++) System.out.print('-');
		System.out.println();

		final BoardPrinter _board_printer = new BoardPrinter(this.board);
		BoardPrinter.boxlen = boxlen;

		Util.place_center(dimension.getKey() - 15 * (boxlen + 1) + 3 - 4);		System.out.print(0 + " ");		_board_printer.row_type1(0);		_board_printer.inter_type1();
		Util.place_center(dimension.getKey() - 15 * (boxlen + 1) + 3 - 4);		System.out.print(1 + " ");		_board_printer.row_type2(1);		_board_printer.inter_type2();
		Util.place_center(dimension.getKey() - 15 * (boxlen + 1) + 3 - 4);		System.out.print(2 + " ");		_board_printer.row_type1(2);		_board_printer.inter_type3();
		Util.place_center(dimension.getKey() - 15 * (boxlen + 1) + 3 - 4);		System.out.print(3 + " ");		_board_printer.row_type1(3);		_board_printer.inter_type2();
		Util.place_center(dimension.getKey() - 15 * (boxlen + 1) + 3 - 4);		System.out.print(4 + " ");		_board_printer.row_type2(4);		_board_printer.inter_type2();
		Util.place_center(dimension.getKey() - 15 * (boxlen + 1) + 3 - 4);		System.out.print(5 + " ");		_board_printer.row_type1(5);		_board_printer.inter_type4();

		Util.place_center(dimension.getKey() - 15 * (boxlen + 1) + 3 - 4);		System.out.print(6 + " ");		_board_printer.row_type3(6);		_board_printer.inter_type5();
		Util.place_center(dimension.getKey() - 15 * (boxlen + 1) + 3 - 4);		System.out.print(7 + " ");		_board_printer.row_type4(7);		_board_printer.inter_type5();
		Util.place_center(dimension.getKey() - 15 * (boxlen + 1) + 3 - 4);		System.out.print(8 + " ");		_board_printer.row_type3(8);		_board_printer.inter_type4();

		Util.place_center(dimension.getKey() - 15 * (boxlen + 1) + 3 - 4);		System.out.print(9 + " ");		_board_printer.row_type1(9);		_board_printer.inter_type2();
		Util.place_center(dimension.getKey() - 15 * (boxlen + 1) + 3 - 4);		System.out.print(10);		_board_printer.row_type2(10);		_board_printer.inter_type2();
		Util.place_center(dimension.getKey() - 15 * (boxlen + 1) + 3 - 4);		System.out.print(11);		_board_printer.row_type1(11);		_board_printer.inter_type3();
		Util.place_center(dimension.getKey() - 15 * (boxlen + 1) + 3 - 4);		System.out.print(12);		_board_printer.row_type1(12);		_board_printer.inter_type2();
		Util.place_center(dimension.getKey() - 15 * (boxlen + 1) + 3 - 4);		System.out.print(13);		_board_printer.row_type2(13);		_board_printer.inter_type1();
		Util.place_center(dimension.getKey() - 15 * (boxlen + 1) + 3 - 4);		System.out.print(14);		_board_printer.row_type1(14);

		System.out.print("  ");
		Util.place_center(dimension.getKey() - 15 * (boxlen + 1) + 3 - 4);
		for (int i = 0; i < (boxlen + 1) * 15 + 1; i++) System.out.print('-');

		System.out.println('\n');
		Util.align_text_center(this.playerMap.get(this.currPlayer).getKey());
		System.out.println();
		for (int i = 0; i < dimension.getKey(); ++i) System.out.print('-');
		System.out.println();

	}

	public boolean InitGame(final short playConsent) {
		return false;
	}

	static private class _smartMoveData {
		Point finalCoords;
		Direction finalDir;
		int moveProfit = 0;
	}

	static private class _moveData {
		Boolean isPossible = false;
		_smartMoveData smartData = null;
	}

	public void sampleTry() {
	}
}