/**
 * 
 */
package name.webdizz.clt.crx.client.event.message;

/**
 * @author Izzet_Mustafayev
 * 
 */
public class PrepareTranslatedTextDisplayMessage extends Message {

	protected PrepareTranslatedTextDisplayMessage() {
	}

	public static final String TYPE = "PrepareTranslatedTextDisplayMessage";

	public final native String getDestLang() /*-{
		return this.dest;
	}-*/;

	public final native String getSrcLang() /*-{
		return this.src;
	}-*/;

	public final native String getTextFrom() /*-{
		return this.textFrom;
	}-*/;

	public final native String getTranslation() /*-{
		return this.translation;
	}-*/;

	public static final native PrepareTranslatedTextDisplayMessage create(
			String textFrom, String translation, String srcLang, String destLang)/*-{
		return {textFrom: textFrom, translation: translation, src: srcLang, dest: destLang, type:'PrepareTranslatedTextDisplayMessage'};
	}-*/;

}
