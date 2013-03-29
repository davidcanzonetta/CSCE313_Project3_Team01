package team01;

//import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
//import java.util.ArrayList;

public class GUI extends JPanel{
	//private static final long serialVersionUID = 7526472295622776147L;  // unique id
	/**
	 * @param args
	 */
	//static int moves = 0; //Keep track of number of game moves
	
	
	private JButton newGameButton;  // Button to start a new game
	private JButton resignButton;   // Button that a player can use to end game
	private JButton col_size1 = new JButton("1");
	private JButton col_size3 = new JButton("3");
	private JButton col_size5 = new JButton("5");
	private JButton col_size7= new JButton("7");
	private JButton col_size9 = new JButton("9");
	private JButton col_size11 = new JButton("11");
	private JButton col_size13 = new JButton("13");
	private JButton row_size1 = new JButton("1");
	private JButton row_size3 = new JButton("3");
	private JButton row_size5 = new JButton("5");
	private JButton row_size7 = new JButton("7");
	private JButton row_size9 = new JButton("9");
	private JButton row_size11 = new JButton("11");
	private JButton row_size13 = new JButton("13");
	private JButton proceed = new JButton("Continue");
	                                  
	   
    private JLabel message;  // Label for displaying messages to the user
	
	public static void main(String[] args) {
		JFrame window = new JFrame("Fanorona");
		GUI content = new GUI();
	    window.setContentPane(content);
	    window.pack();
	    Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
	    window.setLocation( (screensize.width - window.getWidth())/2,
	            (screensize.height - window.getHeight())/2 );
	    window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	    window.setResizable(false);  
	    window.setVisible(true);	
	}
	
	public GUI() {
		setLayout(null);
		setPreferredSize(new Dimension(750, 500)); //width, height
		setBackground(new Color(105,139,34));  // Olive background
	    
		//Create Components
	     GUIBoard guiBoard = new GUIBoard(); //Creates buttons and labels etc.
	     add(guiBoard);
	     add(newGameButton);
	     add(resignButton);
	     add(message);
	     add(col_size1);
	     add(col_size3);
	     add(col_size5);
	     add(col_size7);
	     add(col_size9);
	     add(col_size11);
	     add(col_size13);
	     add(row_size1);
	     add(row_size3);
	     add(row_size5);
	     add(row_size7);
	     add(row_size9);
	     add(row_size11);
	     add(row_size13);
	     add(proceed);
	     
	     //initially you cannot see the newGame and resign buttons
	     newGameButton.setVisible(false);
	     resignButton.setVisible(false);
	     
	     //Set positions and sizes of components
	     guiBoard.setBounds(55,55,640,340); //x, y, width, height
	     newGameButton.setBounds(170, 450, 120, 30);
	     resignButton.setBounds(470, 450, 120, 30);
	     message.setBounds(0, 400, 600, 30);
	     col_size1.setBounds(40, 470, 30, 30);
	     col_size1.setBorder(null);
	     col_size3.setBounds(70, 470, 30, 30);
	     col_size3.setBorder(null);
	     col_size5.setBounds(100, 470, 30, 30);
	     col_size5.setBorder(null);
	     col_size7.setBounds(130, 470, 30, 30);
	     col_size7.setBorder(null);
	     col_size9.setBounds(160, 470, 30, 30);
	     col_size9.setBorder(null);
	     col_size11.setBounds(190, 470, 30, 30);
	     col_size11.setBorder(null);
	     col_size13.setBounds(220, 470, 30, 30);
	     col_size13.setBorder(null);
	     row_size1.setBounds(500, 470, 30, 30);
	     row_size1.setBorder(null);
	     row_size3.setBounds(530, 470, 30, 30);
	     row_size3.setBorder(null);
	     row_size5.setBounds(560, 470, 30, 30);
	     row_size5.setBorder(null);
	     row_size7.setBounds(590, 470, 30, 30);
	     row_size7.setBorder(null);
	     row_size9.setBounds(620, 470, 30, 30);
	     row_size9.setBorder(null);
	     row_size11.setBounds(650, 470, 30, 30);
	     row_size11.setBorder(null);
	     row_size13.setBounds(680, 470, 30, 30);
	     row_size13.setBorder(null);
	     proceed.setBounds(310, 470, 100, 30);
	}
	
