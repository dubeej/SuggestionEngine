package GATest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import GA.Individual;

public class IndividualTest {
	private Individual indiv;
	
	@Before
	public void setUp() {
		indiv = new Individual();
	}
	
	@Test
	public void testGenome() {
		for (int i = 0; i < 7; i++) {
			indiv.setGene(i, i);
			assertEquals((int)indiv.getGene(i), i);
		}
	}
	
	@Test
	public void testFitness() {
		indiv.setFitness(10);
		assertEquals((int)indiv.getFitness(), 10);
	}
}
