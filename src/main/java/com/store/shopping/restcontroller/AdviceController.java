package com.store.shopping.restcontroller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.store.shopping.exception.RecordDoesNotExistsException;
import com.store.shopping.exception.RecordExistsException;

/**
 * RestAPI Abstract Controller.
 * 
 * Responsible to configure general behaviors.
 * 
 * @author pergentino
 */

@RestControllerAdvice
public class AdviceController {
	
	@ExceptionHandler(RecordExistsException.class)
	public ResponseEntity<?> recordExistsExceptionHandle(RecordExistsException e) {
		return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(new ErrorResponse(e, e.getEntity()));
	}
	
	@ExceptionHandler(RecordDoesNotExistsException.class)
	public ResponseEntity<?> recordDoesNotExistsExceptionHandle(RecordDoesNotExistsException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(e, e.getId()));
	}
	
	
	/**
	 * A tiny class to organize response data as an object. 
	 * 
	 * @author pergentino
	 */
	class ErrorResponse {

		private String message;
		private Object data;

		ErrorResponse(Exception e, Object data) {
			this.data = data;
			this.message = e.getMessage();
		}
		
		/**
		 * @return the message
		 */
		public String getMessage() {
			return this.message;
		}

		/**
		 * @return the data
		 */
		public Object getData() {
			return data;
		}
		
	}

}