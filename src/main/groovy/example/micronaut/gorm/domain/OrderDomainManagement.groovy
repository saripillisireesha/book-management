package example.micronaut.gorm.domain

import grails.gorm.annotation.Entity
import groovy.transform.ToString

import java.time.LocalDate

@Entity
class OrderDomainManagement {

    LocalDate date
    static belongsTo = [user:UserDomainManagement]

    static hasMany = [lineitems:LineItemsDomainManagement]
}
