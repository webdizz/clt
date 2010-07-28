/**
 * 
 */
package name.webdizz.clt.crx.client.event.message;

public class SelectTextMessage extends Message {

	public static final String TYPE = "SelectTextMessage";

	protected SelectTextMessage() {
	}

	public final native String getText() /*-{
		return this.text;
	}-*/;

	public final native String getOffsetX() /*-{
		return this.offsetX;
	}-*/;

	public final native String getOffsetY() /*-{
		return this.offsetY;
	}-*/;

	public final native String[] getKeys() /*-{
		return this.keys;
	}-*/;

	public static final native SelectTextMessage create(String text,
			String[] keys)/*-{
		return {text: text, type:'SelectTextMessage', keys: keys};
	}-*/;

}