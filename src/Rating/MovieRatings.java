package Rating;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Map.Entry;
import java.util.TreeMap;

import Profile.ProfileData;

/**
 * This class loads user rating data from a database so that it may be used
 * to accomplish some task. Information in the database may be retrieved in 
 * various ways.
 * 
 * @author James Dubee
 */

public class MovieRatings {
	// Minimum vote to determine if a user liked a movie
	static final int LIKED_VOTE = 3;
	
	// User ratings database
	private TreeMap<Integer, ArrayList<UserRatingData>> ratings;
	
	private int numRatings;							// Number of ratings
	private Iterator itr;							// Database iterator

	/**
	 * Initializes the PDMs to default values
	 */
	public MovieRatings() {
		ratings = new TreeMap<Integer, ArrayList<UserRatingData>>();
		numRatings = 0;
	}
	
	/**
	 * Returns the number of ratings in the database
	 * @return
	 */
	public int size() {
		return numRatings;
	}
	
	/**
	 * Loads the ratings database from file. The database file must be in the
	 * following format: UserID MovieID Rating TimeStamp
	 * 
	 * @param filename					File to load
	 * 
	 * @throws FileNotFoundException
	 */
	public void load(String filename) throws FileNotFoundException {
		FileReader reader;		// File reader for the ratings
		Scanner input;			// Scanner for the ratings
		String movieID;			// Current movie ID 
		String userID;			// Current user ID
		String rating;			// Current rating
		String timestamp;		// Current timestamp

		// Attempt to open the file
		try {
			reader = new FileReader(filename);
			input = new Scanner(reader);
			
			// Read each line from the file and store it in the database
			while (input.hasNext()) {
				UserRatingData userData; // User rating data

				userData = new UserRatingData();
				userID = input.next();
				
				// Read the movie ID
				if (input.hasNext()) {
					movieID = input.next();

					// Read the rating
					if (input.hasNext()) {
						rating = input.next();
						
						// Read the timestmap
						if (input.hasNext()) {
							timestamp = input.next();
							
							userData.setUserID(Integer.parseInt(userID));
							userData.setMovieID(Integer.parseInt(movieID));
							userData.setRating(Integer.parseInt(rating));
							userData.setTimestamp(Integer.parseInt(timestamp));

							addMovie(userData);
						}
					}
				}
			}
			
			input.close();
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException(
					String.format("\"%s\" was not found.\n", filename));
		}
	}
	
	/**
	 * Adds a movie to the rating database if it does not already exist.
	 * 
	 * @param userData		User data to add to the database
	 */
	public void addMovie(UserRatingData userData) {
		ArrayList<UserRatingData> userRatings;		// Ratings for the user
		
		userRatings = ratings.get(userData.getUserID());

		// Add the user rating to the database if it does not already exist
		if (userRatings == null) {
		     userRatings = new ArrayList<UserRatingData>();
		     ratings.put(userData.getUserID(), userRatings);
		     userRatings.add(userData);
		     numRatings++;
		} else {
			if (userRatings.indexOf(userData) < 0) {
				userRatings.add(userData);
				numRatings++;
			}
		}
	}
	
	/**
	 * Return the average rating for a user.
	 * 
	 * @param userID		User to get average rating for
	 * 
	 * @return		Average rating for the user
	 */
	public int getAverageRating(int userID) {
		int avgRating = 0;						// Resultant average
		ArrayList<UserRatingData> userRatings;	// All of the user's ratings
		
		userRatings = ratings.get(userID);
		
		// Average all of the user's ratings
		if (userRatings != null) {
			for (int i = 0; i < userRatings.size(); i++) {
				UserRatingData userData = (UserRatingData)(userRatings.get(i)); 
				avgRating = avgRating + userData.getMovieRating();
			}
			
			avgRating = avgRating / userRatings.size();
		}
		
		return avgRating;
	}
	
	/**
	 * Returns a list of all of the rated movies by a user.
	 * 
	 * @param userID		User to get rated movies for
	 * 
	 * @return		List of movies rated by the user	
	 */
	public ArrayList<Integer> getRatedMovies(int userID) {
		ArrayList<Integer> res;					// Resultant rated movies
		ArrayList<UserRatingData> userRatings;	// User ratings
		
		res = new ArrayList<Integer>();
		userRatings = ratings.get(userID);
		
		// Get all of the movies rated by the user
		if (userRatings != null) {
			for (int i = 0; i < userRatings.size(); i++) {
				UserRatingData userData = (UserRatingData)(userRatings.get(i)); 
				res.add(userData.getMovieID());
			}
		}
		
		return res;
	}
	
