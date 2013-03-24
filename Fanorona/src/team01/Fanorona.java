package team01;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class Fanorona extends JPanel{

	/**
	 * @param args
	 */
	static int moves = 0; //Keep track of number of game moves
	
	
	private JButton newGameButton;  // Button to start a new game
	private JButton resignButton;   // Button that a player can use to end game
	                                  
	   
    private JLabel message;  // Label for displaying messages to the user
	
	public static void main(String[] args) {
		JFrame window = new JFrame("Fanorona");
		Fanorona content = new Fanorona();
	    window.setContentPane(content);
	    window.pack();
	    Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
	    window.setLocation( (screensize.width - window.getWidth())/2,
	            (screensize.height - window.getHeight())/2 );
	    window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	    window.setResizable(false);  
	    window.setVisible(true);	
		
	}
	
	public Fanorona() {
		Board board = new Board(9,5);
		setLayout(null);
		setPreferredSize(new Dimension(750, 500)); //width, height
		setBackground(new Color(105,139,34));  // Olive background
	    
		//Create Components
	      FanoronaBoard fanoronaBoard = new FanoronaBoard(); //Creates buttons and labels etc.
	      add(fanoronaBoard);
	      add(newGameButton);
	      add(resignButton);
	      add(message);
	      
	      
	      //Set positions and sizes of components
	      fanoronaBoard.setBounds(55,55,640,340); //x, y, width, height
	      newGameButton.setBounds(200, 450, 120, 30);
	      resignButton.setBounds(500, 450, 120, 30);
	      message.setBounds(0, 400, 600, 30);
	}
	
	private class FanoronaBoard extends JPanel implements ActionListener, MouseListener, KeyListener {
		Board board;
		boolean gameInProgress;
		boolean pieceSelect = true;
		int currentPlayer;
		Point selectedPiece, selectedSpace;
		Pair[] legalMoves; //An array containing pairs of legal moves
		int col_space;
	    int row_space;
		
		FanoronaBoard() {
			setBackground(Color.BLACK);
			addMouseListener(this);
	        resignButton = new JButton("Resign");
	        resignButton.addActionListener(this);
	        newGameButton = new JButton("New Game");
	        newGameButton.addActionListener(this);
	        message = new JLabel("",JLabel.CENTER);
	        message.setFont(new  Font("Serif", Font.BOLD, 14));
	        message.setForeground(Color.white);
	        board = new Board(9, 5);
	        doNewGame();
	        }
		
		public void actionPerformed(ActionEvent evt) {
			Object src = evt.getSource();
			if (src == newGameButton)
				doNewGame();
			else if (src == resignButton)
				doResign();	
	    }
		void doNewGame() {
	         currentPlayer = Board.WHITE;   // White moves first
	         legalMoves = getLegalMoves(Board.WHITE, board);  // Get White's legal moves.
	         selectedPiece = new Point(-1,-1);   // Set selected piece to none i.e. -1, -1
	         message.setText("WHITE:  Make your move.");
	         gameInProgress = true;
	         newGameButton.setEnabled(false);
	         resignButton.setEnabled(true);
	     //    repaint(); //Is giving weird duplication 
	      }
		 
		 public boolean move_function(int x1, int y1, int x2, int y2) {
				boolean done = true;
				Move m = new Move(board);
				Point from = new Point(x1, y1);
				Point to = new Point(x2, y2);
				if(m.isValidMove(from, to))
				{
					// TODO: handle case where approach and withdraw are possible
					done = m.capture(from, to, m.hasCapture(from, to, true));
					//if move_done == false, no more captures
					message.setText("Valid Move");
					moves++;
				}
				else
				{
					message.setText("Invalid Move");
				}
				return done;
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
		 
		 //Returns a list of legal move pairs for a given player haven't checked validity borrowed from Hayden
		 public Pair[] getLegalMoves (int player, Board passed_board) {
			 Board board = new Board(passed_board);
			 Move move = new Move(board);
			 Pair[] legalMoves = new Pair[100];
			 for (int y1 = 0; y1 < board.getHeight(); y1++)
			 {
			         for (int x1 = 0; x1 < board.getWidth(); x1++)
			         {
			        	 	Point from = new Point(x1,y1);
			        	 	if(player == Board.WHITE) {
				                 if (board.isWhite(from))
				                 {
				                         for (int dx = -1; dx <= 1; dx++)
				                         {
				                                 for (int dy = -1; dy <= 1; dy++)
				                                 {
				                                	 	Point to = new Point(x1+dx, y1+dy);
				                                         if (move.isValidMove(from, to)) {
				                                     
				                                                 Pair pair = new Pair(from,to);
				                                                 Arrays.fill(legalMoves, pair);
				                                         }
				                                 }
				                         }
				                 }
			        	 	} else if (player == Board.BLACK) {
					                 if (board.isWhite(from))
					                 {
					                         for (int dx = -1; dx <= 1; dx++)
					                         {
					                                 for (int dy = -1; dy <= 1; dy++)
					                                 {
					                                	 	Point to = new Point(x1+dx, y1+dy);
					                                         if (move.isValidMove(from, to))
					                                         {

				                                                 Pair pair = new Pair(from,to);
				                                                 Arrays.fill(legalMoves, pair);
					                                         }
					                                 }
					                         }
					                 }
			        	 	}
			         }
			 }
			 return legalMoves;
			
		 }
		
	void doClickSpace(Point p) 
	{
		Move move = new Move(board);
		boolean m = true;
		selectedSpace = p;
		
		message.setText("You have picked piece (" + p.x + ", " + p.y + ")" );
		System.out.println("You have picked piece (" + p.x+ ", "+ p.y + ")");
		
		while(m)
		{
			if (! move.isValidMove(selectedPiece, selectedSpace))
			{
				System.out.println(selectedPiece + " -> " + selectedSpace + " is not a valid move!");
				message.setText(selectedPiece + " -> " + selectedSpace + " is not a valid move! Try Again.");
				System.out.println("Try again");
				m = false;
			}
			
			if(m)
				++moves;
			
			// must have m true or it will always delete piece clicked
			if (m && ! move.capture(selectedPiece, selectedSpace, move.hasCapture(selectedPiece, selectedSpace, true)))
			{
			// no more captures
				System.out.println("Here");
				m = false;
			}
			System.out.println(board);
			
//			repaint();

		}
		pieceSelect = true;
		
	}
	void doClickPiece(Point p) 
	{	
		selectedPiece = p;
		
		message.setText("You have picked piece (" + p.x + ", " + p.y +"). Select a space to move to." );
		System.out.println("You have picked piece (" + p.x + ", " + p.y + "). Select a space to move to.");
		 
		pieceSelect = false;
		 /*Check to see if any legal moves can be made from clicked piece
		  if so make the clicked piece their selected piece
		
		 
        for (int i = 0; i < legalMoves.length; i++) {
           if (legalMoves[i].from  == p) {
              selectedPiece = p;
              if (currentPlayer == Board.WHITE)
                 message.setText("WHITE:  Make your move.");
              else
                 message.setText("BLACK:  Make your move.");
              repaint();
              return;
           }
        }  
       //If piece has no legal moves prompt them to pick another
        if(selectedPiece.x == -1) {
        	message.setText("Pick a piece to move.");
        
        }
        
    
        //If user clicks a legal move square make the move and return 
        for (int i = 0; i < legalMoves.length; i++) {
           if (legalMoves[i].from  == selectedPiece && legalMoves[i].to == p) { 
              move_function(legalMoves[i]);
              return;
           } else {
        	   message.setText("You cannot move there!");
           }
        }  
          
        
        //If we haven't returned they haven't clicked a valid spot
        message.setText("Click the spot you want to move to.");
        */
     }  // end doClickSquare()
	//This works
	public void paintComponent(Graphics g) {
		 //Draw border
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
         for (int row = 0; row < board.getHeight(); row++) {
             for (int col = 0; col < board.getWidth(); col++) {
                Point p = new Point(col, row);
                switch (board.getPosition(p)) {
                case Board.WHITE:
                   g.setColor(Color.WHITE);
                   if(col == 0 && row == 0) {
                	   g.fillOval(25, 25, 35, 35);
                   }
                   else {
                	   g.fillOval(25+col*col_space, 25+row*row_space, 35, 35);
                	   
                   }
                   break;
                   
                case Board.BLACK:
                   g.setColor(Color.BLACK);
                   if(col == 0 && row == 0) {
                	   g.fillOval(25, 25, 35, 35);
                   }
                   else {
                	   g.fillOval(25+col*col_space, 25+row*row_space, 35, 35);
                   }
                   break;
                }
             }
         }
	 } //end paintComponent
	
	 //This works
	 public void mousePressed(MouseEvent evt) {}
	 public void mouseReleased(MouseEvent evt) {}
     public void mouseClicked(MouseEvent evt) {
    	 if (gameInProgress == false)
             message.setText("Click the new game button to start a new game.");
          else {
         	int x=-1, y=-1;
             int orig_x = evt.getX()-40;
             for(int i = 0; i < board.getWidth(); i++) {
 	            if(orig_x >= i*col_space  && orig_x <= (i+1)*col_space ) {
 	            	x = i;
 	            }
             }
             int orig_y = evt.getY()-40;
             for(int i = 0; i < board.getHeight(); i++) {
             	if(orig_y >= i*row_space  && orig_y <= (i+1)*row_space ) {
 	            	y = i;
 	            }
             }
             System.out.print("OX: "+orig_x+"OY: "+orig_y +"\n");
             System.out.print("X: "+x+"Y: "+y +"\n");
             Point p = new Point(x, y);
             if(!pieceSelect)
             	doClickSpace(p);
             else
             	doClickPiece(p);
          }
     }
     public void mouseEntered(MouseEvent evt) { }
     public void mouseExited(MouseEvent evt) { }
     public void keyPressed(KeyEvent event)
 	 {
 		switch (event.getKeyCode())
 		{
 				/*
 				case KeyEvent.VK_UP:          
 									break;
 														
 				case KeyEvent.VK_DOWN:	 
                                       break;
                                                   		
 				case KeyEvent.VK_LEFT: 
 							          break;			          
 							            
 				case KeyEvent.VK_RIGHT:
 									   break;  
 										
 				case KeyEvent.VK_ENTER: 				
 										break;
 				*/						
 				case KeyEvent.VK_ESCAPE:
 				                         System.exit(0);
 				                         break;
 				                         
 				/*
 				case KeyEvent.VK_F5:
 				                         break; 
 				 */                        
 		}	
 	}	
 	public void keyReleased(KeyEvent event) {}
 	public void keyTyped(KeyEvent event) {}
	
	} //End FanoronaBoard Class

	private static String playerIdToString(int player)
	{
		if (player == Board.WHITE)
		{
			return "white";
		}
		
		return "black";
	}
	

	//Returns FALSE if maximum number of moves has been exceeded
	private static boolean maxMoves()
	{
		if(moves >= 50)
		{
			System.out.println("Maximum moves exceeded");
			return false;
		}
		else
			return true;
	}
}
