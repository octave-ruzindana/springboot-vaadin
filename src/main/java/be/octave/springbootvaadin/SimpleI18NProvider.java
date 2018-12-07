package be.octave.springbootvaadin;

import com.vaadin.flow.i18n.I18NProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

@Component
public class SimpleI18NProvider implements I18NProvider {

    private MessageSource messageSource;

    @Autowired
    public SimpleI18NProvider(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public List<Locale> getProvidedLocales() {
        return Collections.unmodifiableList(
                Arrays.asList(Locale.ENGLISH, new Locale("nl","be"), new Locale("fr","be")));
    }

    @Override
    public String getTranslation(String key, Locale locale, Object... objects) {
        return messageSource.getMessage(key,objects, locale);
    }
}

