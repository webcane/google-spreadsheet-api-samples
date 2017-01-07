package cane.brothers.spring.web;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;

import cane.brothers.spring.Global;
import cane.brothers.spring.service.GoogleConnectionService;

@RestController
public class GoogleAuthorizationController extends BaseController {

	@Autowired
	private GoogleConnectionService connection;
	
	@RequestMapping(value = "/ask", method = RequestMethod.GET)
	public void ask(HttpServletResponse response) throws IOException {
		
		// Step 1: Authorize --> ask for auth code
		String url = new GoogleAuthorizationCodeRequestUrl(connection.getClientSecrets(), 
				connection.getRedirectUrl(), Global.SCOPES)
//				.setApprovalPrompt("auto")
				.setApprovalPrompt("force")
				.build();
		
		System.out.println("Go to the following link in your browser: ");
		System.out.println(url);
		
		response.sendRedirect(url);
	}
}
