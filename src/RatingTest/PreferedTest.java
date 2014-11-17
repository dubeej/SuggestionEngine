package RatingTest;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import Movies.Movies;
import Rating.MovieRatings;
import Rating.Prefered;
import Rating.UserPreferedData;

public class PreferedTest {
	private final static String TRAINING_SET = "u1.base";
	private final static String DIRECTORY =
			"C:\\Users\\Dubee\\Desktop\\ml-100k\\ml-100k\\";
	
	private MovieRatings ratings;
	private Movies movies;
	private Prefered pref;
	
	@Before
	public void setUp() {
		ratings = new MovieRatings();
		movies = new Movies();
		
		try {
			ratings.load(DIRECTORY + TRAINING_SET);
			movies.load("text3.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		pref = new Prefered(movies, ratings);
	}
	
	@Test
	public void testGetPreferedData() {
		UserPreferedData prefData = pref.getPrefered(5);
		assertEquals(prefData.getDirector(), "Steven Spielberg");
		assertEquals(prefData.getWriter(), "Gregory Widen");
		assertEquals(prefData.getActor(), "William Shatner");
	}
	
	@Test
	public void testGetPrefered() {
		Prefered newPref;
		ArrayList<Integer> userIDs;
		
		userIDs = new ArrayList<Integer>();
		userIDs.add(1);
		userIDs.add(2);
		
		newPref = pref.getPrefered(userIDs);

		assertEquals(newPref.size(), 2);
	}
	
	
	
}
