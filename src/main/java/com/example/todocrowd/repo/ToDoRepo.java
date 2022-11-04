package com.example.todocrowd.repo;

import com.example.todocrowd.domain.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ToDoRepo extends JpaRepository<ToDo, Long> {
    @Query("from ToDo e " + "where concat(e.text, ' ') " + " like concat('%', :text, '%') ")
    List<ToDo> findByText(@Param("text") String text);
}
