package pacsus.main;

import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

/* Generated by Together */

/**
 * Permit list manages the collection of permits currently issued and not yet
 * cancelled (or expired). It handles most of the use cases in the
 * Administration section. Note that each Permit must have a unique permit
 * holder name (so a HashTable is probably a good implementation of the
 * collection, with permit holder name as key).
 *
 * There will only be one instance of this class.
 */
public class Permit_list {
	/**
	 * The Permit list maintains a collection of the Permits currently issued.
	 *
	 * This association must be implemented by an attribute holding a collection
	 * data structure (for example: array, hash table - the latter is recommended).
	 *
	 * Note that no two Permits may have the same permit holder name (this
	 * information is not represented diagrammatically).
	 * 
	 * @associates Permit
	 * @label Contains
	 * @clientCardinality 1
	 * @supplierCardinality 0..*
	 * @directed
	 */
	
	private java.util.Hashtable<String, Permit> lnkPermit;

	public Permit_list() {
		lnkPermit = new Hashtable<String, Permit>();
		// System.out.println(lnkPermit.toString());
	}

	public boolean add(String key, Permit p) {
		if (!lnkPermit.containsKey(key)) {
			lnkPermit.put(key, p);

			return true;
		}

		else
			return false;
	}

	public String[] populateList() {
		Object[] keys = lnkPermit.keySet().toArray();
		Object[] permits = lnkPermit.values().toArray();

		String[] strings = new String[keys.length];

		for (int i = 0; i < keys.length; i++) {
			strings[i] = "" + permits[i].toString();
		}

		return strings;
	}

	public int size() {
		return lnkPermit.size();
	}

	/**
	 * A public method which takes in the key value for the permit, finds the
	 * associated permit and either removes or adds a warning.
	 * 
	 * @param key  - The name of the permit holder
	 * @param type - value to check if adding or removing a permit
	 */
	public void warnings(String key, int type) {
		if (type == 1) {
			if (lnkPermit.get(key).getWarnings() < 3) {
				lnkPermit.get(key).setWarnings(1);
			}
		}

		else {
			if (lnkPermit.get(key).getWarnings() > 0) {
				lnkPermit.get(key).setWarnings(-1);
			}
		}

		// For testing purposes
		// System.out.println(lnkPermit.get(key) + " " +
		// lnkPermit.get(key).getWarnings());
	}

	public void unsuspendPermit(String key) {

		if (lnkPermit.get(key).getWarnings() == 3) {
			lnkPermit.get(key).setWarnings(-3);
		}

		// For testing purposes
		// System.out.println(lnkPermit.get(key) + " " +
		// lnkPermit.get(key).getWarnings());
	}

	public Permit getPermit(String key) {
		return lnkPermit.get(key);
	}

	public boolean update(String key, Permit p) {
		if (lnkPermit.containsKey(key)) {
			lnkPermit.replace(key, p);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Removes a value from the hashtable with a key
	 * 
	 * @param key Name of the Permit holder
	 * @return True: if the value has been removed. False: if it hasn't.
	 */
	public boolean deletePermit(String key) {
		Permit permit = getPermit(key);
		if (lnkPermit.remove(key) != null)
			return true;
		return false;
	}

	public void newDay(Date date) {
		
	}
	
}