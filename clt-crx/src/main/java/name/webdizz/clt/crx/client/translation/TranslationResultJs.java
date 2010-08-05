/**
 * 
 */
package name.webdizz.clt.crx.client.translation;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * @author webdizz
 * 
 */
public final class TranslationResultJs extends JavaScriptObject {

	protected TranslationResultJs() {
	}

	public static final native TranslationResultJs create(TranslationResult translationResult)/*-{
		var src = translationResult.@name.webdizz.clt.crx.client.translation.TranslationResult::getSrc()();
		var dest = translationResult.@name.webdizz.clt.crx.client.translation.TranslationResult::getDest()();
		var srcLang = translationResult.@name.webdizz.clt.crx.client.translation.TranslationResult::getSrcLang()();
		var destLang = translationResult.@name.webdizz.clt.crx.client.translation.TranslationResult::getDestLang()();
		var translations = translationResult.@name.webdizz.clt.crx.client.translation.TranslationResult::getTranslations()();
		var translationsJs = new Array();
		if(translations){
			for(var idx=0; idx < translations.@java.util.ArrayList::size()(); idx++){
				translationsJs.push(@name.webdizz.clt.crx.client.translation.TranslationResultJs.Translation::create(Lname/webdizz/clt/crx/client/translation/TranslationResult$Translation;)(translations.@java.util.ArrayList::get(I)(idx)))
			}
		}
		return {src:src, dest:dest, srcLang:srcLang, destLang:destLang, translations:translationsJs};
	}-*/;

	/**
	 * @author webdizz
	 * 
	 */
	public final static class Translation extends JavaScriptObject {

		protected Translation() {
		}

		public static final native Translation create(TranslationResult.Translation translationFromResult)/*-{
			var translation = translationFromResult.@name.webdizz.clt.crx.client.translation.TranslationResult.Translation::getTranslation()();
			var explanations = translationFromResult.@name.webdizz.clt.crx.client.translation.TranslationResult.Translation::getExplanations()();
			var explanationsJs = new Array();
			if(explanations){
				for(var idx=0; idx < explanations.@java.util.ArrayList::size()(); idx++){
					explanationsJs.push(@name.webdizz.clt.crx.client.translation.TranslationResultJs.Explanation::create(Lname/webdizz/clt/crx/client/translation/TranslationResult$Explanation;)(explanations.@java.util.ArrayList::get(I)(idx)))
				}
			}
			return {translation:translation, explanations:explanationsJs};
		}-*/;

		public native String getTranslation() /*-{
			return this.translation;
		}-*/;

		public native void setTranslation(String translation) /*-{
			this.translation = translation;
		}-*/;

		public native Explanation[] getExplanations() /*-{
			return this.explanations;
		}-*/;

		public native void setExplanations(Explanation[] explanations) /*-{
			this.explanations = explanations;
		}-*/;

	}

	public static final class Explanation extends JavaScriptObject {

		protected Explanation() {
		}

		public static final native Explanation create(TranslationResult.Explanation explanation) /*-{
			return {explanation: explanation.@name.webdizz.clt.crx.client.translation.TranslationResult$Explanation::getExplanation()()};
		}-*/;

		public native String getExplanation() /*-{
			return this.explanation;
		}-*/;

		public native void setExplanation(String explanation) /*-{
			this.explanation = explanation;
		}-*/;

	}

	/**
	 * @return the src
	 */
	public native String getSrc() /*-{
		return this.src;
	}-*/;

	/**
	 * @param src
	 *            the src to set
	 */
	public native void setSrc(String src) /*-{
		this.src = src;
	}-*/;

	/**
	 * @return the dest
	 */
	public native String getDest() /*-{
		return this.dest;
	}-*/;

	/**
	 * @param dest
	 *            the dest to set
	 */
	public native void setDest(String dest) /*-{
		this.dest = dest;
	}-*/;

	/**
	 * @return the srcLang
	 */
	public native String getSrcLang() /*-{
		return this.srcLang;
	}-*/;

	/**
	 * @param srcLang
	 *            the srcLang to set
	 */
	public native void setSrcLang(String srcLang) /*-{
		this.srcLang = srcLang;
	}-*/;

	/**
	 * @return the destLang
	 */
	public native String getDestLang() /*-{
		return this.destLang;
	}-*/;

	/**
	 * @param destLang
	 *            the destLang to set
	 */
	public native void setDestLang(String destLang) /*-{
		this.destLang = destLang;
	}-*/;

	/**
	 * @return the translations
	 */
	public native Translation[] getTranslations()/*-{
		return this.translations;
	}-*/;

	/**
	 * @param translations
	 *            the translations to set
	 */
	public native void setTranslations(Translation[] translations) /*-{
		this.translations = translations;
	}-*/;

	/**
	 * Checks whether result has translation or no.
	 * 
	 * @return true if result does not contain translations, false otherwise
	 */
	public native boolean isEmpty() /*-{
		return !dest || !translations || translations.length<1;
	}-*/;

}
