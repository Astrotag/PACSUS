package pacsus.main;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;

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
	// TODO Auto-generated constructor stub

	// Make the links to System_Status and Permit_List
	lnkSystem_status = systemStatus;
	lnkPermit_list = permitList;
	today = new Date();

	loadGUI();

    }

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

    private void setTitle()
    {
	// Configure the window
	setTitle("Timer Window: Day: " + today.getDayNumber());
    }

    // Button click handling:
    public void actionPerformed(ActionEvent e)
    {
	if (e.getSource().equals(newDay))
	{
	    lnkSystem_status.nextDay();
	    today = lnkSystem_status.getDate();
	    currentDay.setText("Current Day: " + today.getDayNumber());
	    System.out.println("New day button pressed. Value: " + today.getDayNumber());
	    setTitle();
	    cancelOutOfDatePermits();
	}

    } // actionPerformed
    
    private void cancelOutOfDatePermits() {
	ArrayList<Permit> list = lnkPermit_list.getPermitsByType("Day_visitor_permit");
	
	for (Permit permit : list)
	{
	    if (permit.getDate().getDayNumber() < today.getDayNumber())
	    {
		if (lnkPermit_list.deletePermit(permit.getPermitHolder()))
		{
		    JOptionPane.showMessageDialog(this, permit.toString() + " has expired!");
		}
	    }
	}
    }
}