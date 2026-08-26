package functions;

import java.util.List;
import tasks.Task;
import exceptions.RamboException;
import storage.Storage;

public class Functions {
  /*
   * ===================== TASK FUNCTIONS ======================
   */

  /**
   * Toggles a task's done status and saves the updated task list.
   *
   * @param taskNumber the one-based task number shown to the user
   * @param storage storage used to load and save the tasks
   * @throws RamboException if the task number does not exist or the data file
   *                        cannot be read or written
   */
  public static void toggleTaskInFile(int taskNumber, Storage storage) throws RamboException {
    List<Task> tasks = storage.loadTasks();
    int index = taskNumber - 1;

    if (index < 0 || index >= tasks.size()) {
      throw new RamboException("I cannot find this task! Give a valid index!");
    }

    tasks.get(index).toggleDone();
    storage.saveTasks(tasks);
  }

  /**
   * Deletes a task and saves the remaining task list.
   *
   * @param taskNumber the one-based task number shown to the user
   * @param storage storage used to load and save the tasks
   * @throws RamboException if the task number does not exist or the data file
   *                        cannot be read or written
   */
  public static void deleteTaskFromFile(int taskNumber, Storage storage) throws RamboException {
    List<Task> tasks = storage.loadTasks();
    int index = taskNumber - 1;

    if (index < 0 || index >= tasks.size()) {
      throw new RamboException("I cannot find this task! Give a valid index!");
    }

    tasks.remove(index);
    storage.saveTasks(tasks);
  } 
}
