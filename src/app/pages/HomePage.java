package app.pages;

public class HomePage extends Page {
    public String accept(PageVisitor v) {
        return v.visit(this);
    }
    public String getName() {
        return super.getName();
    }
    public HomePage(String name) {
        super(name);
    }

}
