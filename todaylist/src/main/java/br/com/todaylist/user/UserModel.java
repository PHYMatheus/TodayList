package br.com.todaylist.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "tb_Users") // <- informs the database it is a table
@Data // <- import Getters e Setters
public class UserModel {

    @Id // <- informs the database that it is a primary key
    @GeneratedValue(generator = "UUID") //<- Generates an ID in the format
    private UUID id;
    @Column(unique = true)//<- Only single users are accepted
    private String username;
    private String password;
    private String name;
    @Column(name = "date create")
    @CreationTimestamp // <- the database saves data when it is created
    private LocalDateTime createdAt;
}
