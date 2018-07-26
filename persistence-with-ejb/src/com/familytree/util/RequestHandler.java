package com.familytree.util;

import java.io.BufferedReader;

import javax.servlet.http.HttpServletRequest;

public final class RequestHandler {
	public static final String handleRequest(HttpServletRequest request){
		StringBuffer jb = new StringBuffer();
		String line = null;
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null)
				jb.append(line);
			return jb.toString();
		} catch (Exception e) { return null;}
	}
}
