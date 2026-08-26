package tasks;

import java.time.LocalDate;
import parser.Parser;

/**
 * Represents a deadline task with a name and a deadline date.
 */
public class DeadlineTask extends Task {
  private LocalDate deadline;

  /**
   * Constructs a new deadline task with the given name and deadline string.
   *
   * @param taskName the name of the task
   * @param deadline the deadline date string in YYYY-MM-DD format
   */
  public DeadlineTask(String taskName, String deadline) {
    super(taskName);

    this.deadline = Parser.parseDate(deadline);
  }

  /**
   * Converts the task into the required string format for storage.
   *
   * @return the data string representation of the task
   */
  @Override
  public String toDataString() {
    return String.format("D|%s|%s|%s", super.isDone() ? "X" : "", super.getTaskName(), this.deadline);
  }

  /**
   * Returns a string representation of the task suitable for display.
   *
   * @return the task string representation
   */
  @Override
  public String toString() {
    return String.format("[D][%s] %s (by: %s)", super.isDone() ? "X" : "", super.getTaskName(), this.deadline.format(Task.DISPLAY_FORMAT));
  }
}
