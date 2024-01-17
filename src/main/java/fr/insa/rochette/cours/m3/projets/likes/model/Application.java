package fr.insa.rochette.cours.m3.projets.likes.model;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The entry point of the Spring Boot application.
 *
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 *
 */
@SpringBootApplication
@Theme(value = "")
@Route("main_layout")
public class Application implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    //   MenuBarStyles menuBarStyles = new MenuBarStyles();
    }
    

}
