package com.familytree.services;

import java.io.IOException;

import javax.ejb.EJB;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.familytree.persistence.user.dao.UserBean;
import com.sap.security.auth.login.LoginContextFactory;

/**
 * Servlet implementing a simple EJB based persistence sample application for SAP Cloud Platform.
 */
public class RootServices extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(UserServices.class);
	@EJB
	private UserBean userBean;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String user = request.getRemoteUser();
		if (user != null) {
			response.getWriter().println("Hello, " + user);
		} else {
			LoginContext loginContext;
			try {
				loginContext = LoginContextFactory.createLoginContext("FORM");
				loginContext.login();
				response.getWriter().println("Hello, " +  request.getRemoteUser());
			} catch (LoginException ex)	{
				ex.printStackTrace();
			}
		}
	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
