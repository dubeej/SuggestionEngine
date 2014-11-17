package Rating;

/**
 * This class allows for the storage and retrieval of a user's prefered
 * director, writer, and actor. The user's mean rating for movies is also
 * stored, and retrieved.
 * 
 * @author James Dubee
 *
 */
public class UserPreferedData {
	private String director;		// Prefered director
	private String writer;			// Prefered writer
	private String actor;			// Prefered actor
	private int meanRating;			// Average movie rating

	/**
	 * Sets the prefered director.
	 * 
	 * @param movieDirector		Prefered director
	 */
	public void setDirector(String movieDirector) {
		director = movieDirector;
	}
	
	/**
	 * Returns the prefered director.
	 * 
	 * @return		Prefered director
	 */
	public String getDirector() {
		return director;
	}
	
	/**
	 * Sets the prefered writer.
	 * 
	 * @param movieWriter		Prefered writer
	 */
	public void setWriter(String movieWriter) {
		writer = movieWriter;
	}
	
	/**
	 * Returns the prefered writer.
	 * 
	 * @return		Prefered writer
	 */
	public String getWriter() {
		return writer;
	}
	
	/**
	 * Sets the prefered actor.
	 * 
	 * @param movieActor		Prefered actor
	 */
	public void setActor(String movieActor) {
		actor = movieActor;
	}
	
	/**
	 * Returns the prefered actors.
	 * @return
	 */
	public String getActor() {
		return actor;
	}
	
	/**
	 * Sets the average rating for the user.
	 * 
	 * @param rating		User's average rating
	 */
	public void setMeanRating(int rating) {
		meanRating = rating;
	}
	
	/**
	 * Returns the mean rating for the user.
	 * 
	 * @return		User's average rating
	 */
	public int getMeanRating() {
		return meanRating;
	}
	
	/**
	 * Returns a string representation of the object.
	 */
	@Override
	public String toString() {
		return String.format(
				"Director: %s, Writer: %s, Actor: %s, Mean Rating: %d", 
				director, writer, actor, meanRating);
	}
	
}
