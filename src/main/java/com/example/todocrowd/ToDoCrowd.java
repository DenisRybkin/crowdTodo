package com.example.todocrowd;

import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Theme(value = "myapp", variant = Lumo.LIGHT)
@PWA(name = "ToDoCrowd", shortName = "ToDoCrowd", offlineResources = {})
@NpmPackage(value = "line-awesome", version = "1.3.0")
@NpmPackage(value = "@vaadin-component-factory/vcf-nav", version = "1.0.6")
public class ToDoCrowd implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(ToDoCrowd.class, args);
    }

}
