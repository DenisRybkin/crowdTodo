package com.example.todocrowd.layot.appnav;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.dom.Element;

import java.util.Optional;


@JsModule("@vaadin-component-factory/vcf-nav")
@Tag("vcf-nav")
public class AppNav extends Component implements HasSize, HasStyle {

    public AppNav() {
    }
    public AppNav(String label) {
        setLabel(label);
    }

    public AppNav addItem(AppNavItem... appNavItems) {
        for (AppNavItem appNavItem : appNavItems) {
            getElement().appendChild(appNavItem.getElement());
        }

        return this;
    }
    public AppNav setLabel(String label) {
        getLabelElement().setText(label);
        return this;
    }

    private Optional<Element> getExistingLabelElement() {
        return getElement().getChildren().filter(child -> "label".equals(child.getAttribute("slot"))).findFirst();
    }

    private Element getLabelElement() {
        return getExistingLabelElement().orElseGet(() -> {
            Element element = new Element("span");
            element.setAttribute("slot", "label");
            getElement().appendChild(element);
            return element;
        });
    }

    public boolean isCollapsible() {
        return getElement().hasAttribute("collapsible");
    };

    public AppNav setCollapsible(boolean collapsible) {
        getElement().setAttribute("collapsible", "");
        return this;
    }

}
