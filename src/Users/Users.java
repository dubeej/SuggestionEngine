package Users;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Iterator;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.Map.Entry;

/**
 * This class provides a database of users that can be set, and retreieved. 
 * 
 * @author James Dubee
 */
public class Users {
	private TreeMap<Integer, UserData> users;		// User database
	private Iterator itr;							// Iterator for database
	int numUsers;									// Size of the database
	
	/**
	 * Sets the PDMs to default values.
	 */
	public Users() {
		users = new TreeMap<Integer, UserData>();
		numUsers = 0;
	}
	
	/**
	 * Loads a user database from file. The format of the database must consist
	 * of the following: user id | age | gender | occupation | zip code
	 * 
	 * @param filename					Filename of the database
	 * @throws FileNotFoundException
	 */
	public void load(String filename) throws FileNotFoundException {
		FileReader reader;			// File reader for the database
		Scanner input;				// Scanner for the database
		String inputLine = null;	// Current input line
	
		// Attempt to open the file
		try {
			reader = new FileReader(filename);
			input = new Scanner(reader);
			
			// Read each line from the file
			while (input.hasNextLine()) {
				
				UserData user;	// Resultant user movie data
				user = new UserData();
				
				inputLine = input.nextLine();
				
				String tokens[] = inputLine.split("[|]+");

				user.setID(Integer.parseInt(tokens[0]));
				user.setAge(Integer.parseInt(tokens[1]));
				
				if (tokens[2].equals("M"))
					user.setGender(0);
				else 
					user.setGender(1);
				
				user.setOccupation(Integer.parseInt(tokens[3]));
				
				addUser(Integer.parseInt(tokens[0]), user);
			}
			
			input.close();
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException(
					String.format("\"%s\" was not found.\n", filename));
		}
	}
	
	/**
	 * Add a user and its information to the database
	 * 
	 * @param userID		ID of the user
	 * @param userData		Data of the user
	 */
	public void addUser(int userID, UserData userData) {
		UserData user = users.get(userID);	// User to be added

		// Add the user if it does not exist in the database
		if (user == null) {
		     users.put(userID, userData);
		     numUsers++;
		}
	}
	
	/**
	 * Set the database iterator to the first element.
	 */
	public void beginningOfList() {
		itr = users.keySet().iterator();
	}
	
	/**
	 * Returns the user ID of the current position of the iterator. The 
	 * iterator is then incremented to the next position. The iterator must
	 * not be at the end of the database when this method is called.
	 * 
	 * @return	Current movie ID of the iterator
	 */
	public int getCurrentMovie() {
		return (Integer)itr.next();
	}
	
	/**
	 * Returns the user data for the current user.
	 * 
	 * @return		User data for the current user
	 */
	public UserData getCurrentUserData() {
		return users.get((Integer)itr.next());
	}
	
	/**
	 * Returns the user data for a given user.
	 * 
	 * @param userID		User to get user data for
	 * 	
	 * @return		The user's data
	 */
	public UserData getUserData(int userID) {
		return users.get(userID);
	}
	
	/**
	 * Determine if the iterator is at the end of the database.
	 * 
	 * @return		True is returned of the iterator is at the end of the 
	 * 				database. Otherwise, false is returned.
	 */
	public boolean endOfMovies() {
		return !itr.hasNext(); 
	}
	
	/**
	 * Returns a string representation of the user data data.
	 * 
	 * @return 		The string representation
	 */
	public String toString() {
		String res = null;		// Resultant string

		for (Entry<Integer, UserData> entry : users.entrySet()) {
			UserData value = entry.getValue();	// Movie data
			res = res + value;
		}
		
		return res;
	}
}
