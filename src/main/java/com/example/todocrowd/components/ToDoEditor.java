package com.example.todocrowd.components;

import com.example.todocrowd.domain.ToDo;
import com.example.todocrowd.repo.ToDoRepo;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

public class ToDoEditor extends VerticalLayout {

    private final ToDoRepo toDoRepo;
    private ToDo todo;

    private final TextField text = new TextField("", "Enter a data");
    private final Button saveBtn = new Button("Save");
    private final Button cancelBtn = new Button("Cancel");
    private final Button cleanBtn = new Button("Clean");
    private final Button deleteBtn = new Button("Delete");

    private final Checkbox isDone = new Checkbox(false);

    private final HorizontalLayout form = new HorizontalLayout(text, saveBtn);
    private Binder<ToDo> binder = new Binder<>(ToDo.class);

    @Setter
    private EmployeeEditor.ChangeHandler changeHandler;

    public interface ChangeHandler {
        void onChange();
    }

    @Autowired
    public ToDoEditor(ToDoRepo toDoRepo) {
        this.toDoRepo = toDoRepo;
        add(form);
        binder.bindInstanceFields(this);
        text.setValueChangeMode(ValueChangeMode.EAGER);
        text.addValueChangeListener(v -> chabgeDisabledCleanHandler());
        saveBtn.addClickListener(e -> save());
        cancelBtn.addClickListener(e -> cancel());
        cleanBtn.addClickListener(e -> cleanForm());
        deleteBtn.addClickListener(e -> delete());
    }

    private void save() {
        toDoRepo.save(this.todo);
        cleanForm();
    }

    private void delete() {
        toDoRepo.delete(todo);
        changeHandler.onChange();
    };

    private void cancel () {
        editToDo(null);
        changeHandler.onChange();
    }

    public void editToDo (ToDo newToDo) {
        if(newToDo == null){
            setVisible(false);
            return;
        }
        if(newToDo.getId() != null) {
            deleteBtn.setVisible(true);
            this.todo = toDoRepo.findById(newToDo.getId()).orElse(newToDo);
        } else {
            deleteBtn.setVisible(false);
            this.todo = newToDo;
        }
        binder.setBean(this.todo);
        setVisible(true);
        text.focus();
    }

    private void cleanForm() {
        text.setValue("");
        chabgeDisabledCleanHandler();
    }
    private void chabgeDisabledCleanHandler () {
        cleanBtn.setDisableOnClick(text.getValue().isEmpty());
    }

}
