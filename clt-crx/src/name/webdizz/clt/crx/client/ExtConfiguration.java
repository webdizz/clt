/**
 * 
 */
package name.webdizz.clt.crx.client;

import com.google.gwt.chrome.crx.client.Chrome;
import com.google.gwt.chrome.crx.client.LocalStorage;

/**
 * @author Izzet_Mustafayev
 * 
 */
public class ExtConfiguration {

	private final static LocalStorage LOCAL_STORAGE = Chrome.getExtension()
			.getBackgroundPage().getLocalStorage();

	public enum Configuration {

		/**
		 * The destination language.
		 */
		DEST_LANGUAGE
	}

	public String getDestLanguage() {
		return LOCAL_STORAGE.getItem(Configuration.DEST_LANGUAGE.name());
	}

	public void setDestLanguage(String value) {
		LOCAL_STORAGE.setItem(Configuration.DEST_LANGUAGE.name(), value);
	}
}