package example.micronaut.gorm.controller

import example.micronaut.gorm.exceptionHandlers.BookNotFoundException
import example.micronaut.gorm.exceptionHandlers.ErrorResponse
import example.micronaut.gorm.exceptionHandlers.UserNotFoundException
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Error

@Controller
class GlobalErrorController {
    @Error(global = true, exception=BookNotFoundException)
    HttpResponse<ErrorResponse> HandlerBooknotFoundException(BookNotFoundException ex){
        ErrorResponse errorResponse=new ErrorResponse(HttpStatus.NOT_FOUND.code, "Not Found", ex.message)
        return HttpResponse.status(HttpStatus.NOT_FOUND).body(errorResponse)
    }
    @Error(global = true, exception=UserNotFoundException)
    HttpResponse<ErrorResponse> HandlerUserNotFoundException(UserNotFoundException ex){
        ErrorResponse errorResponse=new ErrorResponse(HttpStatus.NOT_FOUND.code,"Not Found", ex.message)
        return HttpResponse.status(HttpStatus.NOT_FOUND).body(errorResponse)
    }

}
