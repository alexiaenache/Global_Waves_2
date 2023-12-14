package app.pages;

public interface PageVisitor {
    public String visit(Page page);
    public String visit(HomePage homePage);
    public String visit(ArtistPage artistPage);
    public String visit(HostPage hostPage);
    public String visit(LikedContent likedContent);

}
