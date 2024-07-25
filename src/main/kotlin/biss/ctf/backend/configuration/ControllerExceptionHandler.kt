package biss.ctf.backend.configuration

import biss.ctf.backend.exceptions.UnauthorizedException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ControllerExceptionHandler {
    @ExceptionHandler(UnauthorizedException::class)
    fun unauthorizedHandler(exception: UnauthorizedException) =
        ResponseEntity(exception.message ?: "User is unauthorized!", HttpStatus.UNAUTHORIZED)

    @ExceptionHandler(NullPointerException::class)
    fun nullPointerHandler(e: NullPointerException) = ResponseEntity(e.message, HttpStatus.NOT_FOUND)

    @ExceptionHandler(Exception::class)
    fun exceptionHandler(e: Exception) = ResponseEntity(e.message, HttpStatus.UNAUTHORIZED)

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNoSuchElementException(e: NoSuchElementException) = ResponseEntity(e.message, HttpStatus.NOT_FOUND)

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(e: IllegalArgumentException) = ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
}