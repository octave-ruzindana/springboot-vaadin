package be.octave.springbootvaadin.services;

import be.octave.springbootvaadin.domain.Todo;
import be.octave.springbootvaadin.domain.TodoFilter;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.provider.SortDirection;
import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TodoService {

    Logger LOGGER = LoggerFactory.getLogger(TodoService.class);

    private final TodoRepository todoRepository;


    @PostConstruct
    protected void init(){

        todoRepository.save(new Todo("delectus aut autem", false));
        todoRepository.save(new Todo("quis ut nam facilis et officia qui", true));
        todoRepository.save(new Todo("fugiat veniam minus", true));
        todoRepository.save(new Todo("et porro tempora", false));
        todoRepository.save(new Todo("laboriosam mollitia et enim quasi adipisci quia provident illum", false));

    }


    @Autowired
    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<Todo> findAll(){
        return todoRepository.findAll();
    }


    public Todo saveOrpdate(Todo todo){
        LOGGER.info("----------- Create or Update Todo {} with status done = {}" , todo.getTitle(), todo.isCompleted());
        return todoRepository.save(todo);
    }

    public void delete(Todo todo) {
        LOGGER.info("----------- Delete Todo with id" + todo.getId());
        todoRepository.delete(todo);
    }


    public int countBy(Query<Todo, TodoFilter> query) {
        if(query.getFilter().isPresent()){
            Specification<Todo> specificationFromFilter = getSpecificationFromFilter(query.getFilter().get());
            return (int) todoRepository.count(specificationFromFilter);
        }else {

            return (int)todoRepository.count();
        }
    }

    private Specification<Todo> getSpecificationFromFilter(TodoFilter filter) {
        if(filter.getCompleted().equals(true)){
            return TodoSpecification.isComplete();
        }else {
            return TodoSpecification.isOnGoing();
        }
    }


    public Stream<Todo> findBy(Query<Todo, TodoFilter> query) {

        List<Sort.Order> sortOrders = query.getSortOrders().stream()
                .map(sortQuery -> sortQuery.getDirection().equals(SortDirection.ASCENDING) ? Sort.Order.asc(sortQuery.getSorted()) : Sort.Order.desc(sortQuery.getSorted()) )
                .collect(Collectors.toList());

        LOGGER.info("----------- Find todos with page {}, size {}, sort {} and optional filter {}", query.getOffset(), query.getLimit(), sortOrders, query.getFilter());

        Pageable page = PageRequest.of(query.getOffset(), query.getLimit(), Sort.by(sortOrders));

        if(query.getFilter().isPresent()){
            Specification<Todo> specificationFromFilter = getSpecificationFromFilter(query.getFilter().get());

            return todoRepository.findAll(specificationFromFilter, page).stream();
        }else {

            return todoRepository.findAll(page).stream();
        }

    }
}
