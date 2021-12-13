package com.hey.request.system.config;

import com.hey.request.system.aspects.SystemLogAspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@EnableOpenApi
/*@ConditionalOnProperty(prefix = "swagger", value = {"enable"} ,havingValue = "true")*/
public class SwaggerConfig {

  private final Logger swaggerLogger = LoggerFactory.getLogger(SwaggerConfig.class);

  @Bean
  public Docket createRestApi() {

    swaggerLogger.info("======================Swagger UI====================");

    return new Docket(DocumentationType.OAS_30)
        .apiInfo(apiInfo())
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.hey.request.system.controller"))
//        .apis(RequestHandlerSelectors.withMethodAnnotation(Api.class))
        .paths(PathSelectors.any())
        .build();
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title("Request404")
        .description("Request404")
        .contact(new Contact("Request404","","request404@qq.com"))
        .version("1.0.0")
        .build();
  }

}