package be.octave.springbootvaadin;

import be.octave.springbootvaadin.components.AddTodoForm;
import be.octave.springbootvaadin.components.TodoList;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.router.Route;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Stream;

@Route("")
public class MainView extends VerticalLayout implements TodoHandler {

    Logger LOG = LoggerFactory.getLogger(MainView.class);

    private TodoService todoService;
    private TodoList todoList;

    @Autowired
    public MainView(TodoService todoService) {
        LOG.info("************ instatiating Main view");
        this.todoService = todoService;

        Component header = new H2("My todo List");
        Component addForm = new AddTodoForm(this);
        this.todoList = new TodoList(this);
//        this.todoList.setItems(todoService.findAll()); // for in-memory sorting

        this.todoList.setDataProvider(DataProvider.fromCallbacks(this::findBy, this::countBy));

        setHeight("100vh");
        add(header,addForm, todoList);
    }

    private int countBy(Query<Todo, Void> query) {
        return todoService.countAll();
    }

    private Stream<Todo> findBy(Query<Todo, Void> query) {
        //query.getSortOrders()
        return todoService.find(query.getLimit(), query.getOffset()).stream();
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
        Notification.show("Todo "+ todo.getTitle()+ " has been updated");
    }
}