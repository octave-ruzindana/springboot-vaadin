package be.octave.springbootvaadin.components;

import be.octave.springbootvaadin.domain.Todo;
import be.octave.springbootvaadin.services.TodoHandler;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TodoList extends VerticalLayout {

    Logger LOG = LoggerFactory.getLogger(TodoList.class);

    private final TodoHandler handler;
    private Binder<Todo> todoBinder = new Binder<>(Todo.class);
    private Grid<Todo> grid = new Grid<>();
    public TodoList(TodoHandler handler) {
        LOG.info("****** instatiating Grid");

        this.handler = handler;
        this.setSizeFull();
        grid.setHeight("75vh");
        grid.addColumn(Todo::getId).
                setHeader("#");
        grid.addColumn(new ComponentRenderer<>(this::renderTitle))
                //.setComparator(Comparator.comparing(Todo::getTitle))
                .setSortable(true)
                .setSortProperty("title")
                .setHeader(getTranslation("title"));

        grid.addColumn(new ComponentRenderer<>(this::renderCompleted)).setHeader(getTranslation("done"));
        grid.addColumn(new ComponentRenderer<>(this::renderActions));
        add(grid);
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
        grid.getDataProvider().refreshItem(todo);
    }

    private void updateTitle(Todo todo) {
        this.handler.onUpdateTodo(todo);
        todo.setEditable(false);
        grid.getDataProvider().refreshItem(todo);
    }

    private void startTitleEdition(Todo todo) {
        todo.setEditable(true);
        grid.getDataProvider().refreshItem(todo);
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


    public DataProvider<Todo, ?> getDataProvider() {
        return this.grid.getDataProvider();
    }

    public void setDataProvider(DataProvider<Todo, ?> dataProvider){
     this.grid.setDataProvider(dataProvider);
    }

    public Grid<Todo> getGrid() {
        return grid;
    }
}
