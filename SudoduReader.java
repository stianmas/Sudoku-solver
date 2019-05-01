/**
 * @author Stian Masserud
 *
 * Main purpose of class is tp fill in a sudokuboard from a text file.
 * Also contains a method to print sudokuboards to terminal screen.
 */
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

class SudokuReader {

    private Board board;
    private Row r = null; 
    private int[] allsquare;
    private int numberOfBoxes = 0;
    private Box[] allBoxes;
    private int collum;
    private int row;
    int g = 0;
    
    /**
     * Get lenght of row
     * @return row
     */
    public int getRow() {
    	return row;
    }

    /**
     * Get length of collum
     * @return collum
     */
    public int getCollum() {
    	return collum;
    }

    /** 
     * Fills in a sudokuboard from a txt-file.
     * Method do not check txt-file format.
     * @param String, Name of file.
     * @return int[][] The fully filled in sudoku board
     */
    public Board readFromFile(File data) {
		
		try {	
		    Scanner sc = new Scanner(data);
		    row = sc.nextInt(); // row only determines length on each box, not complete board. Not to be used unless a box object should declared.
		    collum = sc.nextInt();
		    int counter = 0;
		    int c2 = 0;
		    numberOfBoxes = row*collum;
		    allBoxes = new Box[numberOfBoxes];
		    allsquare = new int[row*collum];
		    board = new Board(collum, row);

		    while(sc.hasNextLine()) {
				String line = sc.nextLine(); 
				char[] tmp = new char[line.length()];
				line.getChars(0, line.length(), tmp, 0);
				int[] fill = new int[tmp.length];

				if(tmp.length != 0) {

				    // Cast tmp array to fill array
				    for(int i = 0; i < tmp.length; i++) {
						fill[i] = Character.getNumericValue(tmp[i]);
			   		}

				    // Change value of all blanks(-1) in fill to 0
				    for(int i = 0; i < fill.length; i++) {
						if(fill[i] == -1) {
						    fill[i] = 0;
						}
			  		}

			 	   	// Makes square objects and puts them in the Board object
				    for(int i = 0; i < fill.length; i++) {
						boolean b = true;
						if(fill[i] != 0) {
						    Predefined p = new Predefined(fill[i], r, board);
						    board.put(counter, i, p);
						} else {
						    Undefined u = new Undefined(fill[i], r, board);
						    board.put(counter, i, u);
						}
				    }
			    	counter++;
			}	
	    } 
	    
	    Box Box = new Box(row*collum);

	    // Starts the make Row sequence
	    for(int i = 0; i < board.getSquare()[0].length; i++) {
			makeRow(row*collum, i);
	    }

	    // Starts the make Collum sequence
	    for(int i = 0; i < board.getSquare()[0].length; i++) {
			makeCollum(row*collum, i);
	    }
	    
	    
	    // Starts the make Box sequence
		for(int i = 0; i < board.getSquare().length; i++) {
			for(int j = 0; j < board.getSquare()[i].length; j++) {
				if(((i+j)) % row == 0 && j % collum == 0){
					makeBox(i,j,row*collum);
				}
			}
		}
		  
		// Gives all square's next reference the next square in board
		// in order to simplify the recursivly brute-force algorithm.  
	    Square current;
	    Square previous = null;
	    for(int i = 0; i < board.getSquare().length; i++) {
			for(int j = 0; j < board.getSquare().length; j++) {
			    current = board.getSquare()[i][j];
			    if(previous == null) {
					previous = current;
			    } else {
					previous.next = current;
					previous = current;
			    }
			} 
	    }
	    sc.close();
	    return board;

		} catch (FileNotFoundException e){
		    e.printStackTrace();
		    return null;	
		} catch (IndexOutOfBoundsException e) {
		    System.out.println(e.getStackTrace());
		    return null;
		}
    }
    
    /**
     * Makes boxes and put it into belonging square object
     * @param The size of box.
     *		   A place to start in array
     * @return
     */   
    private void makeBox(int r, int col, int size) {
		// Find the top left of its 3x3 box to start validating from
		int startRow = r / row * row;
		int startCol = col / collum * collum;
		Square[] s = new Square[size];
		int counter = 0;
		Box b = new Box(size);
		b.array = s;
		
		for (int i = startRow; i < startRow + row ; i++) {
		    for (int j = startCol; j < startCol + collum; j++) {
				s[counter] = board.getSquare()[i][j];
				counter++;
		    }
		}

	   	for (int i = startRow; i < startRow + row; i++) {
		    for (int j = startCol; j < startCol + collum; j++) {
				board.getSquare()[i][j].box = b;
		    }
		}
		g++;
		board.put(b);
    }
    
    /**
     * Makes collum and put them into belonging square objects
     * and board.
     *
     * @param collum in sodukoboard
     * @return
     */
    private void makeCollum(int length, int current) {
		Collum c = new Collum(length);

		// Finds all squares in a collum and makes a object of it
		for(int i = 0; i < c.array.length; i++) {
		    c.array[i] = board.getSquare()[i][current];
		}

		// Puts Collum into appropiate Square objects
		for(int i = 0; i < c.array.length; i++) {
		    board.getSquare()[i][current].collum = c;
		}

		board.put(c);
    }

    /**
     * Makes rows and put them into belonging square objects
     * and board.
     *
     * @param row in sudokuboard
     * @return
     */
    private void makeRow(int length, int current){
		Row r = new Row(length);

		// Puts belonging squares into a Row-object
		for(int i = 0; i < r.array.length; i++) {
		    r.array[i] = board.getSquare()[current][i];
		}
		
		// Puts Row into appropriate Square object
		for(int i = 0; i < r.array.length; i++) {
		    board.getSquare()[current][i].row = r;
		}

		board.put(r);
    }

    /**
     * Prints sudokuboard to terminal screen
     *
     * @param int[][], sudokuboard to be printed
     * @return
     */
    public void printBoard(Board sudoku) {
		for(int i = 0; i < sudoku.getSquare().length; i++) {
		    System.out.println();
		    for(int j = 0; j < sudoku.getSquare()[i].length; j++) {
				if(sudoku.getSquare()[i][j].getValue() < 10){
				    System.out.print(sudoku.getSquare()[i][j].getValue() + "  ");
				} else {
				    System.out.print(sudoku.getSquare()[i][j].getValue() + " ");
				}
	    	}
		}
		System.out.println();
    }
}
