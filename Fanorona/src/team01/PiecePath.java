package team01;

import java.util.*;

//Fanorona Piece Moving Restrictions:
//1. You cannot return to any space twice in one turn
//2. You cannot move in the same direction twice in a row

//Space movements
//-2 1 4
//-3 0 3
//-4 -1 2

public class PiecePath
{
	//For orientation purposes, up is toward the top of the screen, down is toward
	//the bottom of the screen, regardless of color. Example: Black pieces moving
	//backward are moving "up", while white pieces moving backward are moving "down"
	ArrayList<Integer> spaces=new ArrayList<Integer>();
	private int space_count;
	private boolean up;
	private boolean down;
	private boolean left;
	private boolean right;
	private boolean diag_UpLeft;
	private boolean diag_UpRight;
	private boolean diag_DownLeft;
	private boolean diag_DownRight;
	
	public PiecePath()
	{
		space_count = 0;
		spaces.add(space_count);
		
		up = false;
		down = false;
		left = false;
		right = false;
		diag_UpLeft = false;
		diag_UpRight = false;
		diag_DownLeft= false;
		diag_DownRight = false;
	}
	//Returns true if move can be made
	//TODO: Try to optimize these into one function
	public boolean request_move_up()
	{
		if(is_valid_space(1) == true && up == false){
			set_everything_false();
			up = true;
			return true;
		}
		else
			return false;
	}
	public boolean request_move_down()
	{
		if(is_valid_space(-1) == true && down == false){
			set_everything_false();
			down = true;
			return true;
		}
		else
			return false;
	}
	public boolean request_move_left()
	{
		if(is_valid_space(-3) == true && left == false){
			set_everything_false();
			left = true;
			return true;
		}
		else
			return false;
	}
	public boolean request_move_right()
	{
		if(is_valid_space(3) == true && right == false){
			set_everything_false();
			right = true;
			return true;
		}
		else
			return false;
	}
	public boolean request_move_diag_UpLeft()
	{
		if(is_valid_space(-2) == true && diag_UpLeft == false){
			set_everything_false();
			diag_UpLeft = true;
			return true;
		}
		else
			return false;
	}
	public boolean request_move_diag_UpRight()
	{
		if(is_valid_space(4) == true && diag_UpRight == false){
			set_everything_false();
			diag_UpRight = true;
			return true;
		}
		else
			return false;
	}
	public boolean request_move_diag_DownLeft()
	{
		if(is_valid_space(-4) == true && diag_DownLeft == false){
			set_everything_false();
			diag_DownLeft = true;
			return true;
		}
		else
			return false;
	}
	public boolean request_move_diag_DownRight()
	{
		if(is_valid_space(2) == true && diag_DownRight == false){
			set_everything_false();
			diag_DownRight = true;
			return true;
		}
		else
			return false;
	}
	//This resets every value so only most recent move is set to true
	private void set_everything_false()
	{
		up = false;
		down = false;
		left = false;
		right = false;
		diag_UpLeft = false;
		diag_UpRight = false;
		diag_DownLeft= false;
		diag_DownRight = false;
	}
	private boolean is_valid_space(int increment)
	{
		int desired_move;
		for(int i=0; i<spaces.size(); i++)
		{
			desired_move = spaces.get(i)+increment;
			if(desired_move == 0)
				return false;
		}
		spaces.add(0);
		//If it makes it through the for loop it is a valid move
		return true;
	}
}
