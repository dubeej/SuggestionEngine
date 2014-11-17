package RatingTest;

import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import Rating.UserRatingData;

public class UserRatingDataTest {

	private UserRatingData userData;

	@Before
	public void setUp() {
		userData = new UserRatingData();
	}
	
	private int randomVal(int maximum, int minimum) {
		Random rn = new Random();
		int range = maximum - minimum + 1;
		return rn.nextInt(range) + minimum;
	}
	
	@Test
	public void testUserID() {
		int randomNum = randomVal(2649429, 0);
		
		userData.setUserID(randomNum);
		assertEquals(userData.getUserID(), randomNum);
		
	}
	
	@Test
	public void testMovieID() {
		int randomNum = randomVal(5, 0);
		
		userData.setMovieID(randomNum);
		assertEquals(userData.getMovieID(), randomNum);
	}
	
	@Test
	public void testMovieRating() {
		int randomNum = randomVal(5, 0);
		
		userData.setRating(randomNum);
		assertEquals(userData.getMovieRating(), randomNum);
	}
	
	@Test
	public void testDateRated() {
		int randomNum = randomVal(9999999, 0);
		userData.setTimestamp(randomNum);
		assertEquals(userData.getTimestamp(), randomNum);
		
	}

}
