package prot.lab.reactivex.control;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import prot.lab.reactivex.model.Doc;
import prot.lab.reactivex.service.DocService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.stream.IntStream;

@RestController
@RequestMapping("/docs")
public class DocController {
    private static final Logger log = LoggerFactory.getLogger(DocController.class);

    @Autowired
    DocService docService;

    @GetMapping("/{docId}")
    public Mono<Object> getDoc(@PathVariable String docId) {
        log.debug(">>> doc-id =[{}]", docId);
        Mono<Object> ret = Mono.create(sink -> sink.success(docService.getDoc(docId)));
        ret = ret.doOnSubscribe(s -> log.debug("[{}] starts to subscribe", s))
                .doOnNext(d -> log.debug("processing data: [{}]", d))
                .doOnSuccess(d -> log.debug("success on data: [{}]", d));
        log.debug("<<<");
        return ret;
    }

    @GetMapping(value = "/flux-sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> sseEventStream() {
        Flux<String> ret = Flux.fromStream(
                IntStream.range(1, 20)
                        .mapToObj(i -> {
                            try {
                                Thread.sleep(2_000);
                            } catch (Exception ignore) {

                            }
                            return String.format("result-%03d", i);
                        }))
                .doOnSubscribe(sub -> log.debug("[{}] starts to subscribe", sub))
                .doOnComplete(() -> log.debug("done"))
                .doOnNext(data -> log.debug("processing [{}]", data));
        return ret;
    }
}
