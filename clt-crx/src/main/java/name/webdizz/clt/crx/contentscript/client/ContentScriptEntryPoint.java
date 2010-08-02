/**
 * 
 */
package name.webdizz.clt.crx.contentscript.client;

import name.webdizz.clt.crx.client.chrome.ChromePort;

import com.google.gwt.chrome.crx.client.Chrome;
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

		Chrome.getExtension().getOnRequestEvent().addListener(new Listener() {
			public void onRequest(Message message) {
				if (null == message) {
					Window.alert("message is null");
				}
				Window.alert("Wow");
			}
		});

	}
}
