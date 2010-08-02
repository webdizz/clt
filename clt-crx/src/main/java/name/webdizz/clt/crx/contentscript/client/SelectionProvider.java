package name.webdizz.clt.crx.contentscript.client;

import com.google.gwt.core.client.JavaScriptObject;

public class SelectionProvider {

	private static class SelectionResolver extends JavaScriptObject {
		protected SelectionResolver() {
		}

		static final native String getSelection() /*-{
			var selection = $wnd.getSelection();
			return selection?new String(selection.toString()).trim():'';
		}-*/;
	}

	protected SelectionProvider() {
	}

	public static String getSelection() {
		return SelectionResolver.getSelection();
	}
}