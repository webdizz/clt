/**
 * 
 */
package name.webdizz.clt.crx.client;

import com.google.gwt.chrome.crx.client.ContentScript;
import com.google.gwt.chrome.crx.client.ContentScript.ManifestInfo;

/**
 * Stab class for loading JSON parser.
 * 
 * @author Izzet_Mustafayev
 * 
 */
@ManifestInfo(path = "json.js", whiteList = { "http://*/*", "https://*/*",
		"file:///*" }, runAt = ContentScript.DOCUMENT_END)
public class JsonProvider extends ContentScript {

}
