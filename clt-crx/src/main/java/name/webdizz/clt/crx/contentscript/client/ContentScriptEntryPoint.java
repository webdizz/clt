/**
 * 
 */
package name.webdizz.clt.crx.contentscript.client;

import name.webdizz.clt.crx.client.chrome.ChromePort;

import com.google.gwt.chrome.crx.client.GwtContentScriptEntryPoint;
import com.google.gwt.chrome.crx.client.events.Message;
import com.google.gwt.chrome.crx.client.events.RequestEvent.Listener;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;

/**
 * @author webdizz
 * 
 */
public class ContentScriptEntryPoint extends GwtContentScriptEntryPoint {

	private static final String CONNECTION_NAME = "liner";

	private final class ContentScriptRequestListener implements Listener {
		public void onRequest(Message message) {
			if (null == message) {
				Window.alert("message is null");
			}
			Window.alert("Wow");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.gwt.chrome.crx.client.GwtContentScriptEntryPoint#onScriptLoad
	 * ()
	 */
	@Override
	public void onScriptLoad() {
		ChromePort port = new ChromePort(connect(CONNECTION_NAME));
		// assign native event handler
		Event.addNativePreviewHandler(new ContentScriptEventHandler(port));
		// assign connection request listener
		onRequestEvent(new ContentScriptRequestListener());

	}
}
