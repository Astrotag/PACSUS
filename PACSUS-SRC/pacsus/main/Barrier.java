
package pacsus.main;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/* Generated by Together */

/**
 * This class represents the access control barriers. Information about the
 * barrier functions is in the Barrier use case diagram (hyperlinked from this
 * class). Although in reality this class will be realised by (programmable)
 * hardware with a moveable barrier, and a camera with a registration number
 * recognition system, here the interface comprises one screen with the various
 * functions present on it, all on view at once since there aren't many.
 *
 * There will be a large word PASS or STOP on display at all times: PASS when
 * the barrier is up because the system is inactive or temporarily when a
 * permitted vehicle is being allowed through, and STOP when the barrier is
 * down.
 *
 * There will be a text field to enter the registration number read by the
 * camera, and a button to indicate that the number has been read and is ready
 * for checking (and raising the barrier or not, as appropriate).
 *
 * To simulate the passage of a vehicle through the raised barrier, there is
 * another button to be clicked to simulate when a buried electronic sensor
 * indicates that the vehicle is now clear - the barrier can then be lowered
 * (unless, of course, the system has been deactivated by security staff in the
 * interim...).
 *
 * There could be many instances of this class: one at each entrance lane to the
 * University. The class implements Observer, and observes the system status so
 * that it can keep its activated/deactivated status up to date.
 * 
 * @stereotype boundary
 */
public class Barrier extends JFrame implements Observer, ActionListener
{
    /**
     * Each instance of Barrier has a navigable association to the permit list so
     * that when a vehicle's registration number has been recognized by the camera,
     * the barrier can check whether to raise itself or not by checking the status
     * of that vehicle's permit.
     * 
     * @clientCardinality 1..*
     * @supplierCardinality 1
     * @label Access permits
     * @directed
     */
    private Vehicle_list lnkVehicle_list;

    /**
     * Each instance of Barrier has a navigable association to the system status so
     * that it can check whether the barrier system as a whole is active or
     * inactive, and so that it can send event messages to be recorded in the log.
     * 
     * @clientCardinality 1..*
     * @supplierCardinality 1
     * @label Fetch system status info
     * @directed
     */
    private System_status lnkSystem_status;

    /**
     * This attribute indicates the active/inactive state of the barrier system - as
     * notified by the system status when it changes (Barrier Observes System
     * status). If false then the barrier must be up. If true then the barrier
     * position is determined by attribute raised.
     */
    private boolean active = false;

    /**
     * If the barrier system is active, this attribute indicates whether the barrier
     * is currently in its raised or lowered position. The position is controlled by
     * the result of checking a registration number with the permitted vehicles
     * list, and the "vehicle clear" button.
     */
    private boolean raised = false;

    /**
     * the textfield for inputting the registration number attempting to enter
     */
    private JTextField regField;

    /**
     * enter button for entering the reg no into the system passed button for
     * telling the system when the car has passed the barrier
     */
    private JButton enterButton, passedButton;
    private JLabel label;

    /**
     * main constructor for this class
     * 
     * @param systemStatus current system status
     * @param vehicleList  current list of vehicles
     */
    public Barrier(System_status systemStatus, Vehicle_list vehicleList)
    {
	// Record references to the parent controller and the model
	this.lnkVehicle_list = vehicleList;
	this.lnkSystem_status = systemStatus;

	lnkSystem_status.addObserver(this);
	loadGUI();
    }

    /**
     * this method is for initialising and displaying the gui for the barrier system
     */
    private void loadGUI()
    {
	setTitle(1);
	setLocation(400, 40);
	setSize(350, 150);
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	Container window = getContentPane();
	window.setLayout(new FlowLayout()); // The default is that JFrame uses BorderLayout

	// Set up view GUI
	setBackground(Color.white);
	add(new JLabel("Barrier"));
	// Barrier label
	label = new JLabel();
	add(label);

	regField = new JTextField("", 15);
	add(regField);

	enterButton = new JButton("Enter Registration");
	add(enterButton);
	enterButton.addActionListener(this);

	passedButton = new JButton("Vehicle Passed");
	add(passedButton);
	passedButton.addActionListener(this);

	setVisible(true);
	updateBarrier();
    }

    /**
     * this method is responsible for updating and managing the barrier
     */
    private void updateBarrier()
    {
	if (raised || !active)
	{
	    label.setText("PASS");
	    label.setForeground(Color.green);
	}
	else
	{
	    label.setText("STOP");
	    label.setForeground(Color.red);
	}
    }

    /**
     * this method is responsible for changing the title
     * 
     * @param date current day
     */
    private void setTitle(int date)
    {
	setTitle("Barrier: Date - " + date);
    }

    /**
     * this method updates the day on the title
     * 
     * @Override
     */

    public void update(Observable o, Object arg)
    {
	int date = lnkSystem_status.getDate().getDayNumber();

	setTitle(date);

	active = lnkSystem_status.getSystemStatus();
	updateBarrier();
    }

