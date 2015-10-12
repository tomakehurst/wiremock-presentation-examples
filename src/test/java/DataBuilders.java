import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static stubbing.NewsItemBuilder.newsItem;
import static stubbing.NewsSearchResultBuilder.newsSearchResults;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

public class DataBuilders {

    @Rule
    public WireMockRule wm = new WireMockRule();

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
                            .withWebTitle(
                                "VW’s ‘neat hack’ exposes danger of corporate software"))
                            .buildJson()
                    )));

        String headline = newsService.getFirstSoftwareHeadline();

        assertThat(headline,
                containsString("VW’s ‘neat hack’ exposes danger of corporate software"));
    }

    @Test
    public void body_builders() throws Exception {
        newsService.postNewArticle("PHP programmer releases tool to make tests pass when CI server detected");

        wm.verify(postRequestedFor(urlEqualTo("/articles"))
            .withRequestBody(equalToJson(
                newsItem()
                    .withWebTitle("PHP programmer releases tool to make tests pass when CI server detected")
                    .buildJson())));
    }
}
