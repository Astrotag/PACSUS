package pacsus.main;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;

/* Generated by Together */

/**
 * Vehicle list manages the collection of vehicles currently associated with
 * permits. It handles checks of whether vehicles are allowed through the
 * barriers, records warnings, and various other functions. Note that each
 * Vehicle_info object must have a unique registration number, to allow sensible
 * checking and recording of entries onto the campus (so a HashTable is probably
 * a good implementation of the collection, with registration number as key).
 *
 * There will only be one instance of this class.
 */
public class Vehicle_list
{
    /**
     * The Vehicle list maintains a collection of the known Vehicle_infos associated
     * with all permits. This association must be implemented by an attribute
     * holding a collection data structure (for example: array, hash table - the
     * latter is recommended).
     *
     * Note that no two Vehicle_infos may have the same registration number (this
     * information is not represented diagrammatically) - this is to guarantee
     * consistency with the constraint that no vehicle is associated with more than
     * one permit.
     *
     * Note that, given a registration number, the Vehicle_list can look up the
     * appropriate Vehicle_info instance, and via that it can obtain the vehicle's
     * permit information.
     * 
     * @associates Vehicle_info
     * @label Contains
     * @clientCardinality 1
     * @supplierCardinality 0..*
     * @directed
     */
    private java.util.Hashtable<String, Vehicle_info> lnkVehicle;

    public Vehicle_list()
    {
	lnkVehicle = new Hashtable<String, Vehicle_info>();
    }

    public boolean add(String reg, Vehicle_info vh)
    {
	if (!lnkVehicle.containsValue(reg))
	{
	    lnkVehicle.put(reg, vh);
	    System.out.println("Added: " + vh + " " + reg);
	    return true;

	}
	else
	{
	    System.out.println("Not added");
	    return false;
	}
    }

    public Hashtable<String, Vehicle_info> updateList()
    {
	return this.lnkVehicle;
    }

    public boolean findVehicle(String regNo)
    {
	regNo = regNo.trim();
	regNo = regNo.toUpperCase(Locale.ROOT);
	return lnkVehicle.get(regNo) != null;
    }

    public ArrayList<String> getRegs()
    {
	String[] regs = new String[lnkVehicle.size()];
//		Vehicle_info[] vhs = (Vehicle_info[]) lnkVehicle.values().toArray();

//		for(int i = 0; i < lnkVehicle.size(); i++) {
//			regs[i] = vhs[i].getReg();
//		}

	ArrayList<String> keys = new ArrayList<>(lnkVehicle.keySet());
	for (String s : keys)
	{
	    System.out.println(s);
	}

	return keys;
    }

    @Override
    public String toString()
    {
	return "Vehicle_list [lnkVehicle=" + lnkVehicle + "]";
    }

    public Permit getVehiclePermit(String regNo)
    {
	return lnkVehicle.get(regNo).getPermit();
    }

}