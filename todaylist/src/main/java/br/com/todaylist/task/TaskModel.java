package br.com.todaylist.task;


import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.UUID;

@Entity(name = "tb_task")
@Data
public class TaskModel {

    @Id
  @GeneratedValue(generator = "UUID")
  private UUID id;
  private String description;
  @Column(length = 50)
  private String title;
  private LocalDate startAt;
  private LocalDate endAt;
  @Column(length = 50)
  private String priority;

  @CreationTimestamp
  private String createdAt;

  private UUID idUser;

}
