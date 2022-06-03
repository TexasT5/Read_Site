package Model.Trendyol;

public class AllVariants {
    public int itemNumber;
    public double price;
    public boolean inStock;
    public String currency;
    public String value;
    public String barcode;

    public AllVariants(int itemNumber, double price, boolean inStock, String currency, String value, String barcode) {
        this.itemNumber = itemNumber;
        this.price = price;
        this.inStock = inStock;
        this.currency = currency;
        this.value = value;
        this.barcode = barcode;
    }

    public int getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(int itemNumber) {
        this.itemNumber = itemNumber;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
}
