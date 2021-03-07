package pacsus.main;
/* Generated by Together */

/**
 * For a description of Day visitors, follow hyperlink to the Administration use
 * case for issuing a new Day visitor permit.
 */
public class Day_visitor_permit extends Permit
{
   

	/**
     * The name of the University member hosting the visit.
     */
    private String hostName;

    /**
     * The date that the visitor is visiting on - entry will be allowed on that date
     * only.
     * 
     * @clientCardinality 1
     * @supplierCardinality 1
     * @label Visiting on
     * @link aggregation
     * @directed
     */
    private Date lnkDate;
    
    
    public Day_visitor_permit(String permitHolder, Vehicle_info permittedVehicles,String visiting, Date date) {
		super(permitHolder, permittedVehicles);
		this.hostName = visiting;
		this.lnkDate = date;
	}
}