	/**
	 * Return all of the movie IDs a user rated highly.
	 * 
	 * @param userID		User to get liked movies for
	 * 
	 * @return				Movie IDs liked by the user
	 */
	public ArrayList<Integer> getLikedMovies(int userID) {
		ArrayList<Integer> res;					// Resultant liked movies
		ArrayList<UserRatingData> userRatings;	// User ratings
		
		res = new ArrayList<Integer>();
		userRatings = ratings.get(userID);
		
		// Get all of the movies liked by the user
		if (userRatings != null) {
			for (int i = 0; i < userRatings.size(); i++) {
				UserRatingData userData = (UserRatingData)(userRatings.get(i)); 
				
				// Determine if the user liked the movie
				if (userData.getMovieRating() >= LIKED_VOTE)
					res.add(userData.getMovieID());
			}
		}
		
		return res;
	}
	
	/**
	 * Returns the rating of a movie on by a specific user.
	 * 
	 * @param userID		User to get vote for
	 * @param movieID		Movie to get user vote for
	 * @return
	 */
	public int getRating(int userID, int movieID) {
		ArrayList<UserRatingData> userRatings;		// User ratings
		int res = 0;								// Resultant rating
		
		userRatings = ratings.get(userID);
		
		// Determine the user's rating on the specified movie
		if (userRatings != null) {
			for (int i = 0; i < userRatings.size(); i++) {
				UserRatingData rating = userRatings.get(i);
				if (rating.getMovieID() == movieID)
					return rating.getMovieRating();
			}
				
		}
		
		return res;
	}
	
	/**
	 * Returns a new movie ratings database based on a list of movie IDs.
	 * 
	 * @param movieIDs		Movie IDs to create new database from
	 * 
	 * @return				New ratings database with the specified movies
	 */
	public MovieRatings getRatingsByMovies(ArrayList<Integer> movieIDs) {
		MovieRatings resRatings; 	//Resultant rating database
		
		 resRatings = new MovieRatings();	
		
		// Add all of the ratings for the specified movies to the new
		// ratings database
		for (Entry<Integer, ArrayList<UserRatingData>> entry : ratings.entrySet()) {
			int key = entry.getKey();			// Current user ID
			ArrayList<UserRatingData> value;	// Rating data for the user
			
			value = entry.getValue();
			
			for (int i = 0; i < value.size(); i++) {
				UserRatingData userData;	// Rating data for the movie
				
				userData = value.get(i);
				
				// Add the rating data to the resultant list
				if (movieIDs.contains(userData.getMovieID())) 
					resRatings.addMovie(userData);
			}	
		}
		
		return resRatings;
	}
	
	/**
	 * Returns all the user IDs from the ratings database.
	 * 
	 * @return		All the user ratings from the database
	 */
	public ArrayList<Integer> getUserIDs() {
		ArrayList<Integer> res;			// Resultant user IDs
		
		res = new ArrayList<Integer>();
	
		// Add all of the user IDs from the ratings database to the resultant
		// user ID array
		for (Entry<Integer, ArrayList<UserRatingData>> entry : 
			ratings.entrySet()) {
			int key = entry.getKey();		// Current user ID
			res.add(key);
		}
		
		return res;
	}
	
	/**
	 * Returns the average rating by all users for a movie.
	 * 
	 * @param movieID		Movie to get average rating for
	 * 
	 * @return				Average rating for the movie
	 */
	public int getAverageRatingByMovie(int movieID) {
		int res = 0;			// Resultant average rating
		int numRatings = 0;		// Number of ratings for the movie

		// Find all the users rated the movie, add up all the ratings, and
		// average the scores.
		for (Entry<Integer, ArrayList<UserRatingData>> entry : ratings.entrySet()) {
			int key = entry.getKey();			// Current user ID
			ArrayList<UserRatingData> value;	// Rating data for the user
			
			value = entry.getValue();
			
			// Determine if the user current user rated the movie
			for (int i = 0; i < value.size(); i++) {
				UserRatingData userData;		// User rating
				
				userData = value.get(i);
				
				// If the user rated the movie, add the rating to the average
				if (userData.getMovieID() == movieID) {
					res = res + userData.getMovieRating();
					numRatings++;
				}
			}	
		}
		
		return res / numRatings;
	}
	
	/**
	 * Set the database iterator to the beginning of the database.
	 */
	public void beginningOfList() {
		itr = ratings.keySet().iterator();
	}
	
	/**
	 * Return the user ID where the iterator currently is, and sets the iterator
	 * to the next position in the database. This method must not be called of
	 * the iterator is at the end of the list
	 * 
	 * @return		User ID 
	 */
	public int getCurrentItem() {
		return (Integer)itr.next();
	}
	
	
	public ArrayList<UserRatingData> getCurrentUserRatings() {
		return ratings.get((Integer)itr.next());
	}
	
	/**
	 * Determines if the iterator is at the end of the database.
	 * 
	 * @return		True is returned if the iterator is at the end of the 
	 * 				database. Otherwise, false is returned.
	 */
	public boolean endOfList() {
		return !itr.hasNext(); 
	}
}
