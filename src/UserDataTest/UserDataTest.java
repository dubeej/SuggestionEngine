package UserDataTest;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import Users.UserData;

public class UserDataTest {
	private UserData user;
	
	@Before
	public void setUp() {
		user = new UserData();
	}
	
	@Test
	public void testUserID() {
		user.setID(234);
		assertEquals(user.getID(), 234);
	}
	
	@Test
	public void testAge() {
		user.setAge(33);
		assertEquals(user.getAge(), 33);
	}
	
	@Test
	public void testGender() {
		user.setGender(1);
		assertEquals(user.getGender(), 1);
	}
	
	@Test
	public void testOccupation() {
		user.setOccupation(4);
		assertEquals(user.getOccupation(), 4);
	}
}
