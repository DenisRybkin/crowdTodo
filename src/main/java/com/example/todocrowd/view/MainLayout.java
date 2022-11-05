package com.example.todocrowd.view;

import com.example.todocrowd.layot.appnav.AppNav;
import com.example.todocrowd.layot.appnav.AppNavItem;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.theme.lumo.Lumo;
import com.vaadin.flow.theme.lumo.LumoUtility;

public class MainLayout extends AppLayout {
    private H2 viewTitle;

    public MainLayout () {
        setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addHeaderContent();
    }

    private void addHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.getElement().setAttribute("aria-label", "Meni toggle");
        viewTitle = new H2();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

        addToNavbar(true, toggle, viewTitle);
    }

    private Header addDrawerHeader() {
        H2 appName = new H2("TODO Crowd");
        appName.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);
        Button themeToggleBtn = new Button("Dark", new Icon(VaadinIcon.MOON), e -> themeClickHandler(e));
        HorizontalLayout headerLayout = new HorizontalLayout(appName,themeToggleBtn);
        headerLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        headerLayout.setVerticalComponentAlignment(FlexComponent.Alignment.END, themeToggleBtn);
        return new Header(headerLayout);
    }

    private void themeClickHandler (ClickEvent<Button> click) {
        ThemeList themeList = UI.getCurrent().getElement().getThemeList();
        if(themeList.contains(Lumo.DARK)) {
            themeList.remove(Lumo.DARK);
            click.getSource().setIcon(new Icon(VaadinIcon.MOON));
            click.getSource().setText("Dark");
        } else {
            themeList.add(Lumo.DARK);
            click.getSource().setIcon(new Icon(VaadinIcon.MOON_O));
            click.getSource().setText("Light");
        }
    }

    private void addDrawerContent() {
        Scroller scroller = new Scroller(createNavigation());
        addToDrawer(addDrawerHeader(), scroller, createFooter());
    }

    private AppNav createNavigation() {
        AppNav appNav = new AppNav();
        appNav.addItem(new AppNavItem("Crowd", "crowd"));
        appNav.addItem(new AppNavItem("ToDo", "todo"));
        return appNav;
    }

    private Footer createFooter () {
        return new Footer();
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }

}
