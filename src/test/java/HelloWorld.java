import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static javax.ws.rs.client.Entity.entity;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN_TYPE;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class HelloWorld {

    @Rule
    public WireMockRule wm = new WireMockRule();

    JerseyClient client;

    @Before
    public void init() {
        client = JerseyClientBuilder.createClient();
    }

    @Test
    public void stubbing() {
        wm.stubFor(get(urlEqualTo("/quotes/1"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "text/plain")
                .withBody("I've suffered for my art...now it's your turn!")));

        Response response = client.target("http://localhost:8080/quotes/1").request().get();

        assertThat(response.readEntity(String.class),
                is("I've suffered for my art...now it's your turn!"));
    }

    @Test
    public void verification() {
        wm.stubFor(post(urlEqualTo("/quotes")).willReturn(aResponse().withStatus(200)));

        client.target("http://localhost:8080/quotes").request().post(
                entity("Some cause happiness wherever they go; others, whenever they go.",
                        TEXT_PLAIN_TYPE));

        wm.verify(postRequestedFor(urlEqualTo("/quotes"))
                .withRequestBody(containing("Some cause happiness wherever they go")));
    }
}
