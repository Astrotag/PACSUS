package pacsus.main;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/* Generated by Together */

/**
 * This class represents the interactive interface to PACSUS administration
 * functions carried out in the Estates and Campus Services Office. Information
 * about these functions is in the Administration use case diagram (hyperlinked
 * from this class).
 *
 * The interface comprises one screen with all the functions present on it: they
 * could all be on view at once, or perhaps in alternative JPanels (in a JFrame
 * with JTabbedPane); the current date (day number) is always displayed.
 *
 * There could be any number of instances of this class, potentially one for
 * each workstation in the office, with different staff carrying different
 * functions.
 *
 * The class implements Observer, and observes the system status so that it can
 * keep the displayed current date correct.
 * 
 * @stereotype boundary
 */
public class Administration_office extends JFrame implements Observer, ActionListener
{
    /**
     * Each instance of Administration_office has a navigable association to the
     * permit list so that it can enquire about/add/delete/modify permits.
     * 
     * @supplierCardinality 1
     * @clientCardinality 1..*
     * @label Administration functions
     * @directed
     */
    private Permit_list lnkPermit_list;

    /**
     * Each instance of Administration_office has a navigable association to the
     * vehicle list so that it can enquire about/add/delete/modify vehicle details.
     * 
     * @clientCardinality 1..*
     * @supplierCardinality 1
     * @label Administration functions
     * @directed
     */
    private Vehicle_list lnkVehicle_list;

    /**
     * Each instance of Administration_office has a navigable association to the
     * system status so that it can find out status information about the system.
     * 
     * @clientCardinality 1..*
     * @supplierCardinality 1
     * @label See date
     * @directed
     */
    private System_status lnkSystem_status;

    private JButton newPermitButton, addWarningButton, removeWarningButton, unsuspendButton, cancelButton,
	    selectPermitButton, editPermitButton, statusButton;

    private String[] permitStrings;

    private JComboBox<String> permitTypesNewPermit, permitTypesEdit, allPermitsWarning, allPermitsEdit,
	    allPermitsCancel, allPermitsStatus, allPermitsSuspended;

    private JTextField txtNameNewPermit, txtRegNoNewPermit, txtIssueDateNewPermit, txtEndDateNewPermit,
	    txtVisDateNewPermit;
    private JTextField txtNameEdit, txtRegNoEdit, txtIssueDateEdit, txtEndDateEdit, txtVisDateEdit;

    private JLabel lblYourNameNewPermit, lblRegNoNewPermit, lblIssueDateNewPermit, lblVisitingNewPermit,
	    lblEndDateNewPermit, lblYourNameEdit, lblRegNoEdit, lblIssueDateEdit, lblVisitingEdit, lblEndDateEdit,
	    lblPermitTypes, lblAllPermits;

    private JPanel editPanel, statusPanel, cancelPanel, newPermitPanel, warningPanel, unsuspendPanel, cancelPermit;

    private JTabbedPane tabbedPane;

    public Administration_office(System_status systemStatus, Vehicle_list vehicleList, Permit_list permitList)
    {
	this.lnkVehicle_list = vehicleList;
	this.lnkSystem_status = systemStatus;
	this.lnkPermit_list = permitList;
	// choicePane = new ChoicePane();
	// permitDialog = new PermitDialog(lnkPermit_list, lnkVehicle_list);

	lnkSystem_status.addObserver(this);

	loadGUI();
    }

    private void loadGUI()
    {
	tabbedPane = new JTabbedPane();
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	setTitle(1);

	newPermitPanel = createPermitPanel();

	warningPanel = createWarningPanel();

	unsuspendPanel = createSuspendedPanel();

	cancelPermit = createCancelPanel();

	statusPanel = createStatusPanel();

	editPanel = createEditPermitPanel();

	tabbedPane.addTab("New Permit", newPermitPanel);
	tabbedPane.addTab("Issue Warning", warningPanel);
	tabbedPane.addTab("Unsuspend Permit", unsuspendPanel);
	tabbedPane.addTab("Cancel Permit", cancelPermit);
	tabbedPane.addTab("Edit Permit", editPanel);
	tabbedPane.addTab("Status Enquiry", statusPanel);

	add(tabbedPane);
	setSize(700, 300);
	setLocation(400, 195);
	populatePermitList();

	setVisible(true);
    }

