package com.crawljax.plugins.testplugin;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.crawljax.browser.EmbeddedBrowser;
import com.crawljax.core.CrawljaxRunner;
import com.crawljax.core.configuration.BrowserConfiguration;
import com.crawljax.core.configuration.BrowserOptions;
import com.crawljax.core.configuration.CrawlRules.FormFillMode;
import com.crawljax.core.configuration.CrawljaxConfiguration;
import com.crawljax.core.configuration.InputSpecification;
import com.crawljax.core.plugin.HostInterfaceImpl;
import com.crawljax.core.plugin.Plugin;
import com.crawljax.core.plugin.descriptor.Parameter;
import com.crawljax.core.plugin.descriptor.PluginDescriptor;
import com.crawljax.core.state.Identification;
import com.crawljax.core.state.Identification.How;
import com.crawljax.forms.FormInput.InputType;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import javax.inject.Provider;

/**
 * Use the sample plugin in combination with Crawljax.
 * 
 */
public class Runner {

	private static final String URL = "https://oasis.app/trade/market/WETH/DAI";
	private static final int MAX_DEPTH = 1;
	private static final int MAX_NUMBER_STATES = 3;

	/**
	 * Entry point
	 */
	public static void main(String[] args) {
		CrawljaxConfiguration.CrawljaxConfigurationBuilder builder =
		        CrawljaxConfiguration.builderFor(URL);
		builder.crawlRules().setFormFillMode(FormFillMode.NORMAL);

		builder.crawlRules().click("a");
		builder.crawlRules().click("button");

		// except these
		builder.crawlRules().dontClick("a").underXPath("//DIV[@id='guser']");
		builder.crawlRules().dontClick("a").withText("Language Tools");

		// limit the crawling scope
		builder.setMaximumStates(MAX_NUMBER_STATES);
		builder.setMaximumDepth(MAX_DEPTH);
		Class<? extends Plugin> cls = TestPlugin.class;
		PluginDescriptor descriptor = PluginDescriptor.forPlugin(cls);
		Map<String, String> parameters = new HashMap<>();
		for (Parameter parameter : descriptor.getParameters()) {
			parameters.put(parameter.getId(), "value");
		}
		System.setProperty("webdriver.chrome.driver","D:\\chromedriver_win32\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addExtensions(new File("D:\\MetaMask_v7.7.9.crx"));
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		builder.setBrowserConfig(
				new BrowserConfiguration(EmbeddedBrowser.BrowserType.CHROME, 1, (Provider<EmbeddedBrowser>) options));
		builder.addPlugin(new TestPlugin(new HostInterfaceImpl(new File("out"), parameters)));

		builder.crawlRules().setInputSpec(getInputSpecification());

		CrawljaxRunner crawljax = new CrawljaxRunner(builder.build());
		crawljax.call();
	}

	private static InputSpecification getInputSpecification() {
		InputSpecification input = new InputSpecification();
		input.inputField(InputType.TEXT, new Identification(How.id, "lst-ib"))
		        .inputValues("Crawljax");

		return input;
	}

	private Runner() {
		// Utility class
	}
}
