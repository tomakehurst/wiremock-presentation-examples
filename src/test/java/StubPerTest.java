import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

public class StubPerTest {

    @Rule
    public WireMockRule wm = new WireMockRule();

    NewsService newsService;

    @Before
    public void init() {
        newsService = new NewsService();
    }

    @Test
    public void using_explicit_stubs() {
        wm.stubFor(get(urlPathEqualTo("/search"))
                .withQueryParam("api-key", equalTo("test"))
                .withQueryParam("q",       equalTo("software"))
                .willReturn(aResponse()
                        .withBodyFile("news-search-response.json")));

        String headline = newsService.getFirstSoftwareHeadline();

        assertThat(headline, containsString("VW’s ‘neat hack’"));
    }
}
