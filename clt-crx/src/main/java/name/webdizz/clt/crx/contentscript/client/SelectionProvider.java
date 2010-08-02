package name.webdizz.clt.crx.contentscript.client;

import com.google.gwt.core.client.JavaScriptObject;

public class SelectionProvider {

	private class SelectionResolver extends JavaScriptObject {
		protected SelectionResolver() {
		}

		final native String getSelection() /*-{
			var selection = $wnd.getSelection();
			return selection?new String(selection.toString()).trim():'';
		}-*/;
	}

	public SelectionProvider() {
	}

	public String getSelection() {
		return new SelectionResolver().getSelection();
	}
}