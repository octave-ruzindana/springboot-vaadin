package be.octave.springbootvaadin.components;

import be.octave.springbootvaadin.Todo;
import be.octave.springbootvaadin.TodoHandler;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;

public class TodoList extends Grid<Todo> {

    Logger LOG = LoggerFactory.getLogger(TodoList.class);

    private final TodoHandler handler;
    private Binder<Todo> todoBinder = new Binder<>(Todo.class);

    public TodoList(TodoHandler handler) {
        LOG.info("****** instatiating Grid");

        this.handler = handler;

        this.setSizeFull();
        this.addColumn(Todo::getId).setHeader("#");
        this.addColumn(new ComponentRenderer<>(this::renderTitle))
                .setComparator(Comparator.comparing(Todo::getTitle))
                .setSortable(true)
                .setHeader("Title");

        this.addColumn(new ComponentRenderer<>(this::renderCompleted)).setHeader("Done ?");
        this.addColumn(new ComponentRenderer<>(this::renderActions));
    }

    public void toggleStatus(Todo todo) {
        todo.setCompleted(!todo.isCompleted());
        handler.onUpdateTodo(todo);
    }

    public Component renderTitle(Todo todo) {
        if(todo.isEditable()){
            TextField titleEditor = new TextField();
            this.todoBinder.forField(titleEditor)
                    .withValidator(title -> title.length() > 8,"Must be at least 8 caracters long")
                    .bind(Todo::getTitle,Todo::setTitle);
            this.todoBinder.setBean(todo);

            titleEditor.setSizeFull();
            titleEditor.addKeyUpListener(Key.ENTER, event -> this.updateTitle(todo) );
            titleEditor.addBlurListener(event -> this.cancelTitleEdition(todo));

            return titleEditor;
        }else {
            Label label = new Label(todo.getTitle());
            if(todo.isCompleted()){
                label.getStyle().set("text-decoration", "line-through");
            }
            return label;
        }
    }

    private void cancelTitleEdition(Todo todo) {
        todo.setEditable(false);
        this.getDataProvider().refreshItem(todo);
    }

    private void updateTitle(Todo todo) {
        this.handler.onUpdateTodo(todo);
        todo.setEditable(false);
        this.getDataProvider().refreshItem(todo);
    }

    private void startTitleEdition(Todo todo) {
        todo.setEditable(true);
        this.getDataProvider().refreshItem(todo);
    }

    public Component renderActions(Todo todo) {
        Button deleteButton = new Button(VaadinIcon.TRASH.create());
        Button editButton = new Button(VaadinIcon.EDIT.create());
        deleteButton.addClickListener(event -> handler.onDeletedTodo(todo));
        editButton.addClickListener(event -> this.startTitleEdition(todo));
        return new HorizontalLayout(editButton, deleteButton);
    }

    public Component renderCompleted(Todo todo) {
        Checkbox completedCheckBox = new Checkbox(todo.isCompleted());
        completedCheckBox.addClickListener(event -> this.toggleStatus(todo));
        return completedCheckBox;
    }


}