	private class GUIBoard extends JPanel implements ActionListener, MouseListener, KeyListener{//, UserInput {
		Color olive = new Color(105, 139, 34);
		private static final long serialVersionUID = 7;  // unique id
		Board board;
		boolean gameInProgress, click = false;
		boolean menu = true;
		int currentPlayer;
		Point selectedPiece;
		//Pair[] legalMoves; //An array containing pairs of legal moves
		int col_space; //Space between columns on the board
	    int row_space; //Space between rows on the board
	    int chosen_num_col=9, chosen_num_row=5; //default values are 9x5 board
	    Game fanorona;
	    
	    
		GUIBoard() {
			setBackground(Color.BLACK);
			addMouseListener(this);
			addKeyListener(this);
			
	        resignButton = new JButton("Resign");
	        resignButton.addActionListener(this);
	        newGameButton = new JButton("New Game");
	        newGameButton.addActionListener(this);
	        
	        col_size1.addActionListener(this);
	        col_size3.addActionListener(this);
	        col_size5.addActionListener(this);
	        col_size7.addActionListener(this);
	        col_size9.addActionListener(this);
	        col_size11.addActionListener(this);
	        col_size13.addActionListener(this);
	        
	        row_size1.addActionListener(this);
	        row_size3.addActionListener(this);
	        row_size5.addActionListener(this);
	        row_size7.addActionListener(this);
	        row_size9.addActionListener(this);
	        row_size11.addActionListener(this);
	        row_size13.addActionListener(this);
	        
	        proceed = new JButton("Continue");
	        proceed.addActionListener(this);
	        
	        message = new JLabel("",JLabel.CENTER);
	        message.setFont(new  Font("Serif", Font.BOLD, 14));
	        message.setForeground(Color.white);
	        
//	        fanorona.play();
//	        board = fanorona.getBoard();
//			doNewGame();
	        
	        //Game fanorona = new Game(chosen_num_row, chosen_num_col);
		}
		
		public void actionPerformed(ActionEvent evt) {
	         Object src = evt.getSource();
	         if (src == newGameButton)
	            doNewGame();
	         else if (src == resignButton)
	            doResign();
	         else if (src == col_size1)
	        	 chosen_num_col = 1;
	         else if (src == col_size3)
	        	 chosen_num_col = 3;
	         else if (src == col_size5)
	        	 chosen_num_col = 5;
	         else if (src == col_size7)
	        	 chosen_num_col = 7;
	         else if (src == col_size9)
	        	 chosen_num_col = 9;
	         else if (src == col_size11)
	        	 chosen_num_col = 11;
	         else if (src == col_size13)
	        	 chosen_num_col = 13;
	         else if (src == row_size1)
	        	 chosen_num_row = 1;
	         else if (src == row_size3)
	        	 chosen_num_row = 3;
	         else if (src == row_size5)
	        	 chosen_num_row = 5;
	         else if (src == row_size7)
	        	 chosen_num_row = 7;
		     else if (src == row_size9)
		    	 chosen_num_row = 9;
		     else if (src == row_size11)
		    	 chosen_num_row = 11;
		     else if (src == row_size13)
		    	 chosen_num_row = 13;
		     else if(src == proceed)
		     {
		    	 menu = false;
		    	 newGameButton.setVisible(true);
			     resignButton.setVisible(true);
			     
			     newGameButton.setEnabled(false);
		         resignButton.setEnabled(true);
			     
			     col_size1.setVisible(false);
			     col_size3.setVisible(false);
			     col_size5.setVisible(false);
			     col_size7.setVisible(false);
			     col_size9.setVisible(false);
			     col_size11.setVisible(false);
			     col_size13.setVisible(false);
			     row_size1.setVisible(false);
			     row_size3.setVisible(false);
			     row_size5.setVisible(false);
			     row_size7.setVisible(false);
			     row_size9.setVisible(false);
			     row_size11.setVisible(false);
			     row_size13.setVisible(false);
			     proceed.setVisible(false);
			     
		    	 repaint();
		    	 doNewGame();
		     }
		}
		
