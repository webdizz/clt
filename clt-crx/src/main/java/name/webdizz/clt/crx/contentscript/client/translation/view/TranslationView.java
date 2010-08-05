/**
 * 
 */
package name.webdizz.clt.crx.contentscript.client.translation.view;

import name.webdizz.clt.crx.client.translation.TranslationResultJs;
import name.webdizz.clt.crx.client.translation.TranslationResultJs.Explanation;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Izzet_Mustafayev
 * 
 */
public class TranslationView extends Composite implements ITranslationView {

	private static TranslationUiBinder uiBinder = GWT.create(TranslationUiBinder.class);

	interface TranslationUiBinder extends UiBinder<Widget, TranslationView> {
	}

	interface ViewStyle extends CssResource {
		String explanation();
	}

	@UiField
	ViewStyle style;

	@UiField
	SpanElement sourceWord;

	@UiField
	DdElement translationsElement;

	public TranslationView(TranslationResultJs translationResult) {
		initWidget(uiBinder.createAndBindUi(this));
		initWidgetData(translationResult);
	}

	protected class ExplanationHtml extends HTML {
		public ExplanationHtml(String html) {
			super(html);
			setStyleName(style.explanation());
		}
	}

	private void initWidgetData(TranslationResultJs translationResult) {
		if (null != translationResult) {
			sourceWord.setInnerText(translationResult.getSrc());
			TranslationResultJs.Translation[] translations = translationResult.getTranslations();
			for (int i = 0; i < translations.length; i++) {
				StringBuffer explanationsHtml = new StringBuffer();
				if (null != translations[i].getExplanations()) {
					Explanation[] explanations = translations[i].getExplanations();
					for (int j = 0; j < explanations.length; j++) {
						if (null != explanations[i] && null != explanations[j].getExplanation()) {
							explanationsHtml.append("<div class=\"" + style.explanation() + "\">"
									+ explanations[j].getExplanation() + "</div>");
						}
					}
				}
				String translationHtml = "<div>" + translations[i].getTranslation() + "</div>";
				translationsElement.setInnerHTML(translationsElement.getInnerHTML() + translationHtml
						+ explanationsHtml.toString());
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * name.webdizz.clt.crx.contentscript.client.translation.view.ITranslationView
	 * #asWidget()
	 */
	public Widget asWidget() {
		return this;
	}

}
