/**
 * @author Stian Masserud
 *
 * Class cotaining all gui-related thing.
 * Draws the sudokuboard using Swing/awt and 
 * handles action from buttons.
 * Class has one bug.  I did not manage to clean out previuos
 * gridlayout and repaint it with new board. So i attempt to close previous frame 
 * and open a new one, but previous frame won't always close even though 
 * supposedly adequate method is called.
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
	
class SudokuGui {

    private SudokuContainer sc;
    private JPanel outerPanel;
    private JFrame frame;
    private JButton button;
    private int[][] board;
    private int counter;
    private int solutionPrinted = 0;
    private JPanel jpanel;;
    private JTextField[][] panelHolder;
    private Integer[] startRow;
    private Integer[] startCollum;
    private int size1;
    private int size2;
    private JButton button2;
    private JPanel buttonPanel;
    private JTextField numberOfSolutions;
    private ActionButt action;
    private int numbersolve = 0;
    private int printedBoard = 0;
    private String title = "Sudoku Solver 5000 GT 764++ Gaustad";
    private boolean oneTimer = true;

    // size1 = row
    // size2 = collum
    SudokuGui(int size1, int size2, SudokuContainer sc) {
		jpanel = new JPanel();
	   	this.size1 = size1;	
	   	this.size2 = size2;
		this.sc = sc;
		action = new ActionButt();
	    numbersolve = sc.getSolutionCount(); 
		panelHolder = new JTextField[size1*size2][size1*size2];
		numberOfSolutions = new JTextField(" Solutions: " +numbersolve);
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		button2 = new JButton("Solve from new .txt");
		button = new JButton("Next Solution");
		outerPanel = new JPanel();
		outerPanel.setLayout(new BorderLayout());
		frame = new JFrame(title);
		jpanel.setLayout(new GridLayout(size1*size2, size1*size2));
		frame.setSize(650, 650);
		frame.setLocationRelativeTo(null);
		numberOfSolutions.setEditable(false);

		// Puts values from sudokuboard to 
		if(sc.getFirst() != null) {
		    board = sc.getFirst();
    		for(int i = 0; i < panelHolder.length; i++) {
			    for(int j = 0; j < panelHolder[i].length; j++) {
					panelHolder[i][j] = new JTextField(""+board[i][j]);
					panelHolder[i][j].setHorizontalAlignment(JTextField.CENTER);
					panelHolder[i][j].setEditable(false);
					jpanel.add(panelHolder[i][j]);
		    	}
			}
		}

		// Adds to panels/frame and sets it visible
		buttonPanel.add(button);
		buttonPanel.add(numberOfSolutions);
		buttonPanel.add(button2);
		button.addActionListener(action);
		button2.addActionListener(action);
		outerPanel.add(jpanel, BorderLayout.CENTER);
		outerPanel.add(buttonPanel, BorderLayout.NORTH);
		frame.add(outerPanel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);	
	    }
	/**
	 * Sets how many solutiions this soduko has
	 * 
	 * @return
	 */
    public void setCount() {
		numberOfSolutions = new JTextField(" Solutions: " + printedBoard + "/" + numbersolve);
    }

    /**
     * Clear previous board
     *
     * @param JFrame that contains board to be removed
     */
    public void cleanBoard(JFrame f) {
    	if(oneTimer) {
    		frame.dispose();
    		frame.setVisible(false);

    		oneTimer = false;
    	} else {
    		f.dispose();
    		f.setVisible(false);

		}
    }
    
    /**
     * Adds value from sudokuboard and puts it into
     * a gfridlayout. Puts gridlayout into a frame filechooser
     * and next-solution-button. Makes frame visible to user.
     *
     * @param JFrame to put gridlayout and buttons
     * @return
     */
    public void drawBoard(JFrame frame1) {
    	JTextField[][] text = new JTextField[size1*size2][size1*size2];
    	JPanel newPanel = new JPanel();
    	JPanel pane = new JPanel();
    	frame1.setSize(650,650);
    	frame1.setLocationRelativeTo(null);
    	newPanel.setLayout(new BorderLayout());
		pane.setLayout(new GridLayout(size1*size2, size1*size2));

		for(int i = 0; i < text.length; i++) {
		    for(int j = 0; j < text[i].length; j++) {
				text[i][j] = new JTextField(""+board[i][j]);
				text[i][j].setHorizontalAlignment(JTextField.CENTER);
				text[i][j].setEditable(false);
				pane.add(text[i][j]);
		    }
		}
		newPanel.add(pane, BorderLayout.CENTER);
		newPanel.add(buttonPanel, BorderLayout.NORTH);
		frame1.add(newPanel);
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame1.setVisible(true);
		printedBoard++;
		
    }
	 /**
	  * This class handles actions from both buttons
	  */  
    class ActionButt implements ActionListener {
	
		/**
		 * Handles action based on which button which was pushed.
		 * Either displays next board or start solving a  new sudoku.
		 *
		 * @param Adress of pushed button
		 * @return
		 */
		public void actionPerformed(ActionEvent e) {
		    
		    // Display next solution if any.
		    if(e.getSource() == button){
				if(printedBoard < numbersolve) {
				    board = sc.get(printedBoard);
				    if(board!= null) {
				    	frame.remove(outerPanel);
				    	JFrame frame1 = new JFrame(title);
				    	cleanBoard(frame1);	
						setCount();
						drawBoard(frame1);
				    }
				}
				// Gets a new board and starts solving it.
				// Open new Gui-thread on completion.
		    } else if(e.getSource() == button2){
				JFileChooser jfc = new JFileChooser(System.getProperty("user.dir"));
				jfc.setDialogTitle("Choose a txt-file");
				int returnVal = jfc.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					frame.setVisible(false);
    				frame.dispose();
				    File file = jfc.getSelectedFile();
				    SudokuReader sr = new SudokuReader();
					Board s121 = sr.readFromFile(file);
					s121.getSquare()[0][0].fillRemainderOfBoard(s121);
					final SudokuContainer sc = s121.getContainer();
					final int row = sr.getRow();
					final int collum = sr.getCollum(); 
				
					SwingUtilities.invokeLater
					    (    new Runnable() {
							    public void run() {
							new SudokuGui(row,collum, sc);
						    } 
						}  
					); 
				}
		    }
		}
	}
}