    private JPanel createStatusPanel()
    {
	statusPanel = new JPanel();
	GridBagConstraints gbc = new GridBagConstraints();
	gbc.fill = GridBagConstraints.HORIZONTAL;
	gbc.gridx = 0;
	gbc.gridy = 0;
	int left = 0, right = 1, line = 0;

	gbc.gridx = left;
	gbc.gridy = line;
	lblAllPermits = new JLabel("All Current Permits: ");
	statusPanel.add(lblAllPermits, gbc);

	line++;
	gbc.gridx = right;
	gbc.gridy = line;
	allPermitsStatus = new JComboBox<String>();
	statusPanel.add(allPermitsStatus, gbc);
	statusButton = new JButton("Select");
	statusPanel.add(statusButton);
	statusButton.addActionListener(this);

	GridBagLayout gbl = new GridBagLayout();
	statusPanel.setLayout(gbl);

	return statusPanel;
    }

    private void populatePermitList()
    {
	lnkPermit_list.add("Greig", new University_member_permit("Greig", new Vehicle_info("YT14HBB"), new Date(1)));
	lnkPermit_list.add("Joanes", new University_member_permit("Joanes", new Vehicle_info("SL07HAU"), new Date(1)));
	lnkPermit_list.add("Ryan", new University_member_permit("Ryan", new Vehicle_info("NC02XZT"), new Date(1)));
	lnkPermit_list.add("Niall", new University_member_permit("Niall", new Vehicle_info("TF08GVX"), new Date(1)));
	lnkPermit_list.add("Stuart", new University_member_permit("Stuart", new Vehicle_info("HG04YUY"), new Date(1)));
	
	addToVehicleList("YT14HBB", "Greig");
	addToVehicleList("SL07HAU", "Joanes");
	addToVehicleList("NC02XZT", "Ryan");
	addToVehicleList("TF08GVX", "Niall");
	addToVehicleList("HG04YUY", "Stuart");
	
    }

    /**
     * Creates the UI elements for the Edit panel
     * 
     * @return - JPanel for Edit
     */
    private JPanel createEditPermitPanel()
    {
	editPanel = new JPanel();
	allPermitsEdit = new JComboBox<String>();

	permitStrings = new String[lnkPermit_list.size()];
	permitStrings = lnkPermit_list.populateList();

	popCombo();

	GridBagConstraints gbc = new GridBagConstraints();
	gbc.fill = GridBagConstraints.HORIZONTAL;
	gbc.gridx = 0;
	gbc.gridy = 0;
	int left = 0, right = 1, line = 0;

	gbc.gridx = left;
	gbc.gridy = line;
	lblAllPermits = new JLabel("All Current Permits: ");
	editPanel.add(allPermitsEdit, gbc);

	line++;
	gbc.gridx = right;
	gbc.gridy = line;
	selectPermitButton = new JButton("Select");
	editPanel.add(selectPermitButton);
	selectPermitButton.addActionListener(this);

	GridBagLayout gbl = new GridBagLayout();
	editPanel.setLayout(gbl);

	permitTypesEdit = new JComboBox<String>(new String[]
	{ "Day Permit", "Permanent Vistior", "Regular Vistor", "University Member" });
	gbc.gridx = left;
	gbc.gridy = line + 1;

	lblPermitTypes = new JLabel("Permit Types: ");
	editPanel.add(lblPermitTypes, gbc);

	line++;
	gbc.gridx = right;
	gbc.gridy = line;
	editPanel.add(permitTypesEdit, gbc);

	line++;
	gbc.gridx = left;
	gbc.gridy = line;
	permitTypesEdit.addActionListener(this);

	line++;
	gbc.gridx = left;
	gbc.gridy = line;
	lblYourNameEdit = new JLabel("Name: ");
	editPanel.add(lblYourNameEdit, gbc);

	gbc.gridx = right;
	gbc.gridy = line;
	txtNameEdit = new JTextField();
	txtNameEdit.setEditable(false);
	editPanel.add(txtNameEdit, gbc);

	line++;
	gbc.gridx = left;
	gbc.gridy = line;
	lblRegNoEdit = new JLabel("Registration No: ");
	editPanel.add(lblRegNoEdit, gbc);

	gbc.gridx = right;
	gbc.gridy = line;
	txtRegNoEdit = new JTextField();
	editPanel.add(txtRegNoEdit, gbc);

	line++;
	gbc.gridx = left;
	gbc.gridy = line;
	lblIssueDateEdit = new JLabel("Date Issued: ");
	editPanel.add(lblIssueDateEdit, gbc);

	gbc.gridx = right;
	gbc.gridy = line;
	txtIssueDateEdit = new JTextField();
	editPanel.add(txtIssueDateEdit, gbc);

	line++;
	gbc.gridx = left;
	gbc.gridy = line;
	lblEndDateEdit = new JLabel("End Date: ");
	editPanel.add(lblEndDateEdit, gbc);

	gbc.gridx = right;
	gbc.gridy = line;
	txtEndDateEdit = new JTextField();
	editPanel.add(txtEndDateEdit, gbc);

	line++;
	gbc.gridx = left;
	gbc.gridy = line;
	lblVisitingEdit = new JLabel("Visiting Date: ");
	editPanel.add(lblVisitingEdit, gbc);

	gbc.gridx = right;
	gbc.gridy = line;
	txtVisDateEdit = new JTextField();
	editPanel.add(txtVisDateEdit, gbc);

	line++;
	gbc.gridx = right;
	gbc.gridy = line;
	editPermitButton = new JButton("Edit Permit");
	editPanel.add(editPermitButton, gbc);
	editPermitButton.addActionListener(this);
	return editPanel;
    }

