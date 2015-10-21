import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class NewsApp extends Application<NewsAppConfig> {

    @Override
    public void run(NewsAppConfig configuration, Environment environment) throws Exception {
        environment.jersey().register(new NewsResource(new NewsService(configuration.getNewsServicePort())));
    }

    public static void main(String[] args) throws Exception {
        new NewsApp().run("server");
    }
}
