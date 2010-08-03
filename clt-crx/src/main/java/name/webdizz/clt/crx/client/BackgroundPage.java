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
@Extension.ManifestInfo(name = "CLT (by webdizz)", description = "Learn words simpler while browsing internet.", version = BackgroundPage.VERSION, permissions = {
		"tabs", "http://ajax.googleapis.com/*", "http://*.google.com/*", "http://google.com/*", "http://*/*",
		"https://*/*" }, icons = { "resources/icon16.png", "resources/icon32.png", "resources/icon48.png",
		"resources/icon128.png" })
public class BackgroundPage extends Extension {

	public static final String VERSION = "0.1";

	static Console CONSOLE = Chrome.getExtension().getBackgroundPage().getConsole();

	/**
	 * Our entry point function. All things start here.
	 */
	@Override
	public void onBackgroundPageLoad() {

		GWT.create(JsonProvider.class);
		GWT.create(LanguageApiProvider.class);
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
