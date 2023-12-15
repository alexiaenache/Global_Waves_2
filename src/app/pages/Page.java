package app.pages;

public class Page implements PageVisitable {
    private String name;

    /**
     * Accepts a PageVisitor and invokes the appropriate visit method for a Page.
     *
     * @param v The PageVisitor to accept.
     * @return A String representing the result of the visit operation.
     */
    public String accept(final PageVisitor v) {
        return v.visit(this);
    }

    /**
     * Gets the name of the user the page belongs to.
     *
     * @return A String representing the name of the page.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the user the page belongs to.
     *
     * @param name The String to set as the name of the page.
     */
    public void setName(final String name) {
        this.name = name;
    }

    public Page(final String name) {
        this.name = name;
    }
}
