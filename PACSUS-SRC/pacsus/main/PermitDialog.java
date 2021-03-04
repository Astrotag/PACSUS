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

import javax.swing.JTextField;

public class PermitDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtYourName;
	private JTextField txtRegNo;
	private JTextField txtIssueDate;
	private JTextField txtEndDate;
	private JTextField txtVisiting;
	private JComboBox<String> comboBox;
	/**
	 * Create the dialog.
	 */
	public PermitDialog() 
	{
	setupPane();
	}
	
	public void showDialog() {
		//setup the dialog for new permit
		clear();
		
		setTitle("Create Permit");
		
		setVisible(true);
	}
	
	public void showDialog(String cName, String vName, String regNo,int issueDate, int endDate,int permitType ) 
	{
		//setup the dialog for editing permit
		clear();
		txtYourName.setText(cName);
		txtVisiting.setText(vName);
		txtRegNo.setText(regNo);
		txtIssueDate.setText(String.valueOf(issueDate));
		txtEndDate.setText(String.valueOf(endDate));
		comboBox.setSelectedIndex(permitType);
		setTitle("Edit Permit");
		setVisible(false);
		
	}
	
	
	
	
	
	private void clear() 
	{
		txtYourName.setText("");
		txtVisiting.setText("");
		txtRegNo.setText("");
		txtEndDate.setText("");
		txtIssueDate.setText("");
		comboBox.setSelectedIndex(0);
		
	}

	private void setupPane() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblPermitType = new JLabel("Permit Type:");
			GridBagConstraints gbc_lblPermitType = new GridBagConstraints();
			gbc_lblPermitType.anchor = GridBagConstraints.EAST;
			gbc_lblPermitType.insets = new Insets(0, 0, 5, 5);
			gbc_lblPermitType.gridx = 2;
			gbc_lblPermitType.gridy = 1;
			contentPanel.add(lblPermitType, gbc_lblPermitType);
		}
		{
			comboBox = new JComboBox<String>();
			comboBox.addItem("Select Permit");
			comboBox.addItem("Visitor Permit");
			comboBox.addItem("University member Permit");
			comboBox.addItem("Regular Visitor Permit");
			comboBox.addItem("Permanent Visitor Permit");
			
			
			GridBagConstraints gbc_comboBox = new GridBagConstraints();
			gbc_comboBox.insets = new Insets(0, 0, 5, 0);
			gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
			gbc_comboBox.gridx = 4;
			gbc_comboBox.gridy = 1;
			contentPanel.add(comboBox, gbc_comboBox);
		}
		{
			JLabel lblYourName = new JLabel("Your Name:");
			GridBagConstraints gbc_lblYourName = new GridBagConstraints();
			gbc_lblYourName.anchor = GridBagConstraints.EAST;
			gbc_lblYourName.insets = new Insets(0, 0, 5, 5);
			gbc_lblYourName.gridx = 2;
			gbc_lblYourName.gridy = 2;
			contentPanel.add(lblYourName, gbc_lblYourName);
		}
		{
			txtYourName = new JTextField();
			GridBagConstraints gbc_txtYourName = new GridBagConstraints();
			gbc_txtYourName.insets = new Insets(0, 0, 5, 0);
			gbc_txtYourName.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtYourName.gridx = 4;
			gbc_txtYourName.gridy = 2;
			contentPanel.add(txtYourName, gbc_txtYourName);
			txtYourName.setColumns(10);
		}
		{
			JLabel lblRegNo = new JLabel("Reg No(s):");
			GridBagConstraints gbc_lblRegNo = new GridBagConstraints();
			gbc_lblRegNo.anchor = GridBagConstraints.EAST;
			gbc_lblRegNo.insets = new Insets(0, 0, 5, 5);
			gbc_lblRegNo.gridx = 2;
			gbc_lblRegNo.gridy = 3;
			contentPanel.add(lblRegNo, gbc_lblRegNo);
		}
		{
			txtRegNo = new JTextField();
			GridBagConstraints gbc_txtRagNo = new GridBagConstraints();
			gbc_txtRagNo.anchor = GridBagConstraints.NORTH;
			gbc_txtRagNo.insets = new Insets(0, 0, 5, 0);
			gbc_txtRagNo.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtRagNo.gridx = 4;
			gbc_txtRagNo.gridy = 3;
			contentPanel.add(txtRegNo, gbc_txtRagNo);
			txtRegNo.setColumns(10);
		}
		{
			JLabel lblIssueDate = new JLabel("Issue Date:");
			GridBagConstraints gbc_lblIssueDate = new GridBagConstraints();
			gbc_lblIssueDate.anchor = GridBagConstraints.EAST;
			gbc_lblIssueDate.insets = new Insets(0, 0, 5, 5);
			gbc_lblIssueDate.gridx = 2;
			gbc_lblIssueDate.gridy = 4;
			contentPanel.add(lblIssueDate, gbc_lblIssueDate);
		}
		{
			txtIssueDate = new JTextField();
			txtIssueDate.setText("");
			GridBagConstraints gbc_txtIssueDate = new GridBagConstraints();
			gbc_txtIssueDate.insets = new Insets(0, 0, 5, 0);
			gbc_txtIssueDate.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtIssueDate.gridx = 4;
			gbc_txtIssueDate.gridy = 4;
			contentPanel.add(txtIssueDate, gbc_txtIssueDate);
			txtIssueDate.setColumns(10);
		}
		{
			JLabel lblEndDate = new JLabel("End Date:");
			GridBagConstraints gbc_lblEndDate = new GridBagConstraints();
			gbc_lblEndDate.anchor = GridBagConstraints.EAST;
			gbc_lblEndDate.insets = new Insets(0, 0, 5, 5);
			gbc_lblEndDate.gridx = 2;
			gbc_lblEndDate.gridy = 5;
			contentPanel.add(lblEndDate, gbc_lblEndDate);
		}
		{
			txtEndDate = new JTextField();
			txtEndDate.setText("");
			GridBagConstraints gbc_txtEndDate = new GridBagConstraints();
			gbc_txtEndDate.insets = new Insets(0, 0, 5, 0);
			gbc_txtEndDate.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtEndDate.gridx = 4;
			gbc_txtEndDate.gridy = 5;
			contentPanel.add(txtEndDate, gbc_txtEndDate);
			txtEndDate.setColumns(10);
		}
		{
			JLabel lblNewLabel = new JLabel("Visiting:");
			GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
			gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
			gbc_lblNewLabel.insets = new Insets(0, 0, 0, 5);
			gbc_lblNewLabel.gridx = 2;
			gbc_lblNewLabel.gridy = 6;
			contentPanel.add(lblNewLabel, gbc_lblNewLabel);
		}
		{
			txtVisiting = new JTextField();
			txtVisiting.setText("");
			GridBagConstraints gbc_txtVisiting = new GridBagConstraints();
			gbc_txtVisiting.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtVisiting.gridx = 4;
			gbc_txtVisiting.gridy = 6;
			contentPanel.add(txtVisiting, gbc_txtVisiting);
			txtVisiting.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton cmdAction = new JButton("Save");
				cmdAction.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
					setVisible(false);
					
					//TODO the method for saving new/updating existing permits 
					
					}
				});
				
				buttonPane.add(cmdAction);
				getRootPane().setDefaultButton(cmdAction);
			}
			{
				JButton cmdCancel = new JButton("Cancel");
				
				cmdCancel.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
					setVisible(false);
					
					}
				});
				buttonPane.add(cmdCancel);
			}
		}
	
	}

}
