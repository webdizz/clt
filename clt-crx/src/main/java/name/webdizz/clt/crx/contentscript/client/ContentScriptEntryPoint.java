/**
 * 
 */
package name.webdizz.clt.crx.contentscript.client;

import name.webdizz.clt.crx.client.chrome.ChromePort;
import name.webdizz.clt.crx.contentscript.client.connection.ContentScriptEventHandler;
import name.webdizz.clt.crx.contentscript.client.connection.ContentScriptRequestListener;

import com.google.gwt.chrome.crx.client.GwtContentScriptEntryPoint;
import com.google.gwt.user.client.Event;

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
		// assign connection request listener
		onRequestEvent(new ContentScriptRequestListener());
	}
}
