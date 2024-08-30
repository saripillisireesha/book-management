package example.micronaut.gorm.controller

import example.micronaut.gorm.model.BookModelManagement

import example.micronaut.gorm.service.BookService
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Put

import javax.inject.Inject

@Controller("/books")
class BookController {
    @Inject
    BookService bookService
    @Post
    def add(@Body BookModelManagement bookModelManagement){
        bookService.addBooks(bookModelManagement)
        return bookModelManagement
    }
    @Get
    def get(){
        bookService.getAllBooks()
    }
    @Get("/id/{id}")
    def getById(@PathVariable Long id){
       return  bookService.getBooksById(id)
    }
    @Delete("/delete/{id}")
    def delete(@PathVariable Long id){
        bookService.deleteById(id)
        return "deleted Successfully"
    }
    @Put("/{id}")
    def update(@PathVariable Long id, @Body BookModelManagement bookModelManagement){
        bookService.updateBook(id,bookModelManagement)
        return "updated successfully"
    }
    @Get("/highprice/{price}")
    def getMorePriceBooks(@PathVariable int price){
        return bookService.getMorePriceBooks(price)
    }
}
