package MovieTest;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import Movies.Movies;

public class MovieTest {
	private Movies movies;
	
	@Before
	public void setUp() {
		movies = new Movies();
		try {
			movies.load("text3.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetMovies() {
		ArrayList<Integer> movieIDs;
		Movies newMovies;
		
		movieIDs = new ArrayList<Integer>();
		
		movieIDs.add(1);
		movieIDs.add(2);
		
		newMovies = movies.getMovies(movieIDs);
		
		assertEquals(newMovies.size(), newMovies.size());
	}
	
	@Test
	public void testSize() {
		assertEquals(movies.size(), 1678);
	}
	
	@Test
	public void testGetDirectors() {
		ArrayList<String> directors;
		directors = movies.getDirectors();
		assertEquals(directors.size(), 806);
	}
	
	@Test
	public void testGetMoviesByDirector() {
		ArrayList<Integer> movieIDs;
		
		movieIDs = movies.getMoviesByDirector("Steven Spielberg");
		assertEquals(movieIDs.size(), 6);
	}
	
	@Test
	public void testGetDirectorsByMovies() {
		ArrayList<String> directors;
		ArrayList<Integer> movieIDs;
		
		movieIDs = new ArrayList<Integer>();
		
		movieIDs.add(1);
		movieIDs.add(2);
		directors = movies.getDirectors(movieIDs);
		assertEquals(directors.size(), 2);
	}
	
	@Test
	public void testGetWritersByMovies() {
		ArrayList<String> wrtiers;
		ArrayList<Integer> movieIDs;
		
		movieIDs = new ArrayList<Integer>();
		
		movieIDs.add(1);
		movieIDs.add(2);
		wrtiers = movies.getDirectors(movieIDs);
		assertEquals(wrtiers.size(), 2);
	}
	
	@Test
	public void testGetActorsByMovies() {
		ArrayList<String> actors;
		ArrayList<Integer> movieIDs;
		
		movieIDs = new ArrayList<Integer>();
		
		movieIDs.add(1);
		movieIDs.add(2);
		actors = movies.getDirectors(movieIDs);
		assertEquals(actors.size(), 2);
	}
	
	@Test
	public void testGetDirectorsByMovie() {
		String [] directors;
		directors = movies.getDirectors(1);
		
		assertEquals(directors[0], "John Lasseter");
	}
	
	@Test
	public void testGetWritersByMovie() {
		String [] writers;
		writers = movies.getWriters(1);
		System.out.println(writers[0]);
		assertEquals(writers[0], "John Lasseter");
	}
}
