/**
 * 
 */
package name.webdizz.clt.crx.client.event.message;

import name.webdizz.clt.crx.client.translation.TranslationResult;
import name.webdizz.clt.crx.client.translation.TranslationResultJs;

/**
 * @author Izzet_Mustafayev
 * 
 */
public class ShowTranslatedTextMessage extends Message {

	public static final String TYPE = "ShowTranslatedTextMessage";

	protected ShowTranslatedTextMessage() {
	}

	public final native TranslationResultJs getTranslation() /*-{
		return this.translation;
	}-*/;

	public final native SelectTextMessage getMessage() /*-{
		return this.message;
	}-*/;

	public static final native ShowTranslatedTextMessage create(SelectTextMessage message,
			TranslationResult translationResult)/*-{
		var translation = @name.webdizz.clt.crx.client.translation.TranslationResultJs::create(Lname/webdizz/clt/crx/client/translation/TranslationResult;)(translationResult);
		return {translation: translation, message: message, type:'ShowTranslatedTextMessage'};
	}-*/;
}
