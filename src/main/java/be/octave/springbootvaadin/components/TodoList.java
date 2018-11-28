package be.octave.springbootvaadin.components;

import be.octave.springbootvaadin.Todo;
import be.octave.springbootvaadin.TodoHandler;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.data.renderer.ComponentRenderer;

import java.util.List;

public class TodoList extends Grid<Todo> {

    public TodoList(TodoHandler handler) {

        this.setSizeFull();
        this.addColumn(Todo::getId).setHeader("#");
        this.addColumn(Todo::getTitle).setHeader("Title");
        this.addColumn(new ComponentRenderer<>(todo -> new Checkbox(todo.isCompleted()))).setHeader("Done ?");
        this.addColumn(new ComponentRenderer<>(todo -> {
            Button deleteButton = new Button(VaadinIcon.TRASH.create());
            deleteButton.addClickListener(event -> handler.onDeletedTodo(todo));
            return deleteButton;
        }));

    }

    public void update(List<Todo> todos) {
        this.setItems(todos);
    }
}
