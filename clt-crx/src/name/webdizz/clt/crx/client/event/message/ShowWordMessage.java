/**
 * 
 */
package name.webdizz.clt.crx.client.event.message;

/**
 * @author Izzet_Mustafayev
 * 
 */
public class ShowWordMessage extends Message {

	public static final String TYPE = "ShowWordMessage";

	protected ShowWordMessage() {
	}

	public final native String getWord() /*-{
		return this.word;
	}-*/;

	public static final native ShowWordMessage create(String word)/*-{
		return {word: word, type:'ShowWordMessage'};
	}-*/;
}
