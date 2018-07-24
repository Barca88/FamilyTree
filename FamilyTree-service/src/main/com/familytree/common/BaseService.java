package main.com.familytree.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseService {
		@javax.ws.rs.core.Context
		protected HttpServletRequest request;

		@javax.ws.rs.core.Context
		protected HttpServletResponse response; 
}
