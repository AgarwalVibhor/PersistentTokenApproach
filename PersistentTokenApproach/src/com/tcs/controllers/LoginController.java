package com.tcs.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
	
	@RequestMapping(value = "/")
	public ModelAndView start()
	{
		ModelAndView model = new ModelAndView("redirect:/admin");
		/*
		 * After every successful login, the control will be redirected to the 'admin' page.
		 */
		return model;
	}
	
	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public ModelAndView welcome()
	{
		ModelAndView model = new ModelAndView("welcome");
		model.addObject("title", "Spring Security - Remember Me");
		model.addObject("message", "This page is the Welcome Page !");
		return model;
	}
	
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public ModelAndView admin(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView model = new ModelAndView("admin");
		System.out.println("Session in Admin : " +request.getSession(false));
		model.addObject("title", "Spring Security - Remember Me");
		model.addObject("message", "This page is only for Admin guys !");
		return model;
	}
	
	@RequestMapping(value = "/admin/update", method = RequestMethod.GET)
	public ModelAndView update(HttpServletRequest request, HttpServletResponse response)
	{
		System.out.println("Session in Update : "+ request.getSession(false));
		ModelAndView model = null;
		if(isRememberMeAuthenticated())
		{
			/*
			 * If the user is login through "remember-me" cookie, first bring the login page and then
			 * store the Remember Me target URL to session . This is because if there is a login error, the 
			 * target URL can again be retrieved from session.
			 */
			System.out.println("The user is remember me authenticated.");
			setRememberMeTargetUrlToSession(request);
			model = new ModelAndView();
			model.addObject("loginUpdate", true);
			model.setViewName("redirect:/login");
		}
		else
		{
			model = new ModelAndView("update");
		}
		return model;
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET) 
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout)
	{
		System.out.println("Session Beginning : " + request.getSession(false));
		System.out.println("Error : " + error);
		System.out.println("Logout : " + logout);
		ModelAndView model = new ModelAndView("login");
		
		if(error != null)
		{
			/*
			 * if login error, get the target URL from session again
			 */
			model.addObject("error", "Invalid Username and Password !!");
			HttpSession session = request.getSession(false);
			System.out.println("Sesion Here : " + session);
			if(session != null)
			{
				String targetUrl = getRememberMeTargetUrlToSession(request);
				System.out.println("Get Target Url : " + targetUrl);
				if(targetUrl.equals(""))
				{
					
				}
				else
				{
					model.addObject("targetUrl", targetUrl);
					model.addObject("loginUpdate", true);
				}
			}
			
		}
		
		if(logout != null)
		{
			model.addObject("logout", "You have logged out successfully !!");
		}
		
		return model;
	}
	
	
	/*
	 * Check if the user is login by using "remember-me" cookie/token.
	 */
	private boolean isRememberMeAuthenticated()
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication == null)
		{
			return false;
		}
		return RememberMeAuthenticationToken.class.isAssignableFrom(authentication.getClass());
		/*
		 * RememberMeAuthenticationToken.class.isAssignableFrom(authentication.getClass()) is same as
		 *        authentication instanceof RememberMeAuthenticationToken
		 */
	}
	
	
	
	/*
	 * save targetUrl in session
	 */
	private void setRememberMeTargetUrlToSession(HttpServletRequest request)
	{
		HttpSession session = request.getSession(false);
		System.out.println("Session in set method : " + session);
		if(session != null)
		{
			session.setAttribute("targetUrl", "/admin/update");
		}
		System.out.println("Set Target Url : " + session.getAttribute("targetUrl"));
	}
	
	
	
	/*
	 * get targetUrl from session
	 */
	private String getRememberMeTargetUrlToSession(HttpServletRequest request)
	{
		String targetUrl = "";
		HttpSession session = request.getSession(false);
		System.out.println("Session in get method : " + session);
		if(session != null)
		{
			Object t = session.getAttribute("targetUrl");
			if(t == null)
			{
				targetUrl = "";
			}
			else
			{
				targetUrl = t.toString();
			}
		}
		return targetUrl;
	}

}
