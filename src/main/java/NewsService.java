import com.jayway.jsonpath.JsonPath;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;

import javax.ws.rs.core.Response;


public class NewsService {

    private final JerseyClient client;

    public NewsService() {
        client = JerseyClientBuilder.createClient();
    }

    public String getFirstSoftwareHeadline() {
        Response response = client.target("http://localhost:8080/search?api-key=test&q=software")
                .request()
                .get();
        String body = response.readEntity(String.class);

        return JsonPath.read(body, "$.response.results[0].webTitle");
    }
}