    /**
     * Creates the UI elements for the Cancel panel
     * 
     * @return - JPanel for Cancel
     */
    private JPanel createCancelPanel()
    {
	cancelPanel = new JPanel();

	GridBagConstraints gbc = new GridBagConstraints();
	gbc.fill = GridBagConstraints.HORIZONTAL;
	gbc.gridx = 0;
	gbc.gridy = 0;

	lblAllPermits = new JLabel("All Current Permits: ");
	cancelPanel.add(lblAllPermits, gbc);

	gbc.gridx = 1;
	gbc.gridy = 0;
	allPermitsCancel = new JComboBox<String>();
	cancelPanel.add(allPermitsCancel, gbc);
	GridBagLayout gbl = new GridBagLayout();
	cancelPanel.setLayout(gbl);

	gbc.gridx = 0;
	gbc.gridy = 4;
	cancelButton = new JButton("Cancel Permit");
	cancelPanel.add(cancelButton);
	cancelButton.addActionListener(this);
	return cancelPanel;
    }

    /**
     * Creates the UI elements for the Suspended panel
     * 
     * @return - JPanel for unsuspend
     */
    private JPanel createSuspendedPanel()
    {
	unsuspendPanel = new JPanel();

	GridBagConstraints gbc = new GridBagConstraints();
	gbc.fill = GridBagConstraints.HORIZONTAL;
	gbc.gridx = 0;
	gbc.gridy = 0;

	lblAllPermits = new JLabel("All Current Permits: ");
	unsuspendPanel.add(lblAllPermits, gbc);

	gbc.gridx = 1;
	gbc.gridy = 0;
	allPermitsSuspended = new JComboBox<String>();
	unsuspendPanel.add(allPermitsSuspended, gbc);
	GridBagLayout gbl = new GridBagLayout();
	unsuspendPanel.setLayout(gbl);

	gbc.gridx = 0;
	gbc.gridy = 4;

	unsuspendButton = new JButton("Unsuspend Permit");
	unsuspendPanel.add(unsuspendButton);
	unsuspendButton.addActionListener(this);

	return unsuspendPanel;
    }

    /**
     * Creates the UI elements for the warning panel
     * 
     * @return - JPanel for warning
     */
    private JPanel createWarningPanel()
    {
	JPanel warningPanel = new JPanel();
	GridBagLayout gbl = new GridBagLayout();
	warningPanel.setLayout(gbl);

	GridBagConstraints gbc = new GridBagConstraints();
	gbc.fill = GridBagConstraints.HORIZONTAL;
	gbc.gridx = 0;
	gbc.gridy = 0;

	lblAllPermits = new JLabel("All Current Permits: ");
	warningPanel.add(lblAllPermits, gbc);

	gbc.gridx = 1;
	gbc.gridy = 0;
	allPermitsWarning = new JComboBox<String>();
	permitStrings = new String[lnkPermit_list.size()];
	permitStrings = lnkPermit_list.populateList();

	warningPanel.add(allPermitsWarning, gbc);

	gbc.gridx = 0;
	gbc.gridy = 4;
	addWarningButton = new JButton("Issue Warning");
	warningPanel.add(addWarningButton, gbc);
	addWarningButton.addActionListener(this);

	gbc.gridx = 1;
	gbc.gridy = 4;
	removeWarningButton = new JButton("Remove Warning");
	warningPanel.add(removeWarningButton, gbc);
	removeWarningButton.addActionListener(this);
	return warningPanel;
    }

