package GA;


import java.util.ArrayList;
import java.util.Random;

import Movies.Movies;
import Profile.ProfileData;
import Profile.Profiles;
import Rating.MovieRatings;
import Rating.Prefered;
import Rating.WeightRatings;

/**
 * This class provides a genetic algorithm that is used to train a population
 * to predict the actual movie votes from a user. To do so, a neighbor set
 * is used of other users who rated the same movies the user did. The
 * distance between the user and  the neighbor are calculated to predict the
 * actual vote.
 * 
 * @author James Dubee
 *
 */
public class GA {
	public final static int DEFAULT_SIZE = 100;			// Default pop size
	public final static float DEFAULT_MUTATION = 0.01f;	// Default mutation
	public final static boolean DEFAULT_ELITISM = true;	// Default elitism
	
	private float mutationChance;					// Chance of mutations
	private boolean elitism;						// Denotes elitism
	private Population pop;							// Population set
	private int userID;								// Training user
	private Random rand;							// Number generator
	private Profiles neighborSet;					// Neighborhood set
	private Profiles activeUserSet;					// Active user set

	/**
	 * Sets the PDMs to non-default values.
	 * 
	 * @param user					Active user
	 * @param activeUserProfiles	Profiles for the active user
	 * @param neighborProfiles		Profiles for the neighbor set
	 * @param mutation				Mutation chance
	 * @param elitism				Elitism enabled or disable
	 * @param popSize				Population size
	 */
	public GA(int user, Profiles activeUserProfiles, Profiles neighborProfiles, 
			float mutation, boolean elitism, int popSize) {
		pop = new Population(popSize);
		userID = user;
		activeUserSet = activeUserProfiles;
		neighborSet = neighborProfiles;
		rand = new Random();
		mutationChance = mutation;
		this.elitism = elitism;
	}
	
	/**
	 * Calculates the distance between two users.
	 * 
	 * @param indiv				Current individual
	 * @param activeSet			Active movie set
	 * @param neighborSet		Neighbor movie set
	 * @return
	 */
	public double calcDist(Individual indiv, ArrayList<ProfileData> activeSet, 
			ArrayList<ProfileData> neighborSet) {
		double dist = 0;		// Distance between preferences
		
		// Determine the distance for each item in the neighbor movie set
		for (int i = 0; i < neighborSet.size(); i++) {
			// Profile data for the current neighbor
			ProfileData neighbor = (ProfileData)(neighborSet.get(i));
			
			// Calculate the distance for each movie in the active movie set
			for (int j = 0; j < activeSet.size(); j++) {
				// Profile data for the current active movie
				ProfileData active = (ProfileData)(activeSet.get(i));
				
				if (neighbor.getMovieID() == active.getMovieID()){

					double ratingDiff1 = Math.pow(active.getRateWeight() - neighbor.getRateWeight(), 2);
					
					dist = dist + indiv.getGene(0) * ratingDiff1; 
					
					
					double ratingDiff2 = Math.pow(active.getAgeWeight() - neighbor.getAgeWeight(), 2);
					dist = dist + indiv.getGene(1) * ratingDiff2; 
					
					
					double ratingDiff3 = Math.pow(active.getGenderWeight() - neighbor.getGenderWeight(), 2);
					dist = dist + indiv.getGene(2) * ratingDiff3; 
					
					
					double ratingDiff4 = Math.pow(active.getOccupationWeight() - neighbor.getOccupationWeight(), 2);
					dist = dist + indiv.getGene(3) * ratingDiff4; 
					
					double [] activeGenres;
					double [] neighborGenres;
					
					activeGenres = active.getGenresWeight();
					neighborGenres = neighbor.getGenresWeight();
					
					for (int k = 0; k < 18; k++) {
						dist = dist + indiv.getGene(4 + k) * 
								(Math.pow(activeGenres[k] - neighborGenres[k], 
										2)); 	
					}
			
					break;
				}
	
			}
				
			
			
		}
	
		return (double) Math.sqrt(dist);
	}
	
