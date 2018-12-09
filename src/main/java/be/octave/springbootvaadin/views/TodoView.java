package be.octave.springbootvaadin.views;

import be.octave.springbootvaadin.components.AddTodoForm;
import be.octave.springbootvaadin.components.MainLayout;
import be.octave.springbootvaadin.components.TodoList;
import be.octave.springbootvaadin.domain.Todo;
import be.octave.springbootvaadin.domain.TodoFilter;
import be.octave.springbootvaadin.domain.TodoStatus;
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
public class TodoView extends VerticalLayout implements TodoHandler, HasUrlParameter<String> {

    Logger LOG = LoggerFactory.getLogger(TodoView.class);

    private TodoService todoService;
    private TodoList todoList;
    ConfigurableFilterDataProvider<Todo, Void, TodoFilter> configurableFilterDataProvider;

    @Autowired
    public TodoView(TodoService todoService) {
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
    public void setParameter(BeforeEvent beforeEvent, @OptionalParameter String status) {
        TodoFilter todoFilter = new TodoFilter();
        if (status != null) {
            if (status.equals(TodoStatus.COMPLETED.getLabel())) {
                todoFilter.setStatus(TodoStatus.COMPLETED);
            } else if (status.equals(TodoStatus.ONGOING.getLabel())) {
                todoFilter.setStatus(TodoStatus.ONGOING);
            }else {
                todoFilter.setStatus(TodoStatus.ALL);
            }
            configurableFilterDataProvider.setFilter(todoFilter);
            LOG.info("--------- Displaying todos with parameter {}", status);
        } else {
            todoFilter.setStatus(TodoStatus.ALL);
            LOG.info("--------- Displaying todos without specific status");
        }
    }
}