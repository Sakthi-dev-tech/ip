package functions;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import tasks.Task;
import tasks.DeadlineTask;
import tasks.EventTask;
import java.io.IOException;
import exceptions.RamboException;

public class Functions {
  private static final Path DATA_FILE = Paths.get("./data/Rambo.txt");

  /*
   * ===================== TASK FUNCTIONS ======================
   */

  /**
   * Take a date string that is in the format YYYY-MM-DD and convert it into a LocalDate class
   */
  public static LocalDate convertStringToDate(String date) throws RamboException {
      try {
        return LocalDate.parse(date);
      } catch (DateTimeParseException e) {
        throw new RamboException("Please make sure your date is the following format (YYYY-MM-DD)");
      }
  }

  /**
   * Reads saved tasks from the data file and creates the corresponding task
   * objects.
   *
   * @return the tasks stored in the data file
   * @throws RamboException if the file cannot be read or contains an invalid
   *                        task record
   */
  public static List<Task> readTasks() throws RamboException {
    List<Task> tasks = new ArrayList<>();

    if (!Files.exists(DATA_FILE)) {
      return tasks;
    }

    List<String> lines;
    try {
      lines = Files.readAllLines(DATA_FILE);
    } catch (IOException e) {
      throw new RamboException("Could not read your saved tasks!", e);
    }

    for (String line : lines) {
      if (line.isBlank()) {
        continue;
      }

      String[] fields = line.split("\\|");
      for (int i = 0; i < fields.length; i++) {
        fields[i] = fields[i].trim();
      }

      Task task;
      switch (fields[0]) {
        case "T":
          if (fields.length != 3) {
            throw new RamboException("Invalid task record: " + line);
          }
          task = new Task(fields[2]);
          break;
        case "D":
          if (fields.length != 4) {
            throw new RamboException("Invalid task record: " + line);
          }
          task = new DeadlineTask(fields[2], fields[3]);
          break;
        case "E":
          if (fields.length != 5) {
            throw new RamboException("Invalid task record: " + line);
          }
          task = new EventTask(fields[2], fields[3], fields[4]);
          break;
        default:
          throw new RamboException("Invalid task record: " + line);
      }

      if (!fields[1].isEmpty() && !fields[1].equals("X")) {
        throw new RamboException("Invalid task record: " + line);
      }

      if (fields[1].equals("X")) {
        task.toggleDone();
      }

      tasks.add(task);
    }

    return tasks;
  }

  /**
   * Creates the data file if needed and appends one task to it.
   *
   * @param task the task to save
   * @throws RamboException if the data directory or file cannot be written
   */
  public static void addTaskToFile(Task task) throws RamboException {
    try {
      Files.createDirectories(DATA_FILE.getParent());
      Files.writeString(
          DATA_FILE,
          task.toDataString() + System.lineSeparator(),
          StandardCharsets.UTF_8,
          StandardOpenOption.CREATE,
          StandardOpenOption.APPEND);
    } catch (IOException e) {
      throw new RamboException("Could not save your task!", e);
    }
  }

  /**
   * Replaces the data file contents with the supplied tasks.
   *
   * @param tasks tasks to save
   * @throws RamboException if the data file cannot be written
   */
  private static void writeTasks(List<Task> tasks) throws RamboException {
    List<String> records = new ArrayList<>();
    for (Task task : tasks) {
      records.add(task.toDataString());
    }

    try {
      Files.write(DATA_FILE, records, StandardCharsets.UTF_8,
          StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
    } catch (IOException e) {
      throw new RamboException("Could not update your saved tasks!", e);
    }
  }

  /**
   * Toggles a task's done status and saves the updated task list.
   *
   * @param taskNumber the one-based task number shown to the user
   * @throws RamboException if the task number does not exist or the data file
   *                        cannot be read or written
   */
  public static void toggleTaskInFile(int taskNumber) throws RamboException {
    List<Task> tasks = readTasks();
    int index = taskNumber - 1;

    if (index < 0 || index >= tasks.size()) {
      throw new RamboException("I cannot find this task! Give a valid index!");
    }

    tasks.get(index).toggleDone();
    writeTasks(tasks);
  }

  /**
   * Deletes a task and saves the remaining task list.
   *
   * @param taskNumber the one-based task number shown to the user
   * @throws RamboException if the task number does not exist or the data file
   *                        cannot be read or written
   */
  public static void deleteTaskFromFile(int taskNumber) throws RamboException {
    List<Task> tasks = readTasks();
    int index = taskNumber - 1;

    if (index < 0 || index >= tasks.size()) {
      throw new RamboException("I cannot find this task! Give a valid index!");
    }

    tasks.remove(index);
    writeTasks(tasks);
  } 
}
