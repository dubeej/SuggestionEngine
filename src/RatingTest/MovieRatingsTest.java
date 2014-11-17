package RatingTest;


import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import Rating.*;



public class MovieRatingsTest {
	private final static String TRAINING_SET = "u1.base";
	private final static String DIRECTORY = 
			"C:\\Users\\Dubee\\Desktop\\ml-100k\\ml-100k\\";
	
	MovieRatings ratings;
	
	@Before
	public void setUp() {
		ratings = new MovieRatings();
		
		try {
			ratings.load(DIRECTORY + TRAINING_SET);		
			assertEquals(ratings.size(), 80000);
		} catch (FileNotFoundException e) {
			 System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testAverageRating() {
		assertEquals(ratings.getAverageRating(1), 3);
	}
	
	@Test
	public void testGetRatedMovies() {
		ArrayList<Integer> ratedMovies = ratings.getRatedMovies(1);
		assertEquals(ratedMovies.size(), 135);
	}
	
	@Test
	public void testGetLikedMovies() {
		ArrayList<Integer> ratedMovies = ratings.getLikedMovies(1);
		assertEquals(ratedMovies.size(), 108);
	}
	
	@Test
	public void testGetRating() {
		assertEquals(ratings.getRating(1, 1), 5);
	}
	
	@Test
	public void testGetRatingsByMovies() {
		MovieRatings newRatings;
		ArrayList<Integer> movieIDs;
		
		movieIDs = new ArrayList<Integer>();
		movieIDs.add(1);
		movieIDs.add(2);
		
		newRatings = ratings.getRatingsByMovies(movieIDs);
		assertEquals(newRatings.size(), 488);
	}
	
	
	@Test
	public void testGetUserIDs() {
		ArrayList<Integer> userIDs;
		userIDs = ratings.getUserIDs();
		assertEquals(userIDs.size(), 943);
	}
	
	@Test
	public void testGetAverageRatingByMovie() {
		assertEquals(ratings.getAverageRatingByMovie(1), 3);
	}
	
}
