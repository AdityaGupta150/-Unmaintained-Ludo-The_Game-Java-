package in.ag15;


public class BoardPrinter {

	static public int boxlen;
	static private Ludo_Box[][] board;

	public BoardPrinter(Ludo_Box[][] board) {
		BoardPrinter.board = board;
	}

	public static void titleBar(int width) {
		Util.align_text_center(width, "NAMASTE from \"Ludo - The Game\" :D");
		System.out.println();
		while (width --> 0) System.out.print('=');
		System.out.println();
	}

	void row_type1(int nrow){
		//Actual-Row Start
		System.out.print('|');
	
		for (int i = 0; i < (boxlen+1)*6 - 1; i++)
			System.out.print('\\');
	
		System.out.print('|');
	
		for (int i = 0; i < 3; i++){
			Util.align_text_center(boxlen, board[nrow][6+i].content);
			System.out.print('|');
		}
	
		for (int i = 0; i < (boxlen+1)*6 - 1; i++)	System.out.print('\\');
		System.out.println('|');
		//Actual-Row End
	}
	
	void row_type2(int nrow){
		//!Explanatory comments in row_type1
		System.out.print('|');
	
		for (int i = 0; i < boxlen; i++)	System.out.print('\\');	
		Util.align_text_center(boxlen+2, board[nrow][1].content);
		for (int i = 0; i < (boxlen)*2 + 1; i++)	System.out.print('\\');
		Util.align_text_center(boxlen+2, board[nrow][4].content);
		for (int i = 0; i < boxlen; i++) System.out.print('\\');
		System.out.print('|');
	
		for (int i = 0; i < 3; i++){
			Util.align_text_center(boxlen, board[nrow][6+i].content);
			System.out.print('|');
		}
	
		for (int i = 0; i < boxlen; i++)	System.out.print('\\');
		Util.align_text_center(boxlen+2, board[nrow][10].content);
		for (int i = 0; i < (boxlen)*2 + 1; i++)	System.out.print('\\');
		Util.align_text_center(boxlen+2, board[nrow][13].content);
		for (int i = 0; i < boxlen; i++) System.out.print('\\');
		System.out.println('|');
		//Actual-Row End
	}
		
	void row_type3(int nrow){
		//!Explanatory comments in row_type1
		//Actual-Row Start
		System.out.print('|');
		for (int i = 0; i < 6; i++){
			Util.align_text_center(boxlen, board[nrow][i].content);
			System.out.print('|');
		}
	
		for (int i = 0; i < (boxlen+1)*3 -1; i++) System.out.print(' ');	
		System.out.print('|');
	
		for (int i = 9; i < 15; i++){
			Util.align_text_center(boxlen, board[nrow][i].content);
			System.out.print('|');
		}
		System.out.println();
		//Actual-Row End
	}

	public void row_type4(int i) {
	}

	public void inter_type1() {
	}

	public void inter_type2() {
	}

	public void inter_type3() {
	}

	public void inter_type4() {
	}

	public void inter_type5() {
	}
    
}