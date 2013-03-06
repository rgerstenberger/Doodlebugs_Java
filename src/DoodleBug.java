public class DoodleBug extends Organism
{
	private int starveCount = 0;
	
	//probably a better way to do this but I wanted to keep the variable within the child class
	private static final int STEPS_TO_BREED = 8;
	
	//utilizes super constructor with specified breed interval
	public DoodleBug(World world, int x, int y)
	{
		super(world, x, y, STEPS_TO_BREED);
	}
	
	//string representation of doodlebug
	public String toString()
	{
		return "doodlebug";
	}
	
	
	//Move
	//Every time step the doodlebug will move.
	//First he will check out each direction.  If there is an ant in an adjacent space he will move 
	//there (consequently taking up the ants space and eating him!)
	//If there was no ant in an adjacent space he will move just like ants do.
	//Note:  doodlebugs can’t eat other doodlebugs
	protected boolean checkMove()
	{
		//if an ant is eaten don't move
		if(eatAnt(x + 1, y)) return false;
		else if(eatAnt(x - 1, y)) return false;
		else if(eatAnt(x, y + 1)) return false;
		else if(eatAnt(x, y - 1)) return false;
		
		starveCount++;
		if(starveCount == 3)
		{
			Starve();
			return false;
		}
		return true;
	}
	
	//checks if there is an ant at the adjacent point
	//If so, eats it and returns true
	//If not, return false
	private boolean eatAnt(int maybeX, int maybeY)
	{
		Organism maybeOrg = world.getAt(maybeX,maybeY);
		if(world.pointInGrid(maybeX, maybeY) && maybeOrg != null && maybeOrg.toString() == "ant")
		{
			starveCount = 0;
			world.setAt(x, y, null);
			world.setAt(maybeX, maybeY, this);
			this.x = maybeX;
			this.y = maybeY;
			return true;
		}
		
		return false;
	}

	//A Doodlebug will create a new Doodlebug at an adjacent free space when it breeds
	protected void Multiply(int newX, int newY)
	{
		world.setAt(newX, newY, new DoodleBug(world, newX, newY));
		return;
	}
	
	//Starve
	//If a doodlebug has not eaten an ant within the last 3 time steps, then at the end of the last time 
	//step he will die of starvation.  You will remove him from the grid
	public void Starve()
	{
		world.setAt(x, y, null);
	}
	
}