		void doNewGame() {
			
			 currentPlayer = Board.WHITE;
	         selectedPiece = new Point(-1,-1);   // Set selected piece to none i.e. -1, -1
	         //message.setText("WHITE:  Make your move.");
	         gameInProgress = true;
	         newGameButton.setEnabled(false);
	         resignButton.setEnabled(true);
	       /*
	         Game fanorona = new Game();
	         fanorona.play();
	         board = fanorona.getBoard();*/
	     //    repaint(); //Is giving weird duplication 
		 }
		 
		 void doResign() {
	         if (currentPlayer == Board.WHITE)
	            gameOver("WHITE resigns.  BLACK wins.");
	         else
	            gameOver("BLACK resigns.  WHITE wins.");
	     }
		 
		 void gameOver(String str) {
	         message.setText(str);
	         newGameButton.setEnabled(true);
	         resignButton.setEnabled(false);
	         gameInProgress = false;
	      }
		 
		 //This doesn't seem to work
		 public Point getPoint() {
			 return selectedPiece;
		 }  // end doClickSquare()
	
		 //This works
		 public void paintComponent(Graphics g) {
			 board = new Board(chosen_num_col, chosen_num_row);
			 //Draw border
			 if(!menu)
			 {
				 g.setColor(Color.white);
				 g.drawRect(0,0,getSize().width-1,getSize().height-1);
				 g.drawRect(1,1,getSize().width-3,getSize().height-3);
				 g.setColor(Color.LIGHT_GRAY);
				 g.drawLine(40, 40, 40, 300); //First col
				 g.drawLine(40, 40, 600, 40); //First row
				 col_space = (640 - 80)/(board.getWidth()-1);
				 row_space = (340 - 80)/(board.getHeight()-1);
				 //Draw the 4 partial diagonal lines
				 g.drawLine(40,340/2,col_space*2+40, 300);
				 g.drawLine((board.getWidth()-3)*col_space+40, 40, 600, 340/2);
				 g.drawLine(40,340/2,col_space*2+40, 40);
				 g.drawLine(600, 340/2, (board.getWidth()-3)*col_space+40, 300);
				 //Draw empty board
				 for (int row = 0; row < board.getHeight(); row++) {
					 for (int col = 0; col < board.getWidth(); col++) {
						 g.setColor(Color.LIGHT_GRAY);
						 if (row != 0 && col != 0) {
							 g.drawLine(col*col_space+40, 40, col*col_space+40, 300); //Draw rest of cols
							 g.drawLine(40, row*row_space+40, 600, row*row_space+40); //Draw rest of rows
						 }
						 if (col % 2 == 0 && col != board.getWidth()-1 && col != board.getWidth()-3) {
							 g.drawLine(40+col_space*col, 40, (640/2) + col_space*col, 300); //Draw full diagonal lines
							 g.drawLine(40+col_space*col, 300, (640/2) + col_space*col, 40);
						 }
					 }
				 }
				 //Draw game peices
				 for (int row = 1; row <= board.getHeight(); row++) {
					 for (int col = 1; col <= board.getWidth(); col++) {
						 Point p = new Point(col, row);
						 switch (board.getPoint(p)) {
						 case Board.BLACK:
							 g.setColor(Color.BLACK);
							 if(col == 1 && row == 1) {
								 g.fillOval(25, 25, 35, 35);
							 }
							 else {
								 g.fillOval(25+(col-1)*col_space, 25+(chosen_num_row-row)*row_space, 35, 35);
                	   
							 }
							 break;
                   
						 case Board.WHITE:
							 g.setColor(Color.WHITE);
							 if(col == 1 && row == 5) {
								 g.fillOval(25, 25, 35, 35);
							 }
							 else {
								 g.fillOval(25+(col-1)*col_space, 25+(chosen_num_row-row)*row_space, 35, 35);
							 }
							 break;
						 case Board.WHITE_GRAY:
						 case Board.BLACK_GRAY:
							 g.setColor(Color.GRAY);
							 if(col == 1 && row == 5) {
								 g.fillOval(25, 25, 35, 35);
							 }
							 else {
								 g.fillOval(25+(col-1)*col_space, 25+(chosen_num_row-row)*row_space, 35, 35);
							 }
							 break;
						 } 
					 }
				 }
				 //Highlight all possible moves/movable pieces
				//Draw a cyan border around all movable pieces
	            g.setColor(Color.cyan);
	            for (int i = 0; i < fanorona.isClickable.size(); i++) {
	            	 g.fillOval(25+(fanorona.isClickable.get(i).getX()-1)*col_space, 25+(chosen_num_row-fanorona.isClickable.get(i).getY())*row_space, 35, 35);
	            }
	            /*If a piece has been selected for movement, draw a white border around selected piece, and draw
	              green border around each space that can be moved to 
	            */
	            if (selectedPiece.getX() != -1) { //If there is a selected piece
	               g.setColor(Color.white);
	               g.drawRect(2 + selectedPiece.getX()*20, 2 + selectedPiece.getY()*20, 19, 19);
	               g.setColor(Color.green);
	               for (int i = 0; i < fanorona.isClickable.size(); i++) {
	            	   g.fillOval(25+(fanorona.isClickable.get(i).getX()-1)*col_space, 25+(chosen_num_row-fanorona.isClickable.get(i).getY())*row_space, 35, 35);
	               }
               }
			} //end if(!menu)
		 } //end paintComponent
	
