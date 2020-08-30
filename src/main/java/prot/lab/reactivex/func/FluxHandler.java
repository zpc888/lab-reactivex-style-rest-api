package prot.lab.reactivex.func;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import prot.lab.reactivex.model.Doc;
import prot.lab.reactivex.service.DocService;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FluxHandler {
    @Autowired
    private DocService docService;

    public Mono<ServerResponse> handleWeatherRequest(ServerRequest request) {
        return ServerResponse.ok().body(BodyInserters.fromValue("How is the weather?"));
    }

    public Mono<ServerResponse> handleDocRequest(ServerRequest request) {
        Doc doc = docService.getDoc("999");
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(doc));
    }

    public Mono<ServerResponse> handleDocCreation(ServerRequest request) {
        Mono<Doc> doc = request.bodyToMono(Doc.class).doOnNext(docService::saveDoc);
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body( BodyInserters.fromPublisher(doc, Doc.class) );
    }

    public Mono<ServerResponse> handleParam(ServerRequest request) {
        List<String> ids = request.queryParams().get("id");
        List<String> names = request.queryParams().get("name");
        Map<String, List<String>> idNameMap = new HashMap<>();
        idNameMap.put("requestIds", ids);
        idNameMap.put("requestNames", names);
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(idNameMap));
    }

    public Mono<ServerResponse> handlePath(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(request.pathVariables()));
    }


}
