package cane.brothers.spring.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.extensions.servlet.auth.oauth2.AbstractAuthorizationCodeServlet;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;

import cane.brothers.spring.OAuthUtil;

public class GoogleAuthorizationServlet extends AbstractAuthorizationCodeServlet  {

	private static final long serialVersionUID = 5700059162143547914L;

//	@Inject
//    private GoogleConnector googleConnector;
//	
//	@Override
//    public void init(ServletConfig config) throws ServletException {
//        super.init(config);
//        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
//    }
	
	/**
	 * Invoked only when a user is already authenticated
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// do stuff
		//response.getWriter().println("Hello! " + connection);
		
		// TODO
		//resp.sendRedirect("index.html");
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
		GoogleAuthorizationCodeFlow flow = OAuthUtil.newFlow();
		return flow;
	}
	
	// @Component
	
	
//	@Override
//	  public void init(final ServletConfig config)
//	      throws ServletException {
//	    super.init(config);
//	    WebApplicationContext springContext = WebApplicationContextUtils
//	        .getRequiredWebApplicationContext(config.getServletContext());
//	    final AutowireCapableBeanFactory beanFactory = springContext.getAutowireCapableBeanFactory();
//	    beanFactory.autowireBean(this);
	
	//// SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.servletContext)

//	  }

}
