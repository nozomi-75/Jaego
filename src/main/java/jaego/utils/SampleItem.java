package jaego.utils;


/**
 * SampleItem class is a Plain Old Java Object that represents an item.
 * It contains details often associated with an item.
 * 
 * @param itemID The ID/SKU of an item.
 * @param itemName The name of the item.
 * @param itemPrice The price of the item. Should not be negative.
 * @param itemQty The quantity of an item. Should not be negative.
 * @param itemCategory The category of an item.
 */
public class SampleItem {
    private String itemID;
    private String itemName;
    private double itemPrice;
    private int itemQty;
    private String itemCategory;

    public SampleItem(
        String itemID,
        String itemName,
        double itemPrice,
        int itemQty,
        String itemCategory
    ) {
        if (itemQty < 0) {
            throw new InvalidItemException("The item quantity should not be negative.");
        }

        if (itemPrice < 0) {
            throw new InvalidItemException("The item price should not be negative.");
        }

        this.itemID = itemID;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemQty = itemQty;
        this.itemCategory = itemCategory;
    }

    private static class InvalidItemException extends RuntimeException {
        public InvalidItemException(String message) {
            super(message);
        }
    }

    // GETTER METHODS STARTING HERE

    public String getID() {
        return itemID;
    }

    public String getName() {
        return itemName;
    }

    public double getPrice() {
        return itemPrice;
    }

    public int getQty() {
        return itemQty;
    }

    public String getCategory() {
        return itemCategory;
    }

    // SETTER METHODS STARTING HERE

    public void setID(String newID) {
        this.itemID = newID;
    }

    public void setName(String newName) {
        this.itemName = newName;
    }

    public void setPrice(double newPrice) {
        this.itemPrice = newPrice;
    }

    public void setQty(int newQty) {
        this.itemQty = newQty;
    }

    public void setCategory(String newCategory) {
        this.itemCategory = newCategory;
    }
}