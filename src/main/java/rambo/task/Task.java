package rambo.task;

import java.time.format.DateTimeFormatter;

/**
 * Represents a task with a name and done status.
 */
public class Task {
    protected static final DateTimeFormatter DISPLAY_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy");

    private final String taskName;
    private boolean isDone;

    /**
     * Constructs a new task with the given name and marks it as not done.
     *
     * @param taskName the name of the task
     */
    public Task(String taskName) {
        this.taskName = taskName;
        this.isDone = false;
    }

    /**
     * Returns the name of the task.
     *
     * @return the task name
     */
    public String getTaskName() {
        return taskName;
    }

    /**
     * Returns true if the task is done, false otherwise.
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * Toggles the done status of the task and returns the new status.
     *
     * @return the new done status
     */
    public boolean toggleDone() {
        isDone = !isDone;
        return isDone;
    }

    /**
     * Converts the task into the required string format for storage.
     *
     * @return the data string representation of the task
     */
    public String toDataString() {
        return String.format("T|%s|%s", isDone ? "X" : "", taskName);
    }

    /**
     * Returns a string representation of the task suitable for display.
     *
     * @return the task string representation
     */
    @Override
    public String toString() {
        return String.format("[T][%s] %s", isDone ? "X" : "", taskName);
    }
}
