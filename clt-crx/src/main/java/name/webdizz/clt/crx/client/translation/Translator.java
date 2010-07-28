/**
 * 
 */
package name.webdizz.clt.crx.client.translation;

/**
 * @author webdizz
 * 
 */
public interface Translator {

	/**
	 * Performs translation of the given word and passing result to
	 * {@link TranslationHandler}.
	 * 
	 * @param word
	 *            the word to translate
	 * @param handler
	 *            the {@link TranslationHandler} to pass result to
	 * @throws TranslationException
	 *             translations did not success
	 */
	void translate(String word, TranslationHandler handler) throws TranslationException;

}
