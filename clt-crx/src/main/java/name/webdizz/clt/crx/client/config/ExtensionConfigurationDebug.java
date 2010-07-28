/**
 * 
 */
package name.webdizz.clt.crx.client.config;

/**
 * @author Izzet_Mustafayev
 *
 */
public class ExtensionConfigurationDebug implements ExtensionConfiguration {

	/* (non-Javadoc)
	 * @see name.webdizz.clt.crx.client.config.ExtensionConfiguration#getMonitorUrl()
	 */
	public String getMonitorUrl() {
		return "http://localhost:8888/"+MONITOR+"?gwt.codesvr=127.0.0.1:9997";
	}

}
