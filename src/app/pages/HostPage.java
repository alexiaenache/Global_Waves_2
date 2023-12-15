package app.pages;

public final class HostPage extends Page {

/**
 * Accepts a PageVisitor and invokes the appropriate visit method for an HostPage.
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
    public HostPage(final String name) {
        super(name);
    }
}
