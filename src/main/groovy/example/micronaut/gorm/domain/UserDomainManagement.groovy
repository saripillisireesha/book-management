package example.micronaut.gorm.domain

import grails.gorm.annotation.Entity

@Entity
class UserDomainManagement {
    Long id
    String firstName
    String lastName
    String email
    String password
    Long mobile
    static  mapping={
        id Generator:'increment'
    }
    static constraints={
        firstName nullable:false,blank:false
        lastName nullable:false
        email nullable : false,blank:false, unique: true
        password nullable:false, blank:false, unique: true
        mobile nullable: false
    }

}

