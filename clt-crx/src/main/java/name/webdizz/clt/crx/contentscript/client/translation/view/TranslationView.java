/**
 * 
 */
package name.webdizz.clt.crx.contentscript.client.translation.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Izzet_Mustafayev
 * 
 */
public class TranslationView extends Composite implements ITranslationView {

	private static TranslationUiBinder uiBinder = GWT
			.create(TranslationUiBinder.class);

	interface TranslationUiBinder extends UiBinder<Widget, TranslationView> {
	}

	@UiField
	SpanElement translatedText;
	
	@UiField
	public
	SpanElement translation;

	public TranslationView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	/* (non-Javadoc)
	 * @see name.webdizz.clt.crx.contentscript.client.translation.view.ITranslationView#setTranslatedText(java.lang.String)
	 */
	@Override
	public void setTranslatedText(final String text) {
		translatedText.setInnerText(text);
		translation.setAttribute("translation", text);
	}
	
	/* (non-Javadoc)
	 * @see name.webdizz.clt.crx.contentscript.client.translation.view.ITranslationView#setTranslateableText(java.lang.String)
	 */
	@Override
	public void setTranslateableText(final String text) {
		translation.setAttribute("translateable", text);
	}

	public Widget asWidget() {
		return this;
	}

	public String widgetAsString() {
		return JsonUtils.escapeValue(toString());
	}
}
