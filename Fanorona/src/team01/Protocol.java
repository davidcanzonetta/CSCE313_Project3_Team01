package team01;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Protocol {

	static private final int STATE_ERROR = -1;
	static private final int STATE_READY = 1;
	static private final int STATE_WELCOME = 2;
	static private final int STATE_OK = 3;
	static private final int STATE_ILLEGAL = 4;
	static private final int STATE_TIME = 5;
	static private final int STATE_WINNER = 6;
	static private final int STATE_LOSER = 7;
	static private final int STATE_TIE = 8;
	static private final int STATE_GET_MOVE = 9;
	static private final int STATE_MAKE_MOVE = 10;

	private int width;
	private int height;
	private int player;
	private int time;
	private int state;
	
	private void info(String msg)
	{
		Scanner scanner = new Scanner (msg);

		try
		{
			String info = scanner.next();

			if (info.equals("INFO"))
			{
				if (state == STATE_WELCOME)
				{
					width = scanner.nextInt();
					height = scanner.nextInt();
					player = scanner.next().equals("W") ? Board.WHITE : Board.BLACK;
					time = scanner.nextInt();
	
					if ((3 <= width && width <= 13)
						&& (3 <= height && height <= 13))
					{
						state = STATE_READY;
						return;
					}
				}
			}
			else if (info.equals("BEGIN"))
			{
				if (state == STATE_READY)
				{
					if (player == Board.WHITE)
					{
						state = STATE_GET_MOVE;
					}
					else
					{
						state = STATE_MAKE_MOVE;
					}
					return;
				}
			}
			
			state = STATE_ERROR;
			return;
		}
		catch (InputMismatchException e)
		{
			state = STATE_ERROR;
		}
		finally
		{
			scanner.close();
		}
	}
	

	private void acknowledge(String msg)
	{
		Scanner scanner = new Scanner (msg);

		try
		{
			String ack = scanner.next();

			if (ack.equals("WELCOME"))
				state = STATE_WELCOME;
			else if (ack.equals("READY"))
				state = STATE_READY;
			else if (ack.equals("OK"))
				state = STATE_OK;
			else if (ack.equals("ILLEGAL"))
				state = STATE_ILLEGAL;
			else if (ack.equals("TIME"))
				state = STATE_TIME;
			else if (ack.equals("LOSER"))
				state = STATE_LOSER;
			else if (ack.equals("WINNER"))
				state = STATE_WINNER;
			else if (ack.equals("TIE"))
				state = STATE_TIE;
			else if(scanner.next() == "A") { //Advance capture move 
				//Store positions
				int from = Integer.parseInt(scanner.next());
				int to = Integer.parseInt(scanner.next());
				
			}
			else if(scanner.next() == "W") { //Withdraw capture move 
				//Store positions
				int from = Integer.parseInt(scanner.next());
				int to = Integer.parseInt(scanner.next());
			}
			else if(scanner.next() == "P") { //Paika Move
				//Store positions
				int from = Integer.parseInt(scanner.next());
				int to = Integer.parseInt(scanner.next());
			}
			else if(scanner.next() == "S") { //Sacrifice Move
				//Store position
				int pos = Integer.parseInt(scanner.next());
			}
			else
				state = STATE_ERROR;
				
		}
		catch (InputMismatchException e)
		{
			state = STATE_ERROR;
		}
		finally
		{
			scanner.close();
		}
	}
}
