package GA;

/**
 * This class provides a populations of individuals. Individuals can be
 * retrieved, inserted, and the most fit individual determined.
 * 
 * @author James Dubee
 *
 */
public class Population {
	public static final int POP_SIZE = 100;	// Default population size
	 
	Individual[] individuals;				// Population of individuals
	int populationSize;						// Population size
	
	/**
	 * Initializes the PMDs to default values.
	 */
	public Population() {
		populationSize = POP_SIZE;
		individuals = new Individual[populationSize];
		
		// Creates each individual in the population
		for (int i = 0; i < populationSize; i++) 
			individuals[i] = new Individual();
	}
	
	/**
	 * Creates a population of the size passed.
	 * 
	 * @param size	Size of the population
	 */
	public Population(int size) {
		populationSize = size;
		individuals = new Individual[populationSize];
		
		// Creates each individual in the population
		for (int i = 0; i < populationSize; i++) 
			individuals[i] = new Individual();
	}
	
	/**
	 * Places an individual into the population at the given index.
	 * 
	 * @param index			Index to place the individual		
	 * @param individual	Individual to add
	 */
	public void setIndividual(int index, Individual individual) {
		individuals[index] = individual;
	}
	
	/**
	 * Returns an individual at the given index.
	 * 
	 * @param index		Index of the individual to retrieve
	 * 
	 * @return			Individual at the given index
	 */
	public Individual getIndividual(int index) {
		return individuals[index];
	}
	
	/**
	 * Determines and returns the most fit individual in the population.
	 * 
	 * @return		Must fit individual
	 */
	public Individual getFittest() {
		Individual fittest;
		fittest = individuals[0];
		
		// Determine the must fit individual
		for (int i = 1; i < populationSize; i++) {
			if (fittest.getFitness() < individuals[i].getFitness()) 
				fittest = individuals[i];
		}
		
		return fittest;
	}
	
	/**
	 * Returns the population size.
	 * 
	 * @return		Size of the population
	 */
	public int size() {
		return populationSize;
	}
	
	/**
	 * Returns a string representation of each individual in the population.
	 */
	@Override
	public String toString() {
		String res = null;		// Resultant string
		
		// Add each individual to the string
		for (int i = 0; i < populationSize; i++) 
			res = res + individuals[i].toString() + '\n';
		
		return res;
	}

}
