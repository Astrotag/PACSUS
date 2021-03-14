package pacsus.main;
/* Generated by Together */

/**
 * For a description of Regular visitors, follow hyperlink to the Administration
 * use case for issuing a new Regular visitor permit.
 */
public class Regular_visitor_permit extends Permit
{


	/**
	 * The name of the University member hosting the visit.
	 */
	private String hostName;

	/**
	 * The date that the visit starts - entry will not be allowed before this date.
	 * 
	 * @label Starting on
	 * @clientCardinality 1
	 * @supplierCardinality 1
	 * @link aggregation
	 * @directed
	 */
	private Date lnkStartDate;

	/**
	 * The date that the visit ends - entry will not be allowed after this date.
	 * 
	 * @label Ending on
	 * @clientCardinality 1
	 * @supplierCardinality 1
	 * @link aggregation
	 * @directed
	 */
	private Date lnkEndDate;

	public Regular_visitor_permit(String permitHolder, Vehicle_info permittedVehicles, String visiting, Date start, Date end ) {
		super(permitHolder, permittedVehicles);
		this.hostName = visiting;
		this.lnkStartDate = start;
		this.lnkEndDate = end;


	}
	
	@Override
	public String getStatus() {
		return super.getStatus() + "\nUniversity Host member name: " + hostName + "\nDate visit starts: "
					+ lnkStartDate.getDayNumber() + "\nDate visit ends: " + lnkEndDate.getDayNumber();
	}
}

