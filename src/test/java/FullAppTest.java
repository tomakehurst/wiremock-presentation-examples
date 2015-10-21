import com.github.tomakehurst.wiremock.junit.WireMockRule;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.junit.Rule;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static stubbing.NewsItemBuilder.newsItem;
import static stubbing.NewsSearchResultBuilder.newsSearchResults;

public class FullAppTest {

    @Rule
    public WireMockRule wm = new WireMockRule(wireMockConfig().dynamicPort());

    @Rule
    public DropwizardAppRule<NewsAppConfig> app = new DropwizardAppRule<>(NewsApp.class, new NewsAppConfig() {
        @Override
        public int getNewsServicePort() {
            return wm.port();
        }
    });

    JerseyClient client = JerseyClientBuilder.createClient();

    @Test
    public void news_headline() throws Exception {
        wm.stubFor(get(urlEqualTo("/search?api-key=test&q=software"))
                .willReturn(aResponse()
                        .withBody(newsSearchResults()
                                .withItem(newsItem()
                                        .withWebTitle(
                                                "VW’s ‘neat hack’ exposes danger of corporate software"))
                                .buildJson())));

        String headline = client.target("http://localhost:" + app.getLocalPort() + "/headlines/software")
                .request()
                .get(String.class);

        assertThat(headline, is("VW’s ‘neat hack’ exposes danger of corporate software"));
    }
}
