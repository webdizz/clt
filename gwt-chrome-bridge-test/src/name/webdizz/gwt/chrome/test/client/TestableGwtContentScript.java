/**
 * 
 */
package name.webdizz.gwt.chrome.test.client;

import com.google.gwt.chrome.crx.client.GwtContentScript;
import com.google.gwt.chrome.crx.client.GwtContentScript.ManifestInfo;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * @author webdizz
 * 
 */
@ManifestInfo(matches = { "http://www.google.com/*" }, runAt = GwtContentScript.DOCUMENT_END)
public class TestableGwtContentScript extends GwtContentScript {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.gwt.chrome.crx.client.GwtContentScript#onScriptLoad()
	 */
	@Override
	public void onScriptLoad() {
		Label label = new Label("Testable Label");
		RootPanel.get().add(label);
	}

}
