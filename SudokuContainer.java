/**
 * @author Stian Masserud
 * Class contains uo to 750 solved sudokubaords. 
 * SolutionCounter can go higher, but solution
 * will not be stored.
 */
import java.util.ArrayList;

class SudokuContainer {
    int solutionCount = 0;
    ArrayList<int[][]> boards = new ArrayList<int[][]>();

    /**
     * Adds solution to list and raise counter.
     * Adds maximum 750 solution. Raises counter
     * beyond 750.
     * @param Board to be stored.
     * @return
     */
    public void insert(int[][]  b) {

		if(boards.size() < 750) {
			solutionCount++; 
		    boards.add(b);
		   	//printBoard(boards.size()-1);
		} else {
			solutionCount++; 
		}
    }

    /**
     * Prints a board to the terminal screen
     *
     * @return
     */
    public void printBoard(int index) {
    	System.out.println();
		int[][] sudoku = boards.get(index);
		for(int i = 0; i < sudoku.length; i++) {
		    for(int j = 0; j < sudoku[i].length; j++) {
				System.out.print(sudoku[i][j] + " ");
		    }
		    System.out.println();
		}
    }

    public int[][] getFirst() {
	if(boards.get(0) != null ) {
	    return boards.get(0);
	} else {
	    return null;
	}
    }

    /**
     * Get an array over all solutions
     * in list boards.
     * @return Boards[] array of all solutions. 
     */
    public int[][] get(int index) {
	return boards.get(index);	
	//return boards.toArray(new Board[]{});
    }

    /**
     * Get number of solutions
     * @return int number of solutions
     */
    public int getSolutionCount() {
	return solutionCount;
    }
}

class SolvedSudoku {
    int[][] solved;

    public SolvedSudoku (Square[][] s) {
		solved = new int[s.length][s.length];
		for(int i = 0; i < solved.length; i++) {
		    for(int j = 0; j < solved[i].length; j++) {
				solved[i][j] = s[i][j].getValue();
		    }
		}
    }
}