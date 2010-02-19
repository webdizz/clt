/**
 * 
 */
package name.webdizz.clt.crx.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;

/**
 * @author Izzet_Mustafayev
 * 
 */
public class Monitor implements EntryPoint {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
	 */
	public void onModuleLoad() {
		LayoutPanel mainContainer = new LayoutPanel();
		Label label = new Label("Some selectable text");
		label.addMouseUpHandler(new MouseUpHandler() {

			public void onMouseUp(MouseUpEvent event) {
				Window.alert(SelectionHelper.getSelectedText());
			}

		});
		mainContainer.add(label);
		RootLayoutPanel.get().add(mainContainer);
	}

	private static class SelectionHelper extends JavaScriptObject {

		@SuppressWarnings("unused")
		protected SelectionHelper() {
		}

		public static final native String getSelectedText() /*-{
			return new String($doc.getSelection());
		}-*/;
	}

}
