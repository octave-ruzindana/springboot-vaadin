package be.octave.springbootvaadin.views;

import be.octave.springbootvaadin.components.MainLayout;
import be.octave.springbootvaadin.domain.TodoStatus;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

@Route(value = "completed",layout = MainLayout.class)
public class CompletedTodoView extends VerticalLayout implements BeforeEnterObserver {

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        String url = UI.getCurrent().getRouter().getUrl(TodoView.class);
        beforeEnterEvent.rerouteTo(url, TodoStatus.COMPLETED.getLabel());
    }
}
