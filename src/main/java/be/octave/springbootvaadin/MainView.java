package be.octave.springbootvaadin;

import be.octave.springbootvaadin.components.AddTodoForm;
import be.octave.springbootvaadin.components.MainLayout;
import be.octave.springbootvaadin.components.TodoList;
import com.github.appreciated.app.layout.annotations.MenuCaption;
import com.github.appreciated.app.layout.annotations.MenuIcon;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.router.Route;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Route(value = "", layout = MainLayout.class)
public class MainView extends VerticalLayout implements TodoHandler {

    Logger LOG = LoggerFactory.getLogger(MainView.class);

    private TodoService todoService;
    private TodoList todoList;
    private TodoRepository todoRepository;

    @Autowired
    public MainView(TodoService todoService, TodoRepository todoRepository) {
        LOG.info("************ instatiating Main view");
        this.todoService = todoService;
        this.todoRepository = todoRepository;


        Component header = new H2(getTranslation("header"));
        Component addForm = new AddTodoForm(this);
        this.todoList = new TodoList(this);
//        this.todoList.setItems(todoService.findAll()); // for in-memory sorting

        this.todoList.setDataProvider(DataProvider.fromCallbacks(this::findBy, this::countBy));

        setHeight("75vh");
        add(header, addForm, todoList);
    }

    private void logout() {
        LOG.info("Logged out");
    }

    private int countBy(Query<Todo, Void> query) {
        return todoService.countAll();
    }

    private Stream<Todo> findBy(Query<Todo, Void> query) {


        List<Sort.Order> sortOrders = query.getSortOrders().stream()
                .map(sortQuery -> sortQuery.getDirection().equals(SortDirection.ASCENDING) ? Sort.Order.asc(sortQuery.getSorted()) : Sort.Order.desc(sortQuery.getSorted()) )
                .collect(Collectors.toList());

        LOG.info("----------- Find todos with page {}, size {} and sort {}", query.getOffset(), query.getLimit(), sortOrders);
        Pageable page = PageRequest.of(query.getOffset(), query.getLimit(), Sort.by(sortOrders));
        return todoRepository.findAll(page).getContent().stream();
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