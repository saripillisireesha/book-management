package example.micronaut.gorm.domain

import grails.gorm.annotation.Entity
import groovy.transform.ToString

@Entity
class LineItemsDomainManagement {
    static belongsTo = [order:OrderDomainManagement, book:BookDomainManagement]
}
