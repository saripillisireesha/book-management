package example.micronaut.gorm.service

import example.micronaut.gorm.domain.UserDomainManagement
import example.micronaut.gorm.exceptionHandlers.UserNotFoundException
import example.micronaut.gorm.model.UserModelManagement
import grails.gorm.transactions.Transactional
import org.hibernate.SessionFactory

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserService {
    @Inject
    SessionFactory sessionFactory
    def startnames="SELECT * FROM USER_DOMAIN_MANAGEMENT WHERE FIRST_NAME LIKE 'S%' "
    @Transactional
    def saveUsers(UserModelManagement userModelManagement){
        UserDomainManagement userDomainManagement=new UserDomainManagement()
        userDomainManagement.id=userModelManagement.id
        userDomainManagement.firstName=userModelManagement.firstName
        userDomainManagement.lastName=userModelManagement.lastName
        userDomainManagement.email=userModelManagement.email
        userDomainManagement.password=userModelManagement.password
        userDomainManagement.mobile=userModelManagement.mobile
        userDomainManagement.save()
        return userDomainManagement
    }
    @Transactional
    def getAllUsers(){
        List<UserDomainManagement> userDomainManagementList=UserDomainManagement.findAll()
        return userDomainManagementList
    }
    @Transactional
    def getById(Long id){
        UserDomainManagement userDomainManagement=UserDomainManagement.get(id)
        if(userDomainManagement) {
            return userDomainManagement
        }
        else{
            throw new UserNotFoundException("User with id ${id} is not found")
        }
    }
    @Transactional
    def updateUsers(Long id,UserModelManagement userModelManagement){
        UserDomainManagement userDomainManagement=UserDomainManagement.get(id)
        if(userDomainManagement){
            userDomainManagement.firstName=userModelManagement.firstName
            userDomainManagement.lastName=userModelManagement.lastName
            userDomainManagement.email=userModelManagement.email
            userDomainManagement.password=userModelManagement.password
            userDomainManagement.mobile=userModelManagement.mobile
            return userDomainManagement
        }
    }
    @Transactional
   def deleteUser(Long id){
        UserDomainManagement userDomainManagement=UserDomainManagement.findById(id)
        if(userDomainManagement){
            userDomainManagement.delete()
            return true
        }
        else{
            return false
        }
    }
    @Transactional
    def loginUser(String email, String password){
        UserDomainManagement userDomainManagement=UserDomainManagement.findByEmailAndPassword(email,password)
        if(userDomainManagement){
            return userDomainManagement
        }
        else
            return "Invalid Credentails"
    }
    @Transactional
    def getNameStartsWith(){
        def session=sessionFactory.getCurrentSession()
        def startingName=session.createSQLQuery(startnames)
        def results=startingName.list()
        return results
    }
}
