package be.octave.springbootvaadin.views;

import be.octave.springbootvaadin.components.MainLayout;
import be.octave.springbootvaadin.domain.TodoStatus;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

@Route(value = "", layout = MainLayout.class)
public class HomeView extends Div implements BeforeEnterObserver {

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        String url = UI.getCurrent().getRouter().getUrl(TodoView.class);
        beforeEnterEvent.rerouteTo(url, TodoStatus.ALL.getLabel());
    }
}
