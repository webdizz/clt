package name.webdizz.clt.crx.contentscript.client.translation.view;

import com.google.gwt.user.client.ui.Widget;

public interface ITranslationView {

	/**
	 * Sets translated word.
	 * 
	 * @param text
	 *            the translation result
	 */
	void setTranslatedText(String text);

	/**
	 * Sets source word.
	 * 
	 * @param text
	 *            the source word to translate
	 */
	void setTranslateableText(String text);

	/**
	 * Convert {@link ITranslationView} to {@link Widget}.
	 * 
	 * @return {@link ITranslationView} as a {@link Widget}
	 */
	Widget asWidget();

}