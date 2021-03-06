package pacsus.main;
/* Generated by Together */

/**
 * For a description of University member permits, follow hyperlink to the
 * Administration use case for issuing a new University member permit.
 */
public class University_member_permit extends Permit
{
    /**
     * The date that this permit was issued. This information is required in case a
     * member of the University leaves the University part way through the year and
     * becomes eligible for a pro-rata rebate.
     * 
     * @label Issued on
     * @clientCardinality 1
     * @supplierCardinality 1
     * @link aggregation
     * @directed
     */
    private Date lnkDate;

    /**
     * Constructor to create a University_member_permit. Calls the Permit
     * constructor to deal with the permitHolder and permittedVehicles and assignsa
     * date locally.
     * 
     * @param permitHolder      - The name of the holder of the permit
     * @param permittedVehicles - The registration and vehicle information on the
     *                          permit
     * @param date              - The date it was assigned
     */
    public University_member_permit(String permitHolder, Vehicle_info permittedVehicles, Date date)
    {
	super(permitHolder, permittedVehicles);
	this.lnkDate = date;
    }

    @Override
    public String getStatus()
    {
	return super.getStatus() + "\nDate permit was issued: " + lnkDate.getDayNumber();
    }

    @Override
    /**
     * Getter declared in Permit overridden
     */
    public Date getDate()
    {
	return lnkDate;
    }
}
