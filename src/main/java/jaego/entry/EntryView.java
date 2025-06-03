package jaego.entry;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import jaego.utils.CategoryOptions;

public class EntryView extends JPanel {

    // Common UI elements
    private JComboBox<String> categoryComboBox;
    private JTextField idField;
    private JTextField nameField;
    private JSpinner priceSpinner;
    private JSpinner qtySpinner;
    private JButton saveButton;
    private JButton resetButton;

    // Containers for element layout
    private JPanel formPanel;
    private JPanel buttonPanel;

    public EntryView() {
        initEntryPanel();
        initFormComponents();
        initFormPanel();
        initButtonPanel();
        layoutPanelComp();
    }

    private void initEntryPanel() {
        setLayout(new BorderLayout());
    }

    private void initFormComponents() {
        categoryComboBox = new JComboBox<>(CategoryOptions.CATEGORIES);
        categoryComboBox.setEditable(false);
        
        idField = new JTextField();
        nameField = new JTextField();

        priceSpinner = new JSpinner(new SpinnerNumberModel(0.0, 0.0, Double.MAX_VALUE, 0.1));
        qtySpinner = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
    }

    private void initFormPanel() {
        formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 10, 10, 10));
        
        addFormRow("Category:", categoryComboBox);
        addFormRow("Item ID:", idField);
        addFormRow("Item Name:", nameField);
        addFormRow("Price:", priceSpinner);
        addFormRow("Quantity:", qtySpinner);
    }

    private void addFormRow(String labelText, JComponent field) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 2));
        JLabel label = new JLabel(labelText);
        label.setPreferredSize(new Dimension(100, 24));
        field.setPreferredSize(new Dimension(200, 24));
        row.add(label);
        row.add(field);
        formPanel.add(row);
        formPanel.add(Box.createVerticalStrut(2));
    }

    private void initButtonPanel() {
        saveButton = new JButton("Add Item");
        resetButton = new JButton("Reset");

        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 30, 0));
        buttonPanel.add(saveButton);
        buttonPanel.add(resetButton);
    }

    private void layoutPanelComp() {
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    // === Getters for Controller ===

    public String getIdInput() {
        return idField.getText().trim();
    }

    public String getNameInput() {
        return nameField.getText().trim();
    }

    public double getPriceInput() {
        return (Double) priceSpinner.getValue();
    }

    public int getQuantityInput() {
        return (Integer) qtySpinner.getValue();
    }

    public String getSelectedCategory() {
        return (String) categoryComboBox.getSelectedItem();
    }

    public JButton getSaveButton() {
        return saveButton;
    }

    public JButton getResetButton() {
        return resetButton;
    }

    public void clearFields() {
        idField.setText("");
        nameField.setText("");
        priceSpinner.setValue(0.0);
        qtySpinner.setValue(0);
        categoryComboBox.setSelectedIndex(0);
    }
}
