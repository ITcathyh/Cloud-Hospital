package top.itcat.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import top.itcat.aop.limiter.LimitService;
import top.itcat.cache.CacheService;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class CommonConfig {
    @Bean
    public LimitService limitService() {
        return new LimitService();
    }

    @Bean
    public CacheService cacheService() {
        return new CacheService();
    }

    @Bean
    public HttpMessageConverters fastJsonConfigure() {
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat, SerializerFeature.WriteNonStringKeyAsString);

        List<MediaType> fastMedisTypes = new ArrayList<MediaType>();
        fastMedisTypes.add(MediaType.APPLICATION_JSON_UTF8);
        fastConverter.setSupportedMediaTypes(fastMedisTypes);
        fastConverter.setFastJsonConfig(fastJsonConfig);
//        fastConverter.setFeatures(SerializerFeature.DisableCircularReferenceDetect);
//        JSON.DEFAULT_GENERATE_FEATURE |= SerializerFeature.DisableCircularReferenceDetect.getMask();
        return new HttpMessageConverters(fastConverter);
    }

    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
        return configurableEmbeddedServletContainer -> configurableEmbeddedServletContainer.setSessionTimeout(15 * 60);
    }
}
