package app.pages;

public class ArtistPage extends Page {

    public String accept(PageVisitor v) {
        return v.visit(this);
    }
    public String getName() {
        return super.getName();
    }
    public ArtistPage(String name) {
        super(name);
    }
}
