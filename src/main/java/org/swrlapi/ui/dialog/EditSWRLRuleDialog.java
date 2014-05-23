package org.swrlapi.ui.dialog;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.swrlapi.ui.SWRLAPIApplicationController;
import org.swrlapi.ui.core.SQWRLApplicationView;
import org.swrlapi.ui.model.SWRLRulesTableModel;

public class EditSWRLRuleDialog extends JDialog
{
	private static final long serialVersionUID = 1L;

	private final SWRLAPIApplicationController application;

	private JLabel ruleNameLabel, ruleTextLabel, commentLabel;
	private JTextField ruleNameTextField, commentTextField;
	private JTextArea ruleTextTextArea;

	private boolean editMode = false;

	public EditSWRLRuleDialog(SWRLAPIApplicationController application)
	{
		setTitle("Edit SWRL Rule");
		setModal(true);

		this.application = application;

		createComponents();

		setLocationRelativeTo(application.getApplicationView());

		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we)
			{
			}
		}); // Thwart user close
	}

	public void reset()
	{
		clearEntryFields();
	}

	public void setEditMappingExpression(String ruleName, String ruleText, String comment)
	{
		clearEntryFields();

		ruleNameTextField.setText(ruleName);
		ruleTextTextArea.setText(ruleText);
		commentTextField.setText(comment);

		editMode = true;
	}

	private void clearEntryFields()
	{
		ruleNameTextField.setText("");
		ruleNameTextField.setEnabled(true);
		ruleTextTextArea.setText("");
		ruleTextTextArea.setEnabled(true);
		ruleTextTextArea.setText("");
		commentTextField.setText("");

		editMode = false;
	}

	private void createComponents()
	{
		Container contentPane = getContentPane();
		JPanel surroundPanel, buttonPanel, textFieldPanel;
		JButton cancelButton, okButton;

		ruleNameLabel = new JLabel("Comment");
		ruleNameTextField = new JTextField("");

		ruleTextTextArea = new JTextArea("", 20, 80);
		ruleTextTextArea.setBorder(BorderFactory.createLoweredBevelBorder());

		ruleTextLabel = new JLabel("Sheet name");

		commentLabel = new JLabel("Start column");

		commentTextField = new JTextField("");

		cancelButton = new JButton("Cancel");
		cancelButton.setPreferredSize(new Dimension(100, 30));
		cancelButton.addActionListener(new CancelButtonActionListener());

		okButton = new JButton("OK");
		okButton.setPreferredSize(new Dimension(100, 30));
		okButton.addActionListener(new OkButtonActionListener());

		contentPane.setLayout(new BorderLayout());

		surroundPanel = new JPanel(new BorderLayout());
		surroundPanel.setBorder(BorderFactory.createLoweredBevelBorder());
		contentPane.add(surroundPanel, BorderLayout.CENTER);

		textFieldPanel = new JPanel(new GridLayout(6, 2));

		surroundPanel.add(textFieldPanel, BorderLayout.NORTH);
		surroundPanel.add(ruleTextTextArea, BorderLayout.CENTER);

		textFieldPanel.add(ruleNameLabel);
		textFieldPanel.add(ruleNameTextField);
		textFieldPanel.add(ruleTextLabel);
		textFieldPanel.add(commentLabel);
		textFieldPanel.add(commentTextField);

		buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		buttonPanel.add(cancelButton);
		buttonPanel.add(okButton);

		surroundPanel.add(buttonPanel, BorderLayout.SOUTH);

		pack();
	}

	private class CancelButtonActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			clearEntryFields();
			setVisible(false);
		}
	}

	private class OkButtonActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			String ruleName = "", ruleText = "", comment = "";
			boolean errorOccurred = false;

			comment = ruleNameTextField.getText();

			try {
				ruleName = ruleNameTextField.getText().trim().toUpperCase();
				ruleText = ruleTextTextArea.getText().trim().toUpperCase();
				comment = commentTextField.getText().trim().toUpperCase();

				// Check the rule and name
			} catch (Exception ex) {
				getApplicationDialogManager().showErrorMessageDialog(ex.getMessage());
				errorOccurred = true;
			}

			if (!errorOccurred) {
				if (editMode) {
					getSWRLRulesModel().removeSWRLRule(ruleName); // Remove original
					getSWRLRulesModel().addSWRLRule(ruleName, ruleText, comment);
				} else {
					getSWRLRulesModel().addSWRLRule(ruleName, ruleText, comment);
				}
				setVisible(false);
				clearEntryFields();
			}
		}
	}

	private SWRLRulesTableModel getSWRLRulesModel()
	{
		return application.getApplicationModel().getSWRLRulesTableModel();
	}

	private SQWRLApplicationView getApplicationView()
	{
		return application.getApplicationView();
	}

	private SWRLAPIApplicationDialogManager getApplicationDialogManager()
	{
		return getApplicationView().getApplicationDialogManager();
	}
}