package com.admin.enterprise.config;

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

  @Bean
  public Docket createRestApi() {
    System.out.println("======================Swagger UI====================");
    return new Docket(DocumentationType.OAS_30)
        .apiInfo(apiInfo())
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.admin.enterprise.controller"))
//        .apis(RequestHandlerSelectors.withMethodAnnotation(Api.class))
        .paths(PathSelectors.any())
        .build();
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title("人事管理系统")
        .description("人事管理系统")
        .contact(new Contact("Sophie","","Sophie@163.com"))
        .version("1.0.0")
        .build();
  }

}