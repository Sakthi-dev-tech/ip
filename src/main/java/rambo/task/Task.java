package rambo.task;

import java.time.format.DateTimeFormatter;

/**
 * Represents a task with a name and done status.
 */
public class Task {
  private String taskName;
  private boolean isDone;

  protected static final DateTimeFormatter DISPLAY_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy");

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
    return this.taskName;
  }

  /**
   * Returns true if the task is done, false otherwise.
   */
  public boolean isDone() {
    return this.isDone;
  }

  /**
   * Toggles the done status of the task and returns the new status.
   *
   * @return the new done status
   */
  public boolean toggleDone() {
    this.isDone = !this.isDone;
    return this.isDone;
  }

  /**
   * Converts the task into the required string format for storage.
   *
   * @return the data string representation of the task
   */
  public String toDataString() {
    return String.format("T|%s|%s", this.isDone ? "X" : "", this.taskName);
  }

  @Override
  /**
   * Returns a string representation of the task suitable for display.
   *
   * @return the task string representation
   */
  public String toString() {
    return String.format("[T][%s] %s", isDone ? "X" : "", this.taskName);
  }
}
