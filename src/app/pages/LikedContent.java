package app.pages;

public final class LikedContent extends Page {

/**
 * Accepts a PageVisitor and invokes the appropriate visit method for an LikedContentPage.
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
    public LikedContent(final String name) {
        super(name);
    }
}
