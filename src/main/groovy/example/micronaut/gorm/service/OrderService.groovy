package example.micronaut.gorm.service

import example.micronaut.gorm.domain.BookDomainManagement
import example.micronaut.gorm.domain.LineItemsDomainManagement
import example.micronaut.gorm.domain.OrderDomainManagement
import example.micronaut.gorm.domain.UserDomainManagement
import example.micronaut.gorm.exceptionHandlers.BookNotFoundException
import example.micronaut.gorm.exceptionHandlers.UserNotFoundException
import example.micronaut.gorm.model.OrderModelManagement
import grails.gorm.transactions.Transactional
import org.hibernate.criterion.Order

import javax.inject.Singleton
import javax.sound.sampled.Line

@Singleton
class OrderService {
    @Transactional
    def saveOrders(OrderModelManagement orderModelManagement){
        OrderDomainManagement orderDomainManagement=new OrderDomainManagement()
        orderDomainManagement.user= UserDomainManagement.get(orderModelManagement.userId)
        orderDomainManagement.date=orderModelManagement.date
        orderDomainManagement.save()

        orderModelManagement.bookIds.each{bookId->
            LineItemsDomainManagement lineItemsDomainManagement=new LineItemsDomainManagement()
            lineItemsDomainManagement.order=orderDomainManagement
            lineItemsDomainManagement.book= BookDomainManagement.get(bookId)
            lineItemsDomainManagement.save()
        }
        return "Your order id is:${orderDomainManagement.id}"
    }
    @Transactional
    def GetOrdersByID(Long orderId){
        OrderDomainManagement orderDomainManagement=OrderDomainManagement.findById(orderId)
        if(!orderDomainManagement) {
            return "order not found"
        }
        else{
        return ConvertEntity(orderDomainManagement)
            }
    }
    @Transactional
    def getUserById(Long userId) {
        def user = UserDomainManagement.get(userId)
        if (!user) {
            return "User not found"
        }
        def orders = OrderDomainManagement.findAllByUser(user)
        def orderModels=[]
        orders.each{ order->
           orderModels << ConvertEntity(order)
        }
        return orderModels
    }
    @Transactional
    def getAllData(){
        def orders=OrderDomainManagement.findAll()
        def orderModels=[]
        orders.each{ order->
            orderModels << ConvertEntity(order)
            }
        return orderModels
    }
    @Transactional
    def updateOrder(Long orderId, OrderModelManagement updatedOrderModel) {
        def order = OrderDomainManagement.findById(orderId)
        if (order) {
            // Explicitly delete existing line items
            LineItemsDomainManagement.findAllByOrder(order).each { it.delete(flush: true) }

            // Add new line items based on the updated book IDs
            updatedOrderModel.bookIds.each { bookId ->
                BookDomainManagement bookDomainManagement = BookDomainManagement.findById(bookId)
                if (bookDomainManagement) {
                    LineItemsDomainManagement lineItemsDomainManagement = new LineItemsDomainManagement(order: order, book: bookDomainManagement)
                    order.addToLineitems(lineItemsDomainManagement)
                } else {
                    throw new IllegalArgumentException("Book with ID ${bookId} not found")
                }
            }
            order=order.save(flush: true)
            return ConvertEntity(order)
        } else {
            throw new UserNotFoundException("Order Not Found")
        }
    }
    @Transactional
    def DeleteById(Long orderId){
        OrderDomainManagement orderDomainManagement=OrderDomainManagement.findById(orderId)
        if(orderDomainManagement){
            orderDomainManagement.delete()
        }
        else{
            return false
        }
    }

    static OrderModelManagement ConvertEntity(OrderDomainManagement order) {
            OrderModelManagement orderModelManagement1 = new OrderModelManagement()
            orderModelManagement1.orderId = order.id
            orderModelManagement1.date = order.date
        orderModelManagement1.userId=order.userId
            orderModelManagement1.bookIds = []
            order.lineitems.each { lineItem ->
                orderModelManagement1.bookIds << (lineItem.book.id as Integer)
            }
        return orderModelManagement1
    }
}

