/**
 * 
 */
package name.webdizz.clt.crx.client;

import com.google.gwt.chrome.crx.client.GwtContentScript;
import com.google.gwt.chrome.crx.client.GwtContentScript.ManifestInfo;

/**
 * @author webdizz
 * 
 */
@ManifestInfo(matches = { "http://*/*", "https://*/*", "file:///*" }, module = "name.webdizz.clt.crx.client.contentscript.ContentScript", runAt = GwtContentScript.DOCUMENT_END, allFrames = true)
public class ContentScriptInitialzer extends GwtContentScript {

}