    private void popCombo()
    {
	allPermitsWarning.setModel(new DefaultComboBoxModel<String>(permitStrings));
	allPermitsEdit.setModel(new DefaultComboBoxModel<String>(permitStrings));
	allPermitsCancel.setModel(new DefaultComboBoxModel<String>(permitStrings));
	allPermitsStatus.setModel(new DefaultComboBoxModel<String>(permitStrings));
	allPermitsSuspended.setModel(new DefaultComboBoxModel<String>(permitStrings));
    }

    /**
     * Creates the UI elements for the create permit panel
     * 
     * @return - JPanel for creating a permit
     * @return
     */
    private JPanel createPermitPanel()
    {
	JPanel newPermitPanel = new JPanel();
	GridBagLayout gbl = new GridBagLayout();
	newPermitPanel.setLayout(gbl);

	int left = 0, right = 1, line = 0;
	;
	GridBagConstraints gbc = new GridBagConstraints();
	gbc.fill = GridBagConstraints.HORIZONTAL;
	gbc.gridx = left;
	gbc.gridy = line;

	lblPermitTypes = new JLabel("Permit Types: ");
	newPermitPanel.add(lblPermitTypes, gbc);

	permitTypesNewPermit = new JComboBox<String>(new String[]
	{ "Day Permit", "Permanent Vistior", "Regular Vistor", "University Member" });

	gbc.gridx = right;
	gbc.gridy = line;

	permitTypesNewPermit.addActionListener(this);

	gbc.gridx = 1;
	gbc.gridy = 0;
	newPermitPanel.add(permitTypesNewPermit, gbc);

	line++;
	gbc.gridx = left;
	gbc.gridy = line;
	lblYourNameNewPermit = new JLabel("Name: ");
	newPermitPanel.add(lblYourNameNewPermit, gbc);

	gbc.gridx = right;
	gbc.gridy = line;
	txtNameNewPermit = new JTextField();
	newPermitPanel.add(txtNameNewPermit, gbc);

	line++;
	gbc.gridx = left;
	gbc.gridy = line;
	lblRegNoNewPermit = new JLabel("Registration No: ");
	newPermitPanel.add(lblRegNoNewPermit, gbc);

	gbc.gridx = right;
	gbc.gridy = line;
	txtRegNoNewPermit = new JTextField();
	newPermitPanel.add(txtRegNoNewPermit, gbc);

	line++;
	gbc.gridx = left;
	gbc.gridy = line;
	lblIssueDateNewPermit = new JLabel("Date Issued: ");
	newPermitPanel.add(lblIssueDateNewPermit, gbc);

	gbc.gridx = right;
	gbc.gridy = line;
	txtIssueDateNewPermit = new JTextField();
	newPermitPanel.add(txtIssueDateNewPermit, gbc);

	line++;
	gbc.gridx = left;
	gbc.gridy = line;
	lblEndDateNewPermit = new JLabel("End Date: ");
	newPermitPanel.add(lblEndDateNewPermit, gbc);

	gbc.gridx = right;
	gbc.gridy = line;
	txtEndDateNewPermit = new JTextField();
	newPermitPanel.add(txtEndDateNewPermit, gbc);

	line++;
	gbc.gridx = left;
	gbc.gridy = line;
	lblVisitingNewPermit = new JLabel("Visiting Date: ");
	newPermitPanel.add(lblVisitingNewPermit, gbc);

	gbc.gridx = right;
	gbc.gridy = line;
	txtVisDateNewPermit = new JTextField();
	newPermitPanel.add(txtVisDateNewPermit, gbc);

	line++;
	gbc.gridx = right;
	gbc.gridy = line;
	newPermitButton = new JButton("New Permit");
	newPermitPanel.add(newPermitButton, gbc);
	newPermitButton.addActionListener(this);

	return newPermitPanel;
    }

    /**
     * A method to set the title of each JFrame to include the updated date
     * 
     * @param date - may be updated after a "day" passes
     */
    private void setTitle(int date)
    {
	setTitle("Administration Office: Date - " + date);
    }

    @Override
    public void update(Observable o, Object arg)
    {
	int date = lnkSystem_status.getDate().getDayNumber();
	setTitle(date);
    }

