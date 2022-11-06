package com.example.todocrowd.layot.todo.addlayout;

import com.example.todocrowd.domain.Todo;
import com.example.todocrowd.utils.TodoPriority;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import java.time.LocalDate;
import java.util.Locale;

@SpringComponent
@UIScope
public class AddLayout extends HorizontalLayout {

    private Todo todo;
    private final TextField textField;
    private final DatePicker dataPicker;
    private final Select<TodoPriority> select;
    private final Button button;

    public AddLayout() {
        setWidth("100%");
        textField = new TextField();
        dataPicker = new DatePicker();
        select = new Select<TodoPriority>();
        button = new Button();
        configureFields();
        add(textField,dataPicker,select,button);
    }

    private void configureFields () {
        textField.setPlaceholder("Need to do...");
        textField.setWidthFull();
        textField.setValueChangeMode(ValueChangeMode.EAGER);
        textField.addValueChangeListener(e -> {
            if (e.getValue().isEmpty()) button.setEnabled(false);
            else button.setEnabled(true);
        });

        dataPicker.setClearButtonVisible(true);
        dataPicker.setLocale(Locale.getDefault());
        dataPicker.setPlaceholder("Due date");
        dataPicker.setWidth("200px");
        dataPicker.setMin(LocalDate.now());

        select.setItems(TodoPriority.values());
        select.setPlaceholder("Priority");
        select.setWidth("100px");
        select.setValue(TodoPriority.NORMAL);

        button.addClickShortcut(Key.ENTER);
        button.setEnabled(false);
        button.setIcon(new Icon(VaadinIcon.PLUS));
    }

    public void cleanForm () {
        textField.setValue("");
        select.setValue(TodoPriority.NORMAL);
        dataPicker.setValue(null);
    }

    public Todo getTodo() {
        return new Todo(textField.getValue(), dataPicker.getValue(), select.getValue());
    };

    public void addButtonClickListner(ComponentEventListener<ClickEvent<Button>> listener) {
        button.addClickListener(listener);
    }
}
