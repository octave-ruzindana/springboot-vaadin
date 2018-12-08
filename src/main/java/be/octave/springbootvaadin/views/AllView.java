package be.octave.springbootvaadin.views;

import be.octave.springbootvaadin.components.AddTodoForm;
import be.octave.springbootvaadin.components.MainLayout;
import be.octave.springbootvaadin.components.TodoList;
import be.octave.springbootvaadin.domain.Todo;
import be.octave.springbootvaadin.domain.TodoFilter;
import be.octave.springbootvaadin.services.TodoHandler;
import be.octave.springbootvaadin.services.TodoService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.ConfigurableFilterDataProvider;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.Route;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


@Route(value = "todos", layout = MainLayout.class)
public class AllView extends VerticalLayout implements TodoHandler, HasUrlParameter<Boolean> {

    Logger LOG = LoggerFactory.getLogger(AllView.class);

    private TodoService todoService;
    private TodoList todoList;
    ConfigurableFilterDataProvider<Todo, Void, TodoFilter> configurableFilterDataProvider;

    @Autowired
    public AllView(TodoService todoService) {
        LOG.info("************ instatiating Main view");
        this.todoService = todoService;


        Component header = new H2(getTranslation("header"));
        Component addForm = new AddTodoForm(this);
        this.todoList = new TodoList(this);
//        this.todoList.setItems(todoService.findAll()); // for in-memory sorting

        configurableFilterDataProvider = DataProvider
                .fromFilteringCallbacks(todoService::findBy, todoService::countBy)
                .withConfigurableFilter();

        this.todoList.setDataProvider(configurableFilterDataProvider);
        add(header, addForm, todoList);
    }


    @Override
    public void onAddedTodo(Todo newTodo) {
        todoService.saveOrpdate(newTodo);
        todoList.getDataProvider().refreshAll();
    }

    @Override
    public void onDeletedTodo(Todo todo) {
        todoService.delete(todo);
        todoList.getDataProvider().refreshAll();
    }

    @Override
    public void onUpdateTodo(Todo todo) {
        todoService.saveOrpdate(todo);
        todoList.getDataProvider().refreshItem(todo);// based equals
        Notification.show("Todo " + todo.getTitle() + " has been updated");
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, @OptionalParameter Boolean complete) {
        if (complete != null) {
            TodoFilter todoFilter = new TodoFilter();
            if (complete.equals(true)) {
                todoFilter.withCompletedStatus(true);
            } else {
                todoFilter.withCompletedStatus(false);
            }
            configurableFilterDataProvider.setFilter(todoFilter);
            LOG.info("--------- Displaying todos with parameter {}", complete);
        }
    }
}