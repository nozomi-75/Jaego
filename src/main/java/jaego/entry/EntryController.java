package jaego.entry;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import jaego.utils.SampleItem;

public class EntryController {

    private final EntryModel model;
    private final EntryView view;

    public EntryController(EntryModel model, EntryView view) {
        this.model = model;
        this.view = view;
        attachEventHandlers();
    }

    private void attachEventHandlers() {
        view.getSaveButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSave();
            }
        });

        view.getResetButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.clearFields();
            }
        });
    }

    private void handleSave() {
        String id = view.getIdInput();
        String name = view.getNameInput();
        double price = view.getPriceInput();
        int quantity = view.getQuantityInput();
        String category = view.getSelectedCategory();

        if (id.isEmpty() || name.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Product ID and Name cannot be empty.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        SampleItem item = new SampleItem(id, name, price, quantity, category);
        model.addItem(item);
        JOptionPane.showMessageDialog(null, "Product saved successfully.");
        view.clearFields();
    }
}
