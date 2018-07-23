package main.com.familytree.common.exceptions;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;



@Provider
public class InternalServerErrorExceptionMapper implements ExceptionMapper<InternalServerErrorException> {

	@Context
	private HttpServletRequest request;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ws.rs.ext.ExceptionMapper#toResponse(java.lang.Throwable)
	 */
	public Response toResponse(InternalServerErrorException e) {
		// HTTP Error code 500
		// String message =
		// ErrorEnum.GENERIC_API_ERROR.getErrorMessageLocalized(this.request.getLocale());
		// String json =
		// ExceptionJsonMsg.getGenericExceptionMsg(ErrorEnum.GENERIC_API_ERROR.getErrorCode(),
		// message,
		// e.getMessage());
		String message = "Internal Server Error";
		String json = "Internal Server Error";
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
			.entity(json)
			.type(MediaType.APPLICATION_JSON)
			.build();
	}
}
