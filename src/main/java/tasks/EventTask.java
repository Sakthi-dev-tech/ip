package tasks;

import java.time.LocalDate;
import functions.Functions;

public class EventTask extends Task {
  private LocalDate from;
  private LocalDate to;

  public EventTask(String taskName, String from, String to) {
    super(taskName);

    this.from = Functions.convertStringToDate(from);
    this.to = Functions.convertStringToDate(to);
  }

  @Override
  public String toDataString() {
    return String.format("E|%s|%s|%s|%s", super.checkIfDone() ? "X" : "", super.getTaskName(), this.from, this.to);
  }

  @Override
  public String toString() {
    return String.format("[E][%s] %s (from: %s to: %s)",
        super.checkIfDone() ? "X" : "",
        super.getTaskName(),
        this.from.format(this.DISPLAY_FORMAT),
        this.to.format(this.DISPLAY_FORMAT));
  }
}
