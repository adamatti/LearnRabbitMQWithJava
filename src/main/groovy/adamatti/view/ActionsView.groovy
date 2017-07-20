package adamatti.view

import adamatti.helper.ConfigHelper
import groovy.util.logging.Slf4j
import org.apache.camel.component.rabbitmq.RabbitMQConstants

import static adamatti.helper.JsonHelper.toJsonString
import org.apache.camel.ProducerTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import spark.Request
import spark.Response
import spark.Spark

import javax.annotation.PostConstruct

import static adamatti.helper.MQHelper.buildQueueEndpoint

@Slf4j
@Component
class ActionsView {
    private static final ConfigObject cfg = ConfigHelper.cfg

    @Autowired
    private ProducerTemplate producerTemplate

    @PostConstruct
    void init(){
        Spark.get("/enqueue") { Request req, Response res ->
            int count = (req.queryParams("count") ?: "1").toInteger()

            count.times {
                String newId = UUID.randomUUID().toString()
                sentToMq(newId)
                log.info "Enqueued [id: ${newId}, count: ${it}]"
            }

            res.header("Content-Type","application/json")
            toJsonString([
                status: "ok",
                date: new Date().toString()
            ])
        }

        Spark.get("/toggle"){Request req, Response res ->
            cfg.toggle.shallWork = !cfg.toggle.shallWork

            res.header("Content-Type","application/json")
            toJsonString([current: cfg.toggle.shallWork])
        }
    }

    private void sentToMq(String msg){
        String endpoint = buildQueueEndpoint(msg, "sample_queue")
        producerTemplate.sendBodyAndHeaders(endpoint,msg,[
            (RabbitMQConstants.REQUEUE): true
            //(RabbitMQConstants.DELIVERY_MODE) : MessageDeliveryMode.PERSISTENT
        ])
    }


}
