package app.pages;

public final class HomePage extends Page {

/**
 * Accepts a PageVisitor and invokes the appropriate visit method for an HomePage.
 *
 * @param v The PageVisitor to accept.
 * @return A String representing the result of the visit operation.
 */
    public String accept(final PageVisitor v) {
        return v.visit(this);
    }
    public String getName() {
        return super.getName();
    }
    public HomePage(final String name) {
        super(name);
    }

}
