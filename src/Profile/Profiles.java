package Profile;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.Map.Entry;

/**
 * This class provides a database of user profile data. That data can be
 * set, and retrieved.
 * 
 * @author James Dubee
 *
 */
public class Profiles {
	// User profile database
	private TreeMap<Integer, ArrayList<ProfileData>> profiles;
	private int numProfiles;							// Number of ratings
	private Iterator itr;								// Database iterator

	/**
	 * Initializes the PDMs to default values
	 */
	public Profiles() {
		profiles = new TreeMap<Integer, ArrayList<ProfileData>>();
		numProfiles = 0;
	}
	
	/**
	 * Returns the number of ratings in the database
	 * @return
	 */
	public int size() {
		return numProfiles;
	}

	/**
	 * Adds a profile to the profile database.
	 * 
	 * @param profile		Profile to add
	 */
	public void addProfile(ProfileData profile) {
		ArrayList<ProfileData> userProfiles;		// Profiles for the user

		userProfiles = profiles.get(profile.getUserID());
		
		// Add the user profile to the database if it does not already exist
		if (userProfiles == null) {
			userProfiles = new ArrayList<ProfileData>();
		    profiles.put(profile.getUserID(), userProfiles);
		    userProfiles.add(profile);
		    numProfiles++;
		} else {
			if (userProfiles.indexOf(profile) < 0) {
				userProfiles.add(profile);
				numProfiles++;
			}
		}
	}
	
	/**
	 * Removes a profile from the database.
	 * 
	 * @param userID		User to remove
	 */
	public void removeProfile(int userID) {
		profiles.remove(userID);
	}
	
	/**
	 * Returns a new profiles database of profiles who have ratied the
	 * movie IDs passed to the method.
	 * 
	 * @return		New profile database
	 */
	public Profiles getProfilesByMovies(ArrayList<Integer> movieIDs) {
		Profiles res;		// Resultant profile database
		
		res = new Profiles();
		
		// Add all of the profile who rated movies in the movie ID list
		// to the new profile database
		for (Entry<Integer, ArrayList<ProfileData>> entry : profiles.entrySet()) {
			ArrayList<ProfileData> value;	// Rating data for the user
			
			value = entry.getValue();
			
			// Determine if the user current user rated the movie
			for (int i = 0; i < value.size(); i++) {
				ProfileData profile;		// User profile
				
				profile = value.get(i);
				
				// Add the profile to the new profiles database
				if (movieIDs.contains(profile.getMovieID())) 
					res.addProfile(profile);
			}	
		}
		
		return res;
	}
	
	/**
	 * Returns a new profile database containing the profiles for the 
	 * user ID passed to the method.
	 * 
	 * @param userID		User ID to build new profiles from
	 * 
	 * @return				New profiles database
	 */
	public Profiles getProfilesByUser(int userID) {
		ArrayList<ProfileData> userProfiles;		// Profiles for the user
		Profiles res;								// Resultant profiles
		
		res = new Profiles();
	
		userProfiles = profiles.get(userID);
		
		// Add each of the user's profiles to the new profiles database
		for (int i = 0; i < userProfiles.size(); i++) {
			ProfileData profile = (ProfileData)(userProfiles.get(i));
			res.addProfile(profile);
		}
		
		return res;
	}
	
	/**
	 * Set the database iterator to the beginning of the database.
	 */
	public void beginningOfList() {
		itr = profiles.keySet().iterator();
	}
	
	/**
	 * Return the user ID where the iterator currently is, and sets the iterator
	 * to the next position in the database. This method must not be called of
	 * the iterator is at the end of the list
	 * 
	 * @return		User ID 
	 */
	public int getCurrentItem() {
		return (Integer)itr.next();
	}
	
	/**
	 * Return the profiles for the current user.
	 * 
	 * @return		Profiles of the current user
	 */
	public ArrayList<ProfileData> getCurrentProfiles() {
		return profiles.get((Integer)itr.next());
	}
	
	/**
	 * Return the profiles for a specific user.
	 * 
	 * @param userID		User to get profiles for
	 * 
	 * @return		Profile data for the user
	 */
	public ArrayList<ProfileData> getProfilesForUser(int userID) {
		return profiles.get(userID);
	}
	
	/**
	 * Determines if the iterator is at the end of the database.
	 * 
	 * @return		True is returned if the iterator is at the end of the 
	 * 				database. Otherwise, false is returned.
	 */
	public boolean endOfList() {
		return !itr.hasNext(); 
	}
}
