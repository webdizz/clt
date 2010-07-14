/**
 * 
 */
package name.webdizz.clt.crx.client.contentscript.client;

import com.google.gwt.chrome.crx.client.GwtContentScriptEntryPoint;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

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
		RootPanel.get().add(new Label("Some label"));
	}

}
