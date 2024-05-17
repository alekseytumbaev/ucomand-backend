package com.example.ucomandbackend.config;

import com.example.ucomandbackend.error_handling.ErrorResponseDto;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Configuration
@OpenAPIDefinition(
        info = @Info(title = "Ucomand API", version = "0"),
        security = @SecurityRequirement(name = "BearerAuth"))
@SecurityScheme(
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        name = "BearerAuth")
public class OpenApiConfig {

    @Bean
    public OpenApiCustomizer openApiCustomizer() {
        return openApi -> {
            //Добавление дефолтной схемы для ошибок 4xx и 5xx
            var sharedErrorSchema = ModelConverters.getInstance()
                    .read(ErrorResponseDto.class).get(ErrorResponseDto.class.getSimpleName());
            if (sharedErrorSchema == null) {
                throw new IllegalStateException(
                        "Не удалось сгенерировать ответ для ошибок 4xx и 5xx,поскольку отсутствует схема ошибки");
            }

            openApi.getPaths().values().forEach(pathItem -> pathItem.readOperations().forEach(operation ->
                    operation.getResponses().forEach((status, response) -> {
                        if (status.startsWith("4") || status.startsWith("5")) {
                            response.getContent().forEach((code, mediaType) -> mediaType.setSchema(sharedErrorSchema));
                        }
                    })));
        };
    }

    /**
     * Костыль, чтобы добавить компонент ошибки в open api.
     */
    @RestController
    @RequestMapping("/donotuse")
    static public class DoNotUse {

        @Operation(description = "Не использовать. Костыль, необходимый для конфигурации open api",
                deprecated = true,
                responses = {
                        @ApiResponse(content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
                })
        @DeleteMapping
        public void registerOpenApiComponent() {
        }
    }
}
