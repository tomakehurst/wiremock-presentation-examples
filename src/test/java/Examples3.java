import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.ws.rs.core.Response;

public class Examples3 {

    @Rule
    public WireMockRule wm = new WireMockRule();

    JerseyClient client;

    @Before
    public void init() {
        client = JerseyClientBuilder.createClient();
    }

    @Test
    public void using_recorded_data() {
        Response response = client.target("http://localhost:8080/whatever").request().get();
        String body = response.readEntity(String.class);
    }

    @Test
    public void create_stubs_per_test() {

    }

    @Test
    public void stub_data_builders() {

    }

    @Test
    public void generative_builders() {

    }

    @Test
    public void mock_service_wrapper() {

    }

    @Test
    public void delays() {

    }

    @Test
    public void socket_closed() {

    }


}
