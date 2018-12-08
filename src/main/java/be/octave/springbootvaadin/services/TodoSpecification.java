package be.octave.springbootvaadin.services;

import be.octave.springbootvaadin.domain.Todo;
import org.springframework.data.jpa.domain.Specification;

public class TodoSpecification {

    public static Specification<Todo> isComplete() {
        return (root, query, cb) ->{
            return cb.equal(root.get("completed"), true);
        };
    }

    public static Specification<Todo> isOnGoing() {
        return (root, query, cb) ->{
            return cb.equal(root.get("completed"), false);
        };
    }

}
