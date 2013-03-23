package team01;

public class Util {
	
	public static boolean inRange(int val, int low, int high)
	{
		return (low <= val)
			&& (val <= high);
	}
	
	public static boolean isEven(int val)
	{
		return (val & 1) == 0;
	}
}
