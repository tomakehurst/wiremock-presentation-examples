import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.ws.rs.ProcessingException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.http.Fault.EMPTY_RESPONSE;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class FaultInjection {

    @Rule
    public WireMockRule wm = new WireMockRule();

    NewsService newsService;

    @Before
    public void init() {
        newsService = new NewsService();
    }

    @Test
    public void premature_socket_closure_fault() throws Exception {
        wm.stubFor(get(urlPathEqualTo("/search"))
                .willReturn(aResponse().withFault(EMPTY_RESPONSE)));

        try {
            newsService.getFirstSoftwareHeadline();
            fail();
        } catch (Exception e) {
            assertThat(e, instanceOf(NewsServiceException.class));
            assertThat(e.getCause().getMessage(),
                    containsString("Unexpected end of file"));
        }
    }
}
