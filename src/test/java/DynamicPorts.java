import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static javax.ws.rs.client.Entity.entity;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN_TYPE;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class DynamicPorts {

    @Rule
    public WireMockRule wm = new WireMockRule(wireMockConfig()
                                                .dynamicPort()
                                                .dynamicHttpsPort());

    @Before
    public void init() {
        // Override configuration in your app under test
        System.setProperty("newsstub.http.port",  String.valueOf(wm.port()));
        System.setProperty("newsstub.https.port", String.valueOf(wm.httpsPort()));

        // Then start your app under test...
    }
}
