package example.micronaut.gorm.exceptionHandlers

class BookNotFoundException extends RuntimeException {
    BookNotFoundException(String message){
        super(message)
    }


}
