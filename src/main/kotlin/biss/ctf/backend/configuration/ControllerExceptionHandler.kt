package biss.ctf.backend.configuration

import biss.ctf.backend.exceptions.UnauthorizedException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ControllerExceptionHandler {
    @ExceptionHandler(UnauthorizedException::class)
    fun unauthorizedHandler(exception: UnauthorizedException): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body("User is unauthorized!!")
    }

    @ExceptionHandler(NullPointerException::class)
    fun nullPointerHandler(exception: NullPointerException): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(exception.message)
    }

    @ExceptionHandler(Exception::class)
    fun exceptionHandler(exception: Exception): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(exception.message)
    }
}