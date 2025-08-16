package com.arka.inventory_service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Arka Inventory Service API")
                        .version("0.1.0")
                        .description("API documentation for Inventory management"));
    }

    @Bean
    public GroupedOpenApi brandApi() {
        return GroupedOpenApi.builder()
                .group("brands")
                .pathsToMatch("/api/brands/**")
                .build();
    }

    @Bean
    public GroupedOpenApi categoryApi() {
        return GroupedOpenApi.builder()
                .group("categories")
                .pathsToMatch("/api/categories/**")
                .build();
    }

    @Bean
    public GroupedOpenApi countryApi() {
        return GroupedOpenApi.builder()
                .group("countries")
                .pathsToMatch("/api/countries/**")
                .build();
    }

    @Bean
    public GroupedOpenApi warehouseApi() {
        return GroupedOpenApi.builder()
                .group("warehouses")
                .pathsToMatch("/api/warehouses/**")
                .build();
    }

    @Bean
    public GroupedOpenApi productApi() {
        return GroupedOpenApi.builder()
                .group("products")
                .pathsToMatch("/api/products/**")
                .build();
    }

    @Bean
    public GroupedOpenApi productVariantApi() {
        return GroupedOpenApi.builder()
                .group("product-variants")
                .pathsToMatch("/api/product-variants/**")
                .build();
    }

    @Bean
    public GroupedOpenApi currencyApi() {
        return GroupedOpenApi.builder()
                .group("currencies")
                .pathsToMatch("/api/currencies/**")
                .build();
    }

    @Bean
    public GroupedOpenApi stockApi() {
        return GroupedOpenApi.builder()
                .group("stocks")
                .pathsToMatch("/api/stocks/**")
                .build();
    }

    @Bean
    public GroupedOpenApi stockMovementApi() {
        return GroupedOpenApi.builder()
                .group("stockMovements")
                .pathsToMatch("/api/stock-movements/**")
                .build();
    }

    @Bean
    public GroupedOpenApi stockReservationApi() {
        return GroupedOpenApi.builder()
                .group("stockReservations")
                .pathsToMatch("/api/stock-reservations/**")
                .build();
    }
}
