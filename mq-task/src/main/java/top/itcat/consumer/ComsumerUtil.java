package top.itcat.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import javax.jms.ObjectMessage;

public class ComsumerUtil {
    private static Logger log = LoggerFactory.getLogger(ComsumerUtil.class);

    public static void saveES(ElasticsearchRepository repository, ObjectMessage msg, Object entity) throws Exception {
        log.debug(Thread.currentThread().getName() + ":Consumer收到的报文为:" + msg.getObject());

        if (repository.save(entity) != null) {
            log.error("sava err,msg:{}", msg.toString());
            throw new Exception();
        }

        msg.acknowledge();
    }
}
