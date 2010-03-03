/**
 * 
 */
package name.webdizz.clt.crx.client.view;

import name.webdizz.clt.crx.client.presenter.TranslationPresenter.ITranslationView;

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

	public TranslationView() {
		initWidget(uiBinder.createAndBindUi(this));

		// Can access @UiField after calling createAndBindUi
	}

	public void setTranslatedText(final String text) {
		translatedText.setInnerText(text);
	}

	public Widget asWidget() {
		return this;
	}

	public String widgetAsString() {
		return JsonUtils.escapeValue(toString());
	}
}
