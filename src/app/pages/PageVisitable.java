package app.pages;

public interface PageVisitable {

/**
 * An interface for objects that can be visited by a {@link PageVisitor}.
 * Classes implementing this interface must provide an {@code accept} method to accept a visitor.
 */
    String accept(PageVisitor visitor);
}
