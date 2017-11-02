package com.pedidos;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.pedidos.utils.CustomISO8601DateFormat;

@SpringBootApplication
public class Application {
	
  @PostConstruct
  public void init() {
    TimeZone.setDefault(TimeZone.getTimeZone("GMT-3:00"));
  }	
	
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
  
  @Bean
  public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
      return new Jackson2ObjectMapperBuilderCustomizer() {
          @Override
          public void customize(final Jackson2ObjectMapperBuilder builder) {
              builder.dateFormat(new CustomISO8601DateFormat());
          }           
      };
  }
}
