package com.example.todocrowd.components;

import com.example.todocrowd.domain.Employee;
import com.example.todocrowd.repo.EmployeeRepo;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import com.vaadin.flow.component.button.Button;

@SpringComponent
@UIScope
public class EmployeeEditor extends VerticalLayout implements KeyNotifier {
    private final EmployeeRepo employeeRepo;

    private Employee employee;

    private TextField firstName = new TextField("First name");
    private TextField lastName = new TextField("Last name");
    private TextField patronymic = new TextField("Patronymic");

    private Button saveBtn = new Button("Save", VaadinIcon.CHECK.create());
    private Button cancelBtn = new Button("Cancel", VaadinIcon.CLOSE.create());
    private Button deleteBtn = new Button("Delete", VaadinIcon.TRASH.create());
    private HorizontalLayout actions = new HorizontalLayout(saveBtn, cancelBtn, deleteBtn);

    private Binder<Employee> binder = new Binder<>(Employee.class);

    @Setter
    private ChangeHandler changeHandler;

    public interface ChangeHandler {
        void onChange();
    }

    @Autowired
    public EmployeeEditor(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;

        add(lastName, firstName, patronymic, actions);
        binder.bindInstanceFields(this);

        setSpacing(true);

        saveBtn.getElement().getThemeList().add("primary");
        deleteBtn.getElement().getThemeList().add("error");

        addKeyPressListener(Key.ENTER, e -> save());

        saveBtn.addClickListener(e -> save());
        deleteBtn.addClickListener(e -> delete());
        cancelBtn.addClickListener(e -> cancel());

    }

    private void delete() {
        employeeRepo.delete(employee);
        changeHandler.onChange();
    };

    private void save() {
        employeeRepo.save(employee);
        changeHandler.onChange();
    };

    private void cancel() {
        editEmployee(null);
        changeHandler.onChange();
    }

    public void editEmployee(Employee newEmployee){
        if(newEmployee == null) {
            setVisible(false);
            return;
        }
        if(newEmployee.getId() != null) {
            deleteBtn.setVisible(true);
            this.employee = employeeRepo.findById(newEmployee.getId()).orElse(newEmployee);
        } else {
            deleteBtn.setVisible(false);
            employee = newEmployee;
        }
        binder.setBean(this.employee);
        setVisible(true);
        lastName.focus();
    }
}
