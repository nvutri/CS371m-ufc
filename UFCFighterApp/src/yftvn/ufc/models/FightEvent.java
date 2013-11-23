package yftvn.ufc.models;

public class FightEvent 
{
	private String left, right;
	
	public FightEvent(String first, String second)
	{
		left = first;
		right = second;
	}
	
	public String getLeft()
	{
		return left;
	}
	
	public String getRight()
	{
		return right;
	}

}
