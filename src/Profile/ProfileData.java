package Profile;

import java.util.Arrays;

/**
 * This class provides a user profile that contains a user's ID, movie ID,
 * rating, age, gender, occupation, prefered genres, and average rating.
 * These values can be set, and retrieved.
 * 
 * @author James Dubee
 */

public class ProfileData {
	public static final int MAX_RATING = 5;		// Maximum rating
	public static final int MAX_AGE = 99;			// Maximum age
	public static final int MAX_OCCUPATION = 21;	// Maximum occupation
	public static final int MAX_GENRES = 18;		// Maximum genres
	
	private int userID;		// User ID
	private int movieID;	// Movie ID
	private int rating;		// Movie rating
	private int age;		// User age
	private int gender;		// User gender
	private int occupation;	// User occupation
	private int [] genres;	// User's prefered genres
	private int avgRating;	// User's average rating
	
	/**
	 * Initializes the PDM to default values.
	 */
	public ProfileData() {
		genres = new int [MAX_GENRES];
	}
	
	/**
	 * Sets the user ID.
	 * 
	 * @param user		User's ID
	 */
	public void setUserID(int user) {
		userID = user;
	}
	
	/**
	 * Returns the user's ID.
	 * 
	 * @return		User's ID
	 */
	public int getUserID() {
		return userID;
	}
	
	/**
	 * Sets the movie ID.
	 * 
	 * @param movie		The Movie ID to set
	 */
	public void setMovieID(int movie) {
		movieID = movie;
	}
	
	/**
	 * Returns the movie ID.
	 * 
	 * @return		The Movie ID
	 */
	public int getMovieID() {
		return movieID;
	}
	
	/**
	 * Sets the movie rating.
	 * 
	 * @param userRating	Movie rating to set
	 */
	public void setRating(int userRating) {
		rating = userRating;
	}
	
	/**
	 * Returns the movie rating.
	 * 
	 * @return		The movie rating
	 */
	public int getRating() {
		return rating;
	}
	
	/**
	 * Sets the user's age.
	 * 
	 * @param userAge		The user's age
	 */
	public void setAge(int userAge) {
		age = userAge;
	}
	
	/**
	 * Returns the user's age.
	 * 
	 * @return		The user's age
	 */
	public int getAge() {
		return age;
	}
	
	/**
	 * Sets the user's gender.
	 * 
	 * @param userGender		The user's gender
	 */
	public void setGender(int userGender) {
		gender = userGender;
	}
	
	/**
	 * Returns the user's gender.
	 * 
	 * @return		The user's gender
	 */
	public int getGender() {
		return gender;
	}
	
	/**
	 * Sets the user's occupation.
	 * 
	 * @param userOccupation		The user's occupation
	 */
	public void setOccupation(int userOccupation) {
		occupation = userOccupation;
	}
	
	/**
	 * Returns the user's occupation.
	 * 
	 * @return		The user's occupation
	 */
	public int getOccupation() {
		return occupation;
	}
	
	/**
	 * Sets the user's prefered genres. The array passed must
	 * be of length MAX_GENRES, and consist of 1s for genres
	 * the users prefered, or 0s otherwise.
	 * 
	 * @param userGenres	Prefered genres
	 */
	public void setGenres(int [] userGenres) {
		// Set each genre for the user.
		for (int i = 0; i < MAX_GENRES; i++) 
			genres[i] = userGenres[i];
	}
	
	/**
	 * Returns the users prefered genres.
	 * 
	 * @return		Prefered genres
	 */
	public int [] getGenres() {
		return genres;
	}

	/**
	 * Returns the weight of the movie rating between
	 * zero and one.
	 * 
	 * @return		Rating weight
	 */
	public double getRateWeight() {
		return (double)(rating) / MAX_RATING;
	}
	
	/** 
	 * Returns the weight of the user's age between
	 * zero and one.
	 * 
	 * @return		Age weight
	 */
	public double getAgeWeight() {
		return (double)(age) / MAX_AGE;
	}
	
	/**
	 * Returns the weight of the user's gender between
	 * zero and one.
	 * 
	 * @return		Gender weight
	 */
	public double getGenderWeight() {
		return (double)(gender);
	}
	
	/**
	 * Returns the weight of the user's occupation
	 * between zero and one.
	 * 
	 * @return		Occupation weight
	 */
	public double getOccupationWeight() {
		return (double)(occupation) / MAX_OCCUPATION;
	}
	
	/**
	 * Returns the weight of the user's prefered genres in
	 * an array of size MAX_GENRES with each item in the
	 * array containing the weight of that genre between
	 * zero and one.
	 * 
	 * @return		Genre weights
	 */
	public double [] getGenresWeight() {
		double [] res;		// Resultant weights
		
		res = new double [MAX_GENRES];
		
		// Calculate the weight for each genre
		for (int i = 0; i < MAX_GENRES; i++) 
			res[i] = (double)(genres[i]) / MAX_GENRES;
		
		return res;
	}
	
	/**
	 * Sets the average rating for the user.
	 * 
	 * @param userRating		User's average rating
	 */
	public void setAvgRating(int userRating) {
		avgRating = userRating;
	}
	
	/**
	 * Returns the user's average rating.
	 * 
	 * @return		User's average rating
	 */
	public int getAvgRating() {
		return avgRating;
	}
	
	/**
	 * Determines if one profile data object is the same as another.
	 * 
	 * @return		True is returned of both objects are the same. Otherwise,
	 * 				false is returned.
	 */
	@Override
	public boolean equals(Object in) {
		ProfileData data = (ProfileData)(in);	// Comparison object
		
		return ((data.userID == userID) && (data.movieID == movieID) && 
				(data.rating == rating) && (data.age == age) && 
				(data.gender == gender) && (data.occupation == occupation) &&
				Arrays.equals(data.genres, genres));
	}
	
	/**
	 * Returns a string representation of the profile.
	 * 
	 * @return		The string representation
	 */
	public String toString() {
		String res;		// Resultant string
		
		res = String.format("User ID: %d, Movie ID: %d, Rating: %d, Age: %d, Gender: %d, Occupation: %d", 
				userID, movieID, rating, age, gender, occupation);
		
		res = res + " Genres: " + genres[0];
		
		// Add each genre to the string
		for (int i = 0; i < 18; i++)
			res = res + "," + genres[i];
		
		return res + "\n";
	}
	
}
