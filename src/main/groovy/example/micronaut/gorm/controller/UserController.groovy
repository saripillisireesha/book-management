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
    @Status(HttpStatus.CREATED)

    HttpResponse<UserModelManagement> update(@PathVariable Long id, @Body UserModelManagement userModelManagement) {
        try {
            def user = userService.updateUsers(id, userModelManagement) // Updated method name
            if (user) {
                return HttpResponse.ok(user)
            } else {
                return HttpResponse.badRequest("Failed to update user")
            }
        } catch (Exception e) {
            return HttpResponse.serverError("An error occurred: ${e.message}")
        }
    }

    @Delete("/{id}")
    @Status(HttpStatus.NO_CONTENT)
    def deleteUser(@PathVariable Long id) {
        try {
            // Attempt to delete the user
            boolean isDeleted = userService.deleteUser(id)

            if (isDeleted){
                return HttpResponse.noContent()  // No content typically means success, no body included
            } else {
                // Return 400 Bad Request if deletion failed
                return HttpResponse.notFound("Failed to delete user")
            }
        } catch (Exception e) {
            println "Exception occurred: ${e.message}"
            return HttpResponse.serverError("An error occurred: ${e.message}")
        }
    }


    @Post("/login")
    @Status(HttpStatus.OK)
    def login(@Body UserModelManagement userModelManagement){
        try {
            def user = userService.loginUser(userModelManagement.email,userModelManagement.password)
            println user
            if (user) {
                return HttpResponse.ok(user)
            } else {
                return HttpResponse.badRequest("Failed to add user")
            }
        }
        catch(Exception e){
            return HttpResponse.serverError("An error occurred: ${e.message}")
        }
    }

    @Get("/startingname")
    def starts(){
        return userService.getNameStartsWith()
    }
}