    /**
     * A method which will take the input from the New Permit tab and place the data
     * in the appropriate class to create a new permit. It also adds the vehicles
     * entered to a vehicle list when required.
     */
    private void createPermit()
    {
	int permitType = permitTypesNewPermit.getSelectedIndex();

	switch (permitType)
	{

	case 0:

	    if (!validateInputs(0))
	    {
		return;
	    }
	    Day_visitor_permit dvp = new Day_visitor_permit(txtNameNewPermit.getText(),
		    new Vehicle_info(txtRegNoNewPermit.getText()), txtVisDateNewPermit.getText(),
		    new Date(Integer.parseInt(txtIssueDateNewPermit.getText())));

	    if (lnkPermit_list.add(txtNameNewPermit.getText(), dvp))
	    {
		JOptionPane.showMessageDialog(null, "Permit added", "Success", JOptionPane.INFORMATION_MESSAGE);
	    }
	    else
		JOptionPane.showMessageDialog(null, "Error adding permit", "Error", JOptionPane.ERROR_MESSAGE);

	    addToVehicleList(txtNameNewPermit.getText(), txtRegNoNewPermit.getText());

	    break;

	case 1:

	    if (!validateInputs(1))
	    {
		return;
	    }
	    University_member_permit ump = new University_member_permit(txtNameNewPermit.getText(),
		    new Vehicle_info(txtRegNoNewPermit.getText()),
		    new Date(Integer.parseInt(txtIssueDateNewPermit.getText())));

	    if (lnkPermit_list.add(txtNameNewPermit.getText(), ump))
	    {

		JOptionPane.showMessageDialog(null, "Permit added", "Success", JOptionPane.INFORMATION_MESSAGE);
	    }
	    else
		JOptionPane.showMessageDialog(null, "Error adding permit", "Error", JOptionPane.ERROR_MESSAGE);

	    addToVehicleList(txtNameNewPermit.getText(), txtRegNoNewPermit.getText());

	    break;

	case 2:

	    if (!validateInputs(2))
	    {
		return;
	    }
	    Regular_visitor_permit rvm = new Regular_visitor_permit(txtNameNewPermit.getText(),
		    new Vehicle_info(txtRegNoNewPermit.getText()), txtVisDateNewPermit.getText(),
		    new Date(Integer.parseInt(txtIssueDateNewPermit.getText())),
		    new Date(Integer.parseInt(txtEndDateNewPermit.getText())));

	    if (lnkPermit_list.add(txtNameNewPermit.getText(), rvm))
	    {

		JOptionPane.showMessageDialog(null, "Permit added", "Success", JOptionPane.INFORMATION_MESSAGE);
	    }
	    else
		JOptionPane.showMessageDialog(null, "Error adding permit", "Error", JOptionPane.ERROR_MESSAGE);

	    addToVehicleList(txtNameNewPermit.getText(), txtRegNoNewPermit.getText());

	    break;

	case 3:
	    if (!validateInputs(3))
	    {
		return;
	    }
	    Permanent_visitor_permit pvp = new Permanent_visitor_permit(txtNameNewPermit.getText(),
		    new Vehicle_info(txtRegNoNewPermit.getText()));

	    if (lnkPermit_list.add(txtNameNewPermit.getText(), pvp))
	    {

		JOptionPane.showMessageDialog(null, "Permit added", "Success", JOptionPane.INFORMATION_MESSAGE);
	    }
	    else
		JOptionPane.showMessageDialog(null, "Error adding permit", "Error", JOptionPane.ERROR_MESSAGE);

	    addToVehicleList(txtNameNewPermit.getText(), txtRegNoNewPermit.getText());

	    permitStrings = lnkPermit_list.populateList();
	    popCombo();
	}
    }

    /**
     * This method adds all vehicles added to the permit and separates each car from
     * commas and then adds the separate vehicles to the list
     * 
     * @author NP
     * @param info         - this is the name of the permit holder
     * @param vehicle_info - all the registration numbers from the permit
     */
    private void addToVehicleList(String vehicle_info, String info)
    {
	if (vehicle_info.contains(","))
	{
	    int commaoccurs = -1;
	    for (int i = 0; i < vehicle_info.length(); i++)
	    {

		if (vehicle_info.charAt(i) == ',')
		{
		    Vehicle_info vh = new Vehicle_info(vehicle_info.substring(commaoccurs + 1, i));
		    // System.out.println("Vehicle: " + vh + " Name: " + name);
		    lnkVehicle_list.add(vh, info);
		    commaoccurs = i;
		}
	    }

	    Vehicle_info vh = new Vehicle_info(vehicle_info.substring(commaoccurs + 1));
	    // System.out.println("Vehicle: " + vh + " Name: " + name);
	    lnkVehicle_list.add(vh, info);
	}
	else
	{
	    Vehicle_info vh = new Vehicle_info(vehicle_info);
	    // System.out.println("Vehicle: " + vh + " Name: " + name);
	    lnkVehicle_list.add(vh, info);
	}

    }

