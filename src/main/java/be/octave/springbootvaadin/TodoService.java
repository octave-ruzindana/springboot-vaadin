package be.octave.springbootvaadin;

import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

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

    public List<Todo> find(int limit, int offset){
        LOGGER.info("----------- Find with params page {} and size {}", offset, limit);
        Pageable page = PageRequest.of(offset, limit);
        return todoRepository.findAll(page).getContent();
    }


    public Todo saveOrpdate(Todo todo){
        LOGGER.info("----------- Create or Update Todo {} with status done = {}" , todo.getTitle(), todo.isCompleted());
        return todoRepository.save(todo);
    }

    public void delete(Todo todo) {
        LOGGER.info("----------- Delete Todo with id" + todo.getId());
        todoRepository.delete(todo);
    }


    public int countAll() {
        LOGGER.info("----------- Counting Todo");
        return Long.valueOf(todoRepository.count()).intValue();
    }

    public int count(int limit, int offset) {
       throw new NotImplementedException("soon available");
    }
}
