package pacsus.main;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import javax.swing.JComboBox;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.ErrorManager;

import javax.swing.JTextField;

public class PermitDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtYourName;
	private JTextField txtRegNo;
	private JTextField txtIssueDate;
	private JTextField txtEndDate;
	private JTextField txtVisiting;
	private JComboBox<String> comboBox;
	private Permit_list currentPerList;
	private Vehicle_list currentVehList;
	private JLabel lblYourName;
	private JLabel lblIssueDate;
	private JLabel lblVisiting;
	private JLabel lblEndDate;

	/**
	 * 
	 * Create the dialog.
	 * 
	 * @param lnkVehicle_list
	 */
	public PermitDialog(Permit_list pl, Vehicle_list vl) {
		setupPane();
		this.currentPerList = pl;
		this.currentVehList = vl;
	}

	public void showDialog() {
		// setup the dialog for new permit
		clear();

		setTitle("Create Permit");

		setVisible(true);
	}

	public void showDialog(String cName, String vName, String regNo, int issueDate, int endDate, int permitType) {
		// setup the dialog for editing permit
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

	private void clear() {
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
		gbl_contentPanel.columnWidths = new int[] { 0, 0, 0, 0, 0, 0 };
		gbl_contentPanel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_contentPanel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_contentPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };

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
			comboBox.addItem("Day Visitor Permit");
			comboBox.addItem("University member Permit");
			comboBox.addItem("Regular Visitor Permit");
			comboBox.addItem("Permanent Visitor Permit");
			comboBox.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (comboBox.getSelectedIndex() == 1) {
						// TODO display boxes appropriate for day visitor permit
						// only need host name, name and date

						visibilityChanger(true, false, true, true, false, true);
					}

					else if (comboBox.getSelectedIndex() == 2) {
						// TODO display boxes appropriate for uni member permit
						visibilityChanger(false, false, true, false, false, true);
					}

					else if (comboBox.getSelectedIndex() == 3) {
						// TODO display boxes appropriate for regular visitor permit
						visibilityChanger(true, true, true, true, true, true);
					}

					else if (comboBox.getSelectedIndex() == 4) {
						// TODO display boxes appropriate for permanent visitor permit
						visibilityChanger(false, false, false, false, false, false);
					}
				}
			});

			GridBagConstraints gbc_comboBox = new GridBagConstraints();
			gbc_comboBox.insets = new Insets(0, 0, 5, 0);
			gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
			gbc_comboBox.gridx = 4;
			gbc_comboBox.gridy = 1;
			contentPanel.add(comboBox, gbc_comboBox);
		}

		{
			lblYourName = new JLabel("Your Name:");
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
			lblIssueDate = new JLabel("Issue Date:");
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
			lblEndDate = new JLabel("End Date:");
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
			lblVisiting = new JLabel("Visiting:");
			GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
			gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
			gbc_lblNewLabel.insets = new Insets(0, 0, 0, 5);
			gbc_lblNewLabel.gridx = 2;
			gbc_lblNewLabel.gridy = 6;
			contentPanel.add(lblVisiting, gbc_lblNewLabel);
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

					@Override
					public void actionPerformed(ActionEvent e) {
						// code to handle your button click event
						if (getTitle() == "Create Permit") {
							int permitType = comboBox.getSelectedIndex();

							switch (permitType) {

							case 0:
								JOptionPane.showMessageDialog(null, "Please select a permit ", "Error",
										JOptionPane.ERROR_MESSAGE);

								break;

							case 1:

								Day_visitor_permit dvp = new Day_visitor_permit(txtYourName.getText(),
										new Vehicle_info(txtRegNo.getText()), txtVisiting.getText(),
										new Date(Integer.parseInt(txtIssueDate.getText())));

								if (currentPerList.add(txtYourName.getText(), dvp)) {

									JOptionPane.showMessageDialog(null, "permit added successfully", "success",
											JOptionPane.INFORMATION_MESSAGE);
									setVisible(false);
								} else
									JOptionPane.showMessageDialog(null, "error adding permit", "error",
											JOptionPane.ERROR_MESSAGE);

								addToVehicleList(txtYourName.getText(), new Vehicle_info(txtRegNo.getText()));

								break;

							case 2:

								University_member_permit ump = new University_member_permit(txtYourName.getText(),
										new Vehicle_info(txtRegNo.getText()),
										new Date(Integer.parseInt(txtIssueDate.getText())));

								if (currentPerList.add(txtYourName.getText(), ump)) {

									JOptionPane.showMessageDialog(null, "permit added successfully", "success",
											JOptionPane.INFORMATION_MESSAGE);
									setVisible(false);
								} else
									JOptionPane.showMessageDialog(null, "error adding permit", "error",
											JOptionPane.ERROR_MESSAGE);

								addToVehicleList(txtYourName.getText(), new Vehicle_info(txtRegNo.getText()));

								break;

							case 3:

								Regular_visitor_permit rvm = new Regular_visitor_permit(txtYourName.getText(),
										new Vehicle_info(txtRegNo.getText()), txtVisiting.getText(),
										new Date(Integer.parseInt(txtIssueDate.getText())),
										new Date(Integer.parseInt(txtEndDate.getText())));

								if (currentPerList.add(txtYourName.getText(), rvm)) {

									JOptionPane.showMessageDialog(null, "permit added successfully", "success",
											JOptionPane.INFORMATION_MESSAGE);
									setVisible(false);
								} else
									JOptionPane.showMessageDialog(null, "error adding permit", "error",
											JOptionPane.ERROR_MESSAGE);

								addToVehicleList(txtYourName.getText(), new Vehicle_info(txtRegNo.getText()));

								break;

							case 4:
								Permanent_visitor_permit pvp = new Permanent_visitor_permit(txtYourName.getText(),
										new Vehicle_info(txtRegNo.getText()));

								if (currentPerList.add(txtYourName.getText(), pvp)) {

									JOptionPane.showMessageDialog(null, "permit added successfully", "success",
											JOptionPane.INFORMATION_MESSAGE);
									setVisible(false);
								} else
									JOptionPane.showMessageDialog(null, "error adding permit", "error",
											JOptionPane.ERROR_MESSAGE);

								addToVehicleList(txtYourName.getText(), new Vehicle_info(txtRegNo.getText()));

								break;

							}

						}

					}

				});
				buttonPane.add(cmdAction);

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

	private void visibilityChanger(boolean b1, boolean b2, boolean b3, boolean b4, boolean b5, boolean b6) {
		txtVisiting.setVisible(b1);
		txtEndDate.setVisible(b2);
		txtIssueDate.setVisible(b3);
		lblVisiting.setVisible(b4);
		lblEndDate.setVisible(b5);
		lblIssueDate.setVisible(b6);
	}

	private void addToVehicleList(String name, Vehicle_info regNo) {
		if (regNo.toString().contains(",")) {
			int commaoccurs = -1;
			for (int i = 0; i < regNo.toString().length(); i++) {

				if (regNo.toString().charAt(i) == ',') {
					Vehicle_info vh = new Vehicle_info(regNo.toString().substring(commaoccurs + 1, i));

					currentVehList.add(name, vh);
					commaoccurs = i;

				}

			}
			Vehicle_info vh = new Vehicle_info(regNo.toString().substring(commaoccurs + 1));

			currentVehList.add(name, vh);

		} else {

			currentVehList.add(name, regNo);
		}
	}
}