    /**
     * Finds the name (key) of the selected permit in the allPermitsEdit combobox by
     * using a substring
     * 
     * @return - the selected permits key
     */
    private String editPermitSelectKey()
    {
	String selectedName = allPermitsEdit.getSelectedItem().toString();
	int i = selectedName.indexOf(":") + 1;
	int i2 = selectedName.indexOf("-");
	String key = selectedName.substring(i, i2);
	return key;
    }

    /**
     * Finds the registration of the selected permit in the allPermitsEdit combobox
     * by using a substring
     * 
     * @return - the selected permits registrations
     * 
     *         TODO - We need validation for this if there are more than one
     *         registration on a permit
     */
    private String editPermitSelectReg()
    {
	String selectedReg = allPermitsEdit.getSelectedItem().toString();
	int i3 = selectedReg.indexOf("-") + 1;
	String reg = selectedReg.substring(i3);
	return reg;
    }

    private void editPermit(String key)
    {
	int permitType = permitTypesEdit.getSelectedIndex();
	switch (permitType)
	{
	case 0:
	    Day_visitor_permit dvp = new Day_visitor_permit(key, new Vehicle_info(txtRegNoEdit.getText()),
		    txtVisDateEdit.getText(), new Date(Integer.parseInt(txtIssueDateEdit.getText())));
	    if (lnkPermit_list.update(key, dvp))
	    {
		JOptionPane.showMessageDialog(null, "Permit modified", "Success", JOptionPane.INFORMATION_MESSAGE);
	    }
	    else
		JOptionPane.showMessageDialog(null, "Error modifying permit", "Error", JOptionPane.ERROR_MESSAGE);
	    addToVehicleList(key, txtRegNoEdit.getText());
	    break;
	case 1:
	    University_member_permit ump = new University_member_permit(key, new Vehicle_info(txtRegNoEdit.getText()),
		    new Date(Integer.parseInt(txtIssueDateEdit.getText())));
	    if (lnkPermit_list.update(key, ump))
	    {
		JOptionPane.showMessageDialog(null, "Permit modified", "Success", JOptionPane.INFORMATION_MESSAGE);
	    }
	    else
		JOptionPane.showMessageDialog(null, "Error modifying permit", "Error", JOptionPane.ERROR_MESSAGE);
	    addToVehicleList(key, txtRegNoEdit.getText());
	    break;
	case 2:
	    Regular_visitor_permit rvm = new Regular_visitor_permit(key, new Vehicle_info(txtRegNoEdit.getText()),
		    txtVisDateEdit.getText(), new Date(Integer.parseInt(txtIssueDateEdit.getText())),
		    new Date(Integer.parseInt(txtEndDateEdit.getText())));
	    if (lnkPermit_list.update(key, rvm))
	    {
		JOptionPane.showMessageDialog(null, "Permit modified", "Success", JOptionPane.INFORMATION_MESSAGE);
	    }
	    else
		JOptionPane.showMessageDialog(null, "Error modifying permit", "Error", JOptionPane.ERROR_MESSAGE);
	    addToVehicleList(key, txtRegNoEdit.getText());
	    break;
	case 3:
	    Permanent_visitor_permit pvp = new Permanent_visitor_permit(key, new Vehicle_info(txtRegNoEdit.getText()));
	    if (lnkPermit_list.update(key, pvp))
	    {
		JOptionPane.showMessageDialog(null, "Permit modified", "Success", JOptionPane.INFORMATION_MESSAGE);
	    }
	    else
		JOptionPane.showMessageDialog(null, "Error modifying permit", "Error", JOptionPane.ERROR_MESSAGE);
	    addToVehicleList(key, txtRegNoEdit.getText());
	    break;
	}

	permitStrings = lnkPermit_list.populateList();
	popCombo();
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
	if (e.getSource().equals(newPermitButton))
	{
	    createPermit();
	}

	if (e.getSource().equals(addWarningButton))
	{
	    addWarning();
	}

	if (e.getSource().equals(removeWarningButton))
	{
	    removeWarning();
	}

	if (e.getSource().equals(unsuspendButton))
	{
	    permitSuspension();
	}

	if (e.getSource().equals(cancelButton))
	{
	    deletePermit();
	}

	if (e.getSource().equals(selectPermitButton))
	{
	    // System.out.println(txtNameEdit.getText());
	    String key = editPermitSelectKey(), reg = editPermitSelectReg();
	    txtNameEdit.setText(key);
	    txtRegNoEdit.setText(reg);
	}
	if (e.getSource().equals(statusButton))
	{
	    permitStatus();
	}

	if (e.getSource().equals(editPermitButton))
	{
	    editPermit(editPermitSelectKey());
	}

	//
	if (e.getSource().equals(permitTypesNewPermit))
	{
	    checkVisability(permitTypesNewPermit);
	}

	if (e.getSource().equals(permitTypesEdit))
	{
	    checkVisability(permitTypesEdit);
	}
    }

