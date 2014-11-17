package Movies;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.Map.Entry;

/**
 * This class provides the functionality to load movie data from file, so that
 * it may be used to perform some task. Information about the movie may be 
 * retrieved in various ways. 
 * 
 * @author James Dubee
 *
 */

public class Movies {
	private TreeMap<Integer, MovieData> movies;		// Movie database
	private Iterator itr;							// Iterator for database
	int numMovies;									// Size of the database
	
	/**
	 * Sets the PDMs to default values.
	 */
	public Movies() {
		movies = new TreeMap<Integer, MovieData>();
		numMovies = 0;
	}
	
	/**
	 * Loads a movie database from file. The format of the database must consist
	 * of the following:
	 * 
	 * Movie id|movie title|video release date|IMDb URL|unknown|Action|
	 * Adventure|Animation|Children's|Comedy|Crime|Documentary|Drama|Fantasy|
     * Film-Noir|Horror|Musical|Mystery|Romance|Sci-Fi|Thriller|War|Western|
     * Director1|Director2|Writer1|Writer2|Star1|Star2|Star3
     * 
	 * @param filename					Filename of the database
	 * @throws FileNotFoundException
	 */
	public void load(String filename) throws FileNotFoundException {
		FileReader reader;			// File reader for the database
		Scanner input;				// Scanner for the database
		String inputLine = null;	// Current input line
	
		// Attempt to open the file
		try {
			reader = new FileReader(filename);
			input = new Scanner(reader);
			
			// Read each line from the file
			while (input.hasNextLine()) {
				
				MovieData movieData;	// Resultant user movie data
				int [] genres;			// Movie genres
				String directors [];	// Movie directors
				String writers [];		// Movie writers
				String stars [];		// Movie stars
				
				movieData = new MovieData();
				genres = new int [18];
				directors = new String [2];
				writers = new String [2];
				stars = new String [3];
				
				inputLine = input.nextLine();
				
				String tokens[] = inputLine.split("[|]+");

				movieData.setID(tokens[0]);
				movieData.setTitle(tokens[1]);
				movieData.setVideoReleaseDate(tokens[2]);
				movieData.setIMDB(tokens[3]);
				
				// Store each genre
				for (int i = 5; i < 23; i++) 
					genres[i - 5] = Integer.parseInt(tokens[i]);
				
				movieData.setGenres(genres);
				movieData.setLength(tokens[23]);
				movieData.setCensorRating(tokens[24]);
				movieData.setLanguage(tokens[25]);
				movieData.setCountry(tokens[26]);
				
				// Read each director
				for (int i = 27; i < 29; i++) {
					if (tokens[i].equals("null"))
						directors[i - 27] = null;
					else
						directors[i - 27] = tokens[i];
				}
				
				movieData.setDirectors(directors);
				
				// Read each writer
				for (int i = 29; i < 31; i++) {
					if (tokens[i].equals("null"))
						writers[i - 29] = null;
					else
						writers[i - 29] = tokens[i];
				}
				
				movieData.setWriters(writers);
				
				// Read each star
				for (int i = 31; i < 34; i++) {
					if (tokens[i].equals("null")) 
						stars[i - 31] = null;
					else
						stars[i - 31] = tokens[i];
				}
				
				movieData.setStars(stars);
				
				addMovie(Integer.parseInt(tokens[0]), movieData);
			}
			
			input.close();
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException(
					String.format("\"%s\" was not found.\n", filename));
		}
	}
	
	/**
	 * Add a movie and its information to the database
	 * 
	 * @param movieID		ID of the movie
	 * @param movieData		Data of the movie
	 */
	public void addMovie(int movieID, MovieData movieData) {
		MovieData movie = movies.get(movieID);	// Movie to be added

		// Add the movie if it does not exist in the database
		if (movie == null) {
		     movies.put(movieID, movieData);
		     numMovies++;
		}
	}
	
	/**
	 * Returns the movie data for a given movie ID.
	 * 
	 * @param movieID		ID of the movie
	 * 
	 * @return				Data of the movie
	 */
	public MovieData getMovie(Integer movieID) { 
		return movies.get(movieID);
	}
	
	/**
	 * Return a new movie database consisting of only the movies in the 
	 * movieIDs array.
	 * 
	 * @param movieIDs			IDs of movies to retrieve
	 * 
	 * @return					Movie database consisting of only the specified
	 * 							movies
	 */
	public Movies getMovies(ArrayList<Integer> movieIDs) {
		Movies res;				// Resultant movie database
		
		res = new Movies();
		
		// Add each movie desired movie to the new movie database
		for (int i = 0; i < movieIDs.size(); i++) {
			MovieData movie = movies.get(movieIDs.get(i));

			if (movie != null) {
			     res.addMovie(movieIDs.get(i), movie);
			}
		}
		
		return res;
	}
	
	/**
	 * Return all of the directors in the movie database.
	 * 
	 * @return		Array consisting of all the movies in the movie database.
	 */
	public ArrayList<String> getDirectors() {
		ArrayList<String> res;		// Resultant directors
		
		res = new ArrayList<String>();
		
		// Add each director of the movie database to an array
		for (Entry<Integer, MovieData> entry : movies.entrySet()) {
			String [] directors;				// Movie directors
			MovieData value = entry.getValue();	// Movie data
			
			directors = value.getDirectors();
			
			// Add each director for the current movie to the resultant
			// director array
			for (int i = 0; i < directors.length; i++) {
				if (directors[i] != null) {
					if (!res.contains(directors[i])) {
						res.add(directors[i]);
					}
				}
			}
		}
		
		return res;
	}
	
