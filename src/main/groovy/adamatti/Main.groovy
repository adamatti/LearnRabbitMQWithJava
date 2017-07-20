package adamatti

import adamatti.helper.ConfigHelper
import adamatti.helper.SparkHelper
import groovy.util.logging.Slf4j
import org.springframework.context.ApplicationContext
import org.springframework.context.support.GenericGroovyApplicationContext

@Slf4j
class Main {
    private static final ConfigObject cfg = ConfigHelper.cfg

    static void main(String [] args){
        log.info "Starting"

        SparkHelper.start(cfg.web.port as int)
        startSpring()

        log.info "Started [port: ${cfg.web.port}]"
    }

    private static ApplicationContext startSpring(){
        def context = new GenericGroovyApplicationContext("classpath:spring/root.groovy")
        context.registerShutdownHook()
        context
    }
}
