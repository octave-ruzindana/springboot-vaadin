package be.octave.springbootvaadin.components;

import be.octave.springbootvaadin.domain.Todo;
import be.octave.springbootvaadin.services.TodoHandler;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddTodoForm extends HorizontalLayout {

    Logger LOG = LoggerFactory.getLogger(AddTodoForm.class);

    private Binder<Todo> binder = new Binder<>();

    private TextField title;
    private Button button;

    private TodoHandler todoHandler;

    public AddTodoForm(TodoHandler todoHandler) {
        LOG.info("****** instatiating Form....");

        this.todoHandler = todoHandler;

        this.title = new TextField();
        this.title.setPlaceholder(getTranslation("placeholder"));
        this.title.setWidth("20em");

        binder.forField(this.title)
                .withValidator(title -> title.length() > 8,getTranslation("validation.length"))
                .bind(Todo::getTitle, Todo::setTitle);
        binder.setBean(new Todo("",false));

        this.title.addKeyUpListener(Key.ENTER, keyUpEvent -> onAddedTodo());

        this.button = new Button(getTranslation("add"), VaadinIcon.PLUS.create(),
                event -> this.onAddedTodo());
        add(title, button);


    }

    private void onAddedTodo() {
        if(binder.isValid()){
            todoHandler.onAddedTodo(binder.getBean());
            binder.setBean(new Todo("",false));//clear form after save
        }
    }
}
