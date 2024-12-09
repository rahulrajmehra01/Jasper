package com.rclgroup.dolphin;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.HttpEncodingAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@Configuration
@Import({
        DispatcherServletAutoConfiguration.class,
        ErrorMvcAutoConfiguration.class,
        HttpEncodingAutoConfiguration.class,
        HttpMessageConvertersAutoConfiguration.class,
        JacksonAutoConfiguration.class,
        PropertyPlaceholderAutoConfiguration.class,
        MultipartAutoConfiguration.class,
        RestTemplateAutoConfiguration.class,
        ServletWebServerFactoryAutoConfiguration.class,
        SpringDataWebAutoConfiguration.class,
        TransactionAutoConfiguration.class,
        ValidationAutoConfiguration.class,
        WebMvcAutoConfiguration.class,
        MongoAutoConfiguration.class
})
@EnableTransactionManagement
@EnableJpaRepositories("com.rclgroup.dolphin.*")
@EntityScan("com.rclgroup.dolphin.*")
@ComponentScan(basePackages = "com.rclgroup.dolphin.*")
public class RclJasperReportWSWebApplication  extends SpringBootServletInitializer{


	public static void main(String[] args) {
		SpringApplication.run(RclJasperReportWSWebApplication.class, args);
	}
	  @Primary

	    @Override
	    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
	        return builder.sources(RclJasperReportWSWebApplication.class);
	    }

	    @Bean
	    public ResourceBundleMessageSource messageSource() {

	        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
	        source.setBasenames("messages/messageGlobal");
	        source.setUseCodeAsDefaultMessage(true);

	        return source;
	    }

	    @PostConstruct
	    void started() {
	        // Set the default timezone for the entire application
	        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Singapore"));
	    }
}
