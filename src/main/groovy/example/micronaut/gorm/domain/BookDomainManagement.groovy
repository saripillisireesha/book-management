package example.micronaut.gorm.domain

import grails.gorm.annotation.Entity

import java.time.LocalDate

@Entity
class BookDomainManagement {
    Long id
    String title
    int price
    LocalDate pubDate
    static Mapping={
        id generator:'increment'
    }
    static constraints={
        title blank:false, nullable:false


    }

}
