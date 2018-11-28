package be.octave.springbootvaadin;

public interface TodoHandler {

    void onAddedTodo(Todo newTodo);

    void onDeletedTodo(Todo todo);
}
