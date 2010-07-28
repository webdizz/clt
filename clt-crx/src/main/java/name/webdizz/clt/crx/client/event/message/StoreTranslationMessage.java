/**
 * 
 */
package name.webdizz.clt.crx.client.event.message;

/**
 * @author Izzet_Mustafayev
 * 
 */
public class StoreTranslationMessage extends Message {

	public static final String TYPE = "StoreTranslationMessage";

	protected StoreTranslationMessage() {
	}

	public final native String getTranslation() /*-{
		return this.translation;
	}-*/;

	public final native String getTranslateable() /*-{
		return this.translateable;
	}-*/;

	public static final native StoreTranslationMessage create(
			String translation, String translateable)/*-{
		return {translation: translation, translateable: translateable,  type:'StoreTranslationMessage'};
	}-*/;
}
