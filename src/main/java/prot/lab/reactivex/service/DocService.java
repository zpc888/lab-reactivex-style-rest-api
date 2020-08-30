package prot.lab.reactivex.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import prot.lab.reactivex.model.Doc;

import java.util.Random;

@Service
@Slf4j
public class DocService {
    public Doc getDoc(String docId) {
        try {
            Thread.sleep(2_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Doc ret = new Doc();
        ret.setId(docId);
        ret.setName("Flutter In Action");
        ret.setSize(6_484_499L);
        ret.setFormat("application/pdf");
        return ret;
    }

    public Doc saveDoc(Doc doc) {
        doc.setId(new Random(System.currentTimeMillis()).nextInt() + "");
        log.debug("doc: {} saved", doc);
        return doc;
    }
}
