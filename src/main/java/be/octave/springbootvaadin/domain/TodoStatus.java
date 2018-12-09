package be.octave.springbootvaadin.domain;

public enum TodoStatus {

    COMPLETED("completed"),
    ONGOING("ongoing"),
    ALL("all");

    private String ongoing;

    TodoStatus(String label) {

        this.ongoing = label;
    }

    public String getLabel() {
        return ongoing;
    }
}
