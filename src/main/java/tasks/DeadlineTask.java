package tasks;

import java.time.LocalDate;
import functions.Functions;

public class DeadlineTask extends Task {
  private LocalDate deadline;

  public DeadlineTask(String taskName, String deadline) {
    super(taskName);

    this.deadline = Functions.convertStringToDate(deadline);
  }

  @Override
  public String toDataString() {
    return String.format("D|%s|%s|%s", super.checkIfDone() ? "X" : "", super.getTaskName(), this.deadline);
  }

  @Override
  public String toString() {
    return String.format("[D][%s] %s (by: %s)", super.checkIfDone() ? "X" : "", super.getTaskName(), this.deadline.format(this.DISPLAY_FORMAT));
  }
}
