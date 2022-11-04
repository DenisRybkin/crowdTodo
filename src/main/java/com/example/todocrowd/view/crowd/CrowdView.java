package com.example.todocrowd.view.crowd;

import com.example.todocrowd.components.EmployeeEditor;
import com.example.todocrowd.domain.Employee;
import com.example.todocrowd.repo.EmployeeRepo;
import com.example.todocrowd.view.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("Crowd")
@Route(value = "crowd", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class CrowdView extends VerticalLayout {
    private final EmployeeRepo employeeRepo;
    private Grid<Employee> grid = new Grid<Employee>();
    private final TextField filterInput = new TextField("", "Search");
    private final Button addNewBtn = new Button("Add new");
    private final HorizontalLayout toolbar = new HorizontalLayout(filterInput, addNewBtn);

    private boolean isHiddenEditor = true;
    private final EmployeeEditor editor;

    @Autowired
    public CrowdView(EmployeeRepo employeeRepo, EmployeeEditor editor) {
        this.employeeRepo = employeeRepo;
        this.editor = editor;
        configureColumns();
        add(toolbar, grid, editor);
        editor.setVisible(!isHiddenEditor);
        filterInput.setValueChangeMode(ValueChangeMode.EAGER);
        filterInput.addValueChangeListener(e -> filterListByName(e.getValue()));

        grid.asSingleSelect().addValueChangeListener(e -> editor.editEmployee(e.getValue()));

        addNewBtn.addClickListener(e -> clickAddNewHandler());
        editor.setChangeHandler(this::actionHandler);

        initListEmployee();
    };

    private void filterListByName(String name) {
        if(name.isEmpty()) initListEmployee();
        else grid.setItems(employeeRepo.findByName(name));
    }

    private void initListEmployee() {
        grid.setItems(employeeRepo.findAll());
    }
    private void clickAddNewHandler () {
        isHiddenEditor = !isHiddenEditor;
        addNewBtn.setText(isHiddenEditor ? "Add new" : "Close Editor");
        editor.editEmployee(new Employee());
        editor.setVisible(!isHiddenEditor);
    }

    private void actionHandler () {
        editor.setVisible(false);
        isHiddenEditor = false;
        addNewBtn.setText("Add new");
        filterListByName(filterInput.getValue());
    }

    private void configureColumns() {
        grid.addColumn(Employee::getFirstName).setHeader("First Name");
        grid.addColumn(Employee::getLastName).setHeader("Last Name");
        grid.addColumn(Employee::getPatronymic).setHeader("Patronymic");
    }
}
