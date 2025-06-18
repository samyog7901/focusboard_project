package org.focusboard.model;

import jakarta.persistence.*;
import lombok.Data;


import java.util.List;

@Entity
@Data
public class Project {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    private UserModel owner;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<TaskModel> tasks;
}
