/**
 * 
 */
package name.webdizz.clt.crx.client.translation;

/**
 * @author webdizz
 * 
 */
public interface ITranslator {

	/**
	 * Performs translation of the given word and passing result to
	 * {@link ITranslationHandler}.
	 * 
	 * @param word
	 *            the word to translate
	 * @param handler
	 *            the {@link ITranslationHandler} to pass result to
	 * @throws TranslationException
	 *             translations did not success
	 */
	void translate(String word, ITranslationHandler handler) throws TranslationException;

}