    /**
     * this method deals with button clicks
     * 
     * @Override
     */
    public void actionPerformed(ActionEvent e)
    {

	if (e.getSource().equals(enterButton))
	{
	    String regText = regField.getText();

	    // when system is deactivated let cars pass
	    if (!lnkSystem_status.getSystemStatus())
	    {
		JOptionPane.showMessageDialog(this, "System Deactivated. Vehicle may pass.", "Barrier",
			JOptionPane.WARNING_MESSAGE);
		label.setText("PASS");
		label.setForeground(Color.green);
	    }
	    else
	    {
		String log;
		// when system is active and the regfield is not empty run checks and if it
		// passes let them through
		if (!regText.equals(""))
		{
		    if (lnkVehicle_list.findVehicle(regText))
		    {
			if (!lnkVehicle_list.getVehiclePermit(regText).isEnteredToday()
				&& checkVehiclesEntryDate(regText)
				&& !lnkVehicle_list.getVehiclePermit(regText).isSuspended())
			{

			    lnkVehicle_list.getVehiclePermit(regText).setEnteredToday(true);
			    JOptionPane.showMessageDialog(this, "The vehicle may pass.", "Barrier",
				    JOptionPane.INFORMATION_MESSAGE);
			    label.setText("PASS");
			    label.setForeground(Color.green);
			    log = "Vehicle: " + regText + " entered." + " Date: "
				    + lnkSystem_status.getDate().getDayNumber();
			}
			// if the car has already entered today
			else if (lnkVehicle_list.getVehiclePermit(regText).isEnteredToday())
			{
			    JOptionPane.showMessageDialog(this, "ACCESS DENIED! Permit has already been used today.",
				    "Barrier", JOptionPane.ERROR_MESSAGE);

			    label.setText("STOP");
			    label.setForeground(Color.red);
			    log = "Vehicle: " + regText + " couldn't enter." + " Date: "
				    + lnkSystem_status.getDate().getDayNumber();
			}
			else if (lnkVehicle_list.getVehiclePermit(regText).isSuspended())
			{
			    JOptionPane.showMessageDialog(this, "ACCESS DENIED! This permit has been suspended",
				    "Barrier", JOptionPane.ERROR_MESSAGE);

			    label.setText("STOP");
			    label.setForeground(Color.red);
			    log = "Vehicle: " + regText + " couldn't enter." + " Date: "
				    + lnkSystem_status.getDate().getDayNumber();
			}
			else
			{
			    JOptionPane.showMessageDialog(this, "ACCESS DENIED! Permit is not valid today", "Barrier",
				    JOptionPane.ERROR_MESSAGE);

			    label.setText("STOP");
			    label.setForeground(Color.red);
			    log = "Vehicle: " + regText + " couldn't enter." + " Date: "
				    + lnkSystem_status.getDate().getDayNumber();
			}
		    }
		    else
		    {
			JOptionPane.showMessageDialog(this,
				"ACCESS DENIED! This registration plate is not in the list.", "Barrier",
				JOptionPane.ERROR_MESSAGE);

			label.setText("STOP");
			label.setForeground(Color.red);
			log = "Vehicle: " + regText + " couldn't enter." + " Date: "
				+ lnkSystem_status.getDate().getDayNumber();

		    }

		    // Add entry to log
		    lnkSystem_status.addEntryLog(log);
		}
		else
		{
		    JOptionPane.showMessageDialog(this, "Please enter registration number.", "Barrier",
			    JOptionPane.ERROR_MESSAGE);
		}
	    }

	}
//when the pass button is clicked
	if (e.getSource().equals(passedButton))
	{
	    String regText = regField.getText();
	    // This button simulates when a car goes through
	    // First check if barrier system is active
	    if (active)
	    {
		lnkVehicle_list.getVehiclePermit(regText).setNoOfEntries(1);
		// Set raised to false and update JLabel
		raised = false;
		updateBarrier();
		regField.setText("");

	    }
	}
    }

    /**
     * this method checks to see if a permit is allowed to enter on the current day
     * 
     * @param regText the registration number attempting to enter
     * @return if the car is allowed to enter on the current day
     */
    private boolean checkVehiclesEntryDate(String regText)
    {
	return lnkSystem_status.getDate().getDayNumber() == lnkVehicle_list.getVehiclePermit(regText).getDate()
		.getDayNumber() || checkVehiclesStartAndEndDate(regText);
    }

    /**
     * this method checks that a car with a regular visitor permit can enter on the
     * current day
     * 
     * @param regText registration number to be checked
     * @return true if it can enter false if it cannot
     */
    private boolean checkVehiclesStartAndEndDate(String regText)
    {
	try
	{
	    Regular_visitor_permit rvp = (Regular_visitor_permit) lnkVehicle_list.getVehiclePermit(regText);

	    // make the check to see if the current date is between the start and end date
	    return lnkSystem_status.getDate().getDayNumber() <= rvp.getDate().getDayNumber()
		    && lnkSystem_status.getDate().getDayNumber() >= rvp.getStartDate().getDayNumber();

	}
	catch (ClassCastException classCastException)
	{
	    return true;
	}

    }
}
