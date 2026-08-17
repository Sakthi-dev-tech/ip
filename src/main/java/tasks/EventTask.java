package tasks;

public class EventTask extends Task {
  private String from;
  private String to;

  public EventTask(String taskName, String from, String to) {
    super(taskName);
    
    this.from = from;
    this.to = to;
  }


  @Override
  public String toString() {
    return String.format("[E][%s] %s (from: %s to: %s)", super.checkIfDone() ? "X" : "", super.getTaskName(), this.from, this.to);
  }
}
