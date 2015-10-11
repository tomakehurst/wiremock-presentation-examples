import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import stubbing.NewsRestServiceStub;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;
import static stubbing.NewsItemBuilder.newsItem;
import static stubbing.NewsSearchResultBuilder.newsSearchResults;

public class StubServiceWrapper {

    @Rule
    public NewsRestServiceStub newsServiceStub = new NewsRestServiceStub();

    NewsService newsService;

    @Before
    public void init() {
        newsService = new NewsService();
    }

    @Test
    public void stubbing_via_service_wrapper() throws Exception {
        newsServiceStub.createSearchStub("software",
            newsSearchResults()
                .withItem(newsItem()
                    .withWebTitle("VW’s ‘neat hack’ exposes danger of corporate software")));

        String headline = newsService.getFirstSoftwareHeadline();

        assertThat(headline,
                containsString("VW’s ‘neat hack’ exposes danger of corporate software"));
    }

    @Test
    public void body_builders_to_service_wrapper() throws Exception {
        newsService.postNewArticle(
                "PHP programmer releases tool to make tests pass when CI server detected");

        newsServiceStub.verifyArticlePosted(newsItem()
                .withWebTitle("PHP programmer releases tool to make tests pass when CI server detected")
        );
    }
}
