package org.focusboard.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
public class TaskModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private TaskStatus status = TaskStatus.NOT_STARTED;

    private String createdBy;
    private LocalDate startDate;
    private LocalDate endDate;
    private String priority;
    private String category;

    @ManyToOne
    private Project project;
}
