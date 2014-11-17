package GA;

import java.util.Random;


/**
 * This class provides an individual that contains a genome. The genome contains
 * numbers that are used to weight the importance of some value. Genes can be
 * set, retrieved. Also, the fitness of the individual can be set, and 
 * retrieved.
 * 
 * @author James Dubee
 */

public class Individual {
	public static final int NUMBER_OF_GENES = 22;// Default number of genes
	 
	private int[] genome;						// Genome for the individual
	private double fitness;						// Fitness for the individual
	private Random rand;						// Random number generator
		
	/**
	 * Initializes PDMs to default values.
	 */
	public Individual() {
		genome = new int[NUMBER_OF_GENES];
		fitness = -1;
		rand = new Random() ;
		create();
	}
	
	/**
	 * Returns the gene at the given index from the genome.
	 * 
	 * @param index		Index of the gene
	 * @return			The gene at the given index
	 */
	public int getGene(int index) {
		return genome[index];
	}
	
	/**
	 * Sets the gene at a given index with the given weight.
	 * 
	 * @param index		The index of the gene
	 * @param weight	The value of the weight
	 */
	public void setGene(int index, int weight) {
		genome[index] = weight;
	}
	
	/**
	 * Returns the fitness of the individual.
	 * 
	 * @return		The fitness of the individual
	 */
	public double getFitness() {
		return fitness;
	}
	
	/**
	 * Sets the fitness for the individual to the specified value.
	 * 
	 * @param value		Fitness value
	 */
	public void setFitness(double value) {
		fitness = value;
	}

	/**
	 * Randomly populates the genome with values.
	 */
	public void create() {
		// Assign a random number to each gene
		for (int i = 0; i < NUMBER_OF_GENES; i++) 
			genome[i] = rand.nextInt(2); 
	}
	
	/**
	 * Returns the number of genes in the genome.
	 * 
	 * @return		Number of genes
	 */
	public int size() {
		return NUMBER_OF_GENES;
	}
	
	/**
	 * Displays a string representation of the genome.
	 */
	@Override
	public String toString() {
		String res;		// Resultant string
		
		res = "" + genome[0];
		
		// Add each item of the genome to the resultant string
		for (int i = 1; i < NUMBER_OF_GENES; i++) {
			res = res + ", " + genome[i];
		}
		
		return res;
	}

}
