package Rating;

/**
 * This class allows for the storage and retreival of rating information. The
 * data consists of a user ID, movie ID, rating for the movie, and the time
 * at which the movie was rated. 
 * 
 * @author James Dubee
 */

public class UserRatingData {
	private int id;			// User ID
	private int movieID;	// Movie ID
	private int rating;		// Movie rating
	private int time;		// Time of rating
	
	/**
	 * Sets the user ID for a rating.
	 * 
	 * @param userID		User ID
	 */
	public void setUserID(int userID) {
		id = userID;
	}
	
	/**
	 * Returns the user ID for a rating.
	 * 
	 * @return		User ID
	 */
	public int getUserID() {
		return id;
	}
	
	/**
	 * Sets the movie ID for the rating.
	 * 
	 * @param movie		Movie ID
	 */
	public void setMovieID(int movie) {
		movieID = movie;
	}
	
	/**
	 * Returns the movie ID for a rating.
	 * 
	 * @return		Move ID
	 */
	public int getMovieID() {
		return movieID;
	}
	
	/** 
	 * Sets the rating for a movie.
	 * 
	 * @param movieRating		Rating for a movie
	 */
	public void setRating(int movieRating) {
		rating = movieRating;
	}
	
	/**
	 * Returns the rating for a movie.
	 * 
	 * @return		Movie rating
	 */
	public int getMovieRating() {
		return rating;
	}
	
	/**
	 * Sets the time the rating occurred.
	 * 
	 * @param timestamp		Time of rating
	 */
	public void setTimestamp(int timestamp) {
		time = timestamp;
	}
	
	/**
	 * Returns the time of a rating.
	 * 
	 * @return		Time of raitng
	 */
	public int getTimestamp() {
		return time;
	}
	
	/**
	 * Returns a string representation of the object.
	 * 
	 * @return 		String representation of the object
	 */
	@Override
	public String toString() {
		return String.format("User: %d, Movie: %d, Rating: %d, Time: %d", id, 
				movieID, rating, time);
	}
	
	/**
	 * Determines if one rating object is the same as another.
	 * 
	 * @return		True is returned of both objects are the same. Otherwise,
	 * 				false is returned.
	 */
	@Override
	public boolean equals(Object in) {
		UserRatingData data = (UserRatingData)(in);
		
		return ((data.id == id) && (data.movieID == movieID) && 
				(data.rating == rating) && (data.time == time));
	}
	
	
}
