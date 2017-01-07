package cane.brothers.spring;

import java.util.Arrays;
import java.util.List;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.SheetsScopes;

public class Global {

	public static final List<String> SCOPES = Arrays.asList(SheetsScopes.SPREADSHEETS_READONLY);

	/** Global instance of the JSON factory. */
	public static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

	/** Global instance of the HTTP transport. */
	public static HttpTransport HTTP_TRANSPORT;
	
	static {
		try {
			HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		} catch (Throwable t) {
			t.printStackTrace();
			System.exit(1);
		}
	}

}
