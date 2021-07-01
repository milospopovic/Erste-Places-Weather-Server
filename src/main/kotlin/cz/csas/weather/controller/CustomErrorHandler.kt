package cz.csas.weather.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.ServletWebRequest
import javax.validation.ConstraintViolationException

/**
 * Controller for handling custom exceptions and changes responses status codes and messages
 */
@ControllerAdvice
class CustomErrorHandler {
    /**
     * Handles ConstraintViolationException and set http status to 400
     */
    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolationException(e: ConstraintViolationException, webRequest: ServletWebRequest) {
        webRequest.response?.sendError(HttpStatus.BAD_REQUEST.value(), e.message)
    }

    /**
     * Handles IllegalArgumentException and set http status to 400
     */
    @ExceptionHandler(IllegalArgumentException::class)
    protected fun handleIllegalArgumentException(e: IllegalArgumentException, webRequest: ServletWebRequest) {
        webRequest.response?.sendError(HttpStatus.BAD_REQUEST.value(), e.message)
    }
}
