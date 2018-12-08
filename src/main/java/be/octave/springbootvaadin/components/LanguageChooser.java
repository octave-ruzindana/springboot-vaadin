package be.octave.springbootvaadin.components;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.combobox.ComboBox;

import java.util.Locale;

public class LanguageChooser extends ComboBox<Locale> {

    public LanguageChooser() {;
        this.setItems(new Locale("fr", "be"), new Locale("nl", "be"));
        this.addValueChangeListener(event -> changeLanguage(event.getValue()));
        this.getStyle().set("background-color","white");
    }

    private void changeLanguage(Locale value) {
        UI.getCurrent().setLocale(value);
    }
}