	/**
	 * Returns all of the movieIDs for a given director.
	 * 
	 * @param director		Director of movies to get
	 * 
	 * @return				Movie IDs directed by the director
	 */
	public ArrayList<Integer> getMoviesByDirector(String director) {
		ArrayList<Integer> res;		// Resultant movie IDs
		
		res = new ArrayList<Integer>();
		
		// Add each movie by the director to the resultant movie IDs array
		for (Entry<Integer, MovieData> entry : movies.entrySet()) {
			int key = entry.getKey();			// Movie ID
			String [] directors;				// Movie directors
			MovieData value = entry.getValue();	// Movie data
			
			directors = value.getDirectors();
			
			// Add the current movie ID to the array if it was directed by
			// the given director
			for (int i = 0; i < directors.length; i++) {
				if (directors[i] != null) {
					if (directors[i].equals(director)) {
						res.add(key);
					}
				}
			}
		}
		
		return res;
	}
	
	public int [] getGenresByMovie(int movieID) {
		int [] res = null;		// Resultant movie genres
		
		// Add each movie by the director to the resultant movie IDs array
		for (Entry<Integer, MovieData> entry : movies.entrySet()) {
			int key = entry.getKey();			// Movie ID
			MovieData value = entry.getValue();	// Movie data
			
			if (key == movieID) {
				res = value.getGenres();
				break;
			}
		}
		
		return res;
	}
	
	/**
	 * Set the database iterator to the first element.
	 */
	public void beginningOfList() {
		itr = movies.keySet().iterator();
	}
	
	/**
	 * Returns the movie ID of the current position of the iterator. The 
	 * iterator is then incremented to the next position. The iterator must
	 * not be at the end of the database when this method is called.
	 * 
	 * @return	Current movie ID of the iterator
	 */
	public int getCurrentMovie() {
		return (Integer)itr.next();
	}
	
	/**
	 * Determine if the iterator is at the end of the database.
	 * 
	 * @return		True is returned of the iterator is at the end of the 
	 * 				database. Otherwise, false is returned.
	 */
	public boolean endOfMovies() {
		return !itr.hasNext(); 
	}

	/**
	 * Returns a list of directors that directed the movies in the movie list.
	 * 
	 * @param movieIDs		List of movies to get directors from
	 * 
	 * @return		List of directors that directed the movies
	 */
	public ArrayList<String> getDirectors(ArrayList<Integer> movieIDs) {
		ArrayList<String> directors;		// Resultant directors
		
		directors = new ArrayList<String>();
		
		// Add each director who directed a movie in the movie list to the 
		// resultant array
		for (int i = 0; i < movieIDs.size(); i++) {
			MovieData movie = movies.get(movieIDs.get(i));	// Current movie
			
			if (movie != null) {
				String [] movDirs = movie.getDirectors();
				
				// Add each director for the current movie to the resultant
				// list
				for (int j = 0; j < movDirs.length; j++) {
					if (movDirs[j] != null)
						directors.add(movDirs[j]);
				}
			}
		}
		
		return directors;
	}
	
	/**
	 * Returns a list of writers that wrote the movies in the movie list.
	 * 
	 * @param movieIDs		List of movies to get writers from
	 * 
	 * @return		List of writers that wrote the movies
	 */
	public ArrayList<String> getWriters(ArrayList<Integer> movieIDs) {
		ArrayList<String> writers;		// Resultant writers
		
		writers = new ArrayList<String>();
		
		// Add each writer who wrote a movie in the movie list to the 
		// resultant array
		for (int i = 0; i < movieIDs.size(); i++) {
			MovieData movie = movies.get(movieIDs.get(i));
			
			if (movie != null) {
				String [] movWriters = movie.getWriters();
				
				// Add each writer for the current movie to the resultant
				// list
				for (int j = 0; j < movWriters.length; j++) {
					if (movWriters[j] != null)
						writers.add(movWriters[j]);
				}
			}
			
		}
		
		return writers;
	}
	
	/**
	 * Returns a list of actors that acted in the movies in the movie list.
	 * 
	 * @param movieIDs		List of movies to get actors from
	 * 
	 * @return		List of actors that acted the movies
	 */
	public ArrayList<String> getActors(ArrayList<Integer> movieIDs) {
		ArrayList<String> actors;		// Resultant actors
		
		actors = new ArrayList<String>();
		
		// Add each actor who acted in a movie in the movie list to the 
		// resultant array
		for (int i = 0; i < movieIDs.size(); i++) {
			MovieData movie = movies.get(movieIDs.get(i));
			
			if (movie != null) {
				String [] movActors = movie.getStars();
				
				// Add each actor for the current movie to the resultant
				// list
				for (int j = 0; j < movActors.length; j++) {
					if (movActors[j] != null)
						actors.add(movActors[j]);
				}
			}
			
		}
		
		return actors;
	}
	
	/**
	 * Returns the directors for a given movie.
	 * 
	 * @param movieID		Movie to get directors from
	 * 
	 * @return				Directors of the movie
	 */
	public String [] getDirectors(int movieID) {
		MovieData movie = movies.get(movieID);	// Movie data
		
		if (movie != null) 
			return movie.getDirectors();
		else
			return null;
	}
	
	/**
	 * Returns the writers for a given movie.
	 * 
	 * @param movieID		Movie to get writers from
	 * 
	 * @return				Writers of the movie
	 */
	public String [] getWriters(int movieID) {
		MovieData movie = movies.get(movieID);	// Movie data
		
		if (movie != null) 
			return movie.getWriters();
		else
			return null;
	}

	/**
	 * Return the size of the database.
	 * 
	 * @return		Size of the database
	 */
	public int size() {
		return numMovies;
	}
}
