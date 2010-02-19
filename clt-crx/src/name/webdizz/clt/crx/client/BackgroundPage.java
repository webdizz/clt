package name.webdizz.clt.crx.client;

import com.google.gwt.chrome.crx.client.Chrome;
import com.google.gwt.chrome.crx.client.Console;
import com.google.gwt.chrome.crx.client.Extension;
import com.google.gwt.chrome.crx.client.Port;
import com.google.gwt.chrome.crx.client.Tabs;
import com.google.gwt.chrome.crx.client.View;
import com.google.gwt.chrome.crx.client.Windows;
import com.google.gwt.chrome.crx.client.Tabs.OnTabCallback;
import com.google.gwt.chrome.crx.client.Tabs.Tab;
import com.google.gwt.chrome.crx.client.Windows.OnWindowCallback;
import com.google.gwt.chrome.crx.client.Windows.Window;
import com.google.gwt.chrome.crx.client.events.PageActionEvent.Info;
import com.google.gwt.chrome.crx.client.events.PageActionEvent.Listener;
import com.google.gwt.core.client.GWT;

/**
 * The Chrome extension background page script.
 */
@Extension.ManifestInfo(name = "CLT (by webdizz)", description = "Learn words simpler while browsing internet.", version = "0.1", permissions = {
		"tabs", "http://*/*", "https://*/*" }, icons = {
		"resources/icon16.png", "resources/icon32.png", "resources/icon48.png",
		"resources/icon128.png" })
public abstract class BackgroundPage extends Extension {

	private static Console CONSOLE;

	private final class ConnectEventHandler implements
			com.google.gwt.chrome.crx.client.events.ConnectEvent.Listener {
		public void onConnect(Port port) {
			CONSOLE.log("Enter into onConnect handler");

		}
	}

	// TODO: need to be changed before release
	private static final String MONITOR_RESOURCE_PATH = "http://localhost:8888/monitor.html?gwt.codesvr=127.0.0.1:9997";

	private final static SelectWordPageAction PAGE_ACTION = GWT
			.create(SelectWordPageAction.class);

	/**
	 * Our entry point function. All things start here.
	 */
	@Override
	public void onBackgroundPageLoad() {
		CONSOLE = Chrome.getExtension().getBackgroundPage().getConsole();
		CONSOLE.log("Enter into BackgroundPage.onBackgroundPageLoad.");
		Chrome.getExtension().getOnConnectEvent().addListener(
				new ConnectEventHandler());
		Windows.getCurrent(new OnWindowCallback() {
			public void onWindow(
					com.google.gwt.chrome.crx.client.Windows.Window window) {
				CONSOLE.log("In the OnWindowCallback");
				Tabs.getSelected(window.getWindowId(), new OnTabCallback() {
					public void onTab(Tab tab) {
						PAGE_ACTION.enableForTab(tab.getId(), tab.getUrl(), tab
								.getTitle(), PAGE_ACTION.mtIconActive());
						PAGE_ACTION.setTitle(tab.getId(), "new titile");
						PAGE_ACTION.show(tab.getId());

					}
				});

			}
		});
		// show popup
		PAGE_ACTION.addListener(new Listener() {
			public void onPageAction(int pageActionId, Info info) {
				Windows.create(MONITOR_RESOURCE_PATH, 10, 10, 100, 100, new OnWindowCallback() {
					
					public void onWindow(Window window) {
						
					}
				});
			}
		});
		View backgroundPage = Chrome.getExtension().getBackgroundPage();
		String title = backgroundPage.getDocument().getBody().getTitle();
	}

}
