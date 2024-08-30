package example.micronaut.gorm.service

import example.micronaut.gorm.domain.BookDomainManagement
import example.micronaut.gorm.exceptionHandlers.BookNotFoundException
import example.micronaut.gorm.model.BookModelManagement
import grails.gorm.transactions.Transactional
import org.hibernate.SessionFactory

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookService {
    @Inject
    SessionFactory sessionFactory
    def highPrice="SELECT * FROM BOOK_DOMAIN_MANAGEMENT WHERE PRICE>=:PRICE"
    @Transactional
    def addBooks(BookModelManagement bookModelManagement){
        BookDomainManagement bookDomainManagement=new BookDomainManagement()
        bookDomainManagement.id=bookModelManagement.id
        bookDomainManagement.title=bookModelManagement.title
        bookDomainManagement.price=bookModelManagement.price
        bookDomainManagement.pubDate=bookModelManagement.pubDate
        bookDomainManagement.save()
        return bookDomainManagement
    }
    @Transactional
    def getAllBooks(){
        List<BookDomainManagement> bookDomainManagementList= BookDomainManagement.list()
        return bookDomainManagementList

        }

    @Transactional
    def getBooksById(Long id){
        BookDomainManagement bookDomainManagement=BookDomainManagement.get(id)
        if(bookDomainManagement) {
            return bookDomainManagement
        }else{
            throw new BookNotFoundException("Book with id ${id} is not available")
        }
    }
    @Transactional
    def deleteById(Long id){
        BookDomainManagement bookDomainManagement= BookDomainManagement.findById(id)
        if(bookDomainManagement){
            bookDomainManagement.delete()
            return"Book with Id ${id} is deleted successfully"
        }
        else{
            return false
        }
    }
    @Transactional
    def updateBook(Long id,BookModelManagement bookModelManagement){
        BookDomainManagement bookDomainManagement=BookDomainManagement.get(id)
        if(bookDomainManagement) {
            // bookDomainManagement.id=bookModelManagement.id
            bookDomainManagement.title = bookModelManagement.title
            bookDomainManagement.price = bookModelManagement.price
            bookDomainManagement.pubDate = bookModelManagement.pubDate
            bookDomainManagement.save()
            return bookDomainManagement
        }
        else{
            return " Book with id ${id} is not found"
        }
    }
    @Transactional
    def getMorePriceBooks(int price){
        def session=sessionFactory.getCurrentSession()
        def morePriceBooks=session.createSQLQuery(highPrice).setParameter("PRICE",price).list()
        return morePriceBooks
    }
}
