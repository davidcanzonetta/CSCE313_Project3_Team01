package team01;

import javax.swing.*;
import java.awt.event.*;

public class Menu extends JFrame{
	private int col_size;
	private int row_size;
	
	JMenuBar bar=new JMenuBar();
	
	public Menu()
	{
		//super("Menu Example");
		
		JMenu file = new JMenu("File");
		JMenu settings = new JMenu("Settings");
		file.setMnemonic('C');
		JMenu colSubMenu = new JMenu("Column Size");
		JMenu rowSubMenu = new JMenu("Row Size");
		JMenu mode = new JMenu("Mode");
		JMenuItem new_game = new JMenuItem("New Game");
		new_game.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		JMenuItem exit = new JMenuItem("Exit");
		exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
		
		//Column size objects
		JMenuItem col_one = new JMenuItem("1");
		colSubMenu.add(col_one);
		JMenuItem col_three = new JMenuItem("3");
		colSubMenu.add(col_three);
		JMenuItem col_five = new JMenuItem("5");
		colSubMenu.add(col_five);
		JMenuItem col_seven = new JMenuItem("7");
		colSubMenu.add(col_seven);
		JMenuItem col_nine = new JMenuItem("9");
		colSubMenu.add(col_nine);
		JMenuItem col_eleven = new JMenuItem("11");
		colSubMenu.add(col_eleven);
		JMenuItem col_thirteen = new JMenuItem("13");
		colSubMenu.add(col_thirteen);
		
		//Row size objects
		JMenuItem row_one = new JMenuItem("1");
		rowSubMenu.add(row_one);
		JMenuItem row_three = new JMenuItem("3");
		rowSubMenu.add(row_three);
		JMenuItem row_five = new JMenuItem("5");
		rowSubMenu.add(row_five);
		JMenuItem row_seven = new JMenuItem("7");
		rowSubMenu.add(row_seven);
		JMenuItem row_nine = new JMenuItem("9");
		rowSubMenu.add(row_nine);
		JMenuItem row_eleven = new JMenuItem("11");
		rowSubMenu.add(row_eleven);
		JMenuItem row_thirteen = new JMenuItem("13");
		rowSubMenu.add(row_thirteen);
		
		//Mode objects
		JMenuItem one_player = new JMenuItem("One Player");
		mode.add(one_player);
		JMenuItem two_player = new JMenuItem("Two Player");
		mode.add(two_player);
	
		//File layout
		file.add(new_game);
		file.add(exit);
		
		//settings layout
		settings.add(colSubMenu);
		settings.add(rowSubMenu);
		settings.addSeparator();
		settings.add(mode);
		
		exit.addActionListener( new ActionListener(){
					public void actionPerformed(ActionEvent e){ 
						System.exit(0); 
					}
				});
		
		setJMenuBar(bar);
		bar.add(file);
		bar.add(settings);
		
		//getContentPane();
		//setSize(750,45);
		//setVisible(true);
	}
	public JMenuBar get_bar()
	{
		return bar;
	}
	public static void main(String[] args)
	{
		Menu app = new Menu();
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
