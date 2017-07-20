mq = [
    url : System.env.CLOUDAMQP_URL ?: 'amqp://admin:admin@localhost/admin',
    startConsumer: (System.env.MQ_ENABLE_CONSUMER ?: "true").toBoolean()
]

web = [
    port : (System.env.PORT ?: "8080").toInteger()
]

toggle = [
    shallWork : true
]
