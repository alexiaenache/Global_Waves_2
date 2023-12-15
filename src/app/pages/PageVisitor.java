package app.pages;

public interface PageVisitor {

    /**
     * Visits a generic Page and performs specific operations based on the Page type.
     *
     * @param page The Page instance to visit.
     * @return A String representing the result of the visit operation.
     */
    String visit(Page page);

    /**
     * Visits a HomePage and performs specific operations for a home page.
     *
     * @param homePage The HomePage instance to visit.
     * @return A String representing the result of the visit operation.
     */
    String visit(HomePage homePage);

    /**
     * Visits an ArtistPage and performs specific operations for an artist page.
     *
     * @param artistPage The ArtistPage instance to visit.
     * @return A String representing the result of the visit operation.
     */
    String visit(ArtistPage artistPage);

    /**
     * Visits a HostPage and performs specific operations for a host page.
     *
     * @param hostPage The HostPage instance to visit.
     * @return A String representing the result of the visit operation.
     */
    String visit(HostPage hostPage);

    /**
     * Visits a LikedContent and performs specific operations for liked content.
     *
     * @param likedContent The LikedContent instance to visit.
     * @return A String representing the result of the visit operation.
     */
    String visit(LikedContent likedContent);

}
