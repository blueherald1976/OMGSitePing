package hello;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.slrclub.biz.net.OMGSlrSiteManager;
import com.slrclub.ext.HelloMusic;
import com.slrclub.free.ComponentTest;
import com.slrclub.free.domain.SlrContentsItem;
import com.slrclub.free.service.SlrContentsItemService;

@EnableScheduling
public class ScheduledTasks {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	private long count = 0x00;

	// OMGSlrSiteManager     ssm = new OMGSlrSiteManager();
	
	ApplicationContext     context = new ClassPathXmlApplicationContext(new String[]{"applicationContext.xml"});
	SlrContentsItemService scis    = (SlrContentsItemService) context.getBean("slrContentsItemService");
	OMGSlrSiteManager      ossm    = context.getBean("sitemanager", OMGSlrSiteManager.class);
	
	
	
	@Scheduled(fixedRate = 15000)
    public void reportCurrentTime() {
//		String text = ossm.doShowContents("32689042");
//		if(text != null) {
//			System.out.println(text);
//		}
		
		HelloMusic hm = context.getBean("hellomusic", HelloMusic.class);
		String test = ossm.helloWorld();
		System.out.println(test);
		
		test = hm.sayHello("jinju ~~");
		System.out.println(test);
		
//		
//		ComponentTest ct = (ComponentTest) context.getBean("ComponentTest");
//		ct.hello();
		
//		ComponentTest ct = (ComponentTest) context.getBean("componentTest");
//		ct.hello();
		
    	count++;
    	ArrayList<SlrContentsItem> slrContentsItemList = ossm.getSlrContentsItemList();
    	scis.insertItem(slrContentsItemList);
    	
    	System.out.println("tick count :" + count);
        System.out.println("The time is now " + dateFormat.format(new Date()));
    }
}
