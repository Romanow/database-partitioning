package ru.romanow.partitioning.domain

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.SequenceGenerator
import javax.persistence.Table

@Entity
@Table
data class User(

    @Id
    @SequenceGenerator(name = "seq_users_generator")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Int? = null,

    @Column(name = "login", nullable = false)
    var login: String? = null,

    @Column(name = "first_name", nullable = false)
    var firstName: String? = null,

    @Column(name = "last_name", nullable = false)
    var lastName: String? = null,

    @CreatedDate
    @Column(name = "created_date", nullable = false)
    var createdDate: LocalDateTime? = null,

    @LastModifiedDate
    @Column(name = "last_modified_date", nullable = false)
    var lastModifiedDate: LocalDateTime? = null,
)