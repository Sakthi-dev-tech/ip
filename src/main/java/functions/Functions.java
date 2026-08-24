package functions;

import java.util.ArrayList;
import java.util.List;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import tasks.*;
import java.io.IOException;
import constants.Constants;
import exceptions.RamboException;

public class Functions {
  private static final Path DATA_FILE = Paths.get("./data/Rambo.txt");

  /**
   * Reads saved tasks from the data file and creates the corresponding task
   * objects.
   *
   * @return the tasks stored in the data file
   * @throws IOException if the file cannot be read or contains an invalid task
   *                     record
   */
  public static List<Task> readTasks() throws IOException {
    List<Task> tasks = new ArrayList<>();

    for (String line : Files.readAllLines(DATA_FILE)) {
      if (line.isBlank()) {
        continue;
      }

      String[] fields = line.split("\\|");
      for (int i = 0; i < fields.length; i++) {
        fields[i] = fields[i].trim();
      }

      try {
        Task task;
        switch (fields[0]) {
          case "T":
            if (fields.length != 3) {
              throw new IllegalArgumentException();
            }
            task = new Task(fields[2]);
            break;
          case "D":
            if (fields.length != 4) {
              throw new IllegalArgumentException();
            }
            task = new DeadlineTask(fields[2], fields[3]);
            break;
          case "E":
            if (fields.length != 5) {
              throw new IllegalArgumentException();
            }
            task = new EventTask(fields[2], fields[3], fields[4]);
            break;
          default:
            throw new IllegalArgumentException();
        }

        if (!fields[1].isEmpty() && !fields[1].equals("X")) {
          throw new IllegalArgumentException();
        }

        if (fields[1].equals("X")) {
          task.toggleDone();
        }

        tasks.add(task);
      } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
        throw new IOException("Invalid task record: " + line, e);
      }
    }

    return tasks;
  }

  /**
   * Creates the data file if needed and appends one task to it.
   *
   * @param task the task to save
   * @throws IOException if the data directory or file cannot be written
   */
  public static void addTaskToFile(Task task) throws IOException {
    Files.createDirectories(DATA_FILE.getParent());
    Files.writeString(
        DATA_FILE,
        task.toDataString() + System.lineSeparator(),
        StandardCharsets.UTF_8,
        StandardOpenOption.CREATE,
        StandardOpenOption.APPEND);
  }

  /**
   * Replaces the data file contents with the supplied tasks.
   *
   * @param tasks tasks to save
   * @throws IOException if the data file cannot be written
   */
  private static void writeTasks(List<Task> tasks) throws IOException {
    List<String> records = new ArrayList<>();
    for (Task task : tasks) {
      records.add(task.toDataString());
    }

    Files.write(DATA_FILE, records, StandardCharsets.UTF_8,
        StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
  }

  /**
   * Toggles a task's done status and saves the updated task list.
   *
   * @param taskNumber the one-based task number shown to the user
   * @throws IOException    if the data file cannot be read or written
   * @throws RamboException if the task number does not exist
   */
  public static void toggleTaskInFile(int taskNumber) throws IOException, RamboException {
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
   * @throws IOException if the data file cannot be read or written
   * @throws RamboException if the task number does not exist
   */
  public static void deleteTaskFromFile(int taskNumber) throws IOException, RamboException {
    List<Task> tasks = readTasks();
    int index = taskNumber - 1;

    if (index < 0 || index >= tasks.size()) {
      throw new RamboException("I cannot find this task! Give a valid index!");
    }

    tasks.remove(index);
    writeTasks(tasks);
  }

  /**
   * Renders a welcome message
   */
  public static void home() {
    String banner = " (                           \n"
        + ")\\ )                 )\n"
        + "(()/(   )    )    ( /(\n"
        + " /(_)| /(   (     )\\()) (\n"
        + "(_)) )(_))  )\\  '((_)\\  )\\\n"
        + "| _ ((_)_ _((_)) | |(_)((_)\n"
        + "|   / _` | '  \\()| '_ Y _ \\\n"
        + "|_|_\\__,_|_|_|_| |_.__|___/\n"
        + "                            ";

    Constants.divider();
    System.out.println(banner);
    System.out.println("Hello! I am Rambo.\nWhat can I do for you?");
    System.out.println("\n");
  }

  /**
   * Renders a bye message
   */
  public static void bye() {
    Constants.divider();
    System.out.println("Bye my friend!");
  }
}
