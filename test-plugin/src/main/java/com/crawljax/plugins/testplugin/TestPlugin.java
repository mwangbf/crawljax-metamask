package com.crawljax.plugins.testplugin;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.crawljax.browser.EmbeddedBrowser;
import com.crawljax.core.CrawlerContext;
import com.crawljax.core.plugin.*;
import com.crawljax.core.state.Eventable;
import com.crawljax.core.state.StateVertex;
import org.openqa.selenium.WebDriver;

public class TestPlugin implements OnNewStatePlugin, OnFireEventSucceededPlugin {

	private HostInterface hostInterface;

	public TestPlugin(HostInterface hostInterface) {
		this.hostInterface = hostInterface;
	}

	class Refresh extends TimerTask
	{
		private EmbeddedBrowser browser;
		public Refresh(EmbeddedBrowser browser){
			this.browser = browser;
		}
		public void run()
		{
			Metamask mt = new Metamask();
			mt.test(browser);
		}
	}

	@Override
	public void onFireEventSucceeded(CrawlerContext context, Eventable eventable, List<Eventable> pathToFailure) {
		System.out.println("succeed");
		EmbeddedBrowser browser = context.getBrowser();
		Timer timer = new Timer();
		Refresh task = new Refresh(browser);
		timer.schedule(task,0, 5*60*1000);
	}

	@Override
	public void onNewState(CrawlerContext context, StateVertex newState) {
		try {
			EmbeddedBrowser browser = context.getBrowser();
			Metamask mt = new Metamask();
			mt.test(browser);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

/*	public void onNewState(CrawlerContext context, StateVertex newState) {
		try {
			String dom = context.getBrowser().getStrippedDom();
			File file = new File(hostInterface.getOutputDirectory(), context.getCurrentState().getName() + ".html");

			FileWriter fw = new FileWriter(file, false);
			fw.write(dom);
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

}
