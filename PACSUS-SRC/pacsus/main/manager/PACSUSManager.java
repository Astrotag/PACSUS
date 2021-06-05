package pacsus.main.manager;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import pacsus.main.permits.Day_visitor_permit;
import pacsus.main.permits.Permanent_visitor_permit;
import pacsus.main.permits.Permit_list;
import pacsus.main.permits.Regular_visitor_permit;
import pacsus.main.permits.University_member_permit;
import pacsus.main.ui.System_status;
import pacsus.main.utility.Date;
import pacsus.main.vehicles.Vehicle_info;
import pacsus.main.vehicles.Vehicle_list;

public class PACSUSManager
{

    private Vehicle_list lnkVehicle_list;
    private System_status lnkSystem_status;
    private Permit_list lnkPermit_list;

    private String[] permitStrings;

    public PACSUSManager(System_status systemStatus, Vehicle_list vehicleList, Permit_list permitList)
    {
	setLnkVehicle_list(vehicleList);
	setLnkSystem_status(systemStatus);
	setLnkPermit_list(permitList);

	populatePermitList();
    }

    /**
     * this method is mostly for testing purposes by populating the permit and
     * vehicle lists with some values
     * 
     * @return
     */
    public Permit_list populatePermitList()
    {
	Vehicle_info vi = new Vehicle_info("YT14HBB");
	University_member_permit gm = new University_member_permit("Greig", vi, new Date(1));
	lnkPermit_list.add(gm.getPermitHolder(), gm);
	vi.setLnkPermit(gm);

	Vehicle_info vi_2 = new Vehicle_info("SL07HAU");
	University_member_permit jk = new University_member_permit("Joanes", vi_2, new Date(1));
	lnkPermit_list.add(jk.getPermitHolder(), jk);
	vi_2.setLnkPermit(jk);

	Vehicle_info vi_3 = new Vehicle_info("NC02XZT");
	University_member_permit rk = new University_member_permit("Ryan", vi_3, new Date(1));
	lnkPermit_list.add(rk.getPermitHolder(), rk);
	vi_3.setLnkPermit(rk);

	Vehicle_info vi_4 = new Vehicle_info("TF08GVX");
	Regular_visitor_permit np = new Regular_visitor_permit("Niall", vi_4, "John", new Date(3), new Date(5));
	lnkPermit_list.add(np.getPermitHolder(), np);
	vi_4.setLnkPermit(np);

	Vehicle_info vi_5 = new Vehicle_info("SH07TTH");
	Day_visitor_permit st = new Day_visitor_permit("Stuart", vi_5, "David", new Date(1));
	lnkPermit_list.add(st.getPermitHolder(), st);
	vi_5.setLnkPermit(st);

	lnkVehicle_list.add(vi.getReg(), vi);
	lnkVehicle_list.add(vi_2.getReg(), vi_2);
	lnkVehicle_list.add(vi_3.getReg(), vi_3);
	lnkVehicle_list.add(vi_4.getReg(), vi_4);
	lnkVehicle_list.add(vi_5.getReg(), vi_5);

	return lnkPermit_list;
    }

    /**
     * I wish users were not so prone to making stupid mistakes or this method would
     * not be necessary good luck reading this code
     * 
     * what it essentially does is it validates what has been put into the text
     * boxes and checks if it is what the permit needs
     * 
     * {@link https://www.regexlib.com/REDetails.aspx?regexp_id=595 - Regular
     * expression used to validate registrations}
     * 
     * @param permitType - the type of permit being validated
     * @return
     */
    public boolean validateInputs(int permitType, String name, String registration, String visitorName,
	    String visitDate, String issueDate, String endDate)
    {

	boolean valid = false;

	valid = checkNameIsAllowed(name);

	valid = checkRegistrationIsAllowed(registration);

	valid = checkVehicleListDoesNotContainRegistration(registration);

	switch (permitType)
	{
	case 0:
	    if (!checkNameIsAllowed(visitorName))
	    {
		sendAnErrorMessage("Format Error",
			"Visitor name should contain a uppercase letter and lower case letters");
		return false;
	    }

	    if (checkForAValidDate(visitDate))
	    {
		return true;
	    }

	    break;

	case 1:
	    if (!checkForAValidDate(issueDate))
	    {
		sendAnErrorMessage("Date Error", "Date fields must contain numbers ");
		return false;
	    }

	case 2:
	    if (!checkNameIsAllowed(visitorName))
	    {
		sendAnErrorMessage("Format Error",
			"Visitor name should contain a uppercase letter and lower case letters");
		return false;
	    }

	    if (checkForAValidDate(visitDate) || checkForAValidDate(visitDate))
	    {
		return true;
	    }

	case 3:
	    return true;

	}
	return valid;
    }

