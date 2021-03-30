package pacsus.main;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/* Generated by Together */

/**
 * This represents a hypothetical clock set to produce a "new day" signal every
 * midnight.
 *
 * In this emulation the signal can be triggered by a simple button all by
 * itself in a JFrame. The uses made of this timing signal are described in the
 * Timer use case diagram (hyperlinked from this class).
 *
 * There will only be a single instance of this class. It will maintain a
 * current Date object, incremented with each new day. The interface will always
 * show the current date.
 *
 * @stereotype boundary
 */
public class Timer extends JFrame implements ActionListener
{
    /**
     * The Timer has a navigable association to the system status so that it can
     * send the new date each time that a new day starts.
     * 
     * @clientCardinality 1
     * @supplierCardinality 1
     * @label It's a new day
     * @directed
     */
    private System_status lnkSystem_status;

    /**
     * The Timer has a navigable association to the permit list so that it can send
     * the new date each time that a new day starts.
     * 
     * @clientCardinality 1
     * @supplierCardinality 1
     * @label It's a new day
     * @directed
     */
    private Permit_list lnkPermit_list;

    /**
     * This attribute holds today's date.
     *
     * It is incremented every time a midnight "tick" occurs (by a click on the
     * button in the visible interface).
     *
     * The date is sent to the System_status every time that a tick occurs, so that
     * it can keep an up-to-date note of the date (for example for attaching to each
     * entry in the log).
     *
     * The date is also sent to the Permit_list every time that a tick occurs so
     * that tidying up actions on the permits can be carried out - for example
     * automatically cancelling expired permits.
     * 
     * @supplierCardinality 1
     * @clientCardinality 1
     * @link aggregation
     * @label Contains
     * @directed
     */
    private Date today;

    // JButtons
    private JButton newDay;

    // JLabel
    private JLabel currentDay;

    /**
     * Generated Constructor
     * 
     * @param systemStatus
     * @param permitList
     */
    public Timer(System_status systemStatus, Permit_list permitList)
    {
	// Make the links to System_Status and Permit_List
	lnkSystem_status = systemStatus;
	lnkPermit_list = permitList;
	today = new Date();

	loadGUI();

    }

    /**
     * A method to setup the Frames interface, position it's buttons and labels.
     * Finally, setting it's visability to true.
     */
    private void loadGUI()
    {
	setTitle();
	setLocation(40, 40);
	setSize(350, 150);
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	Container window = getContentPane();
	window.setLayout(new FlowLayout()); // The default is that JFrame uses BorderLayout

	// Set up input GUI
	// Buttons
	newDay = new JButton("New day");
	window.add(newDay);
	newDay.addActionListener(this);
	// Labels
	currentDay = new JLabel("Current Day: " + today.getDayNumber());
	window.add(currentDay);

	// Display the frame
	setVisible(true);
    }

    /**
     * A method which sets the title of the window.
     */
    private void setTitle()
    {
	// Configure the window
	setTitle("Timer Window: Day: " + today.getDayNumber());
    }

    /**
     * A implemented method to listen for button presses in the UI
     */
    public void actionPerformed(ActionEvent e)
    {
	if (e.getSource().equals(newDay))
	{
	    cancelOutOfDatePermits();
	    setHasEnteredToday();
	    lnkSystem_status.nextDay();
	    today = lnkSystem_status.getDate();
	    currentDay.setText("Current Day: " + today.getDayNumber());
	    System.out.println("New day button pressed. Value: " + today.getDayNumber());
	    setTitle();

	    if (today.getDayNumber() == 1)
	    {
		System.out.println("new year");

		yearReset();
	    }
	}

    } // actionPerformed

    /**
     * A method to be called to set a permit as not entered when a new day starts
     */
    private void setHasEnteredToday()
    {
	lnkPermit_list.setPermitsHasEntered();
    }

    /**
     * A method which takes permits which may expire, such as the Day and Regular
     * vistiors and cancels them when they are out of date on the start of a new
     * day.
     */
    private void cancelOutOfDatePermits()
    {
	ArrayList<Permit> list = lnkPermit_list.getPermitsByType("Day_visitor_permit");
	list.addAll(lnkPermit_list.getPermitsByType("Regular_visitor_permit"));

	for (Permit permit : list)
	{
	    if (permit.getDate().getDayNumber() < today.getDayNumber() + 1)
	    {
		if (lnkPermit_list.deletePermit(permit.getPermitHolder()))
		{
		    JOptionPane.showMessageDialog(this, permit.toString() + " has expired!");
		}
	    }
	}
    }

    /**
     * A method which takes types of permits, and resets their warnings and
     * suspended status on the turn of a new year
     */
    private void yearReset()
    {
	ArrayList<Permit> list = lnkPermit_list.getPermitsByType("Permanent_visitor_permit");
	list.addAll(lnkPermit_list.getPermitsByType("Regular_visitor_permit"));
	list.addAll(lnkPermit_list.getPermitsByType("University_member_permit"));
	for (Permit permit : list)
	{
	    if (lnkPermit_list.getPermit(permit.getPermitHolder()).isSuspended())
	    {
		lnkPermit_list.unsuspendPermit(permit.getPermitHolder());

	    }
	    else if (lnkPermit_list.getPermit(permit.getPermitHolder()).getWarnings() >= 1)
	    {
		lnkPermit_list.resetWarnings(permit.getPermitHolder());
	    }

	}

    }
}