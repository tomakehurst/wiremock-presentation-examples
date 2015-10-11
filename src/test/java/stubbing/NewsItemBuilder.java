package stubbing;

import com.fasterxml.jackson.databind.ObjectMapper;

public class NewsItemBuilder {
    
    private String type = "article";
    private String webTitle = "";
    private String webPublicationDate = "2015-10-05T06:00:10Z";

    public static NewsItemBuilder newsItem() {
        return new NewsItemBuilder();
    }

    public String getType() {
        return type;
    }

    public String getWebTitle() {
        return webTitle;
    }

    public String getWebPublicationDate() {
        return webPublicationDate;
    }

    public NewsItemBuilder withType(String type) {
        this.type = type;
        return this;
    }

    public NewsItemBuilder withWebTitle(String webTitle) {
        this.webTitle = webTitle;
        return this;
    }

    public NewsItemBuilder withWebPublicationDate(String webPublicationDate) {
        this.webPublicationDate = webPublicationDate;
        return this;
    }

    public String buildJson() throws Exception {
        return new ObjectMapper().writeValueAsString(this);
    }
}
