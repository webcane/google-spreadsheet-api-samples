package cane.brothers.spring.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.stereotype.Service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;

import cane.brothers.spring.Global;

@Service
public class GoogleConnectionService implements GoogleConnection {

	private static final String CLIENT_SECRETS = "/client_secrets.json";

	private static GoogleClientSecrets clientSecrets;

	private String authorizationCode;

	private String sourceUrl;

	private Credential credential;

	private InputStream getSecretFile() throws IOException {
		return this.getClass().getResourceAsStream(CLIENT_SECRETS);
	}
	
	public GoogleConnectionService() {
		System.out.println("google connection");
	}

	@Override
	public GoogleClientSecrets getClientSecrets() {
		if (clientSecrets == null) {
			try {
				// load client secrets
				InputStreamReader clientSecretsReader = new InputStreamReader(getSecretFile());
				clientSecrets = GoogleClientSecrets.load(Global.JSON_FACTORY, clientSecretsReader);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return clientSecrets;
	}

	@Override
	public Credential getCredentials() {
		return credential;
	}

	/**
	 * @param code
	 * @return
	 * @throws IOException
	 */
	public boolean exchangeCode(String code) {
		this.authorizationCode = code;
		
		// Step 2: Exchange --> exchange code for tokens 
		boolean result = false;
		String callbackUri = clientSecrets.getDetails().getRedirectUris().get(0);
		GoogleTokenResponse response;
		try {
			response = new GoogleAuthorizationCodeTokenRequest(Global.HTTP_TRANSPORT, Global.JSON_FACTORY,
					clientSecrets.getDetails().getClientId(), 
					clientSecrets.getDetails().getClientSecret(), code, callbackUri).execute();
			
			// Build a new GoogleCredential instance and return it.
			credential = new GoogleCredential.Builder()
					.setClientSecrets(clientSecrets)
					.setJsonFactory(Global.JSON_FACTORY)
					.setTransport(Global.HTTP_TRANSPORT)
					.build()
					.setAccessToken(response.getAccessToken())
					.setRefreshToken(response.getRefreshToken());
			result = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		// End of Step 2 <--
		return result;
	}

	public String getAuthorizationCode() {
		return authorizationCode;
	}

	@Override
	public String getSourceUrl() {
		return sourceUrl;
	}

	@Override
	public void setSourceUrl(String redirectUrl) {
		this.sourceUrl = redirectUrl;
	}

	@Override
	public String getRedirectUrl() {
		if(clientSecrets != null) {
			return clientSecrets.getDetails().getRedirectUris().get(0);
		}
		return null;
	}

}
