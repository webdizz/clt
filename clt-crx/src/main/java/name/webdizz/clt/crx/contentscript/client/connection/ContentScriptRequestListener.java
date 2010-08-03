package name.webdizz.clt.crx.contentscript.client.connection;

import com.google.gwt.chrome.crx.client.events.Message;
import com.google.gwt.chrome.crx.client.events.RequestEvent.Listener;
import com.google.gwt.user.client.Window;

/**
 * Listener for request communications from extension or another content script.
 * 
 * @author webdizz
 * 
 */
public final class ContentScriptRequestListener implements Listener {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.gwt.chrome.crx.client.events.RequestEvent.Listener#onRequest
	 * (com.google.gwt.chrome.crx.client.events.Message)
	 */
	public void onRequest(final Message message) {
		if (null == message) {
			Window.alert("message is null");
		}
		Window.alert("Wow");
	}
}