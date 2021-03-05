package pacsus.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * The Campus security staff actually activate and deactivate the barriers,
 * enter warnings for vehicles breaching the parking regulations and monitor the
 * state of the barrier system. This class represents the interactive interface
 * to these functions. Information about these functions is in the Campus
 * security use case diagram (hyperlinked from this class). The interface
 * comprises one screen with the various functions present on it, all on view at
 * once since there aren't many. There could be several instances of this class:
 * one in the central security office, and one at each entrance to the
 * University (in a staffed booth). The class implements Observer, and observes
 * the system status so that it can keep the displayed information up to date
 * (current date, barriers active or not, log of entries through the barriers).
 * 
 * @stereotype boundary
 */
public class Campus_security extends JFrame implements Observer, ActionListener
{
    /**
     * Each instance of Campus_security has a navigable association to the vehicle
     * list so that warnings can be recorded on the permit for vehicles that breach
     * parking regulations. Note that this process goes via the vehicle list as the
     * only information available about such a vehicle is its registration number.
     * 
     * @clientCardinality 1..*
     * @supplierCardinality 1
     * @label Record warning
     * @directed
     */
    private Vehicle_list lnkVehicle_list;

    /**
     * Each instance of Campus_security has a navigable association to the system
     * status so that it can both find out status information about the system, and
     * send controlling messages to the system status (to activate/deactivate the
     * system).
     * 
     * @clientCardinality 1..*
     * @supplierCardinality 1
     * @label Control/monitor
     * @directed
     */
    private System_status lnkSystem_status;

    private JComboBox<String> cb;
    private JTextField barrierStatus, warningStatus;
    private JButton activateButton, deactivateButton, issueWarning;

    /**
     * Generated Constructor
     * 
     * @param systemStatus
     * @param vehicleList
     */
    public Campus_security(System_status systemStatus, Vehicle_list vehicleList)
    {
	// Record references to the parent controller and the model
	this.lnkVehicle_list = vehicleList;
	this.lnkSystem_status = systemStatus;

	loadGUI();
    }

    private void loadGUI()
    {
	setTitle("Campus Security");
	setLocation(40, 195);
	setSize(350, 300);
	setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	setBackground(Color.white);

	JPanel statusPanel = new JPanel();
	statusPanel.add(new JLabel("Barrier Status: "));
	barrierStatus = new JTextField(lnkSystem_status.getSystemActive(), 15);
	barrierStatus.setEditable(false);
	barrierStatus.setMaximumSize(new Dimension(75, 300));
	statusPanel.add(barrierStatus);
	add(statusPanel);

	JPanel statusPanelButtons = new JPanel();
	activateButton = new JButton("Activate\n");
	statusPanelButtons.add(activateButton);
	activateButton.addActionListener(this);
	
	deactivateButton = new JButton("Deactivate\n");
	statusPanelButtons.add(deactivateButton);
	deactivateButton.addActionListener(this);
	add(statusPanelButtons);

	add(Box.createRigidArea(new Dimension(0, 30)));

	JPanel vehicleWarningPanel = new JPanel();
	vehicleWarningPanel.setLayout(new BoxLayout(vehicleWarningPanel, BoxLayout.Y_AXIS));
	vehicleWarningPanel.add(new JLabel("Issue Vehicle Warning"));

	JPanel vWPanelRegs = new JPanel();
	String[] vehicleRegs =
	{ "A", "BCD", "EFGHIJ" };
	cb = new JComboBox<String>(vehicleRegs);
	cb.setMaximumSize(new Dimension(150, 20));
	vWPanelRegs.add(cb);
	vehicleWarningPanel.add(vWPanelRegs);

	JPanel vWPanelIssueWarning = new JPanel();
	issueWarning = new JButton("Issue Warning\n");
	vWPanelIssueWarning.add(issueWarning);
	issueWarning.addActionListener(this);
	vehicleWarningPanel.add(vWPanelIssueWarning);

	JPanel vWPanelIWText = new JPanel();
	warningStatus = new JTextField("", 20);
	warningStatus.setEditable(false);
	vWPanelIWText.add(warningStatus);
	vehicleWarningPanel.add(vWPanelIWText);

	add(vehicleWarningPanel);
	add(Box.createRigidArea(new Dimension(0, 150)));
	setVisible(true);
    }

    @Override
    public void update(Observable o, Object arg)
    {
	// TODO Auto-generated method stub
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
	if (e.getSource().equals(activateButton))
	{
	    lnkSystem_status.setSystemActive(true);
	    barrierStatus.setText("Active");
	    barrierStatus.setForeground(Color.GREEN);
//			System.out.println("*C_S* Barrier Status: " + status.getText()); // For Testing
	}

	if (e.getSource().equals(deactivateButton))
	{
	    lnkSystem_status.setSystemActive(false);
	    barrierStatus.setText("Deactivated");
	    barrierStatus.setForeground(Color.RED);
//			System.out.println("*C_S* Barrier Status: " + status.getText()); // For Testing
	}

	if (e.getSource().equals(issueWarning))
	{
	    warningStatus.setText("Warn Successfully Issued.");
	    warningStatus.setForeground(Color.GREEN);
	}
    }
}