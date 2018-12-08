package be.octave.springbootvaadin.services;

import be.octave.springbootvaadin.domain.Todo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends PagingAndSortingRepository<Todo,Integer>, JpaSpecificationExecutor<Todo> {

    @Override
    List<Todo> findAll();

}
