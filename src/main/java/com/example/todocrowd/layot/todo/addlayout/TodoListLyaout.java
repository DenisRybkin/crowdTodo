package com.example.todocrowd.layot.todo.addlayout;

import com.example.todocrowd.domain.Todo;
import com.example.todocrowd.repo.TodoRepo;
import com.example.todocrowd.utils.TodoChangeListner;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@SpringComponent
@UIScope
public class TodoListLyaout extends VerticalLayout implements TodoChangeListner {

    private final TodoRepo todoRepo;

    @Autowired
    public TodoListLyaout(TodoRepo todoRepo) {
        this.todoRepo = todoRepo;
        setWidth("100%");
        update();
    }

    public int countCompleted() {
        return todoRepo.countByDone(true);
    };

    public void save(Todo todo) {
        todoRepo.save(todo);
        update();
    }

    public void deleteCompleted() {
        todoRepo.deleteByDone(true);
        update();
    }

    @Override
    public void onChange(Todo repo) { save(repo); };

    @Override
    public void onChange() { update(); };

    private void update() {
        removeAll();
        List<Todo> all = todoRepo.findByOrderByDueAsc();
        int countCompleted = countCompleted();

        double step = (double) all.size() == 0 ? 100 : (countCompleted == 0 ? 0 :countCompleted * 100/ all.size());
        ProgressBar progress = new ProgressBar(0,100, step);

        Button deleteButton = new Button("Delete completed", e -> clickDeleteHandler(countCompleted));
        if (all.size() == 0) deleteButton.setEnabled(false);

        //Button calendarButton = new Button("Export to ics", new Icon(VaadinIcon.CALENDAR));
        //DefaultFileDownloader buttonWrapper = new DefaultFileDownloader(new StreamResource("todo.ics", () -> new ByteArrayInputStream(calendarSrvice.getEvents().getBytes())));
        add(progress);
        if(all.isEmpty()) {
            Image img = new Image("https://my.tonnus.io/planning-empty.png", "placeholder plant");
            img.setWidth("450px");
            setJustifyContentMode(JustifyContentMode.CENTER);
            setDefaultHorizontalComponentAlignment(Alignment.CENTER);
            add(img);
        } else {
            all.forEach(todo -> add(new TodoItemLayout(todo, this, todoRepo)));
            add(new HorizontalLayout(deleteButton));
        }
    }

    private void clickDeleteHandler (int countCompleted) {
        if(countCompleted == 0) {
            Notification.show("No completed todos");
            return;
        }
        deleteCompleted();
        Notification.show("Deleted ...");
    }

}
