/**
 * @author Stian Masserud
 *
 * Class contains boards. 
 * Has a mapping of every square, row and collum
 * in a board. 
 */
class Board {
 
    private Square[][] square;
    private int collumlength;
    private int rowlength;
    private Row[] rows;
    private Collum[] collums;
    SudokuContainer sc = new SudokuContainer();
    Box[] boxes;
    
    Board(int c, int r) {
    	square = new Square[c*r][c*r];
    	rowlength = r;
    	collumlength = c;
    	rows = new Row[c*r];
    	collums = new Collum[c*r];
    	boxes = new Box[c*r];
    }

    /** 
     * Get the container all solutions is stored in.
     * 
     * @return A reference to SudokuContainer, where all
     *		   solutions is stored.
     */
    public SudokuContainer getContainer() {
		return sc;
    }

    /**
     * Prints a board to the terminal.
     * 
     * @return 
     */
    public void printBoard() {
		for(int i = 0; i < square.length; i++) {
		    System.out.println();
		    for(int j = 0; j < square[i].length; j++) {
				if(square[i][j].getValue() < 10){
				    System.out.print(square[i][j].getValue() + "  ");
				} else {
				    System.out.print(square[i][j].getValue() + " ");
				}
		    }
		}
		System.out.println();
	}

	/**
	 * Get a mapping of all squares in the board.
	 * 
	 * @return mapping of all squares.
	 */
    public Square[][] getSquare() {
    	return square;
    }

    /**
     * Get the length of a collum in each box.
     * 
     * @return Length of collum.
     */
    public int getCollumLength() {
		return collumlength;
    }

    /**
     * Get the length of a row in each box.
     * 
     * @return length of row. 
     */
    public int getRowLength() {
		return rowlength;
    }

    /**
     * Puts a square into the the mapping of all squares.
     *
     * @param The indexes the square should be stored on
     *		  and a square to store,
     * @return 
     */
    public void put(int index0, int index1, Square s) {
    	square[index0][index1] = s;
    }
	
	/**
	 * Get all rows of the board.
	 *
	 * @return An array of all rows of the board. 
	 */
	public Row[] getRows() {
		return rows;
	}

	/**
	 * Get all collum of the board.
	 *
	 * @return An array of all the collums of the board.
	 */
	public Collum[] getCollums() {
		return collums;
	}

	/**
	 * Put a box into the mapping of all boxes.
	 *
	 * @param  Box to be stored.
	 * @return
	 */
    public void put(Box b) {
    	for(int i = 0; i < boxes.length; i++) {
    		if(boxes[i] == null) {
    			boxes[i] = b;
    			return;
    		}
    	}
    }

    /**
     * Puts a row into the mapping of all rows.
     * 
     * @param Row to be stored
     * @return
     */
    public void put(Row r) {
    	for(int i = 0; i < rows.length; i++) {
    		if(rows[i] == null) {
    			rows[i] = r;
    			return;
    		}
    	}
    }

    /**
     * Puts a collum into the mapping of all collums
     * 
     * @param Collum to be stored.
     * @return 
     */
    public void put(Collum c) {
    	for(int i = 0; i < collums.length; i++) {
    		if(collums[i] == null) {
    			collums[i] = c;
    			return;
    		}
    	}
    }

    /**
     * Get the value in of a square.
     *
     * @param indexes to locate square.
     * @return number stored on square.
     */
    public int getValue(int i, int j) {
    	return square[i][j].getValue();
    }
}