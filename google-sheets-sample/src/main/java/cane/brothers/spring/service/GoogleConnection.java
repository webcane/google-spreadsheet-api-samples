package cane.brothers.spring.service;

import com.google.api.client.auth.oauth2.Credential;

public interface GoogleConnection {

	Credential getCredentials();
}
