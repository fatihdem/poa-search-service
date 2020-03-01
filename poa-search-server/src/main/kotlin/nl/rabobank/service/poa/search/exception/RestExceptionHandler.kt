package nl.rabobank.service.poa.search.exception

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class RestExceptionHandler: ResponseEntityExceptionHandler() {
    private val LOG = LoggerFactory.getLogger(RestExceptionHandler::class.java)

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<Response> {
        return createErrorResponse(500, e.message, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(InternalSearchException::class)
    fun handleInternalSearchException(e: InternalSearchException): ResponseEntity<Response> {
        return createErrorResponse(e.errorCode, e.message, determineStatus(e.errorCode))
    }

    private fun determineStatus(errorCode: Int): HttpStatus {
        return when (errorCode) {
            in 400000..400999 -> HttpStatus.BAD_REQUEST
            in 401000..401999 -> HttpStatus.UNAUTHORIZED
            in 404000..404999 -> HttpStatus.NOT_FOUND
            else -> HttpStatus.INTERNAL_SERVER_ERROR
        }

    }

    private fun createErrorResponse(errorCode: Int, errorMessage: String?, status: HttpStatus ): ResponseEntity<Response> {
        LOG.error("[$errorCode] $errorMessage")
        return ResponseEntity(Response(errorMessage?:"Something went wrong.", errorCode), status)
    }
}

data class Response(val errorMessage: String, val errorCode: Int)