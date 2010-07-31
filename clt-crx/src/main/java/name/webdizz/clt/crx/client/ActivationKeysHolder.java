/**
 * 
 */
package name.webdizz.clt.crx.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.dom.client.NativeEvent;

/**
 * @author Izzet_Mustafayev
 * 
 */
public class ActivationKeysHolder {

	public static final String CTRL = "Ctrl";

	public static final String ALT = "Alt";

	public static final String META = "Meta";

	public static final String SHIFT = "Shift";

	/**
	 * The keys to activate translation for selected text.
	 */
	public static final String[] KEYS = { CTRL, ALT, SHIFT, META };

	/**
	 * Resolves used keys when given {@link NativeEvent} event was fired.
	 * 
	 * @param event
	 *            the {@link NativeEvent}
	 * @return used keys array
	 */
	private static String[] getUsedKeys(NativeEvent event) {
		List<String> list = new ArrayList<String>();
		if (event.getAltKey()) {
			list.add(ALT);
		}
		if (event.getCtrlKey()) {
			list.add(CTRL);
		}
		if (event.getMetaKey()) {
			list.add(META);
		}
		if (event.getShiftKey()) {
			list.add(SHIFT);
		}
		return list.toArray(new String[0]);
	}

	/**
	 * Perform validation whether message is able to be translated.
	 * 
	 * @param keys
	 *            the array of pressed keyboard keys
	 * @return true if right keyboard key was pressed
	 */
	public static boolean isKeysAllowed(final NativeEvent event) {
		String[] keys = getUsedKeys(event);
		boolean result = false;
		for (String key : keys) {
			if (ActivationKeysHolder.CTRL.equals(key)) {
				result = true;
				break;
			}
		}
		return result;
	}

}
