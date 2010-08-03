/**
 * 
 */
package name.webdizz.clt.crx.client.event.message;

/**
 * @author Izzet_Mustafayev
 * 
 */
public class ShowTranslatedTextMessage extends Message {

	public static final String TYPE = "ShowTranslatedTextMessage";

	protected ShowTranslatedTextMessage() {
	}

	public final native PrepareTranslatedTextDisplayMessage getTranslation() /*-{
		return this.translation;
	}-*/;

	public final native SelectTextMessage getMessage() /*-{
		return this.message;
	}-*/;

	public static final native ShowTranslatedTextMessage create(SelectTextMessage message,
			PrepareTranslatedTextDisplayMessage translation)/*-{
		return {translation: translation, message: message, type:'ShowTranslatedTextMessage'};
	}-*/;
}
