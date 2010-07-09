package name.webdizz.gwt.chrome.test.client;

import com.google.gwt.chrome.crx.client.BrowserAction;
import com.google.gwt.chrome.crx.client.Icon;
import com.google.gwt.chrome.crx.client.Icon.Source;

/**
 * @author webdizz
 * 
 */
@BrowserAction.ManifestInfo(defaultIcon = "icon16.png", name = "TestableBrowserAction")
public abstract class TestableBrowserAction extends BrowserAction {

	@Override
	public String getName() {
		return "TestableBrowserAction";
	}

	@Source("icon16.png")
	public abstract Icon mtIcon();

}
