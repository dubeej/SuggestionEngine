package Users;

public class UserData {
	private int id;			// User ID
	private int age;		// User age
	private int gender;		// User gender
	private int occupation;	// User occupation
	
	/**
	 * Sets the user ID.
	 * 
	 * @param user		User's ID
	 */
	public void setID(int user) {
		id = user;
	}
	
	/**
	 * Returns the user's ID.
	 * 
	 * @return		User's ID
	 */
	public int getID() {
		return id;
	}
	
	/**
	 * Sets the user's age.
	 * 
	 * @param userAge		The user's age
	 */
	public void setAge(int userAge) {
		age = userAge;
	}
	
	/**
	 * Returns the user's age.
	 * 
	 * @return		The user's age
	 */
	public int getAge() {
		return age;
	}
	
	/**
	 * Sets the user's gender.
	 * 
	 * @param userGender		The user's gender
	 */
	public void setGender(int userGender) {
		gender = userGender;
	}
	
	/**
	 * Returns the user's gender.
	 * 
	 * @return		The user's gender
	 */
	public int getGender() {
		return gender;
	}
	
	/**
	 * Sets the user's occupation.
	 * 
	 * @param userOccupation		The user's occupation
	 */
	public void setOccupation(int userOccupation) {
		occupation = userOccupation;
	}
	
	/**
	 * Returns the user's occupation.
	 * 
	 * @return		The user's occupation
	 */
	public int getOccupation() {
		return occupation;
	}
	
	/**
	 * Returns a string representation of the profile.
	 * 
	 * @return		The string representation
	 */
	public String toString() {
		return String.format("ID: %d, Age: %d, Gender: %d, Occupation: %d\n", 
				id, age, gender, occupation);
	}
}
