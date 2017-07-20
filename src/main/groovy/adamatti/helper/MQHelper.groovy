package adamatti.helper

abstract class MQHelper {
    static String buildQueueEndpoint(String routingKey, String queueName) {
        return "rabbitmq://localhost:5672/amq.topic?" +
            "queue=${queueName}" +
            "&exchangeType=topic" +
            "&durable=true" +
            "&routingKey=${routingKey}" +
            "&autoDelete=false" +
            "&autoAck=false" +
            "&connectionFactory=#customConnectionFactory"
    }
}
