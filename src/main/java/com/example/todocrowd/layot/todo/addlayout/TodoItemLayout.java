package com.example.todocrowd.layot.todo.addlayout;

import com.example.todocrowd.domain.Todo;
import com.example.todocrowd.repo.TodoRepo;
import com.example.todocrowd.utils.TodoPriority;
import com.example.todocrowd.utils.TodoChangeListner;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.component.checkbox.Checkbox;

public class TodoItemLayout extends HorizontalLayout {

    private final Todo todo;
    private final TodoRepo todoRepo;
    private final Checkbox done;
    private final TextField text;
    private final DatePicker due;

    public TodoItemLayout(Todo todo, TodoChangeListner listner, TodoRepo todoRepo) {
        this.todoRepo = todoRepo;
        this.todo = todo;
        setWidth("100%");
        done = new Checkbox();
        text = new TextField();
        text.setWidthFull();
        due = new DatePicker();

        if(todo.isDone()) {
            text.addClassName("done");
            text.setReadOnly(true);
            done.addClassName("done");
            done.setReadOnly(true);
            due.addClassName("done");
            due.setReadOnly(true);
        }

        text.setValueChangeMode(ValueChangeMode.ON_BLUR);
        Binder<Todo> binder = new Binder<>(Todo.class);
        binder.bindInstanceFields(this);
        binder.setBean(todo);

        HorizontalLayout layout = new HorizontalLayout(createPriorety(todo.getPriority()),done, text, due,createDeleteIcon(listner));
        layout.setAlignItems(Alignment.CENTER);
        layout.setWidthFull();

        add(layout);

        binder.addValueChangeListener(event -> listner.onChange(todo));
    }

    private Component createPriorety(TodoPriority priority) {
        Label label = new Label();

        if(priority == null) {
            label.add(" ");
            label.setWidth("50px");
            return label;
        }

        switch (priority) {
            case LOW -> label.add(new Icon(VaadinIcon.ANGLE_DOWN));
            case NORMAL -> label.add(new Icon(VaadinIcon.ANGLE_UP));
            case HIGH -> label.add(new Icon(VaadinIcon.ANGLE_DOUBLE_UP));
            default -> {}
        }
        return label;
    }

    private Button createDeleteIcon (TodoChangeListner listner) {
        Icon deleteIcon = new Icon(VaadinIcon.TRASH);
        deleteIcon.getStyle().set("fill","#ed1724");
        Button deleteButton = new Button(deleteIcon);
        deleteButton.addClickListener(e -> {
            todoRepo.deleteById(todo.getId());
            listner.onChange();
        });
        return deleteButton;
    }
}
