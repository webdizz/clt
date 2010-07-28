/**
 * 
 */
package name.webdizz.clt.crx.client.translation;


/**
 * @author webdizz
 * 
 */
public interface Translator {

	void translate(String word, TranslationHandler handler) throws TranslationException;

}