	/**
	 * Calculates the fitness for the given population.
	 * 
	 * @throws Exception		Divide by zero error
	 */
	public void calcFitness() throws Exception {
		// Profiles for the active user
		ArrayList<ProfileData> activeUserMovies = 
				activeUserSet.getProfilesForUser(userID);
		int neighborVote = 0;		// Neighbor's vote
		int neighborAvgVote = 0;	// Neighbor's active vote
		double predictedVote;		// Predicted vote
		int actualVote;				// Actual vote
		
		for (int i = 0; i < pop.size(); i++) {
			double fitness = 0;		// Current fitness
			double sumDist = 0;		// Sum of distances
			double dist2 = 0;		
			double dist;
			double pa = 0;			// Predicted vote average
			int ra =0;				// Real vote average
			
			
			// Predict vote for each movie in the active user set
			for (int j = 0; j < activeUserMovies.size(); j++) {
				ProfileData active = (ProfileData)(activeUserMovies.get(j));
				
				neighborSet.beginningOfList();
				
				// Calculate distance for each neighbor
				while(!neighborSet.endOfList()) {
					
					// Common movies from the neighbor
					ArrayList<ProfileData> commonMovies = 
							neighborSet.getCurrentProfiles();
					
					// Get the current neighbor's vote and mean vote for each
					// movie
					for (int k = 0; k < commonMovies.size(); k++) {
						// Profile data for the neighbor
						ProfileData neighbor = 
								(ProfileData)(commonMovies.get(k));
						
						if (neighbor.getMovieID() == active.getMovieID()) {
							neighborVote = neighbor.getRating();
							neighborAvgVote = neighbor.getAvgRating();
							break;
						}
					}
					
					dist = calcDist(pop.getIndividual(i), activeUserMovies, 
							commonMovies);
					sumDist = sumDist + dist;
					dist2 = dist2 + (dist * (neighborVote - neighborAvgVote));
				}
				
				// Can't divide by zero
				if (sumDist == 0)
					throw new Exception("Attempt to divide by zero");
				
				dist2 = dist2 / sumDist;
				predictedVote = active.getAvgRating() + dist2;
				
				actualVote = active.getRating();

				pa = pa + predictedVote;
				ra = ra + actualVote;
			}
		
			// Calculate the fitness
			if (ra > pa)
				fitness = (pa * 100) / ra;
			else
				fitness = (ra * 100) / pa;
	
			pop.getIndividual(i).setFitness(fitness);
		}
	}
	
	/**
	 * Returns the fittest individual from the population.
	 * 
	 * @return		Fittest individual
	 */
	public Individual getFittest() {
		return pop.getFittest();
	}
	
	/**
	 * Creates a new population by selecting individuals from randomly 
	 * created populations.
	 */
	public void getNextGeneration() {
		Population resPop;		// Resultant population
		Individual indiv1;		// Selected individual
		Individual indiv2;		// Selected individual
		
		
		resPop = new Population(pop.size());
		
		if (elitism) 
			resPop.setIndividual(0, pop.getFittest());
	
		// Add a new individual to the new population after performing 
		// selection
		for (int i = ((elitism) ? 1 : 0); i < pop.size(); i++) {
			indiv1 = selection();
			indiv2 = selection();
			indiv1.setFitness(-1);
			indiv2.setFitness(-1);
			resPop.setIndividual(i, mutate(crossover(indiv1, indiv2)));
		}

		pop = resPop;
	}
	
	/**
	 * Creates a new population by through tournament selection.
	 * 
	 * @return		Random individual
	 */
	public Individual selection() {
		Individual resFit;		// Resultant fittest individual
        Population randPop;
        int rand;
        
        randPop = new Population(pop.size());
        
        // Create a random population from the existing one
        for (int i = 0; i < pop.size(); i++) {
            rand = (int) (Math.random() * pop.size());
            randPop.setIndividual(i, pop.getIndividual(rand));
        }
        
        resFit = randPop.getFittest();

        return resFit;
	}
	
	/**
	 * Performs a single-point crossover on two individuals, returning a new 
	 * individual.
	 * 
	 * @param individual1		First parent
	 * @param individual2		Second parent
	 * 
	 * @return 					New individual
	 */
	public Individual crossover(Individual individual1, Individual individual2) {
		Individual resIndiv;		// Resultant individual
		int gene;					// Current gene
		int crossoverPoint;
		
		crossoverPoint = rand.nextInt(Individual.NUMBER_OF_GENES);

		resIndiv = new Individual();
		
		// Take half of the genes from the first parent
		for (int i = 0; i < crossoverPoint; i++) {
			gene = individual1.getGene(i);
			resIndiv.setGene(i, gene);
		}
		
		// Take the second half of genes from the second parent
		for (int i = crossoverPoint; i < Individual.NUMBER_OF_GENES; i++) {
			gene = individual2.getGene(i);
			resIndiv.setGene(i, gene);
		}
		
		return resIndiv;
	}
	
	/**
	 * Mutates an individuals genes if a random value falls within the
	 * mutation chance.
	 * 
	 * @param individual		Individual to mutate
	 * 
	 * @return					Mutated individual				
	 */
	private Individual mutate(Individual individual) {
		int gene; 	// Current gene
		
		// For each gene in the individual, determine if a mutation should occur
		// on a gene
		for (int i = 0; i < individual.size(); i++) {
			
			// Determine if a mutation should occur
            if (Math.random() <= mutationChance) {
                gene = rand.nextInt(2);
                individual.setGene(i, gene);
            }
        }
		
		return individual;
	}
	
	/**
	 * Sets elitism to enabled or disabled.
	 * 
	 * @param enabled		True to enable elitism. Otherwise, false.
	 */
	public void setElitism(boolean enabled) {
		elitism = enabled;
	}
	
	/**
	 * Sets the mutation percentage.
	 * 
	 * @param mutation		Percentage mutation
	 */
	public void setMutation(float mutation) {
		mutationChance = mutation;
	}

}
