package example.micronaut.gorm.exceptionHandlers

import example.micronaut.gorm.model.UserModelManagement

class UserNotFoundException extends RuntimeException{
    UserNotFoundException(String message){
        super(message)
    }
}
