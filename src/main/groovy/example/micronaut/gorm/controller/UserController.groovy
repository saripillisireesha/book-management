package example.micronaut.gorm.controller

import example.micronaut.gorm.model.UserModelManagement
import example.micronaut.gorm.service.UserService
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Put
import io.micronaut.http.annotation.Status

import javax.inject.Inject

@Controller("/users")
class UserController {
    @Inject
    UserService userService
    @Post
    @Status(HttpStatus.CREATED)
    def saveUsers(@Body UserModelManagement userModelManagement){
        try {
            def user = userService.saveUsers(userModelManagement)
            println user
            if (user) {
                return HttpResponse.created(user)
            } else {
                return HttpResponse.badRequest("Failed to add user")
            }
        }
        catch(Exception e){
            return HttpResponse.serverError("An error occurred: ${e.message}")
        }
    }
    @Get
    def getAllUsers(){
        return userService.getAllUsers()
    }
    @Get("/id/{id}")
    def getById(@PathVariable Long id){
        return userService.getById(id)
    }
    @Put("/update/{id}")
    def update(@PathVariable Long id, @Body UserModelManagement userModelManagement){
        userService.updateUsers(id, userModelManagement)
        return "updated Successfully"
    }
    @Delete("/{id}")
    def deleteById(@PathVariable Long id){
        userService.deleteUser(id)
        return "Deleted Successfully"
    }
    @Post("/login")
    def login( @Body UserModelManagement userModelManagement){
        return userService.loginUser(userModelManagement.email,userModelManagement.password)
    }
    @Get("/startingname")
    def starts(){
        return userService.getNameStartsWith()
    }
}
