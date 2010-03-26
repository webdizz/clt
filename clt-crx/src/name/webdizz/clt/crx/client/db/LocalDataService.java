/**
 * 
 */
package name.webdizz.clt.crx.client.db;

import com.google.code.gwt.database.client.service.Connection;
import com.google.code.gwt.database.client.service.DataService;
import com.google.code.gwt.database.client.service.ListCallback;
import com.google.code.gwt.database.client.service.RowIdListCallback;
import com.google.code.gwt.database.client.service.Update;
import com.google.code.gwt.database.client.service.VoidCallback;

/**
 * @author Izzet_Mustafayev
 * 
 */
@Connection(name = "clt_db", version = "1.0", description = "The CLT Local database.", maxsize = 1000)
public interface LocalDataService extends DataService {

	@Update("CREATE TABLE IF NOT EXISTS translations ("
			+ "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
			+ "text VARCHAR(255), translation VARCHAR(255), "
			+ "src VARCHAR(10), dest VARCHAR(10), " + "translated INTEGER )")
	void initDatabase(VoidCallback callback);

	@Update("INSERT INTO translations (text, translation, src, dest, translated) VALUES ({translation.getText()}, {translation.getTranslation()}, {translation.getSrc()}, {translation.getDest()}, {translation.getTranslated()} )")
	void storeTranslation(Translation translation, RowIdListCallback callback);

	@Update("SELECT * FROM translations")
	void getTranslations(ListCallback<Translation> callback);
}
