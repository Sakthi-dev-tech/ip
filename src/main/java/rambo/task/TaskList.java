package rambo.task;

import java.util.ArrayList;
import java.util.List;

import rambo.exception.RamboException;

/**
 * Contains the application's tasks and provides operations for modifying them.
 */
public class TaskList {
    private final List<Task> tasks;

    /**
     * Creates a task list containing the supplied tasks.
     *
     * @param tasks initial tasks in the list
     */
    public TaskList(List<Task> tasks) {
        this.tasks = new ArrayList<>(tasks);
    }

    /**
     * Adds a task to the list.
     *
     * @param task task to add
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Toggles the done status of a task.
     *
     * @param taskNumber one-based task number shown to the user
     * @throws RamboException if the task number does not exist
     */
    public void toggle(int taskNumber) throws RamboException {
        tasks.get(getIndex(taskNumber)).toggleDone();
    }

    /**
     * Deletes a task from the list.
     *
     * @param taskNumber one-based task number shown to the user
     * @throws RamboException if the task number does not exist
     */
    public void delete(int taskNumber) throws RamboException {
        tasks.remove(getIndex(taskNumber));
    }

    /**
     * Returns a snapshot of the tasks in this list.
     *
     * @return tasks in their current order
     */
    public List<Task> getTasks() {
        return List.copyOf(tasks);
    }

    /**
     * Converts a one-based task number to its list index after validating it.
     */
    private int getIndex(int taskNumber) throws RamboException {
        int index = taskNumber - 1;
        if (index < 0 || index >= tasks.size()) {
            throw new RamboException("I cannot find this task! Give a valid index!");
        }
        return index;
    }
}
