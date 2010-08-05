package name.webdizz.clt.crx.contentscript.client.translation.view;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.TagName;

@TagName("dd")
public class DdElement extends Element {
	static final String TAG = "dd";

	public static DdElement as(Element elem) {
		assert elem.getTagName().equalsIgnoreCase(TAG);
		return (DdElement) elem;
	}

	protected DdElement() {
	}

}