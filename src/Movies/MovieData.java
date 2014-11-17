package Movies;

/**
 * This class provides the ability to store attributes about a movie. These
 * attributes consist of movie ID, movie title, video release date, IMDB URL,
 * genre, movie length, censorship rating, language of the movie, country the
 * movie was made in, directors of the movie, writers of the movie, and the
 * stars of the movie.
 * 
 * @author James Dubee
 */

public class MovieData {
	private String id;					// Movie ID
	private String title;				// Movie title
	private String videoReleaseDate;	// Date of video release
	private String imdbURL;				// IMDB URL for movie
	private int [] genre;				// Movie genre
	private String length;				// Movie length
	private String censorRating;		// Censor rating for movie
	private String language;			// Language of movie
	private String country;				// Country movie was made in
	private String [] directors;		// Movie directors
	private String [] writers;			// Movie writers
	private String [] stars;			// Movie stars

	/**
	 * Sets the PDMs to default values
	 */
	public MovieData() {
		id = null;
		title = null;
		videoReleaseDate = null;
		imdbURL = null;
		genre = new int [18];
		length = null;
		censorRating = null;
		language = null;
		country = null;
		directors = new String [2];
		writers = new String [2];
		stars = new String [3];
	}
	
	/**
	 * Sets the movie id to the given value.
	 * 
	 * @param movieID		ID of the movie
	 */
	public void setID(String movieID) {
		id = movieID;
	}
	
	/**
	 * Returns the movie ID.
	 * 
	 * @return		ID of the movie
	 */
	public String getID() {
		return id;
	}
	
	/**
	 * Sets the title for a movie.
	 * 
	 * @param movieTitle		Title of the movie
	 */
	public void setTitle(String movieTitle) {
		title = movieTitle;
	}
	
	/**
	 * Returns the title of a movie.
	 * 
	 * @return		Title of the movie
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Sets the release date for the movie.
	 * 
	 * @param dateReleased		Release date of the movie
	 */
	public void setVideoReleaseDate(String dateReleased) {
		videoReleaseDate = dateReleased;
	}
	
	/**
	 * Returns the release date of the movie.
	 * 
	 * @return		Release date of the movie
	 */
	public String getVideoReleaseDate() {
		return videoReleaseDate;
	}
	
	/**
	 * Sets the IMDB URL for the movie.
	 * 
	 * @param url		IMDB URL for the movie
	 */
	public void setIMDB(String url) {
		imdbURL = url;
	}
	
	/**
	 * Returns the IMDB URL for the movie.
	 * 
	 * @return		IMDB URL for the movie
	 */
	public String getIMDB() {
		return imdbURL;
	}
	
	/**
	 * Sets the genres for the movie. The genres array must consists of an array
	 * of 19 genres. The movie consists of a genre of the item is 1. Otherwise,
	 * a 0 denotes that the movie is not of that genre.
	 * 
	 * @param genres		Genres of the movie
	 */
	public void setGenres(int [] genres) {
		for (int i = 0; i < genres.length; i++) 
			genre[i] = genres[i];
	}
	
	/**
	 * Returns an array of 19 values that denotes the genres for the movie.
	 * An item consisting of 1 signifies that the movie is of that genre. 
	 * Otherwise, an item of value 0 denotes that the movie is not of that
	 * genre.
	 * 
	 * @return		Genres of the movie
	 */
	public int [] getGenres() {
		return genre;
	}
	
	/**
	 * Sets the length of the movie.
	 * 
	 * @param movieLength		Length of the movie
	 */
	public void setLength(String movieLength) {
		length = movieLength;
	}
	
	/**
	 * Returns the length of the movie.
	 * 
	 * @return		Length of the movie
	 */
	public String getLength() {
		return length;
	}
	
	/**
	 * Sets the censorship rating of the movie.
	 * 
	 * @param rating		Censorship rating of the movie
	 */
	public void setCensorRating(String rating) {
		censorRating = rating;
	}
	
	/**
	 * Returns the censorship rating of a movie.
	 * 
	 * @return			Censorship rating of the movie
	 */
	public String getCensorRating() {
		return censorRating;
	}
	
	/**
	 * Sets the language for a movie.
	 * 
	 * @param movieLanguage		Language of the movie
	 */
	public void setLanguage(String movieLanguage) {
		language = movieLanguage;
	}
	
	/**
	 * Returns the language of a movie.
	 * 
	 * @return		Language of the movie
	 */
	public String getLanguage() {
		return language;
	}
	
	/**
	 * Sets the country the movie was produced in.
	 * 
	 * @param movieCountry		Country of the movie
	 */
	public void setCountry(String movieCountry) {
		country = movieCountry;
	}
	
	/**
	 * Returns the country the movie was produced in.
	 * 
	 * @return			Country of the movie
	 */
	public String getCountry() {
		return country;
	}
	
	
	/**
	 * Sets the directors of the movie. The directors must in an array 
	 * consisting of two directors.
	 * 
	 * @param movieDirectors		Directors of the movie
	 */
	public void setDirectors(String [] movieDirectors) {
		for (int i = 0; i < movieDirectors.length; i++)
			directors[i] = movieDirectors[i];
	}
	
	/**
	 * Returns an array of two items that denote the directors of the movie.
	 * 
	 * @return		Directors of the movie
	 */
	public String [] getDirectors() {
		return directors;
	}
	
	/**
	 * Sets the writers of the movie. The writers must in an array consisting
	 * of two writers.
	 * 
	 * @param movieWriters 		Writers of the movie	
	 */
	public void setWriters(String [] movieWriters) {
		for (int i = 0; i < movieWriters.length; i++)
			writers[i] = movieWriters[i];
	}
	
	/**
	 * Returns an array of two items that denote the writers of the movie.
	 * 
	 * @return		Writers of the movie
	 */
	public String [] getWriters() {
		return writers;
	}
	
	/**
	 * Sets the actors of the movie. The actors must in an array consisting of
	 * three actors.
	 * 
	 * @param movieStars		Stars of the movie
	 */
	public void setStars(String [] movieStars) {
		for (int i = 0; i < movieStars.length; i++)
			stars[i] = movieStars[i];
	}
	
	/**
	 * Returns an array of three items that denote the stars of the movie.
	 * 
	 * @return		Stars of hte movie
	 */
	public String [] getStars() {
		return stars;
	}
	
	/**
	 * Returns a string representation of the movie data.
	 */
	@Override
	public String toString() {
		String res = null;		// Resultant string
		  
		res = String.format("%s|%s|%s|%s", id, title, videoReleaseDate, 
				imdbURL);
		
		// Add each genre to the string
		for (int i = 0; i < genre.length; i++) 
			res = res + "|" + Integer.toString(genre[i]);
		
		res = res + String.format("|%s|%s|%s|%s", length, censorRating, 
				language, country);
		 
		// Add each director to the string
		for (int i = 0; i < directors.length; i++) 
			res = res + "|" + directors[i];
		
		// Add each writer to the string
		for (int i = 0; i < writers.length; i++) 
			res = res + "|" + writers[i];
		
		// Add each star to the string
		for (int i = 0; i < stars.length; i++) 
			res = res + "|" + stars[i];
		
		return res;
	}
}
