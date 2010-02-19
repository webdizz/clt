package name.webdizz.clt.crx.client;

import java.util.HashMap;

import com.google.gwt.chrome.crx.client.Chrome;
import com.google.gwt.chrome.crx.client.Extension;
import com.google.gwt.chrome.crx.client.Icon;
import com.google.gwt.chrome.crx.client.Port;
import com.google.gwt.chrome.crx.client.Windows;
import com.google.gwt.chrome.crx.client.Tabs.Tab;
import com.google.gwt.chrome.crx.client.Windows.OnWindowCallback;
import com.google.gwt.chrome.crx.client.Windows.Window;
import com.google.gwt.chrome.crx.client.events.BrowserActionEvent;
import com.google.gwt.chrome.crx.client.events.ConnectEvent;
import com.google.gwt.chrome.crx.client.events.PageActionEvent;
import com.google.gwt.chrome.crx.client.events.PageActionEvent.Info;
import com.google.gwt.core.client.GWT;

/**
 * The Chrome extension background page script.
 */
@Extension.ManifestInfo(name = "CLT (by webdizz)", description = "Learn words simpler while browsing internet.", version = "0.1", permissions = {
		"tabs", "http://*/*", "https://*/*" }, icons = {
		"resources/icon16.png", "resources/icon32.png", "resources/icon48.png",
		"resources/icon128.png" })
public abstract class BackgroundPage extends Extension {

	private final WordsTabBrowserAction browserAction = GWT
			.create(WordsTabBrowserAction.class);

	private final SelectWordPageAction selectWordPageAction = GWT
			.create(SelectWordPageAction.class);

	// TODO: need to be changed before release
	private static final String MONITOR_RESOURCE_PATH = "http://localhost:8888/monitor.html?gwt.codesvr=127.0.0.1:9997";

	private static final int CHROME_BROWSER_ID = 0;

	private final HashMap<Integer, BrowserConnectionState> browserConnectionMap = new HashMap<Integer, BrowserConnectionState>();

	/**
	 * Our entry point function. All things start here.
	 */
	@Override
	public void onBackgroundPageLoad() {
		// Chrome is "connected". Insert an entry for it.
		browserConnectionMap.put(CHROME_BROWSER_ID,
				new BrowserConnectionState());

		// A content script connects to us when we want to load data.
		Chrome.getExtension().getOnConnectEvent().addListener(
				new ConnectEvent.Listener() {
					public void onConnect(final Port port) {
					}
				});

		// Register page action and browser action listeners.
		browserAction.addListener(new WordsTabClickListener());
		selectWordPageAction.addListener(new PageActionEvent.Listener() {

			public void onPageAction(int pageActionId, Info info) {
				selectWordPageAction.show(pageActionId);
				selectWordPageAction.setIcon(info.getTabId(), browserAction
						.mtIconDisabled());
			}
		});
		Windows.getCurrent(new OnWindowCallback() {

			public void onWindow(Window window) {
				selectWordPageAction.show(window.getId());
				selectWordPageAction.setTitle(window.getId(), "Some titile");
			}
		});
	}

	/**
	 * Simple data structure class to maintain information about the connection
	 * states for a connected browser and its tabs.
	 */
	private class BrowserConnectionState {
		private final HashMap<Integer, TabModel> tabMap = new HashMap<Integer, TabModel>();

		BrowserConnectionState() {
		}
	}

	private class WordsTabClickListener implements BrowserActionEvent.Listener {

		public void onClicked(Tab tab) {
			BrowserConnectionState browserConnection = browserConnectionMap
					.get(CHROME_BROWSER_ID);

			int tabId = tab.getId();
			String tabUrl = tab.getUrl();

			// Verify that it is not a click on the browser action button in a
			// Monitor window. If it is, early out.
			String urlNoParams = tabUrl.split("\\?")[0];
			if (urlNoParams.equals(Chrome.getExtension().getUrl(
					MONITOR_RESOURCE_PATH))) {
				return;
			}

			TabModel tabModel = getOrCreateTabModel(browserConnection, tabId);

			// We want to either open the monitor or resume monitoring.
			if (tabModel.currentIcon == browserAction.mtIcon()) {

				if (tabModel.monitorClosed) {
					// Open the Monitor UI.
					openMonitor(CHROME_BROWSER_ID, tabId, tabModel);
				} else {

					setBrowserActionIcon(tabId, browserAction.mtIconActive(),
							tabModel);
				}

				return;
			}

			// If the icon is the record button, then we should already have an
			// open
			// monitor, and we should start monitoring.
			if (tabModel.currentIcon == browserAction.mtIconActive()) {
				setBrowserActionIcon(tabId, browserAction.mtIcon(), tabModel);
			}
		}
	}

	/**
	 * Opens the monitor UI for a given tab, iff it is not already open.
	 */
	private void openMonitor(final int browserId, final int tabId,
			final TabModel tabModel) {
		assert (tabModel != null);

		/*
		 * Windows.create(MONITOR_RESOURCE_PATH + "?tabId=" + tabId +
		 * "&browserId=" + Integer.toString(browserId), 0, 0, 850, 700, new
		 * OnWindowCallback() { public void onWindow(Window window) {
		 * tabModel.monitorClosed = false; // The Tab containing the Monitor UI
		 * should not have a // valid browser // action button.
		 * Tabs.getSelected(window.getId(), new OnTabCallback() { public void
		 * onTab(Tab tab) { setBrowserActionIcon(tab.getId(), browserAction
		 * .mtIconDisabled(), null); } }); } });
		 */
	}

	/**
	 * Simple wrapper that hold on to a reference for the DataInstance and most
	 * recent TabDescription object for a tab.
	 */
	private static class TabModel {
		Icon currentIcon;
		boolean monitorClosed = true;

		TabModel(Icon icon) {
			this.currentIcon = icon;
		}
	}

	/**
	 * Returns a TabModel for a specified tab in a BrowserConnectionState
	 * object, or creates one if one is not found. It also initialized the
	 * TabModel with the specified DataInstance.
	 */
	private TabModel getOrCreateTabModel(
			BrowserConnectionState browserConnection, int tabId) {
		TabModel tabModel = browserConnection.tabMap.get(tabId);
		if (tabModel == null) {
			tabModel = new TabModel(browserAction.mtIcon());
			browserConnection.tabMap.put(tabId, tabModel);
		}
		return tabModel;
	}

	private void setBrowserActionIcon(int tabId, Icon icon, TabModel tabModel) {
		String title = "";
		if (icon == browserAction.mtIcon()) {
			title = "Monitor Tab";
		}
		if (icon == browserAction.mtIconActive()) {
			title = "Stop Monitoring";
		}

		browserAction.setIcon(tabId, icon);
		browserAction.setTitle(tabId, title);
		if (tabModel != null) {
			tabModel.currentIcon = icon;
		}
	}

}
