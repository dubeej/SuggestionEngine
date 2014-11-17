package RatingTest;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import Rating.WeightRatings;

public class WeightRatingsTest {

	private WeightRatings weights;
	
	@Before
	public void setUp() {
		weights = new WeightRatings();
		
	}
	
	@Test
	public void testAddDirectorRating() {
		weights.addDirectorRating("test1", 2);
		assertEquals(weights.getDirectorRating("test1"), 2);
		
		weights.addDirectorRating("test1", 5);
		assertEquals(weights.getDirectorRating("test1"), 7);
		
		weights.addDirectorRating("test2", 1);
		assertEquals(weights.getDirectorRating("test2"), 1);
	}
	
}
