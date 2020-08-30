package prot.lab.reactivex.func;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;

@Configuration
public class FluxRouter {
    // Rounter = annotation of @Controller + @RequestMapping
    // Handler = processing, i.e. method of controller for @RequestMapping
    @Bean
    public RouterFunction<ServerResponse> routerFlux(FluxHandler handler) {
        return RouterFunctions
            .route(
                RequestPredicates.path("/001")
                .and(RequestPredicates.accept(MediaType.TEXT_PLAIN))
                , request -> ServerResponse.ok().body(BodyInserters.fromValue("Hello World"))
            ).andRoute(
                   RequestPredicates.GET("/002"), handler::handleWeatherRequest
            ).andRoute(
                RequestPredicates.GET("/003"), handler::handleDocRequest
            ).andRoute(
                    RequestPredicates.GET("/004"), handler::handleParam
            ).andRoute(
                    RequestPredicates.GET("/005/{id}/{name}"), handler::handlePath
            ).andRoute(
                    RequestPredicates.GET("/006/{id}_{name}"), handler::handlePath
            ).andRoute(
                    RequestPredicates.POST("/docs")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON))
                        , handler::handleDocCreation
            );
    }
}
