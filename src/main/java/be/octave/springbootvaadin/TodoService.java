package be.octave.springbootvaadin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class TodoService {

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

    public void add(Todo newTodo){
        todoRepository.save(newTodo);
    }

    public void delete(Todo removedTodo) {
        todoRepository.delete(removedTodo);
    }
}
