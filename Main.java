/**
 * @author Stian Masserud
 * 
 * Main class. 
 * Starts both the solution pprosses and possibly the Gui thread.
 * Demands on or two parametres from terminal to start.
 */
import javax.swing.*;
import java.io.*;;
class Main {
	
	/**
	 * Main method. Takes arguement from terminal and does stuff bassed on
	 * user sends 1 or 2 arguements. 
	 */
    public static void main (String[] args) {

    	if(args.length == 0) {
    		System.exit(1);
    	}
		// Read first file, then solve all possible solutions
		File data = new File(args[0]);
		SudokuReader sr = new SudokuReader();
		Board s1 = sr.readFromFile(data);
		s1.getSquare()[0][0].fillRemainderOfBoard(s1);
		final SudokuContainer sc = s1.getContainer();

		// Starts the GUI-thread
		if(args.length == 1) {
			final int row = sr.getRow();
			final int collum = sr.getCollum(); 
			
			SwingUtilities.invokeLater
			    (    new Runnable() {
				    public void run() {
					new SudokuGui(row,collum, sc);
				    } 
				}  
			); 

		// Write up 750 solutions to a txt-file
		} else if(args.length == 2) {
			try{
				int[][] board = null;
				FileWriter fstream = new FileWriter(args[1]);
				BufferedWriter out = new BufferedWriter(fstream);
				int counter = sc.getSolutionCount();
				if(counter > 750) {
					counter = 750;
				}

				// Print prosses
				for(int i = 0; i < counter; i++) {
					board = sc.get(i);
					out.write("\n");
					for(int k = 0; k < board.length; k++) {
	   					for(int j = 0; j <board[k].length; j++) {
							out.write(board[k][j] + " ");
	    				}
	    				out.write("\n");
					}
				}
				out.write("SolutionCount: " + sc.getSolutionCount());
				out.close();
			}catch (Exception e) {
				System.out.println("Something went wrong with filename, try again");
			}
		} 
    }
}
