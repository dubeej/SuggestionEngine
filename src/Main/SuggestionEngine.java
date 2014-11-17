package Main;

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
	
	private final static int DESIRED_FITNESS = 100;	// Desired fitness rating
	
	public static void main(String[] args) throws IOException {
		MovieRatings trainingSet;					// Training set
		MovieRatings testSet;						// Test set
		Movies movies;								// Movies database
		Users users;								// User database
		Profiles profiles;							// User profiles
		Scanner input;								// User input scanner
		int activeUser;								// User to train for
		ArrayList<Integer> moviesRatedByActiveUser;	// Movies rated by active user
		Profiles neighborSet;						// Common profiles to the active user
		Profiles activeUserSet;						// Profiles for the active user
		
		trainingSet = new MovieRatings();
		testSet = new MovieRatings();
		movies = new Movies();
		users = new Users();
		input = new Scanner(System.in);
		activeUser = 1;
		
		// Load the ratings, movie, and user data. Then train the genetic
		// algorithm for the active user
		try {
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
			train(activeUser, activeUserSet, neighborSet);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Generates the profiles database for the g
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
	 * @param neighborSet		Movies liked by the user's nreighbors
	 */
	public static void train(int userID, Profiles activeUserSet, Profiles neighborSet) {
		int generation;		// Current generation
		GA ga;				// The genetic algorithm to train
	
		generation = 0;
		
        ga = new GA(userID, activeUserSet, neighborSet);
        
        ga.getNextGeneration();
        
        // Continue to train the genetic algorithm until the desired fitness is 
        // reached
        while (ga.getFittest().getFitness() < DESIRED_FITNESS) {
        	try {
        	ga.calcFitness();
        	} catch (Exception e) {
        		System.out.println(e.getMessage());
        	}
            generation++;
            System.out.println("Generation: " + generation + " Fittest: " + 
            ga.getFittest().getFitness() + " Genome: " + 
            		ga.getFittest());
            ga.getNextGeneration();
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
