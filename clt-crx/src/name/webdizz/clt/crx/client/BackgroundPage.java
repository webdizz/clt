package name.webdizz.clt.crx.client;

import name.webdizz.clt.crx.client.event.TextSelectEvent;
import name.webdizz.clt.crx.client.event.handler.ConnectEventHandler;
import name.webdizz.clt.crx.client.event.handler.TextSelectEventHandler;

import com.google.gwt.chrome.crx.client.Chrome;
import com.google.gwt.chrome.crx.client.Console;
import com.google.gwt.chrome.crx.client.Extension;
import com.google.gwt.chrome.crx.client.Tabs;
import com.google.gwt.chrome.crx.client.Tabs.Tab;
import com.google.gwt.chrome.crx.client.events.TabUpdatedEvent.ChangeInfo;
import com.google.gwt.chrome.crx.client.events.TabUpdatedEvent.Listener;

/**
 * The Chrome extension background page script.
 */
@Extension.ManifestInfo(name = "CLT (by webdizz)", description = "Learn words simpler while browsing internet.", version = "0.1", permissions = {
		"tabs", "http://*/*", "https://*/*" }, icons = {
		"resources/icon16.png", "resources/icon32.png", "resources/icon48.png",
		"resources/icon128.png" })
public abstract class BackgroundPage extends Extension {

	static Console CONSOLE = Chrome.getExtension().getBackgroundPage()
			.getConsole();

	/**
	 * Our entry point function. All things start here.
	 */
	@Override
	public void onBackgroundPageLoad() {
		CONSOLE.log("Enter into BackgroundPage.onBackgroundPageLoad.");
		Chrome.getExtension().getOnConnectEvent().addListener(
				new ConnectEventHandler());

		Tabs.getOnUpdatedEvent().addListener(new Listener() {
			public void onTabUpdated(int tabId, ChangeInfo changeInfo, Tab tab) {
				CONSOLE.log("The onTabUpdated on the Background Page.");
				TextSelectEvent.onTextSelected(new TextSelectEventHandler());
			}
		});
	}

}
