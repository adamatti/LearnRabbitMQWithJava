package adamatti.service

import adamatti.helper.ConfigHelper
import org.apache.camel.Exchange
import org.apache.camel.LoggingLevel
import org.apache.camel.Processor
import org.apache.camel.component.rabbitmq.RabbitMQConstants
import org.apache.camel.spring.SpringRouteBuilder
import org.springframework.stereotype.Component

import static adamatti.helper.MQHelper.buildQueueEndpoint

@Component
class SampleRoute extends SpringRouteBuilder implements Processor {
    private static final ConfigObject cfg = ConfigHelper.cfg

    void configure() throws Exception {
/*
        onException(Exception.class)
            //.maximumRedeliveries(2)
            //.redeliverDelay(1)
            .logContinued(true)
            .logExhausted(true)
            .logHandled(true)
            .logRetryAttempted(true)
            .logRetryStackTrace(false)

        errorHandler(defaultErrorHandler()
            .useOriginalMessage()
            .maximumRedeliveries(200)
            .redeliveryDelay(1000)
            .retryAttemptedLogLevel(LoggingLevel.WARN))
*/
        if (cfg.mq.startConsumer) {
            from(buildQueueEndpoint("*", "sample_queue"))
            //.transacted() //FIXME check it
            //.errorHandler(noErrorHandler())
                .bean("sampleRoute", "process")
        }
    }

    void process(Exchange exchange) throws Exception {
        log.info "Msg received, headers: "

        exchange.in.headers.each { k, v ->
            println "    ${k} = ${v}"
        }

        if (!cfg.toggle.shallWork) {
            throw new Exception("Not work")
        }
    }
}

