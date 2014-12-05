package Main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import GA.GA;
import Movies.Movies;
import Profile.ProfileData;
import Profile.Profiles;
import Rating.MovieRatings;
import Rating.UserRatingData;
import Rating.WeightRatings;
import Users.UserData;
import Users.Users;

/**
 * This program loads a movie, and user rating database. The information from
 * these databases is then used to run a genetic algorithm that trains to
 * predict movies that a user may like.
 * 
 * @author James Dubee
 */

public class SuggestionEngine {
	private final static String TEST_SET = "u1.test";		// Test set
	private final static String TRAINING_SET = "u1.base";	// Training set
	private final static String MOVIES	= "movies.txt";		// Movie database
	private final static String USERS_DATA = "u.user";		// User database
	private final static String LOG_FILE = "log.csv";		// Log file
	private final static int DEFAULT_FITNESS = 100;			// Default fitness
	
	private static FileWriter writer;			// File writer 
	private static File file;					// File to writer to
	private static boolean log;					// Log generation and fitness 
	
	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {
		// Movies rated by active user
		ArrayList<Integer> moviesRatedByActiveUser; 
		MovieRatings trainingSet;		// Training set
		MovieRatings testSet;			// Test set
		Movies movies;					// Movies database
		Users users;					// User database
		Profiles profiles;				// User profiles
		Scanner input;					// User input scanner
		int activeUser;					// User to train for
		Profiles neighborSet;			// Common profiles to the active user
		Profiles activeUserSet;			// Profiles for the active user
		boolean elitism;				// Non-default elitism value
		float mutation;					// Non-default mutation chance
		int popSize;					// Non-default population size
		int desiredFitness;				// Non-default desired fitness
		
		trainingSet = new MovieRatings();
		testSet = new MovieRatings();
		movies = new Movies();
		users = new Users();
		input = new Scanner(System.in);
		activeUser = 1;

		// Load the ratings, movie, and user data. Then train the genetic
		// algorithm for the active user
		try {
			// Use default values for elitism, mutation chance, population size,
			// and desired fitness if the user does not provide non-default 
			// values.
			if (args.length == 5) {
				elitism = Boolean.valueOf(args[0]);
				mutation = Float.parseFloat(args[1]);
				popSize = Integer.parseInt(args[2]);
				desiredFitness = Integer.parseInt(args[3]);
				log = Boolean.valueOf(args[4]);
				
				// Set up the log file if necessary
				if (log) {
				      file = new File(LOG_FILE);
				      file.createNewFile();
				      writer = new FileWriter(file); 
				      writer.write("Generation,Fitness\n");
				      writer.flush();
				}
			} else {
				elitism = GA.DEFAULT_ELITISM;
				mutation = GA.DEFAULT_MUTATION;
				popSize = GA.DEFAULT_SIZE;
				desiredFitness = DEFAULT_FITNESS;
				log = false;
			}
			

			System.out.println("Loading data...");
			
			testSet.load(TEST_SET);
			trainingSet.load(TRAINING_SET);
			movies.load(MOVIES);
			users.load( USERS_DATA);
			
			System.out.println("Please provide a user ID to train for.");
			activeUser = Integer.parseInt(input.nextLine());
			
			if (!trainingSet.getUserIDs().contains(activeUser))
				throw new Exception("The user ID does not exist.");
			
			profiles = genProfiles(movies, trainingSet, users);
			moviesRatedByActiveUser = trainingSet.getRatedMovies(activeUser);
			neighborSet = profiles.getProfilesByMovies(moviesRatedByActiveUser);
			activeUserSet = profiles.getProfilesByUser(activeUser);
			neighborSet.removeProfile(activeUser);
			
			System.out.println("Training for user " + activeUser + "...");
			
			train(activeUser, activeUserSet, neighborSet, mutation, elitism, 
					popSize, desiredFitness);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		if (log)
			writer.close();
	}

	/**
	 * Generates the profiles database for the genetic algorithm.
	 * @param movies
	 * @param ratings
	 * @param users
	 * @return
	 */
	public static Profiles genProfiles(Movies movies, 
			MovieRatings ratings, Users users) {
		Profiles profiles;						// Resultant profiles
		ArrayList<UserRatingData> userRatings;	// Ratings for the users
				
		profiles = new Profiles();

		ratings.beginningOfList();
		
		// Create a user profile for each rating made by each user
		while (!ratings.endOfList()) {
			userRatings = ratings.getCurrentUserRatings();
			int [] genrePrefs;		// Genre preferences
			int [] curGenres;		// Current genre preferences
			int avgRating = 0;		// Average user rating
			
			genrePrefs = new int [ProfileData.MAX_GENRES];
			
			// Set all of the genres to zero
			for (int i = 0; i < ProfileData.MAX_GENRES; i++) 
				genrePrefs[i] = 0;
			
			// Get the all of the liked genres for the current user
			for (int i = 0; i < userRatings.size(); i++) {
				UserRatingData userRating;		// Rating data for the user
				
				userRating = (UserRatingData)(userRatings.get(i));
				avgRating = avgRating + userRating.getMovieRating();
				
				// Get the genres for the current movie if its rating is > 2
				if (userRating.getMovieRating() > 2) {
					curGenres = movies.getGenresByMovie(userRating.getMovieID());
					if (curGenres != null) {
						
						// Assign the prefered genres
						for (int j = 0; j < ProfileData.MAX_GENRES; j++) {
							if (curGenres[j] == 1)
								genrePrefs[j] = curGenres[j];
						}
					}
				}
			}
			
			avgRating = avgRating / userRatings.size();
			
			// Create a user profile for each rating made by the current user
			for (int i = 0; i < userRatings.size(); i++) {
				ProfileData profile;		// Current profile
				UserRatingData userRating;	// Current user rating
				UserData user;				// Current user
				
				profile = new ProfileData();
				userRating = (UserRatingData)(userRatings.get(i));
				user = users.getUserData(userRating.getUserID());
				
				profile.setUserID(userRating.getUserID());
				profile.setMovieID(userRating.getMovieID());
				profile.setRating(userRating.getMovieRating());
				profile.setGender(user.getGender());
				profile.setAge(user.getGender());
				profile.setOccupation(user.getOccupation());
				profile.setGenres(genrePrefs);
				profile.setAvgRating(avgRating);
				
				profiles.addProfile(profile);
			}
		}
		
		return profiles;
	}
	
	/**
	 * Trains the genetic algorithm on the provided user, their liked movies,
	 * and the same movies liked by other users.
	 * 
	 * @param userID			User to train for
	 * @param activeUserSet		Movies liked by the user
	 * @param neighborSet		Movies liked by the user's neighbors
	 * @param mutation			Mutation chance
	 * @param elitism			Elitism enabled or disabled
	 * @param popSize			Population size
	 * @param desiredFitness	Desired fitness
	 */
	public static void train(int userID, Profiles activeUserSet, 
			Profiles neighborSet, float mutation, boolean elitism, 
			int popSize, int desiredFitness) {
		int generation;				// Current generation
		GA ga;						// The genetic algorithm to train
	
		generation = 0;
		
        ga = new GA(userID, activeUserSet, neighborSet, mutation, elitism, 
        		popSize);
        
        // Continue to train the genetic algorithm until the desired fitness is 
        // reached
        while ((int)ga.getFittest().getFitness() < desiredFitness) {
        	try {
           	 	ga.getNextGeneration();
        		ga.calcFitness();
                generation++;
                
                System.out.println("Generation: " + generation + " Fittest: " + 
                ga.getFittest().getFitness() + " Genome: " + 
                		ga.getFittest());
                
                if (log) {
                	writer.write(generation + "," + 
                			ga.getFittest().getFitness() + "\n");
                	writer.flush();
                }
        	} catch (Exception e) {
        		//System.out.println(e.getMessage());
        	}
 
        }
        
        System.out.println("Solution found!");
        System.out.println("Generation: " + generation);
        System.out.println("Fitness: " + ga.getFittest().getFitness());
        System.out.println("Genes:");
        System.out.println(ga.getFittest());
	}
	
	/**
	 * Determine the distance between each weight feature for each user who
	 * has rated movies
	 * 
	 * The movies object must contains a database of movies, and their
	 * attributes.
	 * 
	 * The ratings object must contain user ratings on the movies in the movie
	 * object.
	 * 
	 * @return WeightRating, the weights of user preferences
	 */
	public static WeightRatings calcDistanceBetweenWeights(Movies movies, 
			MovieRatings ratings) {
		WeightRatings weight = new WeightRatings();			// Resultant weights
		ArrayList<String> directors = movies.getDirectors();// All directors
		
		// Determine the weight for each director
		for (int i = 0; i < directors.size(); i++) {
			// Determine which movies were directed by the director
			ArrayList<Integer> moviesByDir = 			
					movies.getMoviesByDirector(directors.get(i));
			
			int avgRating = 0;	// Average rating for the weight
			
			
			// Add all the average ratings for movies directed by the director
			for (int j = 0; j < moviesByDir.size(); j++) 
				avgRating = avgRating + 
				ratings.getAverageRating(moviesByDir.get(j));
			
			weight.addDirectorRating(directors.get(i), 
					avgRating / moviesByDir.size());
		}
		
		return weight;
	}
}
