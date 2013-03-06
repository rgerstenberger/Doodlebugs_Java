import acm.util.RandomGenerator;

public abstract class Organism
{
	protected World world;
	protected int x;
	protected int y;
	protected boolean simulated;
	protected int stepsTaken = 0;
	protected int breedInterval;
	protected RandomGenerator rgen = RandomGenerator.getInstance();
	
	
	
	public Organism(World world, int x, int y, int breedInterval)
	{
		this.world = world;
		this.x = x;
		this.y = y;
		this.breedInterval = breedInterval;
		simulated = true;
	}
	
	//returns the string representation of the organism
	public abstract String toString();
	
	//each organism checks for other behavior first
	protected abstract boolean checkMove();
	
	
	//all organisms move the same
	public void Move()
	{
		
		//pick a random direction
		int newX = x + rgen.nextInt(-1,1);
		int newY = y + rgen.nextInt(-1,1);
		
		//check space on grid
		if(world.pointInGrid(newX, newY) && world.getAt(newX, newY) == null)
		{
			world.setAt(newX, newY, this);
			world.setAt(x, y, null);
			x = newX;
			y = newY;
						
		}
		
		return;
	};
	
	//organisms need a way to create a copy of themselves when they breed
	//could be accomplished with a check in the breed method but I like this way better
	protected abstract void Multiply(int newX, int newY);
	
	//each organism breeds depending on their breedInterval variables
	protected void Breed()
	{
		if(world.pointInGrid(x + 1, y) && world.getAt(x + 1, y) == null) 
		{
			Multiply(x + 1, y);
			return;
		}
		else if(world.pointInGrid(x - 1, y) && world.getAt(x - 1, y) == null) 
		{
			Multiply(x - 1, y);
			return;
		} 
		else if(world.pointInGrid(x, y + 1) && world.getAt(x, y + 1) == null) 
		{
			Multiply(x, y + 1);
			return;
		} 
		else if(world.pointInGrid(x, y - 1) && world.getAt(x, y - 1) == null) 
		{
			Multiply(x, y - 1);
			return;
		};
		
		return;

	}
	
	
	public void simulate()
	{
		//don't simulate twice in a round
		if(simulated) return;
		simulated = true;
		
		stepsTaken++;

		//check for breed criteria
		if(stepsTaken != 0 && stepsTaken % breedInterval == 0)
		{
			Breed();
			return;			
		}
		
		//each organism has different criteria for moving
		if(checkMove())
		{
			Move();
			return;
		}
				
		return;
	}

	//indicate that the organism hasn't simulated this round
	public void resetSimulation()
	{
		simulated = false;
	}
}