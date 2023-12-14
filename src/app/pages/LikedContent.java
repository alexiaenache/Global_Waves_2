package app.pages;

public class LikedContent extends Page {
    public String accept(PageVisitor v) {
        return v.visit(this);
    }
    public String getName() {
        return super.getName();
    }
    public LikedContent(String name) {
        super(name);
    }
}
