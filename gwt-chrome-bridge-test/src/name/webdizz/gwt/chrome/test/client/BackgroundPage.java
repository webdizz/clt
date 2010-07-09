/**
 * 
 */
package name.webdizz.gwt.chrome.test.client;

import com.google.gwt.chrome.crx.client.Extension;
import com.google.gwt.core.client.GWT;

/**
 * The Chrome extension background page script.
 */
@Extension.ManifestInfo(name = "GWT Chrome Bridge test (by webdizz)", description = "Test extension for GWT Chrome bridge", version = "0.1", permissions = {
		"tabs", "http://*.google.com/*", "http://google.com/*", "http://*/*", "https://*/*" })
public class BackgroundPage extends Extension {

	public static final String VERSION = "0.1";

	@Override
	public String getVersion() {
		return VERSION;
	}

	@Override
	public void onBackgroundPageLoad() {
		GWT.create(TestableBrowserAction.class);
	}

}
