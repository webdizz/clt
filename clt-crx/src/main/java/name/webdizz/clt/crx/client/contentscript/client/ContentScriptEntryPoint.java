/**
 * 
 */
package name.webdizz.clt.crx.client.contentscript.client;

import name.webdizz.clt.crx.client.event.message.SelectTextMessage;

import com.google.gwt.chrome.crx.client.GwtContentScriptEntryPoint;
import com.google.gwt.chrome.crx.client.Port;

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
		Port port = connect("from_gwt_content_script");
		SelectTextMessage msg = SelectTextMessage.create("sample word", new String[] { "Ctrl" });
		port.postMessage(msg);
	}

}
