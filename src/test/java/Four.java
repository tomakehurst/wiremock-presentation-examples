import builders.NewsSearchResultBuilder;
import com.github.tomakehurst.wiremock.common.ConsoleNotifier;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static builders.NewsItemBuilder.newsItem;
import static builders.NewsSearchResultBuilder.newsSearchResults;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

public class Four {

    @Rule
    public WireMockRule wm = new WireMockRule();
//    public WireMockRule wm = new WireMockRule(wireMockConfig().notifier(new ConsoleNotifier(true)));

    NewsService newsService;

    @Before
    public void init() {
        newsService = new NewsService();
    }

    @Test
    public void using_stub_data_builders() throws Exception {
        wm.stubFor(get(urlPathEqualTo("/search"))
                .withQueryParam("api-key", equalTo("test"))
                .withQueryParam("q",       equalTo("software"))
                .willReturn(aResponse()
                        .withBody(
                                newsSearchResults()
                                        .withItem(newsItem()
                                                .withWebTitle("VW’s ‘neat hack’ exposes danger of corporate software"))
                                        .buildJson()
                        )));

        String headline = newsService.getFirstSoftwareHeadline();

        assertThat(headline, containsString("VW’s ‘neat hack’ exposes danger of corporate software"));
    }
}
