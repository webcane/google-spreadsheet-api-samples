package cane.brothers.spring.intersept;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cane.brothers.spring.OAuthUtil;

public class GoogleAuthorizationInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse response, Object arg2, Exception arg3)
			throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse response, Object arg2, ModelAndView arg3)
			throws Exception {
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		System.out.println("Pre-handle");
		System.out.println(request.getRequestURI());		
		
		// authorize on google
		//if (OAuthUtil.getCredentials() == null) {
		if (request.getSession().getAttribute("access_token") == null) {
			OAuthUtil.setSourceUrl(request.getRequestURI());
			response.sendRedirect("/ask");
			return false;
		}
		// is connected
		return true;
	}

}
