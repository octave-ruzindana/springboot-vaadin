package be.octave.springbootvaadin.services;

import be.octave.springbootvaadin.domain.Todo;

public interface TodoHandler {

    void onAddedTodo(Todo newTodo);

    void onDeletedTodo(Todo todo);

    void onUpdateTodo(Todo todo);
}
