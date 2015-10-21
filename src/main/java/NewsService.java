import com.jayway.jsonpath.JsonPath;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.Response;

import static javax.ws.rs.client.Entity.entity;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;


public class NewsService {

    private final JerseyClient client;
    private final int port;

    public NewsService(int port) {
        client = JerseyClientBuilder.createClient();
        this.port = port;
    }

    public NewsService() {
        this(8080);
    }

    public String getFirstSoftwareHeadline() {
        try {
            Response response = client.target("http://localhost:" + port + "/search?api-key=test&q=software")
                    .request()
                    .get();

            if (response.getStatus() != 200) {
                throw new NewsServiceException("Status " + response.getStatus() +
                        " returned when fetching news item");
            }

            String body = response.readEntity(String.class);

            return JsonPath.read(body, "$.response.results[0].webTitle");
        } catch (ProcessingException e) {
            throw new NewsServiceException("Error while retrieving news", e);
        }
    }

    public void postNewArticle(String webTitle) {
        client.target("http://localhost:" + port + "/articles")
                .request()
                .post(entity("{\n" +
                        "    \"type\": \"article\",\n" +
                        "    \"webTitle\": \"" + webTitle + "\",\n" +
                        "    \"webPublicationDate\": \"2015-10-05T06:00:10Z\"\n" +
                        "}", APPLICATION_JSON_TYPE));
    }
}
