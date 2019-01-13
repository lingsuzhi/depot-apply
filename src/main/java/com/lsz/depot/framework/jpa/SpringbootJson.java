package com.lsz.depot.framework.jpa;


import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SpringbootJson {
    /**
     * 使用bean方式注入fastjson解析器
     *
     * @return
     */
    @Bean
    public HttpMessageConverters fastJsonHttpMessageConverters() {
        // 创建fastjson对象
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();

        FastJsonConfig confg = new FastJsonConfig();
        // 设置是否需要格式化
        confg.setSerializerFeatures(SerializerFeature.PrettyFormat);
        converter.setFastJsonConfig(confg);

        List<MediaType> fastMediaTypes = new ArrayList<>();
         fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        converter.setSupportedMediaTypes(fastMediaTypes);

        return new HttpMessageConverters(converter);
    }

}
