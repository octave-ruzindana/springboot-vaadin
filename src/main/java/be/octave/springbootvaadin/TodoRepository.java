package be.octave.springbootvaadin;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends PagingAndSortingRepository<Todo,Integer> {

    @Override
    List<Todo> findAll();

}
