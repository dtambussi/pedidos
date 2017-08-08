package com.pedidos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

// @SpringBootApplication(exclude= HibernateJpaAutoConfiguration.class)
@SpringBootApplication
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
  
  @Bean
  public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
      return new Jackson2ObjectMapperBuilderCustomizer() {
          @Override
          public void customize(final Jackson2ObjectMapperBuilder builder) {
              builder.dateFormat(new ISO8601DateFormat());        
          }           
      };
  }
}
