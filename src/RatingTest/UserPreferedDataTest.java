package RatingTest;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import Rating.UserPreferedData;

public class UserPreferedDataTest {
	UserPreferedData userPref;
	
	@Before
	public void setUp() {
		userPref = new UserPreferedData();
	}
	
	@Test
	public void testDirector() {
		userPref.setDirector("director");
		assertEquals(userPref.getDirector(), "director");
	}
	
	@Test
	public void testWriter() {
		userPref.setWriter("writer");
		assertEquals(userPref.getWriter(), "writer");
	}
	
	@Test
	public void testActor() {
		userPref.setActor("actor");

		assertEquals(userPref.getActor(), "actor");
	}
	
	@Test
	public void testMeanRating() {
		userPref.setMeanRating(5);
		assertEquals(userPref.getMeanRating(), 5);
	}
	
}
