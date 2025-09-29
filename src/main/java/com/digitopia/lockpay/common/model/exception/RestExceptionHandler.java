package com.digitopia.lockpay.common.model.exception;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolation;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.digitopia.lockpay.common.enums.GeneralStatusEnum;
import com.digitopia.lockpay.common.enums.HttpHeadersEnum;
import com.digitopia.lockpay.common.model.response.ResponseHeader;
import com.digitopia.lockpay.common.model.response.ResponseMessage;
import com.digitopia.lockpay.common.model.response.ResponseStatus;
import com.digitopia.lockpay.common.model.response.SubError;
import com.fasterxml.jackson.databind.JsonMappingException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * Controller Advisor to catch exceptions through the application Advisor handle
 * the following validations 
 * 1- Malformed JSON request 
 * 2- Missing Servlet Request Parameter Exception 
 * 3- Method and request Argument Not Valid Exception for validation errors 
 * 4- Constraint Violation Exception produced from hibernate 
 * 5- Application Exception which is customized by the developer
 * 6- General Exception.
 *
 *
 */

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@Log4j2
@AllArgsConstructor
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

//	private AuditAsyncService auditAsyncService;

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers,
			HttpStatusCode status, WebRequest request) {
		return buildResponseEntity(getResponseMessagForGeneralException(GeneralStatusEnum.GENERAL_ERROR,
				request.getHeader(HttpHeadersEnum.SESSION_ID.getCode())));
	}

	/**
	 * Handle HttpMessageNotReadableException. Happens when request JSON is
	 * malformed.
	 *
	 * @param ex      HttpMessageNotReadableException
	 * @param headers HttpHeaders
	 * @param status  HttpStatus
	 * @param request WebRequest
	 * @return the ResponseMessage object
	 */
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		try {
			return buildResponseEntity(getResponseMessageFromMessageNotReadableException(ex,
					request.getHeader(HttpHeadersEnum.SESSION_ID.getCode())));
		} catch (Exception e) {
			return buildResponseEntity(getResponseMessagForGeneralException(GeneralStatusEnum.GENERAL_ERROR,
					request.getHeader(HttpHeadersEnum.SESSION_ID.getCode())));
		}
	}
	
	/**
	 * Handle MissingServletRequestParameterException. Triggered when a 'required'
	 * request parameter is missing.
	 *
	 * @param ex      MissingServletRequestParameterException
	 * @param headers HttpHeaders
	 * @param status  HttpStatus
	 * @param request WebRequest
	 * @return the response message object
	 */
	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		return buildResponseEntity(getResponseMessagForGeneralException(GeneralStatusEnum.MISSING_PARAM,
				request.getHeader(HttpHeadersEnum.SESSION_ID.getCode())));
	}

	/**
	 * Handle MethodArgumentNotValidException. Triggered when an object fails @Valid
	 * validation.
	 *
	 * @param ex      the MethodArgumentNotValidException that is thrown when @Valid
	 *                validation fails
	 * @param headers HttpHeaders
	 * @param status  HttpStatus
	 * @param request WebRequest
	 * @return the response message object
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		return buildResponseEntity(getResponseMessageFromMethodNonValidtExpection(ex,
				request.getHeader(HttpHeadersEnum.SESSION_ID.getCode())));
	}

	@ExceptionHandler(BindException.class)
	protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status,
			WebRequest request) {
		// construct MethodArgumentNotValidException from the BindException with same
		// BindingResult
		MethodArgumentNotValidException methodArgumentNotValidException = new MethodArgumentNotValidException(null,
				ex.getBindingResult());
		return buildResponseEntity(getResponseMessageFromMethodNonValidtExpection(methodArgumentNotValidException,
				request.getHeader(HttpHeadersEnum.SESSION_ID.getCode())));
	}

	/**
	 * Handles javax.validation.ConstraintViolationException. Thrown when @Validated
	 * fails.
	 *
	 * @param ex      the ConstraintViolationException
	 * @param request WebRequest
	 * @return the ResponseMessage object
	 */
	@ExceptionHandler(javax.validation.ConstraintViolationException.class)
	protected ResponseEntity<Object> handleConstraintViolation(javax.validation.ConstraintViolationException ex,
			WebRequest request) {
		return buildResponseEntity(
				getResponseMessageFromConstraintExpection(ex, request.getHeader(HttpHeadersEnum.SESSION_ID.getCode())));
	}

	/**
	 * Handles Application Exception. fails.
	 *
	 * @param ex      the ApplicationException
	 * @param request WebRequest
	 * @return the HTTP Response object
	 */
	@ExceptionHandler({ ApplicationException.class })
	protected ResponseEntity<Object> handleApplicationExceptions(ApplicationException ex, WebRequest request) {
		return buildResponseEntity(
				getResponseMessageFromAppExpection(ex, request.getHeader(HttpHeadersEnum.SESSION_ID.getCode())));
	}

	/**
	 * Handles General Exception. fails.
	 *
	 * @param ex      the ApplicationException
	 * @param request WebRequest
	 * @return the HTTP Response object
	 */

	@ExceptionHandler({ Exception.class, RuntimeException.class, NullPointerException.class })
	protected ResponseEntity<Object> handleGeneralException(Exception ex, HttpServletRequest request) {
		try {
			auditException(ex, request);
		} catch (IOException e) {
			System.out.println("E1");
			log.error("Application error in: [" + e.getClass().getName() + "]", e);
		}
		
		return buildResponseEntity(getResponseMessagForGeneralException(GeneralStatusEnum.GENERAL_ERROR,
				request.getHeader(HttpHeadersEnum.SESSION_ID.getCode())));
	}

	/**
	 * Handle building Response object.
	 *
	 * @param ex        the ApplicationException
	 * @param sessionId represent request ID in message http header of the request
	 * @return the ResponseMessage object
	 */
	ResponseMessage<?> getResponseMessageFromAppExpection(ApplicationException ex, String sessionId) {
		ResponseMessage<?> resp = new ResponseMessage<>();
		ResponseHeader respHeader = new ResponseHeader();
		respHeader.setSessionId(sessionId);
		ResponseStatus respStatus = new ResponseStatus();
		respStatus.setCode(ex.getErrorCode());
		respStatus.setDetails(ex.getDetails());
		respStatus.setHttpStatusCode(HttpStatus.valueOf(Integer.parseInt(ex.getHttpErrorCode().getHttpStatusCode())));
		respHeader.setStatus(respStatus);
		resp.setHeader(respHeader);
		return resp;
	}

	/**
	 * Handle building Response object.
	 *
	 * @param ex        the ConstraintViolationException
	 * @param sessionId represent request ID in message http header of the request
	 * @return the ResponseMessage object
	 */
	ResponseMessage<?> getResponseMessageFromConstraintExpection(javax.validation.ConstraintViolationException ex,
			String sessionId) {

		ResponseMessage<?> resp = new ResponseMessage<>();

		ResponseHeader respHeader = new ResponseHeader();
		respHeader.setSessionId(sessionId);

		ResponseStatus respStatus = new ResponseStatus();

		respStatus.setCode(GeneralStatusEnum.CONSTRAINT_VIOLATE.getErrorCode());
		respStatus.setHttpStatusCode(HttpStatus.valueOf(
				Integer.parseInt(GeneralStatusEnum.CONSTRAINT_VIOLATE.getHttpErrorCode().getHttpStatusCode())));

		if (ex.getConstraintViolations() != null) {
			ArrayList<SubError> subErrors = new ArrayList<>();
			for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
				SubError subError = new SubError();
				subError.setDetails(violation.getMessage());
				subError.setField(violation.getPropertyPath().toString());
				subErrors.add(subError);
			}
			respStatus.setSubErrors(subErrors);
		}

		respHeader.setStatus(respStatus);

		resp.setHeader(respHeader);
		return resp;
	}

	
	/**
	 * @param ex
	 */
	private ResponseMessage<?> getResponseMessageFromMessageNotReadableException(HttpMessageNotReadableException ex,
			String sessionId) {

		ResponseMessage<?> resp = new ResponseMessage<>();

		ResponseHeader respHeader = new ResponseHeader();
		respHeader.setSessionId(sessionId);

		ResponseStatus respStatus = new ResponseStatus();
		respStatus.setSubErrors(new ArrayList<>());

		respStatus.setCode(GeneralStatusEnum.CONSTRAINT_VIOLATE.getErrorCode());
		respStatus.setHttpStatusCode(HttpStatus.valueOf(
				Integer.parseInt(GeneralStatusEnum.CONSTRAINT_VIOLATE.getHttpErrorCode().getHttpStatusCode())));

		// prepare subError
		String field = null;
		String errorMessage = null;

		Throwable mostSpecificCause = ex.getMostSpecificCause();

		if (mostSpecificCause instanceof JsonMappingException) {
			JsonMappingException jsonMappingException = (JsonMappingException) mostSpecificCause;
			errorMessage = jsonMappingException.getMessage();
			field = extractJsonPath(jsonMappingException.getPath());

		} else if (mostSpecificCause instanceof DateTimeParseException) {
			field = "Invalid date/time format ";
			errorMessage = mostSpecificCause.getMessage();

		} else {
			// Add extra handling in the future if Needed
			throw ex;
		}

		// if we could locate the error
		if (field != null || errorMessage != null) {
			SubError subErorr = new SubError();
			subErorr.setField(field);
			subErorr.setDetails(errorMessage);
			respStatus.getSubErrors().add(subErorr);
		}

		respHeader.setStatus(respStatus);

		resp.setHeader(respHeader);
		return resp;
	}

	private String extractJsonPath(List<JsonMappingException.Reference> references) {
		List<String> pathItems = new ArrayList<>();
		for (JsonMappingException.Reference ref : references) {
			if (ref.getFieldName() != null) {
				pathItems.add(ref.getFieldName());
			} else if (ref.getIndex() >= 0) {
				pathItems.add("[" + ref.getIndex() + "]");
			}
		}
		return String.join(".", pathItems);
	}

	
	
	/**
	 * Handle building Response object.
	 *
	 * @param ex        the MethodArgumentNotValidException
	 * @param requestId represent request ID in message http header of the request
	 * @return the ResponseMessage object
	 */
	ResponseMessage<?> getResponseMessageFromMethodNonValidtExpection(MethodArgumentNotValidException ex,
			String requestId) {

		ResponseMessage<?> resp = new ResponseMessage<>();

		ResponseHeader respHeader = new ResponseHeader();
		respHeader.setSessionId(requestId);

		ResponseStatus respStatus = new ResponseStatus();

		respStatus.setCode(GeneralStatusEnum.CONSTRAINT_VIOLATE.getErrorCode());
		respStatus.setHttpStatusCode(HttpStatus.valueOf(
				Integer.parseInt(GeneralStatusEnum.CONSTRAINT_VIOLATE.getHttpErrorCode().getHttpStatusCode())));

		if (ex.getBindingResult() != null && ex.getBindingResult().getFieldErrors() != null) {

			ArrayList<SubError> subErorrs = new ArrayList<>();
			for (FieldError f : ex.getBindingResult().getFieldErrors()) {
				SubError subErorr = new SubError();
				subErorr.setField(f.getField());
				subErorr.setDetails(f.getDefaultMessage());
				subErorrs.add(subErorr);
			}
			for (ObjectError f : ex.getBindingResult().getGlobalErrors()) {
				SubError subErorr = new SubError();
				subErorr.setField(f.getObjectName());
				subErorr.setDetails(f.getDefaultMessage());
				subErorrs.add(subErorr);
			}
			respStatus.setSubErrors(subErorrs);
		}
		respHeader.setStatus(respStatus);

		resp.setHeader(respHeader);
		return resp;
	}

	/**
	 * Handle building Response object.
	 *
	 * @param e         Errors Enum
	 * @param sessionId represent session ID in message http header of the request
	 * @return the ResponseMessage object
	 */
	ResponseMessage<?> getResponseMessagForGeneralException(BasicErrorsEnum e, String sessionId) {

		ResponseMessage<?> resp = new ResponseMessage<>();

		ResponseHeader respHeader = new ResponseHeader();

		respHeader.setSessionId(sessionId);

		ResponseStatus respStatus = new ResponseStatus();

		respStatus.setCode(e.getErrorCode());
		respStatus.setHttpStatusCode(HttpStatus.valueOf(Integer.parseInt(e.getHttpErrorCode().getHttpStatusCode())));
		respStatus.setDetails(e.getDescription());
		respHeader.setStatus(respStatus);

		resp.setHeader(respHeader);
		return resp;
	}

	/**
	 * Handle building Response object.
	 *
	 * @param resp Response
	 * @return the ResponseMessage object
	 */
	private ResponseEntity<Object> buildResponseEntity(ResponseMessage<?> resp) {
		return new ResponseEntity<>(resp, resp.getHeader().getStatus().getHttpStatusCode());
	}

	private void auditException(Exception ex, HttpServletRequest request) throws IOException {

		StringWriter sw = new StringWriter();
		ex.printStackTrace(new PrintWriter(sw));
		String fullStackTrace = sw.toString();

		//TODO: audit

	}

}