    private void permitSuspension()
    {
	String selectedName = allPermitsSuspended.getSelectedItem().toString();
	int i = selectedName.indexOf(":") + 1;
	int i2 = selectedName.indexOf("-");
	String key = selectedName.substring(i, i2);

	lnkPermit_list.unsuspendPermit(key);
    }

    /**
     * A method which takes a combobox and checks it's selected index to determine
     * which fields and labels should be hidden
     * 
     * @param permitTypes - the tabs combobox
     */
    private void checkVisability(JComboBox<String> permitTypes)
    {
	if (permitTypes.getSelectedIndex() == 0)
	{
	    // TODO display boxes appropriate for day visitor permit
	    // only need host name, name and date

	    visibilityChanger(true, false, true, true, false, true);
	}

	else if (permitTypes.getSelectedIndex() == 1)
	{
	    // TODO display boxes appropriate for uni member permit
	    visibilityChanger(false, false, true, false, false, true);
	}

	else if (permitTypes.getSelectedIndex() == 2)
	{
	    // TODO display boxes appropriate for regular visitor permit
	    visibilityChanger(true, true, true, true, true, true);
	}

	else if (permitTypes.getSelectedIndex() == 3)
	{
	    // TODO display boxes appropriate for permanent visitor permit
	    visibilityChanger(false, false, false, false, false, false);
	}
    }

    /**
     * TODO
     */
    private void permitStatus()
    {
	int selected = allPermitsStatus.getSelectedIndex();

	String name = permitStrings[selected];
	name = name.substring(name.indexOf(":") + 1, name.indexOf("-"));

	Permit selectedPermit = lnkPermit_list.getPermit(name);
	String status = selectedPermit.getStatus();

	JDialog dialog = new JDialog();
	JTextArea txtArea = new JTextArea();
	txtArea.setText(status);
	txtArea.setEditable(false);
	dialog.setBounds(100, 100, 350, 200);
	dialog.add(txtArea);
	dialog.setTitle("Permit Status");
	dialog.setVisible(true);

    }

    /**
     * TODO
     */
    private void removeWarning()
    {
	int selected = allPermitsWarning.getSelectedIndex();

	String name = permitStrings[selected];
	name = name.substring(name.indexOf(":") + 1, name.indexOf("-"));

	lnkPermit_list.warnings(name, 0);
    }

    /**
     * TODO
     */
    private void addWarning()
    {
	int selected = allPermitsWarning.getSelectedIndex();

	String name = permitStrings[selected];
	name = name.substring(name.indexOf(":") + 1, name.indexOf("-"));

	lnkPermit_list.warnings(name, 1);

    }

    /**
     * @author NP this lovely method is an absolute abomination of a method ensures
     *         the user doesn't pointlessly enter info the permit type doesn't need
     * @param b1
     * @param b2
     * @param b3
     * @param b4
     * @param b5
     * @param b6
     */
    private void visibilityChanger(boolean b1, boolean b2, boolean b3, boolean b4, boolean b5, boolean b6)
    {
	switch (tabbedPane.getSelectedIndex())
	{
	case 0:
	    txtVisDateNewPermit.setVisible(b1);
	    txtEndDateNewPermit.setVisible(b2);
	    txtIssueDateNewPermit.setVisible(b3);
	    lblVisitingNewPermit.setVisible(b4);
	    lblEndDateNewPermit.setVisible(b5);
	    lblIssueDateNewPermit.setVisible(b6);
	    break;
	case 4:
	    txtVisDateEdit.setVisible(b1);
	    txtEndDateEdit.setVisible(b2);
	    txtIssueDateEdit.setVisible(b3);
	    lblVisitingEdit.setVisible(b4);
	    lblEndDateEdit.setVisible(b5);
	    lblIssueDateEdit.setVisible(b6);
	    break;

	default:
	    break;
	}
    }

