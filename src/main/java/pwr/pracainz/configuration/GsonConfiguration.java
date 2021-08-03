package pwr.pracainz.configuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pwr.pracainz.entities.databaseerntities.animeInfo.AnimeUserStatus;
import pwr.pracainz.entities.databaseerntities.forum.Enums.TagImportance;
import pwr.pracainz.entities.databaseerntities.forum.Enums.ThreadStatus;
import pwr.pracainz.entities.mappers.AnimeUserStatusMapper;
import pwr.pracainz.entities.mappers.LocalDateMapper;
import pwr.pracainz.entities.mappers.TagImportanceMapper;
import pwr.pracainz.entities.mappers.ThreadStatusMapper;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class GsonConfiguration implements WebMvcConfigurer {

    @Bean
    Gson getGsonBean() {
        return new GsonBuilder()
                .registerTypeAdapter(AnimeUserStatus.class, new AnimeUserStatusMapper())
                .registerTypeAdapter(LocalDate.class, new LocalDateMapper())
                .registerTypeAdapter(TagImportance.class, new TagImportanceMapper())
                .registerTypeAdapter(ThreadStatus.class, new ThreadStatusMapper())
                .create();
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        GsonHttpMessageConverter gsonHttpMessageConverter = new GsonHttpMessageConverter();
        gsonHttpMessageConverter.setGson(getGsonBean());
        gsonHttpMessageConverter.setSupportedMediaTypes(List.of(MediaType.APPLICATION_JSON));

        converters.add(gsonHttpMessageConverter);
        WebMvcConfigurer.super.configureMessageConverters(converters);
    }
}
