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

	private int info(String msg)
	{
		Scanner scanner = new Scanner (msg);
		int state = STATE_ERROR;

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
					}
				}
			}
			else if (info.equals("BEGIN"))
			{
				if (state == STATE_READY)
				{
					if (player == Board.WHITE)
						state = STATE_GET_MOVE;
					else
						state = STATE_MAKE_MOVE;
				}
			}
		}
		catch (InputMismatchException e)
		{
			//
		}
		finally
		{
			scanner.close();
		}

		return state;
	}
	

	private int acknowledge(String msg)
	{
		Scanner scanner = new Scanner (msg);
		int state = STATE_ERROR;

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
		}
		catch (InputMismatchException e)
		{
			//
		}
		finally
		{
			scanner.close();
		}

		return state;
	}
}
