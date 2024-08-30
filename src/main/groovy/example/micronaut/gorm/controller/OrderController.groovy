package example.micronaut.gorm.controller

import example.micronaut.gorm.model.OrderModelManagement
import example.micronaut.gorm.service.OrderService
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Put

import javax.inject.Inject

@Controller("/orders")
class OrderController {
    @Inject
    OrderService orderService
    @Post
    def addition(@Body OrderModelManagement orderModelManagement){
        return orderService.saveOrders(orderModelManagement)
    }
    @Get("/id/{orderId}")
    def getOrders(@PathVariable Long orderId){
        return orderService.GetOrdersByID(orderId)
    }
    @Get("/userId/{userId}")
    def getUsrId(@PathVariable Long userId){
        return orderService.getUserById(userId)
    }
    @Get
    def getAllData(){
        return orderService.getAllData()
    }
    @Put("/update/{orderId}")
    def updateOrders(@PathVariable Long orderId, @Body OrderModelManagement updateModel){
        return orderService.updateOrder(orderId,updateModel)
    }
    @Get("/{orderId}")
    def delete(@PathVariable Long orderId){
        orderService.DeleteById(orderId)
        return "Deleted Successfully"
    }
}
