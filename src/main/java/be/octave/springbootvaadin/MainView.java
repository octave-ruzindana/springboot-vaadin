package be.octave.springbootvaadin;

import be.octave.springbootvaadin.components.AddTodoForm;
import be.octave.springbootvaadin.components.TodoList;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route("")
public class MainView extends VerticalLayout implements TodoHandler {

    private TodoService todoService;
    private TodoList todoList;

    @Autowired
    public MainView(TodoService todoService) {
        this.todoService = todoService;

        Component header = new H2("My todo List");
        Component addForm = new AddTodoForm(this);
        this.todoList = new TodoList(this);

        this.todoList.setDataProvider(DataProvider.fromCallbacks(query -> {
            return todoService.find(query.getLimit(), query.getOffset()).stream();
        }, query -> {return todoService.countAll();}));

        setHeight("100vh");
        add(header,addForm, todoList);
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