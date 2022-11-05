package com.example.todocrowd.view.todo;

import com.example.todocrowd.domain.Todo;
import com.example.todocrowd.layot.todo.addlayout.AddLayout;
import com.example.todocrowd.layot.todo.addlayout.TodoListLyaout;
import com.example.todocrowd.view.MainLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("Todo")
@Route(value = "todo", layout = MainLayout.class)
@RouteAlias(value = "todo", layout = MainLayout.class)
public class TodoView extends VerticalLayout {

    private final AddLayout addLayout;
    private final TodoListLyaout todoListLyaout;

    @Autowired
    public TodoView(AddLayout addLayout, TodoListLyaout todoListLyaout) {
        this.addLayout = addLayout;
        this.todoListLyaout = todoListLyaout;
        setAlignItems(Alignment.CENTER);
        setWidth("100%");
        add(createLayout());
    }

    private VerticalLayout createLayout() {
        VerticalLayout layout = new VerticalLayout();
        layout.setMaxWidth("800px");
        layout.setAlignItems(Alignment.CENTER);

        addLayout.addButtonClickListner(click -> addIssue());

        VerticalLayout header = new VerticalLayout(new H1("TODO"));
        header.setAlignItems(Alignment.CENTER);

        layout.add(
                header,
                addLayout,
                todoListLyaout
        );
        return layout;
    }

    private void addIssue() {
        todoListLyaout.save(addLayout.getTodo());
        addLayout.cleanForm();
    }

}