    /**
     * @author NP I wish users were not so prone to making stupid mistakes or this
     *         method would not be necessary good luck reading this code lmao
     * 
     *         what it essentially does is it validates what has been put into the
     *         text boxes and checks if it is what the permit needs
     * @param permitType - the type of permit being validated
     * @return
     */
    private boolean validateInputs(int permitType)
    {

	boolean valid = false;
	// System.out.println(txtNameNewPermit.getText());
	if (!txtNameNewPermit.getText().matches("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$"))
	{
	    JOptionPane.showMessageDialog(null, "Name should contain a uppercase letter and lower case letters",
		    "Format Error", JOptionPane.ERROR_MESSAGE);
	    return valid;
	}

	if (txtRegNoNewPermit.getText().length() < 5)
	{
	    JOptionPane.showMessageDialog(null, "Registration number must be 5 characters or more ", "Format Error",
		    JOptionPane.ERROR_MESSAGE);
	    return valid;
	}

	switch (permitType)
	{
	case 0:
	    if (!(txtIssueDateNewPermit.getText().matches("^[0-9]{1,3}")
		    && (txtVisDateNewPermit.getText().matches("^[0-9]{1,3}"))))
	    {
		JOptionPane.showMessageDialog(null, "Date fields must contain numbers ", "Format Error",
			JOptionPane.ERROR_MESSAGE);
		return valid;

	    }
	    else if (((Integer.parseInt(txtIssueDateNewPermit.getText()) < 1)
		    || (Integer.parseInt(txtIssueDateNewPermit.getText()) > 365))
		    || ((Integer.parseInt(txtVisDateNewPermit.getText()) < 1
			    || Integer.parseInt(txtVisDateNewPermit.getText()) > 365)))
	    {

		JOptionPane.showMessageDialog(null, "Date fields must have a number between 1 and 365 ", "Date Error",
			JOptionPane.ERROR_MESSAGE);

	    }
	    else
	    {
		return true;

	    }

	    break;

	case 1:
	    if (!txtIssueDateNewPermit.getText().matches("^[0-9]{1,3}"))
	    {

		JOptionPane.showMessageDialog(null, "Date fields must contain numbers ", "Date Error",
			JOptionPane.ERROR_MESSAGE);
		return valid;

	    }
	    else if (Integer.parseInt(txtIssueDateNewPermit.getText()) < 1
		    || Integer.parseInt(txtIssueDateNewPermit.getText()) > 365)
	    {

		JOptionPane.showMessageDialog(null, "Date fields must have a number between 1 and 365 ", "Date Error",
			JOptionPane.ERROR_MESSAGE);
		return valid;

	    }
	    else
	    {
		return true;
	    }

	case 2:
	    if (!txtIssueDateNewPermit.getText().matches("^[0-9]{1,3}")
		    || !txtVisDateNewPermit.getText().matches("^[0-9]{1,3}")
		    || !txtEndDateNewPermit.getText().matches("^[0-9]{1,3}"))
	    {
		JOptionPane.showMessageDialog(null, "Date fields must contain numbers ", "Format Error",
			JOptionPane.ERROR_MESSAGE);
		return valid;
	    }

	    else if ((Integer.parseInt(txtIssueDateNewPermit.getText()) < 1
		    || Integer.parseInt(txtIssueDateNewPermit.getText()) > 365)
		    || (Integer.parseInt(txtVisDateNewPermit.getText()) < 1
			    || Integer.parseInt(txtVisDateNewPermit.getText()) > 365)
		    || (Integer.parseInt(txtEndDateNewPermit.getText()) < 1
			    || Integer.parseInt(txtEndDateNewPermit.getText()) > 365))
	    {

		JOptionPane.showMessageDialog(null, "Date fields must have a number between 1 and 365 ", "Date Error",
			JOptionPane.ERROR_MESSAGE);
		return valid;

	    }
	    else
	    {
		return true;
	    }

	case 3:
	    return true;

	}
	return valid;
    }

    /**
     * TODO
     */
    private void deletePermit()
    {
	int selected = allPermitsCancel.getSelectedIndex();

	String name = permitStrings[selected];
	name = name.substring(name.indexOf(":") + 1, name.indexOf("-"));
	if (JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this permit?", "WARNING",
		JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
	{
	    if (!lnkPermit_list.deletePermit(name))
		JOptionPane.showMessageDialog(null, "There has been an issue while deleting the permit.");
	    ;
	}
	// Update comboBox
	permitStrings = new String[lnkPermit_list.size()];
	permitStrings = lnkPermit_list.populateList();
	popCombo();

    }
}
