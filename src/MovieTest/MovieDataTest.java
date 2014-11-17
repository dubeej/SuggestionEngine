package MovieTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import Movies.MovieData;

public class MovieDataTest {

	private MovieData data;
	
	@Before
	public void setup() {
		data = new MovieData();
	}
	
	@Test
	public void testID() {
		data.setID("id");
		assertEquals(data.getID(), "id");
	}
	
	@Test
	public void testTitle() {
		data.setTitle("title");
		assertEquals(data.getTitle(), "title");
	}
	
	@Test
	public void testVideoReleaseDate() {
		data.setVideoReleaseDate("release date");
		assertEquals(data.getVideoReleaseDate(), "release date");
	}
	
	@Test
	public void testIMDBURL() {
		data.setIMDB("IMDB");
		assertEquals(data.getIMDB(), "IMDB");
	}
	
	@Test
	public void testGenres() {
		int [] genre1;
		int [] genre2;
		
		genre1 = new int[19];
		for (int i = 0 ; i < 19; i++)
			genre1[i] = i;
		
		data.setGenres(genre1);
		
		genre2 = data.getGenres();
		
		for (int i = 0 ; i < 19; i++) 
			assertEquals(genre1[i], genre2[i]);
	}
	
	@Test
	public void testLength() {
		data.setLength("length");
		assertEquals(data.getLength(), "length");
	}
	
	@Test
	public void testCensor() {
		data.setCensorRating("censor");
		assertEquals(data.getCensorRating(), "censor");
	}
	
	@Test
	public void testLanguage() {
		data.setLanguage("language");
		assertEquals(data.getLanguage(), "language");
	}
	
	@Test
	public void testCountry() {
		data.setCountry("country");
		assertEquals(data.getCountry(), "country");
	}
	
	@Test
	public void testDirectors() {
		String [] directors1;
		String [] directors2;
		
		directors1 = new String[2];
		for (int i = 0 ; i < 2; i++)
			directors1[i] = Integer.toString(i);
		
		data.setDirectors(directors1);
		
		directors2 = data.getDirectors();
		
		for (int i = 0 ; i < 2; i++) 
			assertEquals(directors1[i], directors2[i]);
	}
	
	@Test
	public void testWriters() {
		String [] wrtiers1;
		String [] writers2;
		
		wrtiers1 = new String[2];
		for (int i = 0 ; i < 2; i++)
			wrtiers1[i] = Integer.toString(i);
		
		data.setWriters(wrtiers1);
		
		writers2 = data.getWriters();
		
		for (int i = 0 ; i < 2; i++) 
			assertEquals(wrtiers1[i], writers2[i]);
	}
	
	@Test
	public void testActors() {
		String [] actors1;
		String [] actors2;
		
		actors1 = new String[2];
		for (int i = 0 ; i < 2; i++)
			actors1[i] = Integer.toString(i);
		
		data.setStars(actors1);
		
		actors2 = data.getStars();
		
		for (int i = 0 ; i < 2; i++) 
			assertEquals(actors1[i], actors2[i]);
	}
}
