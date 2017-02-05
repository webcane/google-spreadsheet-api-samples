package cane.brothers.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;

import cane.brothers.spring.Global;

@Service
public class GoogleConnectionService implements GoogleConnection {

	@Autowired
	private OAuth2ClientContext oAuth2ClientContext;

	@Value("security.oauth2.client.client-id")
	String clientId;

	@Value("security.oauth2.client.clientSecret")
	String clientSecret;

	private GoogleCredential googleCredentials = null;

	/**
	 * Constructor
	 */
	public GoogleConnectionService() {
		System.out.println("google connection");
	}

	@Override
	public Credential getCredentials() {
		if (googleCredentials == null) {
			googleCredentials = new GoogleCredential.Builder()
					.setTransport(Global.HTTP_TRANSPORT)
					.setJsonFactory(Global.JSON_FACTORY)
					.setClientSecrets(clientId, clientSecret)
					.build()
					.setFromTokenResponse(getTokenResponse());
		}
		return googleCredentials;
	}
	
	private TokenResponse getTokenResponse() {
		TokenResponse tr = new TokenResponse();
		OAuth2AccessToken ac = oAuth2ClientContext.getAccessToken();
		if (ac != null) {
			tr.setAccessToken(ac.getValue());
			tr.setExpiresInSeconds((long) ac.getExpiresIn());
			tr.setTokenType(ac.getTokenType());
		}
		
		return tr;
	}

}
