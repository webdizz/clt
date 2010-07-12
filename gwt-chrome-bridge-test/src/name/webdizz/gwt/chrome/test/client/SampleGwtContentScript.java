/**
 * 
 */
package name.webdizz.gwt.chrome.test.client;

import com.google.gwt.chrome.crx.client.GwtContentScript;
import com.google.gwt.chrome.crx.client.GwtContentScript.ManifestInfo;

/**
 * @author webdizz
 * 
 */
@ManifestInfo(module = "name.webdizz.gwt.chrome.test.GwtContentScriptTest", matches = { "http://google.com/*" }, runAt = GwtContentScript.DOCUMENT_START)
public class SampleGwtContentScript extends GwtContentScript {
}
