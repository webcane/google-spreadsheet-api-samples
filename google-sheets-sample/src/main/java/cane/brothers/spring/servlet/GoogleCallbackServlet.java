package cane.brothers.spring.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.AuthorizationCodeResponseUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.servlet.auth.oauth2.AbstractAuthorizationCodeCallbackServlet;

import cane.brothers.spring.OAuthUtil;

public class GoogleCallbackServlet extends AbstractAuthorizationCodeCallbackServlet {

	private static final long serialVersionUID = 7558236944620582363L;
	
	@Override
	  protected void onSuccess(HttpServletRequest req, HttpServletResponse resp, Credential credential)
	      throws ServletException, IOException {
		System.out.println("authorization accepted");
		
		OAuthUtil.setCredential(credential);
		
		HttpSession session = req.getSession();
		session.setAttribute("access_token", credential.getAccessToken());
	    resp.sendRedirect(OAuthUtil.getSourceUrl());
	  }

	  @Override
	  protected void onError(
	      HttpServletRequest req, HttpServletResponse resp, AuthorizationCodeResponseUrl errorResponse)
	      throws ServletException, IOException {
	    // handle error
		  resp.sendRedirect("/error");
	  }
	  
	@Override
	protected String getRedirectUri(HttpServletRequest req) throws ServletException, IOException {
		return OAuthUtil.getRedirectUri(req);
	}

	// return user ID
	@Override
	protected String getUserId(HttpServletRequest request) throws ServletException, IOException {
		String userId = "user" + request.getSession(true).getId();
		System.out.println("user id:" + userId);
		return userId;
	}

	@Override
	protected AuthorizationCodeFlow initializeFlow() throws ServletException, IOException {
		return OAuthUtil.newFlow();
	}

}
