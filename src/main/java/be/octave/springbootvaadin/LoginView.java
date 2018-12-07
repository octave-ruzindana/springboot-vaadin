package be.octave.springbootvaadin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("login")
public class LoginView extends FormLayout {

    public LoginView() {
        this.add(new TextField("User"));
        this.add(new TextField("Password"));
        this.add(new Button("Login"));
    }
}
