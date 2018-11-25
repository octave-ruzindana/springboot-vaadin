package be.octave.springbootvaadin;

import be.octave.springbootvaadin.components.AddTodoForm;
import be.octave.springbootvaadin.components.TodoList;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
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
        this.todoList = new TodoList();

        updateList();

        setHeight("100vh");
        add(header,addForm, todoList);
    }

    protected void updateList() {
        this.todoList.update(todoService.findAll());
    }

    @Override
    public void onAddedTodo(Todo newTodo) {
        todoService.add(newTodo);
        updateList();
    }
}