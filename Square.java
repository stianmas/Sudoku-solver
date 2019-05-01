/**
 * @author Stian Masserud
 *
 * Initialize squares. Also contains methods to solve a soduko recursivly.
 * Has two subclasses, one for predefined square and another for blanks squares.
 */

abstract class Square {
	protected int value;
    protected Row row;
    protected Collum collum;
    protected Box box;
    Square next;
    SudokuContainer sc = new SudokuContainer();
    private Board myBoard = null;
    SolvedSudoku ss;
    
    Square(int value, Row row, Board b) {
		this.value = value;
		this.row = row;
		myBoard = b;
    }
    
    /**
     * Gets value outside Square
     *
     * @return int value
     */
    public int getValue() {
		return value;
    }

    /**
     * Fills in every possible value in all blank(undefined) squares. 
     * Uses a brute-force algorithm.
     * Stored all solutions in a container.
     * Goes from top-left to bottom-right.
     *
     * @param Board to be solved.
     * @return
     */
    public void fillRemainderOfBoard(Board board) {
		int putNumber = 1;
		int max = board.getSquare()[0][0].row.array.length;
	
		// Puts value in undefined squares.
		if(this instanceof Undefined) {
		    while(putNumber <= max) {
		    	// Check if no other square in row/box/collum has same value.
				if(this.valid(putNumber)) {
				    this.value = putNumber;
				    // Calls next square recursivly if not at last square.
				    if(this.next != null) { 
						this.next.fillRemainderOfBoard(board);
						// Puts solution into container.
				    } else {
						ss = new SolvedSudoku(myBoard.getSquare());
						myBoard.sc.insert(ss.solved);
				    }
				}
				putNumber++;
		    }
		    this.value = 0;
		    // If square is Predefined, jump to next square if any,
		    // else it is done solution and stores board.
		} else if(this instanceof Predefined) {
		    if(this.next != null) {
				this.next.fillRemainderOfBoard(board);
		    } else {
			SolvedSudoku ss = new SolvedSudoku(myBoard.getSquare());
			myBoard.sc.insert(ss.solved);
		    }
		}
    }
    
    /**
     * Checks if a value is already in the
     * row/collum/box, making it illegal.
     *
     * @param value to be checked.
     * @return true if value is valid, 
     *	 	   false if it is in an array.
     */
    private boolean valid(int val) {

	if(this.row.exist(val)) {
	    return false;
	} else if(this.collum.exist(val)) {
	    return false;
	} else if(this.box.exist(val)) {
	    return false;
	}
	return true;
    }
}

/**
 * Initialize Predefined squares.
 * Value is allready known.
 */
class Predefined extends Square {
	
    Predefined(int value, Row row, Board b) {
	    super(value, row, b);
	}
}

/**
 * Initialize Undefined squares.
 * Value is unknown.
 */
class Undefined extends Square {

    Undefined(int value, Row row, Board b) {
		super(value, row, b);
    }
}
