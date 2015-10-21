import io.dropwizard.Configuration;

public class NewsAppConfig extends Configuration {

    private String appName = "News App";
    private int newsServicePort = 8080;

    public NewsAppConfig() {
    }

    public NewsAppConfig(int newsServicePort) {
        this.newsServicePort = newsServicePort;
    }

    public String getAppName() {
        return appName;
    }

    public int getNewsServicePort() {
        return newsServicePort;
    }
}
