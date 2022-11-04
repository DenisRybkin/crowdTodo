package com.example.todocrowd.view.todo;

import com.example.todocrowd.domain.ToDo;
import com.example.todocrowd.repo.ToDoRepo;
import com.example.todocrowd.view.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("Todo")
@Route(value = "todo", layout = MainLayout.class)
@RouteAlias(value = "todo", layout = MainLayout.class)
public class TodoView extends VerticalLayout {

    private final ToDoRepo toDoRepo;
    private ToDo todo;

    private final TextField filterInput = new TextField("", "Search");
    private final Button addBtn = new Button("Add");
    private final Checkbox isDone = new Checkbox(false);
    private final Grid<ToDo> grid = new Grid<ToDo>();


    @Autowired
    public TodoView(ToDoRepo toDoRepo) {
        this.toDoRepo = toDoRepo;
        this.configureColumns();
        add(grid);
        filterInput.setValueChangeMode(ValueChangeMode.EAGER);
        filterInput.addValueChangeListener(e -> filterByText(e.getValue()));

        initTodos();
    }

    private void addToDo() {
        toDoRepo.save(this.todo);
    }

    private void filterByText(String name) {
        if(filterInput.getValue().isEmpty()) initTodos();
        else grid.setItems(toDoRepo.findByText(name));
    }

    private void initTodos () {
        grid.setItems(toDoRepo.findAll());
    }

    private LitRenderer<ToDo> renderCheckBox(Long id, boolean value) {
        Checkbox checkbox = new Checkbox();
        checkbox.setVisible(false);
        return LitRenderer.<ToDo> of (
                "<vaadin-checkbox></vaadin-checkbox>"
        );
    }

    private void configureColumns() {
        grid.addColumn((t) ->  renderCheckBox(t.getId(), t.isDone())).setHeader("Is done");
        grid.addColumn(ToDo::getText).setHeader("Text");
    }

}