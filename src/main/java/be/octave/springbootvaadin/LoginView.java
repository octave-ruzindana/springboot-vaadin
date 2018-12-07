package be.octave.springbootvaadin;

import be.octave.springbootvaadin.components.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.github.appreciated.app.layout.annotations.MenuCaption;
import com.github.appreciated.app.layout.annotations.MenuIcon;

@Route(value = "login", layout = MainLayout.class)
public class LoginView extends FormLayout {

    public LoginView() {
        this.add(new TextField("User"));
        this.add(new TextField("Password"));
        this.add(new Button("Login"));
    }
}
