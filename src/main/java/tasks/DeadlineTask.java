package tasks;

public class DeadlineTask extends Task {
  private String deadline;

  public DeadlineTask(String taskName, String deadline) {
    super(taskName);

    this.deadline = deadline;
  }

  @Override
  public String toDataString() {
    return String.format("D|%s|%s|%s", super.checkIfDone() ? "X" : "", super.getTaskName(), this.deadline);
  }

  @Override
  public String toString() {
    return String.format("[D][%s] %s (by: %s)", super.checkIfDone() ? "X" : "", super.getTaskName(), this.deadline);
  }
}
