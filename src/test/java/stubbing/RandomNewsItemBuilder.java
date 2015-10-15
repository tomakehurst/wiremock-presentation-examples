package stubbing;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.shamdata.Sham;

import java.time.LocalDateTime;

public class RandomNewsItemBuilder {

    private String type = "article";
    private String webTitle = Sham.getInstance().nextHeadline();
    private String body = Sham.getInstance().nextParagraph(10);
    private String author = Sham.getInstance().nextPerson().getName();
    private String webPublicationDate = randomDateInLast10Years();

    private static String randomDateInLast10Years() {
        int hoursDelta = Sham.getInstance().getRandom().nextInt(10 * 365 * 24);
        return LocalDateTime.now().minusHours(hoursDelta).toString();
    }

    public static RandomNewsItemBuilder randomNewsItem() {
        return new RandomNewsItemBuilder();
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

    public String getBody() {
        return body;
    }

    public String getAuthor() {
        return author;
    }

    public RandomNewsItemBuilder withType(String type) {
        this.type = type;
        return this;
    }

    public RandomNewsItemBuilder withWebTitle(String webTitle) {
        this.webTitle = webTitle;
        return this;
    }

    public RandomNewsItemBuilder withWebPublicationDate(String webPublicationDate) {
        this.webPublicationDate = webPublicationDate;
        return this;
    }

    public String buildJson() throws Exception {
        return new ObjectMapper().writeValueAsString(this);
    }
}