		 //This works
		 public void mousePressed(MouseEvent evt) {
			 if (gameInProgress == false)
				 message.setText("Click the new game button to start a new game.");
			 else {
				 if(!menu)
				 {
					 int x=-1, y=-1;
					 int orig_x = evt.getX()-40;
					 for(int i = 0; i <= board.getWidth(); i++) {
						 if(orig_x >= i*col_space  && orig_x <= (i+1)*col_space ) {
							 x = i+1;
						 }
					 }	
					 int orig_y = evt.getY()-40;
					 for(int i = 0; i <= board.getHeight(); i++) {
						 if(orig_y >= (i-1)*row_space  && orig_y <= (i)*row_space ) {
							 //converts clicked y value to grid y value
							 y = 5-i;
						 }
					 }
					 System.out.print("OX: "+orig_x+" OY: "+orig_y +"\n");
					 System.out.print("X: "+x+" Y: "+y +"\n");
					 //TODO: Don't forget to convert 5-i when passing arguments to game
					 Point p = new Point(x, y);
					 selectedPiece = p;
					 click = true;
				 }
			 }
		 }
		 public void mouseReleased(MouseEvent evt) { }
		 public void mouseClicked(MouseEvent evt) {mousePressed(evt);}
		 public void mouseEntered(MouseEvent evt) { }
		 public void mouseExited(MouseEvent evt) { }
		 public void keyPressed(KeyEvent event)
		 {
			switch (event.getKeyCode())
			{						
				case KeyEvent.VK_ESCAPE: System.exit(0);
						                 break;                         
			}	
		 }	
		 public void keyReleased(KeyEvent event){}
		 public void keyTyped(KeyEvent event){}
	} //End GUIBoard Class
}
