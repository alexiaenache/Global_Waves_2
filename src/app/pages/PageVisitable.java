package app.pages;

public interface PageVisitable {
    public String accept(PageVisitor visitor);
}
