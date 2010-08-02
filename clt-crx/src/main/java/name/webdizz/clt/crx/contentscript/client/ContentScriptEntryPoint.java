/**
 * 
 */
package name.webdizz.clt.crx.contentscript.client;

import name.webdizz.clt.crx.client.chrome.ChromePort;

import com.google.gwt.chrome.crx.client.GwtContentScriptEntryPoint;
import com.google.gwt.user.client.Event;

/**
 * @author webdizz
 * 
 */
public class ContentScriptEntryPoint extends GwtContentScriptEntryPoint {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.gwt.chrome.crx.client.GwtContentScriptEntryPoint#onScriptLoad
	 * ()
	 */
	@Override
	public void onScriptLoad() {
		ChromePort port = new ChromePort(connect("liner"));
		Event.addNativePreviewHandler(new ContentScriptEventHandler(port, new SelectionProvider()));
	}

}
