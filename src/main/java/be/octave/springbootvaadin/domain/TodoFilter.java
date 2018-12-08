package be.octave.springbootvaadin.domain;

public class TodoFilter {

    private Boolean completed;

    public void withCompletedStatus(boolean completed) {
        this.completed = completed;
    }

    public Boolean getCompleted() {
        return completed;
    }
}
