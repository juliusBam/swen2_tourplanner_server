package at.technikum.swen2_tourplanner_server;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySource("classpath:application.properties")
public class AppPropertySource {

    @Value( "${mapQuest.key}" )
    private String mapQuestKey;

    @Value( "${mapQuest.url}" )
    private String mapQuestUrl;

    @Bean
    protected MapQuestSource configSource() {
        MapQuestSource mapQuestSource = new MapQuestSource();
        mapQuestSource.setApiUrl(this.mapQuestUrl);
        mapQuestSource.setApiKey(this.mapQuestKey);
        return mapQuestSource;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer getMapQuestKey() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
