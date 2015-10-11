import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class RecordPlayback {

    @Rule
    public WireMockRule wm = new WireMockRule();

    NewsService newsService;

    @Before
    public void init() {
        newsService = new NewsService();
    }

    @Test
    public void using_recorded_data() {
        String headline = newsService.getFirstSoftwareHeadline();

        assertThat(headline,
                containsString("VW’s ‘neat hack’ exposes danger of corporate software"));
    }

}
