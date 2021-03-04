package pacsus.main;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JComboBox;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChoicePane extends JDialog
{

    private JComboBox comboBox;
    private JLabel lblChoice;
    private JButton cmdEnter;
    private final JPanel contentPanel = new JPanel();

    /**
     * Create the dialog.
     */
    public ChoicePane()
    {

	setupDialog();

    }

    public void showDialog(int type)
    {
	switch (type)
	{

	case 0:
	    // set up the dialog for issuing warning on permit
	    // TODO set up the combobox

	    setDialogText("Issue warning ", "Confirm warning", "Choose Permit:");
	    break;

	case 1:
	    // set up the dialog for unsuspending permit
	    // TODO set up the combobox

	    setDialogText("Unsuspend Permit", "Unsuspend", "Choose Permit:");
	    break;

	case 2:
	    // set up the dialog for cancelling permit
	    // TODO set up the combobox

	    setTitle("Cancel A Permit ");

	    cmdEnter.setText("Delete");
	    lblChoice.setText("choose permit:");
	    
	    setDialogText("Cancel A Permit ", "Delete", "Choose Permit:");
	    break;

	case 3:
	    // set up the dialog for status readout
	    // TODO set up the combobox
	
	    setDialogText("Status Enquiry ", "Show Details", "Choose Permit: ");
	    break;
	}
    }

    /**
     * A simple method to set the text shown in the dialog.
     * 
     * @param title - Title of the dialog
     * @param buttonText - Text shown in the button on the dialog
     * @param lblText - Text shown in the label of the dialog
     * 
     */
    private void setDialogText(String title, String buttonText, String lblText)
    {
	setTitle(title);

	cmdEnter.setText(buttonText);
	lblChoice.setText(lblText);

	setVisible(true);
    }
    
    private void setupDialog()
    {
	setBounds(100, 100, 450, 300);
	getContentPane().setLayout(new BorderLayout());
	contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
	getContentPane().add(contentPanel, BorderLayout.CENTER);
	
	GridBagLayout gbl_contentPanel = new GridBagLayout();
	gbl_contentPanel.columnWidths = new int[]
	{ 0, 0, 0, 0, 0, 0 };
	gbl_contentPanel.rowHeights = new int[]
	{ 0, 0, 0 };
	gbl_contentPanel.columnWeights = new double[]
	{ 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
	gbl_contentPanel.rowWeights = new double[]
	{ 0.0, 0.0, Double.MIN_VALUE };
	
	contentPanel.setLayout(gbl_contentPanel);
	{
	    lblChoice = new JLabel("choice text");
	    GridBagConstraints gbc_lblChoice = new GridBagConstraints();
	    gbc_lblChoice.insets = new Insets(0, 0, 0, 5);
	    gbc_lblChoice.anchor = GridBagConstraints.WEST;
	    gbc_lblChoice.gridx = 1;
	    gbc_lblChoice.gridy = 1;
	    contentPanel.add(lblChoice, gbc_lblChoice);
	}
	
	{
	    comboBox = new JComboBox();
	    GridBagConstraints gbc_comboBox = new GridBagConstraints();
	    gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
	    gbc_comboBox.gridx = 4;
	    gbc_comboBox.gridy = 1;
	    contentPanel.add(comboBox, gbc_comboBox);
	}
	
	{
	    JPanel buttonPane = new JPanel();
	    buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
	    getContentPane().add(buttonPane, BorderLayout.SOUTH);
	    {
		cmdEnter = new JButton("OK");
		cmdEnter.addActionListener(new ActionListener()
		{
		    public void actionPerformed(ActionEvent e)
		    {
			setVisible(false);
			// TODO add code to handle the different scenarios

		    }
		});
		buttonPane.add(cmdEnter);
		getRootPane().setDefaultButton(cmdEnter);
	    }
	    
	    {
		JButton cmdCancel = new JButton("Cancel");
		cmdCancel.addActionListener(new ActionListener()
		{
		    public void actionPerformed(ActionEvent e)
		    {
			setVisible(false);

		    }
		});
		buttonPane.add(cmdCancel);
	    }
	}
    }
}
