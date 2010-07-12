/**
 * 
 */
package name.webdizz.gwt.chrome.test.client;

import com.google.gwt.chrome.crx.client.GwtContentScriptEntryPoint;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * @author webdizz
 * 
 */
public class TestableGwtContentScript extends GwtContentScriptEntryPoint {

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
