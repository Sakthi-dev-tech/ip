package tasks;

public class Task {
  private String taskName;
  private boolean isDone;

  public Task(String taskName) {
    this.taskName = taskName;
    this.isDone = false;
  }

  public String getTaskName() {
    return this.taskName;
  }

  public boolean toggleDone() {
    this.isDone = !this.isDone;
    return this.isDone;
  }

  @Override
  public String toString() {
    return String.format("[%s] %s", isDone ? "X" : "", this.taskName);
  }
}
