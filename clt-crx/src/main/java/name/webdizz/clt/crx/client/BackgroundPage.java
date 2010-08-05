package name.webdizz.clt.crx.client;

import name.webdizz.clt.crx.client.event.handler.ConnectEventHandler;

import com.google.gwt.chrome.crx.client.Chrome;
import com.google.gwt.chrome.crx.client.Console;
import com.google.gwt.chrome.crx.client.Extension;
import com.google.gwt.core.client.GWT;
import com.mvp4g.client.Mvp4gModule;

/**
 * The Chrome extension background page script.
 */
@Extension.ManifestInfo(name = "Loogat (by Izzet Mustafaiev)", description = "Qırımtatarian-Russian and Russian-Qırımtatarian translator.", version = BackgroundPage.VERSION, permissions = {
		"tabs", "http://qlugat.appspot.com/lugat/get*" }, icons = { "resources/icon16.png", "resources/icon32.png",
		"resources/icon48.png", "resources/icon128.png" })
public class BackgroundPage extends Extension {

	public static final String VERSION = "1.0";

	static Console CONSOLE = Chrome.getExtension().getBackgroundPage().getConsole();

	/**
	 * Our entry point function. All things start here.
	 */
	@Override
	public void onBackgroundPageLoad() {

		GWT.create(ContentScriptInitialzer.class);

		// init MVP
		Mvp4gModule module = GWT.create(Mvp4gModule.class);
		module.createAndStartModule();

		Chrome.getExtension().getOnConnectEvent()
				.addListener(new ConnectEventHandler((ExtEventBus) module.getEventBus()));
	}

	public String getVersion() {
		return VERSION;
	}

}
