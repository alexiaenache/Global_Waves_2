package app.user;

public final class Merch {
    private String name;
    private String description;
    private int price;

    public Merch(final String name, final String description, final int price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    /**
     * Sets the name of the merch.
     *
     * @param name The new name for the merch.
     */
    public void setName(final String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the merch.
     *
     * @param description The new name for the merch.
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

}
