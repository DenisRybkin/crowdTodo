package com.example.todocrowd.utils;

import com.example.todocrowd.domain.Todo;

public interface TodoChangeListner {
    void onChange(Todo todo);

    void onChange();
}