    private void sendAnErrorMessage(String title, String message)
    {
	JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);

    }

    private boolean checkForAValidDate(String visitDate)
    {
	if (!(visitDate.matches("^[0-9]{1,3}")))
	{
	    sendAnErrorMessage("Format Error", "Date fields must contain numbers ");
	    return false;

	}
	else if (((Integer.parseInt(visitDate) < 1 || Integer.parseInt(visitDate) > 365)))
	{

	    sendAnErrorMessage("Date Error", "Date fields must have a number between 1 and 365 ");
	    return false;

	}
	else
	{
	    return true;
	}
    }

    private boolean checkVehicleListDoesNotContainRegistration(String registration)
    {
	try
	{
	    if (!(lnkVehicle_list.getVehiclePermit(registration).equals(null)))
	    {
		sendAnErrorMessage("Format Error",
			"Issue creating permit. this registration number is already attached to a permit");
		return false;
	    }
	}
	catch (NullPointerException npe)
	{
	    sendAnErrorMessage(npe.getMessage(), "Issue creating permit");
	}
	return false;
    }

    private boolean checkRegistrationIsAllowed(String registration)
    {
	if (registration.length() < 5
		&& !registration.matches("^([A-HK-PRSVWY][A-HJ-PR-Y])\\s?([0][2-9]|[1-9][0-9])\\s?[A-HJ-PR-Z]{3}$"))
	{
	    sendAnErrorMessage("Issue creating permit. Check that the license plate entered is valid", "Format Error");
	    return false;
	}
	else
	{
	    return true;
	}
    }

    private boolean checkNameIsAllowed(String name)
    {
	if (name.matches("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$"))
	{
	    return true;
	}
	else
	{
	    sendAnErrorMessage("Format Error", "A name should contain a uppercase letter and lower case letters");
	    return false;
	}
    }

    // ---------GETTERS AND SETTERS --------
    public Vehicle_list getLnkVehicle_list()
    {
	return lnkVehicle_list;
    }

    public void setLnkVehicle_list(Vehicle_list lnkVehicle_list)
    {
	this.lnkVehicle_list = lnkVehicle_list;
    }

    public System_status getLnkSystem_status()
    {
	return lnkSystem_status;
    }

    public void setLnkSystem_status(System_status lnkSystem_status)
    {
	this.lnkSystem_status = lnkSystem_status;
    }

    public Permit_list getLnkPermit_list()
    {
	return lnkPermit_list;
    }

    public void setLnkPermit_list(Permit_list lnkPermit_list)
    {
	this.lnkPermit_list = lnkPermit_list;
    }

    /**
     * A method which will take the input from the New Permit tab and place the data
     * in the appropriate class to create a new permit. It also adds the vehicles
     * entered to a vehicle list when required.
     * 
     */
    public void createPermit(int selection, String name, String registration, String visitorName, String visitDate,
	    String issueDate, String endDate)
    {

	switch (selection)
	{
	// if the user want to create a new day permit
	case 0:

	    if (!validateInputs(0, name, registration, visitorName, visitDate, issueDate, endDate))
	    {
		return;
	    }
	    Vehicle_info vi = new Vehicle_info(registration);
	    Day_visitor_permit dvp = new Day_visitor_permit(name, vi, visitorName,
		    new Date(Integer.parseInt(visitDate)));
	    vi.setLnkPermit(dvp);

	    if (lnkPermit_list.add(name, dvp))
	    {
		JOptionPane.showMessageDialog(null, "Permit added", "Success", JOptionPane.INFORMATION_MESSAGE);
	    }
	    else
	    {
		JOptionPane.showMessageDialog(null, "Error adding permit", "Error", JOptionPane.ERROR_MESSAGE);
	    }

	    lnkVehicle_list.add(vi.getReg(), vi);

	    break;

	// if the to be created is a university member permit
	case 1:

	    if (!validateInputs(1, name, registration, visitorName, visitDate, issueDate, endDate))
	    {
		return;
	    }
	    Vehicle_info vi_2 = new Vehicle_info(registration);
	    University_member_permit ump = new University_member_permit(name, vi_2,
		    new Date(Integer.parseInt(issueDate)));
	    vi_2.setLnkPermit(ump);

	    if (lnkPermit_list.add(name, ump))
	    {

		JOptionPane.showMessageDialog(null, "Permit added", "Success", JOptionPane.INFORMATION_MESSAGE);
	    }
	    else
	    {
		JOptionPane.showMessageDialog(null, "Error adding permit", "Error", JOptionPane.ERROR_MESSAGE);
	    }

	    lnkVehicle_list.add(vi_2.getReg(), vi_2);

	    break;

	// if permit to be created is a regular visitor permit

	case 2:

	    if (!validateInputs(2, name, registration, visitorName, visitDate, issueDate, endDate))
	    {
		return;
	    }
	    Vehicle_info vi_3 = new Vehicle_info(registration);
	    Regular_visitor_permit rvm = new Regular_visitor_permit(name, vi_3, visitorName,
		    new Date(Integer.parseInt(issueDate)), new Date(Integer.parseInt(endDate)));
	    vi_3.setLnkPermit(rvm);

	    if (lnkPermit_list.add(name, rvm))
	    {
		JOptionPane.showMessageDialog(null, "Permit added", "Success", JOptionPane.INFORMATION_MESSAGE);
	    }
	    else
	    {
		JOptionPane.showMessageDialog(null, "Error adding permit", "Error", JOptionPane.ERROR_MESSAGE);
	    }

	    lnkVehicle_list.add(vi_3.getReg(), vi_3);

	    break;

	// if the permit to created is a permanent visitor
	case 3:
	    if (!validateInputs(3, name, registration, visitorName, visitDate, issueDate, endDate))
	    {
		return;
	    }
	    Vehicle_info vi_4 = new Vehicle_info(registration);
	    Permanent_visitor_permit pvp = new Permanent_visitor_permit(name, vi_4);
	    vi_4.setLnkPermit(pvp);

	    if (lnkPermit_list.add(name, pvp))
	    {
		JOptionPane.showMessageDialog(null, "Permit added", "Success", JOptionPane.INFORMATION_MESSAGE);
	    }
	    else
	    {
		JOptionPane.showMessageDialog(null, "Error adding permit", "Error", JOptionPane.ERROR_MESSAGE);
	    }

	    lnkVehicle_list.add(vi_4.getReg(), vi_4);

	    break;
	}

	lnkSystem_status.dataChanged();
	permitStrings = lnkPermit_list.populateList();
    }

    /**
     * this method edits and updates an existing permit
     * 
     * @param key       - the key of the permit to be edited
     * @param endDate
     * @param issueDate
     * @param visDate
     * @param visiting
     * @param red
     * @param name
     */
    public void editPermit(String key, int selection, String name, String reg, String visiting, String visDate,
	    String issueDate, String endDate)
    {

	switch (selection)
	{
	case 0:
	    if (validateInputs(selection, name, reg, visiting, visDate, issueDate, endDate))
	    {
		Vehicle_info vi = new Vehicle_info(reg);
		Day_visitor_permit dvp = new Day_visitor_permit(key, vi, visiting, new Date(Integer.parseInt(visDate)));
		if (lnkPermit_list.update(key, dvp))
		{
		    JOptionPane.showMessageDialog(null, "Permit modified", "Success", JOptionPane.INFORMATION_MESSAGE);
		}
		else
		    JOptionPane.showMessageDialog(null, "Error modifying permit", "Error", JOptionPane.ERROR_MESSAGE);
		lnkVehicle_list.add(vi.getReg(), vi);
	    }
	    break;
	case 1:
	    if (validateInputs(selection, name, reg, visiting, visDate, issueDate, endDate))
	    {
		Vehicle_info vi_2 = new Vehicle_info(reg);
		University_member_permit ump = new University_member_permit(key, vi_2,
			new Date(Integer.parseInt(issueDate)));
		if (lnkPermit_list.update(key, ump))
		{
		    JOptionPane.showMessageDialog(null, "Permit modified", "Success", JOptionPane.INFORMATION_MESSAGE);
		}
		else
		    JOptionPane.showMessageDialog(null, "Error modifying permit", "Error", JOptionPane.ERROR_MESSAGE);
		lnkVehicle_list.add(vi_2.getReg(), vi_2);
	    }
	    break;
	case 2:
	    if (validateInputs(selection, name, reg, visiting, visDate, issueDate, endDate))
	    {
		Vehicle_info vi_3 = new Vehicle_info(reg);
		Regular_visitor_permit rvm = new Regular_visitor_permit(key, vi_3, visiting,
			new Date(Integer.parseInt(issueDate)), new Date(Integer.parseInt(endDate)));
		if (lnkPermit_list.update(key, rvm))
		{
		    JOptionPane.showMessageDialog(null, "Permit modified", "Success", JOptionPane.INFORMATION_MESSAGE);
		}
		else
		    JOptionPane.showMessageDialog(null, "Error modifying permit", "Error", JOptionPane.ERROR_MESSAGE);
		lnkVehicle_list.add(vi_3.getReg(), vi_3);
	    }
	    break;
	case 3:
	    if (validateInputs(selection, name, reg, visiting, visDate, issueDate, endDate))
	    {
		Vehicle_info vi_4 = new Vehicle_info(reg);
		Permanent_visitor_permit pvp = new Permanent_visitor_permit(key, new Vehicle_info(reg));
		if (lnkPermit_list.update(key, pvp))
		{
		    JOptionPane.showMessageDialog(null, "Permit modified", "Success", JOptionPane.INFORMATION_MESSAGE);
		}
		else
		    JOptionPane.showMessageDialog(null, "Error modifying permit", "Error", JOptionPane.ERROR_MESSAGE);
		lnkVehicle_list.add(vi_4.getReg(), vi_4);
	    }
	    break;
	}

	lnkSystem_status.dataChanged();
	permitStrings = lnkPermit_list.populateList();

    }

    public String[] getPermitStrings()
    {
	return permitStrings;
    }
}
