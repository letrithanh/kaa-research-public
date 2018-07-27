package me.letrithanh.kaa.datacollectionsimple;

import org.kaaproject.kaa.client.DesktopKaaPlatformContext;
import org.kaaproject.kaa.client.Kaa;
import org.kaaproject.kaa.client.KaaClient;
import org.kaaproject.kaa.client.SimpleKaaClientStateListener;
import org.kaaproject.kaa.client.logging.BucketInfo;
import org.kaaproject.kaa.client.logging.RecordInfo;
import org.kaaproject.kaa.client.logging.future.RecordFuture;
import org.kaaproject.kaa.client.logging.strategies.RecordCountLogUploadStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.letrithanh.research.datacollectonfirstapp.DataCollectionFirstApp;

public class DataCollectionDemo {
	
	private static final Logger LOG = LoggerFactory.getLogger(DataCollectionDemo.class);
	
	private static final int LOGS_DEFAULT_THRESHOLD = 1;
	private static final int MAX_SECONDS_TO_INIT_KAA = 2;

	public static void main(String[] args) {
		LOG.info("============= Data collection demo started =============");
		
		/*
		 * Create KaaClient
		 */
		KaaClient kaaClient = Kaa.newClient(new DesktopKaaPlatformContext(), new SimpleKaaClientStateListener() {

			/* (non-Javadoc)
			 * @see org.kaaproject.kaa.client.SimpleKaaClientStateListener#onStarted()
			 */
			@Override
			public void onStarted() {
				LOG.info("============= Kaa client onStarted =============");
			}

			/* (non-Javadoc)
			 * @see org.kaaproject.kaa.client.SimpleKaaClientStateListener#onStopped()
			 */
			@Override
			public void onStopped() {
				LOG.info("============= Kaa client onStopped =============");
			}
			
		}, true);
		
		/*
         * Set record count strategy for uploading every log record as soon as it is created.
         */
        kaaClient.setLogUploadStrategy(new RecordCountLogUploadStrategy(LOGS_DEFAULT_THRESHOLD));
        
        /*
         * Start Kaa Client
         */
        kaaClient.start();
        
        DataCollectionFirstApp record = new DataCollectionFirstApp(30);
        
        /*
         * Send to server
         */
        RecordFuture future = kaaClient.addLogRecord(record);
        LOG.info("Log record {} submitted for sending", record.toString());
        try {
            RecordInfo recordInfo = future.get(); // wait for log record delivery error
            BucketInfo bucketInfo = recordInfo.getBucketInfo();
            LOG.info("Received log record delivery info. Bucket Id [{}]. Record delivery time [{} ms].",
                    bucketInfo.getBucketId(), recordInfo.getRecordDeliveryTimeMs());
        } catch (Exception e) {
            LOG.error("Exception was caught while waiting for log's delivery report.", e);
        }
        
        /*
         * Stop Kaa Client
         */
        kaaClient.stop();
        
        LOG.info("============= Data collection demo stoped =============");
	}
	
}
