package ru.romanow.partitioning.domain

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener::class)
data class User(

    @Id
    @SequenceGenerator(name = "generator", sequenceName = "seq_users_generator", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    var id: Int? = null,

    @Column(name = "login", nullable = false)
    var login: String? = null,

    @CreatedDate
    @Column(name = "created_date", nullable = false)
    var createdDate: LocalDateTime? = null,

    @LastModifiedDate
    @Column(name = "last_modified_date", nullable = false)
    var lastModifiedDate: LocalDateTime? = null,
)