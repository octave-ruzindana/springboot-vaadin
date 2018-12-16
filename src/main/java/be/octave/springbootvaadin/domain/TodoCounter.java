package be.octave.springbootvaadin.domain;

public class TodoCounter {

    private Long total;
    private Long completed;
    private Long ongoing;

    public TodoCounter(Long completed, Long ongoing) {
        this.completed = completed;
        this.ongoing = ongoing;
        this.total = completed + ongoing;
    }

    public Long getCompleted() {
        return completed;
    }

    public Long getOngoing() {
        return ongoing;
    }

    public Long getTotal() {
        return total;
    }

    @Override
    public String toString() {
        return "TodoCounter{" +
                "total=" + total +
                ", completed=" + completed +
                ", ongoing=" + ongoing +
                '}';
    }
}
