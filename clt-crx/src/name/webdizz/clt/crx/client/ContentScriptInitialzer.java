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
@ManifestInfo(matches = { "http://google.com/*" }, module = "name.webdizz.clt.crx.client.contentscript.ContentScript", runAt = GwtContentScript.DOCUMENT_START)
public class ContentScriptInitialzer extends GwtContentScript {

}
