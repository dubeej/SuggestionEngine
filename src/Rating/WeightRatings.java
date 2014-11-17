package Rating;

import java.util.TreeMap;
/**
 * This class allows the ratings of directors, writers, and actors to be 
 * stored and average. Those average ratings can then be retrieved.
 * 
 * @author James Dubee
 */

public class WeightRatings {
	private TreeMap<String, Integer> directors;		// Director ratings
	
	/**
	 * Initializes the PDMs to default values.
	 */
	public WeightRatings() {
		directors = new TreeMap<String, Integer>();
	}
	
	/**
	 * Adds a director, and rating for that director to the director ratings.
	 * 
	 * @param director		Director to rate
	 * @param rating		Rating for the director
	 */
	public void addDirectorRating(String director, int rating) {
		Integer totalRating = directors.get(director);	// Current director
		
		// Add the director and rating if the director doesn't exist. Otherwise,
		// update the directors rating.
		if (totalRating == null) {
			directors.put(director, rating);
		} else {
			totalRating = totalRating + rating;
			directors.put(director, totalRating);
		}
	}
	
	/**
	 * Returns a rating for a given director.
	 * 
	 * @param director		Director to get a rating for
	 * @return		Rating for the given director
	 */
	public int getDirectorRating(String director) {
		return directors.get(director);
	}
}
