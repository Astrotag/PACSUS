package pacsus.main;
/* Generated by Together */

/**
 * Abstract superclass of all permit types. Note that since this class is
 * abstract, all "instances" of it are actually instances of its concrete
 * subclasses. The vehicles permitted entry by this permit are recorded here in
 * the permittedVehicles attribute. Further information could be recorded here,
 * such as contact details for the permit holder (and for the hosts in Regular
 * and Day visitor subclasses), but in this design these details are not
 * required. Note that no permit holder may have more than one permit at any
 * time, so no two instances of Permit (through its concrete sub-classes) may
 * have the same permit holder name. There is no direct notation for this.
 * Instead the Permit list object collecting the Permit must enforce it by
 * checking new additions (a hash table will help with this).
 *
 * For subclasses with dates: No permits are issued to span from one year into
 * the next; instead re-issue occurs "automatically" at the start of the year
 * (see the Timer use case diagram - follow hyperlink) - all permits are simply
 * carried over to the new year (except Day visitor and Regular visitor permits
 * that expire on the last day of the year). Permits which have a limited period
 * of validity (Day visitor and Regular visitor permits) are automatically
 * cancelled from PACSUS at the start of the day following their last valid day.
 */
abstract public class Permit
{
    /**
     * The name of the permit holder
     */
    private String permitHolder;

    /**
     * Counts the number of days on which the campus was entered while the access
     * barriers were in operation. Counting starts when the permit is issued, and
     * afresh at the start of each year.
     */
    private int noOfEntries = 0;

    /**
     * Counts the number of warnings issued to vehicles registered on this permit.
     */
    private int warnings = 0;

    /**
     * False if the permit has not been suspended, and true if it has (on the third
     * warning).
     */
    private boolean suspended = false;

    /**
     * Set to false at the start of each day. Remains false until first entry of a
     * vehicle on this permit, when it is set true, and the vehicle is noted in
     * vehicleUsed. Used for checking that subsequent entries in the day are the
     * same vehicle (since exits are not monitored).
     */
    private boolean enteredToday = false;

    /**
     * Once a vehicle has entered on this permit on any day, this attribute records
     * the vehicle that entered, so that any subsequent entries associated with this
     * permit can be verified as the same vehicle (or at least with the same
     * registration number!). The attribute will be null until a vehicle has
     * entered.
     * 
     * @clientCardinality 1
     * @directed true
     * @label Allowed today
     * @supplierCardinality 0..1
     */
    private Vehicle_info vehicleUsedToday;

    /**
     * This holds references to all the Vehicle_info instances for the vehicles
     * registered to this permit. Note that any vehicle can be registered to only
     * one permit, but many vehicles may be registered to the same permit. This
     * attribute must be implemented by a collection data structure (such as array,
     * hash table,...).
     * 
     * @clientCardinality 1
     * @directed true
     * @label Controls access of
     * @supplierCardinality 0..*
     */
    private Vehicle_info permittedVehicles;

    /**
     * Constructor for a Permit which can be called in it's sub classes.
     * 
     * @param permitHolder      - the permits holder
     * @param permittedVehicles - the vehicles permitted on the permit
     */
    public Permit(String permitHolder, Vehicle_info permittedVehicles)
    {
	this.permitHolder = permitHolder;
	this.permittedVehicles = permittedVehicles;
    }

    /**
     * Getter to retrieve the warnings on the permit
     * 
     * @return warnings on the permit
     */
    public int getWarnings()
    {
	return warnings;
    }

    /**
     * Getter to retrieve the permit holder
     * 
     * @return the permit holder
     */
    public String getPermitHolder()
    {
	return permitHolder;
    }

    /**
     * Setter to set the permits warnings
     * 
     * @param warnings - amount of warnings
     */
    public void setWarnings(int warnings)
    {
	this.warnings += warnings;
	checkSuspended();
    }

    /**
     * A method to determine if there is enough warnings to suspend the permit
     */
    public void checkSuspended()
    {
	if (this.warnings == 3)
	{
	    setSuspended(true);
	}
	else
	{
	    setSuspended(false);
	}
    }

    /**
     * Getter to retrieve if the permit is suspended
     * 
     * @return true/false - if the permit is suspended or not
     */
    public boolean isSuspended()
    {
	return suspended;
    }

    /**
     * Setter to set if the permit is suspended or not
     * 
     * @param suspended - true if the permit is suspended, false if not.
     */
    public void setSuspended(boolean suspended)
    {
	this.suspended = suspended;
    }

    /**
     * A toString method overridden from the Object class
     */
    @Override
    public String toString()
    {
	return "Permit:" + permitHolder + "-" + permittedVehicles.getReg();
    }

    /**
     * A method to give the functionality to the status enquiry
     * 
     * @return a String with the permits details
     */
    public String getStatus()
    {
	return "Permit Holder Name: " + permitHolder + "\nNumber of Warnings: " + warnings + "\nSuspended: " + suspended
		+ "\nNumber of Entries: " + noOfEntries;
    }

    /**
     * An abstract method to be implemented in the subclasses.
     * 
     * @return the Date
     */
    abstract Date getDate();

    /**
     * A getter to return the number of times a permit has been used and entered
     * 
     * @return the number of entries
     */
    public int getNoOfEntries()
    {
	return noOfEntries;
    }

    /**
     * A setter to set the amount of times a vehicle has entered
     * 
     * @param noOfEntries - How many times the vehicle has entered
     */
    public void setNoOfEntries(int noOfEntries)
    {
	this.noOfEntries = noOfEntries;
    }

    /**
     * A getter to return if the associated vehicle has entered campus today
     * 
     * @return enteredToday - true if it has entered, false if it hasn't
     */
    public boolean isEnteredToday()
    {
	return enteredToday;
    }

    /**
     * A setter to set if a vehicle has entered today.
     * 
     * @param enteredToday - true if it has entered, false if it hasn't
     */
    public void setEnteredToday(boolean enteredToday)
    {
	this.enteredToday = enteredToday;
    }
}