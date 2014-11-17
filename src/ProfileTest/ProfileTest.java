package ProfileTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import Profile.ProfileData;

public class ProfileTest {
	private ProfileData profile;
	
	@Before
	public void setUp() {
		profile = new ProfileData();
	}
	
	@Test
	public void testUserID() {
		profile.setUserID(234);
		assertEquals(profile.getUserID(), 234);
	}
	
	@Test
	public void testMovieID() {
		profile.setMovieID(56);
		assertEquals(profile.getMovieID(), 56);
	}
	
	@Test
	public void testRating() {
		profile.setRating(3);
		assertEquals(profile.getRating(), 3);
	}
	
	@Test
	public void testAge() {
		profile.setAge(33);
		assertEquals(profile.getAge(), 33);
	}
	
	@Test
	public void testGender() {
		profile.setGender(1);
		assertEquals(profile.getGender(), 1);
	}
	
	@Test
	public void testOccupation() {
		profile.setOccupation(4);
		assertEquals(profile.getOccupation(), 4);
	}
	
	@Test
	public void testGenres() {
		int [] genres;
		
		genres = new int[ProfileData.MAX_GENRES];
		
		for (int i = 0; i < ProfileData.MAX_GENRES; i++) 
			genres[i] = 1;
		
		profile.setGenres(genres);
		
		for (int i = 0; i < ProfileData.MAX_GENRES; i++) 
			assertEquals(profile.getGenres()[i], genres[i]);
	}
}
