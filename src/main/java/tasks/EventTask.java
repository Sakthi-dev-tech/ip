package tasks;

import java.time.LocalDate;
import parser.Parser;

/**
 * Represents an event task with a name, from date, and to date.
 */
public class EventTask extends Task {
  private LocalDate from;
  private LocalDate to;

  /**
   * Constructs a new event task with the given name and date range.
   *
   * @param taskName the name of the task
   * @param from the from date string in YYYY-MM-DD format
   * @param to the to date string in YYYY-MM-DD format
   */
  public EventTask(String taskName, String from, String to) {
    super(taskName);

    this.from = Parser.parseDate(from);
    this.to = Parser.parseDate(to);
  }

  /**
   * Converts the task into the required string format for storage.
   *
   * @return the data string representation of the task
   */
  @Override
  public String toDataString() {
    return String.format("E|%s|%s|%s|%s", super.isDone() ? "X" : "", super.getTaskName(), this.from, this.to);
  }

  /**
   * Returns a string representation of the task suitable for display.
   *
   * @return the task string representation
   */
  @Override
  public String toString() {
    return String.format("[E][%s] %s (from: %s to: %s)",
        super.isDone() ? "X" : "",
        super.getTaskName(),
        this.from.format(Task.DISPLAY_FORMAT),
        this.to.format(Task.DISPLAY_FORMAT));
  }
}
