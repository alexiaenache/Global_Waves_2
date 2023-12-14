package app.pages;

public class Page implements PageVisitable{
    private String name;
    public String accept(PageVisitor v) {
        return v.visit(this);
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Page(String name) {
        this.name = name;
    }
}
