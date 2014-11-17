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
	private static final float mutationChance = 0.01f;	// Chance of mutations
	private final int DEFAULT_SIZE = 100;				// Default pop size
	
	private static boolean elitism = true;				// Denotes elitism
	private Population pop;								// Population set
	private Movies movies;								// Common movies
	private Prefered prefs;								// User preferences
	private MovieRatings ratings;						// User ratings
	private WeightRatings weight;						// User weights
	private int userID;									// Training user
	private ArrayList<Integer> movieIDs;				// Common movie IDs	
	private Random rand;								// Number generator
	private Profiles neighborSet;
	private Profiles activeUserSet;
	
	
	/**
	 * Initializes the PMDs to non-default values.
	 * 
	 * @param user				userID to train for
	 * @param movieIDsRated		Movies rated by the user
	 * @param attrWeight		Weight of attributes
	 * @param commonMovies		Details of the rated movies 
	 * @param userRatings		Neighbors who rated the same movies as the user
	 * @param userPrefs			Preferences for the users
	 */
	public GA(int user, ArrayList<Integer> movieIDsRated, 
			WeightRatings attrWeight, Movies commonMovies, 
			MovieRatings userRatings, Prefered userPrefs) {
		pop = new Population(DEFAULT_SIZE);
		userID = user;
		movieIDs = movieIDsRated;
		movies = commonMovies;
		weight = attrWeight;
		ratings = userRatings;
		prefs = userPrefs;
		rand = new Random() ;
	}
	
	public GA(int user, Profiles activeUserProfiles, Profiles neighborProfiles) {
		pop = new Population(DEFAULT_SIZE);
		userID = user;
		activeUserSet = activeUserProfiles;
		neighborSet = neighborProfiles;
		rand = new Random() ;
	}
	
	/**
	 * Determines the distance between two users based on the value of
	 * a weight on a movie
	 * 
	 * @param individualIndex		Index of the current individual
	 * @param neighborID			ID of the neighbor
	 * @param movieIndex			Index of the movie
	 * 
	 * @return						Distance between users for this weight
	 */
	private int getDistByDirector(int individualIndex, int neighborID, 
			int movieIndex) {
		String [] directors;		// Movie's Directors
		int weightUser = 0;			// Weight for user
		int weightNeighbor = 0;		// Weight for neighbor
		
		directors = movies.getDirectors(movieIndex);
		if (directors != null) {
				
			// Determine the distance between both users, and the current 
			// movie based on director preferences
			for (int k = 0; k < directors.length; k++) {
				if (directors[k] != null) {
					if (prefs.getPrefered(userID).getDirector() != null)
						weightUser = weightUser + (weight.getDirectorRating(prefs.getPrefered(userID).getDirector()) - weight.getDirectorRating(directors[k]));
					
					if (prefs.getPrefered(neighborID).getDirector() != null)
						weightNeighbor = weightNeighbor + (weight.getDirectorRating(prefs.getPrefered(neighborID).getDirector()) - weight.getDirectorRating(directors[k]));
				}
			}
	
			return (int) (pop.getIndividual(individualIndex).getGene(0) * (weightUser - weightNeighbor));
		} else
			return 0;
	}
	
	/**
	 * Determines the Euclidean distance between the preferences of two users,
	 * and multiplies a weight from an individual genome by those differences.
	 * 
	 * @param individualIndex		Index of an individual in the population
	 * @param neighborID			Neighbor ID
	 * @param neighborMovies		Neighbor movies that the user liked
	 * 
	 * @return		Returns the distance between the preferences of two users
	 * 				based on a weight provided by an individual in the 
	 * 				population.
	 */
	private int getEuclideanDist(int individualIndex, int neighborID, 
			ArrayList<Integer> neighborMovies) {
		int dist = 0;		// Distance result

		ArrayList<Integer> ratedMovieIDs = ratings.getRatedMovies(neighborID);
		
		// For each movie rated by the neighbor and the user, calculate the
		// distance between the neighbor, and the user preferences
		for (int i = 0; i < ratedMovieIDs.size();  i++) {
			dist = dist + getDistByDirector(individualIndex, neighborID, ratedMovieIDs.get(i));
		}
			
		return (int)(Math.sqrt(dist));
	}
	
	/**
	 * Determines the sum of a user's vote on each rated movie minus the user's
	 * average vote.
	 * 
	 * @param neighborID		ID of the user
	 * @param ratedMovieIDs		IDs of each movie rated by the user
	 * @return
	 */
	private int getVoteMinusMean(int neighborID, ArrayList<Integer> ratedMovieIDs) {
		int voteMinusMean = 0;	// Total vote minus mean vote for each movie
		
		// Get the vote of the neighbor on each movie
		for (int i = 0; i < ratedMovieIDs.size(); i++) {
			voteMinusMean = voteMinusMean +
					ratings.getRating(neighborID, ratedMovieIDs.get(i)) -  
					prefs.getPrefered(neighborID).getMeanRating();
		}
		
		return voteMinusMean;
	}
	
	/**
	 * Predicts the users vote based on the calculated distance.
	 * 
	 * @param dist		Distance between two users
	 * @return			Predicted vote
	 */
	private int predictVote(int dist) {
		int predict;		// Predicted vote
		
		predict = (prefs.getPrefered(userID).getMeanRating() + 
				((dist / ratings.size())));
		
		
		//if (predict > 5)
		//	predict = predict - 5;
		
		return predict;
	}
	
	public double calcDist(Individual indiv, ArrayList<ProfileData> activeSet, ArrayList<ProfileData> neighborSet) {
		double dist = 0;
		
		for (int i = 0; i < neighborSet.size(); i++) {
			ProfileData neighbor = (ProfileData)(neighborSet.get(i));
			
			for (int j = 0; j < activeSet.size(); j++) {
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
						
						
						
						dist = dist + indiv.getGene(4 + k) * (Math.pow(activeGenres[k] - neighborGenres[k], 2)); 
						
					}
			
					break;
				}
	
			}
				
			
			
		}
	
		return (double) Math.sqrt(dist);
	}
	
	public void calcFitness() throws Exception {
		ArrayList<ProfileData> activeUserMovies = activeUserSet.getProfilesForUser(userID);
		
		
		int neighborVote = 0;
		int neighborAvgVote = 0;
		double predictedVote;
		int actualVote;
		
		for (int i = 0; i < pop.size(); i++) {
			double fitness = 0;
			double sumDist = 0;
			double dist2 = 0;
			double dist;
			double  pa= 0;
			int ra =0;
			
			
			// Predict vote for each movie in the active user set
			for (int j = 0; j < activeUserMovies.size(); j++) {
				ProfileData active = (ProfileData)(activeUserMovies.get(j));
				
				neighborSet.beginningOfList();
				
				// Calc dist for each neighbor
				while(!neighborSet.endOfList()) {
			
					ArrayList<ProfileData> commonMovies = neighborSet.getCurrentProfiles();
					
					
					for (int k = 0; k < commonMovies.size(); k++) {
						ProfileData neighbor = (ProfileData)(commonMovies.get(k));
						if (neighbor.getMovieID() == active.getMovieID()) {
							neighborVote = neighbor.getRating();
							neighborAvgVote = neighbor.getAvgRating();
							break;
						}
					}
					
					dist = calcDist(pop.getIndividual(i), activeUserMovies, commonMovies);

					sumDist = sumDist + dist;

					dist2 = dist2 + (dist * (neighborVote - neighborAvgVote));


				}
				
				
				//predict
				// check for div by zero
		
				if (sumDist == 0)
					throw new Exception("Attempt to divide by zero");
				
				dist2 = dist2 / sumDist;
				predictedVote = active.getAvgRating() + dist2;
				//System.out.println((int) Math.round(active.getAvgRating() + dist2));
				//System.exit(1);
				actualVote = active.getRating();

				pa = pa + predictedVote;
				ra = ra + actualVote;
				//System.out.println("actualVote: " + actualVote + " predictedVote: " + predictedVote + " fitness: " + fitness);
				//System.exit(1);
			}
		
			
			if (ra > pa)
				fitness = (pa * 100) / ra;
			else
				fitness = (ra * 100) / pa;
	
			//System.out.println(fitness);
			pop.getIndividual(i).setFitness(fitness);
			
	
			
		}
		
	
	}
	
	/**
	 * Determines the fitness for each individual in the population. This is
	 * done by evaluating the distance between preferences the user and
	 * neighbors that liked the same movies have.
	 */
	/*public void calcFitness() {
		int neighborID; 				// Neighbor ID number
		int predictedVote;				// Predicted vote
		int realVote;					// Actual user vote
		
		// For each individual in the population calculate its fitness
		for (int i = 0; i < pop.size(); i++) {
			int dist = 0;
			System.out.println(i);
			
			// Predict the vote for each movie liked by the user
			for (int j = 0; j < movieIDs.size(); j++) {
				ratings.beginningOfList();
				
				// For each neighbor in the neighbor set, determine the distance
				// between the neighbor and the user based on their preferences
				while (!ratings.endOfList()) {
					neighborID = ratings.getCurrentItem();
					
					ArrayList<Integer> ratedMovieIDs = ratings.getRatedMovies(neighborID);
					
					if (ratedMovieIDs.contains(movieIDs.get(j))) {
						dist = dist + getEuclideanDist(i, neighborID, ratedMovieIDs) *
								getVoteMinusMean(neighborID, ratedMovieIDs);
					}
				}
				
				predictedVote = predictVote(dist);
				realVote = ratings.getRating(userID, movieIDs.get(j));
				float fitness = (float)(Math.abs(predictedVote) * 100) / realVote;
				
				pop.getIndividual(i).setFitness(fitness);
			}
		}
	}*/
	
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

}
