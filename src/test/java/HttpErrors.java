import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class HttpErrors {

    @Rule
    public WireMockRule wm = new WireMockRule();

    NewsService newsService;

    @Before
    public void init() {
        newsService = new NewsService();
    }

    @Test(expected = NewsServiceException.class)
    public void service_unavailable_error() {
        wm.stubFor(get(urlPathEqualTo("/search"))
                .willReturn(aResponse().withStatus(503)));

        newsService.getFirstSoftwareHeadline();
    }
}
