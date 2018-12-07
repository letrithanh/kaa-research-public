package me.letrithanh.kaa.devices;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.kaaproject.kaa.client.DesktopKaaPlatformContext;
import org.kaaproject.kaa.client.Kaa;
import org.kaaproject.kaa.client.KaaClient;
import org.kaaproject.kaa.client.SimpleKaaClientStateListener;
import org.kaaproject.kaa.client.notification.NotificationListener;
import org.kaaproject.kaa.client.notification.NotificationTopicListListener;
import org.kaaproject.kaa.client.notification.UnavailableTopicException;
import org.kaaproject.kaa.common.endpoint.gen.SubscriptionType;
import org.kaaproject.kaa.common.endpoint.gen.Topic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.letrithanh.research.simpleforecast.message.SimpleMessageForForecast;

public class FanApplication {

//	private static final Logger LOG = LoggerFactory.getLogger(FanApplication.class);
    private static KaaClient kaaClient;
    
    private static Fan fan;
    
    /**
     * The list of all available notification topics.
     */
    private static List<Topic> topics;
    
    /**
     * Topics client subscribed
     */
    private static List<Topic> subscribedTopics = new ArrayList<Topic>();
    
    
	public static void main(String[] args) {
		fan = new Fan();
		System.out.println("=============================================================");
		System.out.println("Status of Fan:");
		System.out.println(fan.toString());
		System.out.println("=============================================================");
		
	
		kaaClient = Kaa.newClient(new DesktopKaaPlatformContext(), new SimpleKaaClientStateListener(), true);
		
		/*
         * A listener that listens to the notification topic list updates.
         */
        NotificationTopicListListener topicListListener = new BasicNotificationTopicListListener();
        kaaClient.addTopicListListener(topicListListener);
        
        /*
         * Add a notification listener that listens to all notifications.
         */
        kaaClient.addNotificationListener(new NotificationListener() {

			public void onNotification(long topicId, SimpleMessageForForecast notification) {
//				LOG.info("Notification from the topic with id [{}] received.", topicId);
//				LOG.info("Message: " + notification.getMessage());
				
				controlFan(notification.getMessage());
                inputTopicIdMessage();
			}
        });
		
        /*
         * Start the Kaa client and connect it to the Kaa server.
         */
        kaaClient.start();

        topics = kaaClient.getTopics();

        /*
         * List the obtained notification topics.
         */
//        showTopics();

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLong()) {
            long topicId = scanner.nextLong();
            if (getTopic(topicId) != null) {
//                LOG.info("Subscribing to optional topic {}", topicId);
                subscribeTopic(topicId);
            } else {
//                LOG.info("There is no input topic id. Please, input existing topic id.");
            }
        }

        /*
         * Stop listening to the notification topic list updates.
         */
        kaaClient.removeTopicListListener(topicListListener);
        unsubscribeOptionalTopics();

        /*
         * Stop the Kaa client and release all the resources which were in use.
         */
        kaaClient.stop();
	}
	
	private static void subscribeTopic(long topicId) {
        try {
            subscribedTopics.add(getTopic(topicId));
            kaaClient.subscribeToTopic(topicId, true);
        } catch (UnavailableTopicException e) {
            e.printStackTrace();
        }
        inputTopicIdMessage();
    }

    private static Topic getTopic(long id) {
        for (Topic t : topics)
            if (t.getId() == id)
                return t;
        return null;
    }

    private static void unsubscribeOptionalTopics() {
        List<Topic> topics = getOneTypeTopics(SubscriptionType.OPTIONAL_SUBSCRIPTION);

        for (Topic t : subscribedTopics) {
            try {
                kaaClient.unsubscribeFromTopic(t.getId());
            } catch (UnavailableTopicException e) {
                // if not subscribe
            }
        }
    }

	 /**
     * A listener that tracks the notification topic list updates
     * and subscribes the Kaa client to every new topic available.
     */
    private static class BasicNotificationTopicListListener implements NotificationTopicListListener {
        public void onListUpdated(List<Topic> list) {
//            LOG.info("Topic list was updated:");
            topics.clear();
            topics.addAll(list);

//            showTopics();
        }
    }
    
    private static void showTopics() {
        if (topics == null || topics.isEmpty()) {
//            LOG.info("Topic list is empty");
            return;
        }

//        LOG.info("Available topics:");
        for (Topic topic : topics) {
//            LOG.info("Topic id: {}, name: {}, type: {}", topic.getId(), topic.getName(), topic.getSubscriptionType());
        }

//        LOG.info("Subscribed on topics:");
        for (Topic t : getOneTypeTopics(SubscriptionType.MANDATORY_SUBSCRIPTION)) {
//            LOG.info("Topic id: {}, name: {}, type: {}", t.getId(), t.getName(), t.getSubscriptionType().name());
        }
        /*
         * Optional topics
         */
        if (!subscribedTopics.isEmpty()) {
            for (Topic t : subscribedTopics) {
//                LOG.info("Topic id: {}, name: {}, type: {}", t.getId(), t.getName(), t.getSubscriptionType().name());
            }
        }
        inputTopicIdMessage();
    }
    
    private static void inputTopicIdMessage() {
//        LOG.info("\nPlease, type topic ID in order to subscribe to ones or type any text to exit: \n");
    }
    
    private static List<Topic> getOneTypeTopics(SubscriptionType type) {
        List<Topic> res = new ArrayList<Topic>();
        for (Topic t : FanApplication.topics) {
            if (t.getSubscriptionType() == type) {
                res.add(t);
            }
        }
        return res;
    }
    
    private static void controlFan(String message) {
    	if(message.equalsIgnoreCase("nhiet do hon 28")) {
    		fan.setOn(true);
    	}
    	System.out.println("=============================================================");
		System.out.println("Status of Fan:");
		System.out.println(fan.toString());
		System.out.println("=============================================================");
    }
}
