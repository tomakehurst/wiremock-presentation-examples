package stubbing;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.rules.ExternalResource;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class NewsRestServiceStub extends ExternalResource {

    private final WireMockServer wm;

    public NewsRestServiceStub() {
        wm = new WireMockServer();
    }

    @Override
    protected void before() throws Throwable {
        wm.start();
    }

    @Override
    protected void after() {
        wm.stop();
    }

    public void createSearchStub(String query, NewsSearchResultBuilder searchResultBuilder) throws Exception {
        wm.stubFor(get(urlPathEqualTo("/search"))
                .withQueryParam("api-key", equalTo("test"))
                .withQueryParam("q",       equalTo(query))
                .willReturn(aResponse()
                        .withBody(searchResultBuilder.buildJson())));
    }

    public void verifyArticlePosted(NewsItemBuilder newsItemBuilder) throws Exception {
        wm.verify(postRequestedFor(urlEqualTo("/articles"))
                .withRequestBody(equalToJson(
                        newsItemBuilder.buildJson())));
    }
}
