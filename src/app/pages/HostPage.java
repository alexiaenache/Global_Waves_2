package app.pages;

public class HostPage extends Page {
    public String accept(PageVisitor v) {
        return v.visit(this);
    }
    public String getName() {
        return super.getName();
    }
    public HostPage(String name) {
        super(name);
    }
}
