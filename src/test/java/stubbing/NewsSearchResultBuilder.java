package stubbing;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class NewsSearchResultBuilder {

    public static NewsSearchResultBuilder newsSearchResults() {
        return new NewsSearchResultBuilder();
    }

    private Response response = new Response();

    public Response getResponse() {
        return response;
    }

    public NewsSearchResultBuilder withItem(NewsItemBuilder item) {
        response.results.add(item);
        return this;
    }

    public static class Response {

        private List<NewsItemBuilder> results = new ArrayList<>();

        public List<NewsItemBuilder> getResults() {
            return results;
        }
    }

    public String buildJson() throws Exception {
        return new ObjectMapper().writeValueAsString(this);
    }
}
