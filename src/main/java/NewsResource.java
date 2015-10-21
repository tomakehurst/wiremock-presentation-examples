import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/headlines")
public class NewsResource {

    private final NewsService newsService;

    public NewsResource(NewsService newsService) {
        this.newsService = newsService;
    }

    @GET
    @Path("software")
    public String getLatestSoftwareHeadline() {
        return newsService.getFirstSoftwareHeadline();
    }
}
