package be.octave.springbootvaadin.components;

import be.octave.springbootvaadin.Todo;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;

import java.util.List;

public class TodoList extends Grid<Todo> {

    //private Grid<Todo> grid = new Grid<>();

    public TodoList() {
        this.setSizeFull();
        this.addColumn(Todo::getId).setHeader("#");
        this.addColumn(Todo::getTitle).setHeader("Title");
        this.addColumn(Todo::isCompleted).setHeader("Done ?");

    }

    public void update(List<Todo> todos) {
        this.setItems(todos);
    }
}
