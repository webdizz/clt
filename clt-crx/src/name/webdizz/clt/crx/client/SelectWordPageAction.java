/**
 * 
 */
package name.webdizz.clt.crx.client;

import com.google.gwt.chrome.crx.client.Icon;
import com.google.gwt.chrome.crx.client.PageAction;
import com.google.gwt.chrome.crx.client.Icon.Source;
import com.google.gwt.chrome.crx.client.PageAction.ManifestInfo;

/**
 * @author Izzet_Mustafayev
 * 
 */
@ManifestInfo(name = "Select Word Page Action", pageActionId = "selectWordPageAction")
public abstract class SelectWordPageAction extends PageAction {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.gwt.chrome.crx.client.PageAction#getId()
	 */
	@Override
	public String getId() {
		return "selectWordPageAction";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.gwt.chrome.crx.client.PageAction#getName()
	 */
	@Override
	public String getName() {
		return "Select Word Page Action";
	}

	@Source("mt-icon-active.png")
	public abstract Icon mtIconActive();
	
}
