package tipka.net.domain;

public enum MenuItems {

    RULES("spelu-noteikumi"),
    RESERVATION("rezervacija"),
    GIFT("-shop-product-podarochnaia-karta-"),
    FEEDBACK("atsauksmes"),
    CONTACTS("contacts");

    private String description;

    MenuItems(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
