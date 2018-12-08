package be.octave.springbootvaadin.views;

import be.octave.springbootvaadin.components.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route(value = "login", layout = MainLayout.class)
public class LoginView extends VerticalLayout {

    public LoginView() {
        Component formTitle = new H3("Login");
        FormLayout loginForm = new FormLayout();
        loginForm.add(new TextField("User"));
        loginForm.add(new TextField("Password"));
        loginForm.add(new Button("Login"));
        add(formTitle, loginForm);
    }
}
