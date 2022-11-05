package com.example.todocrowd.domain;

import com.example.todocrowd.utils.TodoPriority;
import lombok.Data;


import javax.persistence.*;
import java.time.LocalDate;
@Data
@Entity
public class Todo {
    @Id
    @GeneratedValue
    private Long id;
    private String text;
    private LocalDate due;
    private boolean done;
    @Enumerated(EnumType.ORDINAL)
    private TodoPriority priority;

    public Todo() {
    }

    public Todo(LocalDate due, boolean isDone, TodoPriority priority) {
        this.due = due;
        this.done = isDone;
        this.priority = priority;
    }

    public Todo( String text, LocalDate due, TodoPriority priority) {
        this.due = due;
        this.text = text;
        this.priority = priority;
    }

    public void setPriority(TodoPriority priority) {
        this.priority = priority;
    }
}
