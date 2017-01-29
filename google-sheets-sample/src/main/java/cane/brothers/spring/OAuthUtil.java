package cane.brothers.spring;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.SheetsScopes;

public class OAuthUtil {

	private static final String CLIENT_SECRETS = "/client_secrets.json";

	private static GoogleClientSecrets clientSecrets;

	private static String authorizationCode;

	private static String sourceUrl;

	private static Credential credential;
	
	public static final List<String> SCOPES = Arrays.asList(SheetsScopes.SPREADSHEETS_READONLY);

	/** Global instance of the JSON factory. */
	public static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

	/** Global instance of the HTTP transport. */
	public static HttpTransport HTTP_TRANSPORT;
	
	/** Directory to store user credentials for this application. */
	public static final java.io.File DATA_STORE_DIR = new java.io.File(
        System.getProperty("user.home"), ".credentials/habr-app");

    
	/** Global instance of the {@link FileDataStoreFactory}. */
	public static FileDataStoreFactory DATA_STORE_FACTORY;
	
	static {
		try {
			HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
			DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
		} catch (Throwable t) {
			t.printStackTrace();
			System.exit(1);
		}
	}

	private static InputStream getSecretFile() throws IOException {
		return OAuthUtil.class.getResourceAsStream(CLIENT_SECRETS);
	}

	public static GoogleClientSecrets getClientSecrets() {
		if (clientSecrets == null) {
			try {
				// load client secrets
				InputStreamReader clientSecretsReader = new InputStreamReader(getSecretFile());
				clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, clientSecretsReader);
				
				Preconditions.checkArgument(
	                    !clientSecrets.getDetails().getClientId().startsWith("Enter ") && 
	                    !clientSecrets.getDetails().getClientSecret().startsWith("Enter "),
	                    "Download client_secrets.json file");

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return clientSecrets;
	}

	public static Credential getCredentials() {
		return credential;
	}

	public static void setAuthorizationCode(String authorizationCode) {
		OAuthUtil.authorizationCode = authorizationCode;
	}

	public static void setCredential(Credential credential) {
		OAuthUtil.credential = credential;
		if(credential != null) {
			OAuthUtil.authorizationCode = credential.getAccessToken();
		}
	}
	
	public static GoogleAuthorizationCodeFlow newFlow() throws IOException {
        return new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, 
        			getClientSecrets(), SCOPES)
                .setDataStoreFactory(DATA_STORE_FACTORY)
                .setAccessType("offline").build();
    }

	public String getAuthorizationCode() {
		return authorizationCode;
	}

	public static String getSourceUrl() {
		return sourceUrl;
	}

	public static void setSourceUrl(String redirectUrl) {
		sourceUrl = redirectUrl;
	}

	public String getRedirectUrl() {
		if(clientSecrets != null) {
			return clientSecrets.getDetails().getRedirectUris().get(0);
		}
		return null;
	}
	
	public static String getRedirectUri(HttpServletRequest req) {
        GenericUrl url = new GenericUrl(req.getRequestURL().toString());
        url.setRawPath("/oauth2callback");
        return url.build();
    }
}
