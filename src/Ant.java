

public class Ant extends Organism
{
	
	private static final int STEPS_TO_BREED = 3;
	
	//utilizes super constructor with specified breed interval
	public Ant(World world, int x, int y)
	{
		super(world, x, y, STEPS_TO_BREED);
	}
	
	//returns a string representation of the ant
	public String toString()
	{
		return "ant";
	}
	
	
	//Every time step the ant will attempt to move.
	protected boolean checkMove()
	{
		return true;		
	}
	
	//An ant will create a new ant at an adjacent free space when it breeds
	protected void Multiply(int newX, int newY)
	{
		world.setAt(newX, newY, new Ant(world, newX, newY));
		return;
	}
	
	
}