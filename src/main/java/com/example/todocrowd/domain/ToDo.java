package com.example.todocrowd.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class ToDo {
    @Id
    @GeneratedValue
    private Long id;
    private boolean isDone;
    private String text;
}
