package be.octave.springbootvaadin.views;

import be.octave.springbootvaadin.components.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

import java.util.Arrays;

@Route(value = "completed",layout = MainLayout.class)
public class CompletedView extends VerticalLayout implements BeforeEnterObserver {

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        String url = UI.getCurrent().getRouter().getUrl(AllView.class);
        beforeEnterEvent.rerouteTo(url, Arrays.asList(true));
    }
}
