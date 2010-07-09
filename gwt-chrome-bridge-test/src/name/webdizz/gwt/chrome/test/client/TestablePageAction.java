/**
 * 
 */
package name.webdizz.gwt.chrome.test.client;

import com.google.gwt.chrome.crx.client.Icon;
import com.google.gwt.chrome.crx.client.PageAction;
import com.google.gwt.chrome.crx.client.Icon.Source;

/**
 * @author webdizz
 * 
 */
@PageAction.ManifestInfo(name = "icon16.png", pageActionId = "pageActionId")
public abstract class TestablePageAction extends PageAction {

	@Override
	public String getId() {
		return "pageActionId";
	}

	@Override
	public String getName() {
		return "TestablePageAction";
	}

	@Source("icon16.png")
	public abstract Icon mtIcon();

}
