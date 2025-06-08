package jaego.edit;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.stream.Stream;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import jaego.utils.ButtonFactory;
import jaego.utils.CategoryOptions;
import jaego.utils.SampleItem;

/**
 * A modal dialog for editing or deleting a SampleItem.
 */
public class EditDialog extends JDialog {
    private final JComboBox<String> categoryBox = new JComboBox<>(CategoryOptions.CATEGORIES);
    private final JTextField idField = new JTextField(20);
    private final JTextField nameField = new JTextField(20);
    private final JSpinner priceSpinner = new JSpinner(new SpinnerNumberModel(0.0, 0.0, Double.MAX_VALUE, 0.1));
    private final JSpinner qtySpinner = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));

    private final EditController controller;

    public EditDialog(JFrame parent, SampleItem item) {
        super(parent, "Edit Item - " + item.getID(), true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        controller = new EditController(categoryBox, idField, nameField, priceSpinner, qtySpinner);
        controller.populateFromItem(item);
        
        add(createFormPanel(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);

        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));

        panel.add(new JLabel("Category:"));
        panel.add(categoryBox);
        // panel.add(new JLabel("ID:"));
        // panel.add(idField);
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Price:"));
        priceSpinner.setPreferredSize(new Dimension(100, 25));
        panel.add(priceSpinner);
        panel.add(new JLabel("Quantity:"));
        qtySpinner.setPreferredSize(new Dimension(100, 25));
        panel.add(qtySpinner);

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        Dimension btnSize = new Dimension(72, 25);

        JButton saveBtn = ButtonFactory.createSizedButton("Save", () -> {
            controller.markConfirmed();
            dispose();
        }, btnSize);

        JButton deleteBtn = ButtonFactory.createSizedColoredButton("Delete", () -> {
            controller.markDeleteRequested();
            dispose();
        }, btnSize, new Color(165, 29, 45), Color.WHITE);

        JButton cancelBtn = ButtonFactory.createSizedButton("Cancel", () -> dispose(), btnSize);
        Stream.of(saveBtn, deleteBtn, cancelBtn).forEach(btn -> panel.add(btn));
        return panel;
    }

    // Public accessors (delegating to controller)

    public boolean isConfirmed() {
        return controller.isConfirmed();
    }

    public boolean isDeleteRequested() {
        return controller.isDeleteRequested();
    }

    public String getUpdatedCategory() {
        return controller.getUpdatedCategory();
    }

    public String getUpdatedId() {
        return controller.getUpdatedId();
    }

    public String getUpdatedName() {
        return controller.getUpdatedName();
    }

    public double getUpdatedPrice() {
        return controller.getUpdatedPrice();
    }

    public int getUpdatedQty() {
        return controller.getUpdatedQty();
    }
}
