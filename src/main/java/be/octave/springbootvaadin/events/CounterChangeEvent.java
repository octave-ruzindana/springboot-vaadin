package be.octave.springbootvaadin.events;

import be.octave.springbootvaadin.components.TodoList;
import com.vaadin.flow.component.ComponentEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CounterChangeEvent extends ComponentEvent<TodoList> {

    private final Logger logger = LoggerFactory.getLogger(CounterChangeEvent.class);

    public CounterChangeEvent(TodoList source, boolean fromClient) {
        super(source, fromClient);
    }

}
