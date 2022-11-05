package com.example.todocrowd.repo;

import com.example.todocrowd.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

public interface TodoRepo extends JpaRepository<Todo, Long> {
    @Transactional
    void deleteByDone(boolean done);

    int countByDone(boolean done);

    List<Todo> findByOrderByDueAsc();

    List<Todo> findByDoneFalseAndDueNotNull();
}
