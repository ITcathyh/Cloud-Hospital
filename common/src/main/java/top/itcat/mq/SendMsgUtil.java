package top.itcat.mq;

public class SendMsgUtil {
//    public static boolean sendMessage(JmsMessagingTemplate jmsMessagingTemplate,
//                                      Destination destination, Serializable msg) {
//        if (msg instanceof String) {
//            throw new IllegalArgumentException();
//        }
//
//        try {
//            jmsMessagingTemplate.convertAndSend(destination, msg);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//
//        return true;
//    }
//
//    public static boolean sendMessageDelay(JmsMessagingTemplate jmsMessagingTemplate,
//                                           Destination destination, Serializable data, long time) {
//        Connection connection = null;
//        Session session = null;
//        MessageProducer producer = null;
//        ConnectionFactory connectionFactory = jmsMessagingTemplate.getConnectionFactory();
//
//        try {
//            connection = connectionFactory.createConnection();
//            connection.start();
//            session = connection.createSession(Boolean.TRUE, Session.CLIENT_ACKNOWLEDGE);
//            producer = session.createProducer(destination);
//            producer.setDeliveryMode(JmsProperties.DeliveryMode.PERSISTENT.getValue());
//            ObjectMessage message = session.createObjectMessage(data);
//
//            message.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, time);
//            producer.send(message);
//            session.commit();
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (producer != null) {
//                    producer.close();
//                }
//                if (session != null) {
//                    session.close();
//                }
//                if (connection != null) {
//                    connection.close();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        return false;
//    }
}
