package Rating;

import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;

import Movies.Movies;

/**
 * This class provides the functionality to determine the prefered director,
 * writer, and actor for a user from a database of user ratings and movies.
 * 
 * @author James Dubee
 */
public class Prefered {
	private Movies movies;								// Movie database
	private MovieRatings ratings;						// Ratings database
	private TreeMap<Integer, UserPreferedData> prefs;	// Preferences
	private int size;
	
	/**
	 * Sets the PDMs to default values.
	 */
	public Prefered() {
		prefs = new TreeMap<Integer, UserPreferedData>();
		movies = new Movies();
		ratings = new MovieRatings();
		size = 0;
	}
	
	/**
	 * Sets the PDMs to non-default values.
	 * 
	 * @param movieList		Movie database
	 * @param ratingList	Ratings database
	 */
	public Prefered(Movies movieList, MovieRatings ratingList) {
		prefs = new TreeMap<Integer, UserPreferedData>();
		movies = movieList;
		ratings = ratingList;
		size = 0;
		
		findPreferences();
	}
	
	/**
	 * Returns the user preferences for a user.
	 * 
	 * @param userID		ID to get preferences for
	 * 
	 * @return			User preferences
	 */
	public UserPreferedData getPrefered(int userID) {
		return prefs.get(userID); 
	}
	
	/**
	 * Creates a new preference database that consists only of the user IDs
	 * provided.
	 * 
	 * @param userIDs		User IDs to create new preference database from
	 * 
	 * @return			Resultant preference database
	 */
	public Prefered getPrefered(ArrayList<Integer> userIDs) {
		Prefered res = new Prefered();		// Resultant preference database
		
		// Add all the preferences for the give users to the resultant
		// database
		for (int i = 0; i < userIDs.size(); i++) {
			UserPreferedData userData = prefs.get(userIDs.get(i));
			res.addPreference(userIDs.get(i), userData);
		}
		
		return res;
	}
	
	/**
	 * Determine the prefered director, writer, and actor for each user 
	 * based on movies they liked.
	 */
	public void findPreferences() {
		ratings.beginningOfList();
		
		// Determine the each user's preferences
		while (!ratings.endOfList()) {
			int userID = ratings.getCurrentItem();	// Current user ID
			
			// Movies liked by the user
			ArrayList<Integer> liked = ratings.getLikedMovies(userID);
			
			// User preference data
		    UserPreferedData userPref = new UserPreferedData();
		    
		    userPref.setMeanRating(ratings.getAverageRating(userID));
		    userPref.setDirector(findPreferedDirector(liked));
		    userPref.setWriter(findPreferedWriter(liked));
		    userPref.setActor(findPreferedActor(liked));
			addPreference(userID, userPref);
		}
	}
	
	/**
	 * Returns the prefered director based on a list of prefered movies.
	 * 
	 * @param liked		Prefered movies
	 * @return		Prefered director
	 */
	private String findPreferedDirector(ArrayList<Integer> liked) {
		// Directors of liked movies
		ArrayList<String> directors = movies.getDirectors(liked);
		String mostCommon = null;	// Must common director
	    String last = null;			// Last director
		int mostCount = 0;			// Most common director count
	    int lastCount = 0;			// Last director count
		
		Collections.sort(directors);

		// Determine the most common director 
	    for (int i = 0; i < directors.size(); i++) {
	        if (directors.get(i).equals(last)) {
	            lastCount++;
	        } else if (lastCount > mostCount) {
	            mostCount = lastCount;
	            mostCommon = last;
	        }
	        last = directors.get(i);
	    }

	    return mostCommon;
	}
	
	/**
	 * Returns the prefered writer based on a list of prefered movies.
	 * 
	 * @param liked		Prefered movies
	 * @return		Prefered writer
	 */
	private String findPreferedWriter(ArrayList<Integer> liked) {
		// Writers of liked movies
		ArrayList<String> writers = movies.getWriters(liked);
		String mostCommon = null;	// Must common director
	    String last = null;			// Last director
		int mostCount = 0;			// Most common director count
	    int lastCount = 0;			// Last director count
		
		Collections.sort(writers);
	    
		// Determine the most common writer 
	    for (int i = 0; i < writers.size(); i++) {
	        if (writers.get(i).equals(last)) {
	            lastCount++;
	        } else if (lastCount > mostCount) {
	            mostCount = lastCount;
	            mostCommon = last;
	        }
	        last = writers.get(i);
	    }

	    return mostCommon;
	}
	
	/**
	 * Returns the prefered actor based on a list of prefered movies.
	 * 
	 * @param liked		Prefered movies
	 * @return		Prefered actor
	 */
	private String findPreferedActor(ArrayList<Integer> liked) {
		// Actors of liked movies
		ArrayList<String> actors = movies.getActors(liked);
		String mostCommon = null;	// Must common director
	    String last = null;			// Last director
		int mostCount = 0;			// Most common director count
	    int lastCount = 0;			// Last director count
		
		Collections.sort(actors);
	    
		// Determine the most common writer 
	    for (int i = 0; i < actors.size(); i++) {
	        if (actors.get(i).equals(last)) {
	            lastCount++;
	        } else if (lastCount > mostCount) {
	            mostCount = lastCount;
	            mostCommon = last;
	        }
	        last = actors.get(i);
	    }

	    return mostCommon;
	}
	
	/**
	 * Adds a user preference to the prefered database.
	 * 
	 * @param userID		User ID
	 * @param userPref		User preferences
	 */
	public void addPreference(int userID, UserPreferedData userPref) {
		UserPreferedData pref = prefs.get(userID);	// User data

		if (pref == null) {
		     prefs.put(userID, userPref);
		     size++;
		}
	}
	
	/**
	 * Returns the number of preferences in the database.
	 * 
	 * @return		Size of the database
	 */
	public int size() {
		return size;
	}
}