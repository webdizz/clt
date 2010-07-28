/**
 * 
 */
package name.webdizz.clt.crx.client.translation;

/**
 * @author webdizz
 * 
 */
public class TranslationResult {

	private String src;
	private String dest;
	private String srcLang;
	private String destLang;

	/**
	 * @return the src
	 */
	public String getSrc() {
		return src;
	}

	/**
	 * @param src
	 *            the src to set
	 */
	public void setSrc(String src) {
		this.src = src;
	}

	/**
	 * @return the dest
	 */
	public String getDest() {
		return dest;
	}

	/**
	 * @param dest
	 *            the dest to set
	 */
	public void setDest(String dest) {
		this.dest = dest;
	}

	/**
	 * @return the srcLang
	 */
	public String getSrcLang() {
		return srcLang;
	}

	/**
	 * @param srcLang
	 *            the srcLang to set
	 */
	public void setSrcLang(String srcLang) {
		this.srcLang = srcLang;
	}

	/**
	 * @return the destLang
	 */
	public String getDestLang() {
		return destLang;
	}

	/**
	 * @param destLang
	 *            the destLang to set
	 */
	public void setDestLang(String destLang) {
		this.destLang = destLang;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dest == null) ? 0 : dest.hashCode());
		result = prime * result + ((destLang == null) ? 0 : destLang.hashCode());
		result = prime * result + ((src == null) ? 0 : src.hashCode());
		result = prime * result + ((srcLang == null) ? 0 : srcLang.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TranslationResult other = (TranslationResult) obj;
		if (dest == null) {
			if (other.dest != null)
				return false;
		} else if (!dest.equals(other.dest))
			return false;
		if (destLang == null) {
			if (other.destLang != null)
				return false;
		} else if (!destLang.equals(other.destLang))
			return false;
		if (src == null) {
			if (other.src != null)
				return false;
		} else if (!src.equals(other.src))
			return false;
		if (srcLang == null) {
			if (other.srcLang != null)
				return false;
		} else if (!srcLang.equals(other.srcLang))
			return false;
		return true;
	}

}
