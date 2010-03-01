/**
 * 
 */
package name.webdizz.clt.crx.client;

import com.google.gwt.chrome.crx.client.ContentScript;
import com.google.gwt.chrome.crx.client.ContentScript.ManifestInfo;

/**
 * Stub class with annotation so we can get the content script referenced in our
 * manifest.
 * 
 * @author Izzet_Mustafayev
 * 
 */
@ManifestInfo(path = "event.js", whiteList = { "http://*/*", "https://*/*",
		"file:///*" }, runAt = ContentScript.DOCUMENT_END)
public class ExtensionInitializer extends ContentScript {

}
