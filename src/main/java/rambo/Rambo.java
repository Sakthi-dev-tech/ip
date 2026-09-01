package rambo;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import rambo.exception.RamboException;
import rambo.parser.Parser;
import rambo.storage.Storage;
import rambo.task.DeadlineTask;
import rambo.task.EventTask;
import rambo.task.Task;
import rambo.task.TaskList;
import rambo.ui.Constants;
import rambo.ui.Ui;

/**
 * Main application class for Rambo.
 */
public class Rambo {
    private static final String EVENT_USAGE = "event TASK_NAME /from YYYY-MM-DD /to YYYY-MM-DD";
    private static final String HELP_MESSAGE = "Try one of these commands:\n"
            + "list\n"
            + "todo TASK_NAME\n"
            + "deadline TASK_NAME /by YYYY-MM-DD\n"
            + EVENT_USAGE + "\n"
            + "done TASK_NUMBER\n"
            + "delete TASK_NUMBER\n"
            + "bye";

    private final Parser parser;
    private final Storage storage;
    private final TaskList taskList;
    private boolean isExitRequested;

    /**
     * Creates a Rambo instance that can respond to one-line GUI commands.
     */
    public Rambo() {
        this.parser = new Parser();
        this.storage = new Storage();
        this.taskList = new TaskList(storage.loadTasks());
        this.isExitRequested = false;
    }

    /**
     * Returns Rambo's response to a one-line command entered in the GUI.
     *
     * @param input text entered by the user
     * @return response to show in the chat window
     */
    public String getResponse(String input) {
        try {
            return executeGuiCommand(input.trim());
        } catch (RamboException e) {
            return e.getMessage();
        }
    }

    /**
     * Returns the message shown when Rambo first starts.
     *
     * @return welcome message and command list
     */
    public String getWelcomeMessage() {
        return "Hello! I am Rambo.\nWhat can I do for you?\n\n" + HELP_MESSAGE;
    }

    /**
     * Returns whether the user has asked to leave the GUI session.
     *
     * @return true if the GUI should stop accepting input
     */
    public boolean isExitRequested() {
        return isExitRequested;
    }

    private String executeGuiCommand(String input) throws RamboException {
        if (input.isEmpty()) {
            throw new RamboException("Please enter a command.");
        }

        String lowerCaseInput = input.toLowerCase(Locale.ROOT);
        if (lowerCaseInput.equals("bye") || lowerCaseInput.equals("q")) {
            isExitRequested = true;
            return "Bye my friend!";
        }
        if (lowerCaseInput.equals("help")) {
            return HELP_MESSAGE;
        }
        if (lowerCaseInput.equals("list")) {
            return getTaskListResponse("");
        }
        if (lowerCaseInput.startsWith("find ")) {
            return getTaskListResponse(input.substring("find".length()).trim());
        }
        if (lowerCaseInput.startsWith("todo ")) {
            return addTodo(input.substring("todo".length()).trim());
        }
        if (lowerCaseInput.startsWith("deadline ")) {
            return addDeadline(input.substring("deadline".length()).trim());
        }
        if (lowerCaseInput.startsWith("event ")) {
            return addEvent(input.substring("event".length()).trim());
        }
        if (lowerCaseInput.startsWith("done ")) {
            return toggleTask(input.substring("done".length()).trim());
        }
        if (lowerCaseInput.startsWith("toggle ")) {
            return toggleTask(input.substring("toggle".length()).trim());
        }
        if (lowerCaseInput.startsWith("delete ")) {
            return deleteTask(input.substring("delete".length()).trim());
        }

        throw new RamboException("I do not understand that command.\n" + HELP_MESSAGE);
    }

    private String addTodo(String taskName) throws RamboException {
        validateNotBlank(taskName, "Task name cannot be blank!");
        Task task = new Task(taskName);
        taskList.add(task);
        storage.saveTasks(taskList.getTasks());
        return getAddedTaskResponse(task);
    }

    private String addDeadline(String taskDetails) throws RamboException {
        String[] fields = splitRequiredMarker(taskDetails, "/by", "Use: deadline TASK_NAME /by YYYY-MM-DD");
        Task task = new DeadlineTask(fields[0], fields[1]);
        taskList.add(task);
        storage.saveTasks(taskList.getTasks());
        return getAddedTaskResponse(task);
    }

    private String addEvent(String taskDetails) throws RamboException {
        String[] fromFields = splitRequiredMarker(taskDetails, "/from", EVENT_USAGE);
        String[] toFields = splitRequiredMarker(fromFields[1], "/to", EVENT_USAGE);
        Task task = new EventTask(fromFields[0], toFields[0], toFields[1]);
        taskList.add(task);
        storage.saveTasks(taskList.getTasks());
        return getAddedTaskResponse(task);
    }

    private String toggleTask(String taskNumberText) throws RamboException {
        int taskNumber = parser.parseTaskNumber(taskNumberText);
        taskList.toggle(taskNumber);
        storage.saveTasks(taskList.getTasks());
        return "Nice! I've updated this task:\n  " + getTask(taskNumber);
    }

    private String deleteTask(String taskNumberText) throws RamboException {
        int taskNumber = parser.parseTaskNumber(taskNumberText);
        Task taskToDelete = getTask(taskNumber);
        taskList.delete(taskNumber);
        storage.saveTasks(taskList.getTasks());
        return "Noted. I've removed this task:\n  " + taskToDelete;
    }

    private String getTaskListResponse(String searchTerm) {
        List<Task> tasksList = taskList.getTasks();
        if (tasksList.isEmpty()) {
            return "Your task list is empty.";
        }

        StringBuilder response = new StringBuilder();
        String normalisedSearchTerm = searchTerm.toLowerCase(Locale.ROOT);
        for (int i = 0; i < tasksList.size(); i++) {
            Task task = tasksList.get(i);
            if (searchTerm.isEmpty() || task.getTaskName().toLowerCase(Locale.ROOT).contains(normalisedSearchTerm)) {
                response.append(i + 1).append(". ").append(task).append(System.lineSeparator());
            }
        }

        if (response.isEmpty()) {
            return String.format("No tasks found matching \"%s\".", searchTerm);
        }
        return response.toString().stripTrailing();
    }

    private String getAddedTaskResponse(Task task) {
        return String.format("Got it. I've added this task:%n  %s%nNow you have %d task(s) in the list.",
                task, taskList.getTasks().size());
    }

    private Task getTask(int taskNumber) throws RamboException {
        int index = taskNumber - 1;
        List<Task> tasksList = taskList.getTasks();
        if (index < 0 || index >= tasksList.size()) {
            throw new RamboException("I cannot find this task! Give a valid index!");
        }
        return tasksList.get(index);
    }

    private String[] splitRequiredMarker(String input, String marker, String usageMessage) throws RamboException {
        int markerIndex = input.indexOf(marker);
        if (markerIndex < 0) {
            throw new RamboException(usageMessage);
        }

        String beforeMarker = input.substring(0, markerIndex).trim();
        String afterMarker = input.substring(markerIndex + marker.length()).trim();
        validateNotBlank(beforeMarker, usageMessage);
        validateNotBlank(afterMarker, usageMessage);
        return new String[] { beforeMarker, afterMarker };
    }

    private void validateNotBlank(String value, String errorMessage) throws RamboException {
        if (value.isBlank()) {
            throw new RamboException(errorMessage);
        }
    }

  public static void main(String[] args) {
    String options = "1) Echo\n"
        + "2) Add Task\n"
        + "3) List Tasks (use: 3 <keyword> to search)\n"
        + "4) Toggle Task Done Status\n"
        + "5) Delete Task\n"
        + "q or bye) Quit\n";

    Scanner scanner = new Scanner(System.in);
    Ui ui = new Ui(scanner);
    Parser parser = new Parser();
    Storage storage = new Storage();

    // When I first start this program, I would like to greet first
    ui.showWelcome();

    TaskList taskList;
    try {
      taskList = new TaskList(storage.loadTasks());
    } catch (RamboException e) {
      ui.showLine(Constants.ANSI_RED + e.getMessage() + Constants.ANSI_RESET);
      ui.showGoodbye();
      ui.close();
      return;
    }

    boolean isChatRunning = true;

    while (isChatRunning) {
      // Show the options to the users
      ui.showDivider();

      ui.showLine(options);
      ui.showLine("\n");

      ui.showPrompt("Enter your option: ");
      if (!ui.hasNextLine()) {
        break;
      }

      String input = ui.readLine();
      try {
        char userOpt = parser.parseCommand(input);

        switch (userOpt) {
          // Echo Selected
          case '1': {
            // Start the loop for echo app
            Echo.start(ui);
            break;
          }

          // Adding task has been chosen
          case '2': {
            ui.showDivider("TASK TYPE");
            ui.showLine("1) Todo\n"
                + "2) Deadline\n"
                + "3) Event\n");
            ui.showLine("\n");

            ui.showPrompt("Choose the type of task you want to add: ");

            int typeOfTask = parser.parseTaskType(ui.readLine());

            Task taskToBeAdded = null;

            /*
             * This will dictate the type of task the user chooses
             */
            switch (typeOfTask) {
              case 1: {
                ui.showPrompt("Enter your task name: ");
                String taskName = ui.readLine();

                if (taskName.isBlank()) {
                  throw new RamboException("Task name cannot be blank!");
                }
                taskToBeAdded = new Task(taskName);
                break;
              }

              case 2: {
                ui.showPrompt("Enter your task name: ");
                String taskName = ui.readLine();
                if (taskName.isBlank()) {
                  throw new RamboException("Task name cannot be blank!");
                }

                ui.showPrompt("Enter your deadline: ");
                String deadline = ui.readLine();
                if (deadline.isBlank()) {
                  throw new RamboException("Deadline cannot be blank!");
                }

                taskToBeAdded = new DeadlineTask(taskName, deadline);
                break;
              }

              case 3: {
                ui.showPrompt("Enter your task name: ");
                String taskName = ui.readLine();
                if (taskName.isBlank()) {
                  throw new RamboException("Task name cannot be blank!");
                }

                ui.showPrompt("Enter your from date: ");
                String from = ui.readLine();
                if (from.isBlank()) {
                  throw new RamboException("From date cannot be blank!");
                }

                ui.showPrompt("Enter your to date: ");
                String to = ui.readLine();
                if (to.isBlank()) {
                  throw new RamboException("To date cannot be blank!");
                }

                taskToBeAdded = new EventTask(taskName, from, to);
                break;
              }

              default: {
                throw new RamboException("Not a valid task type!");
              }
            }

            taskList.add(taskToBeAdded);
            storage.saveTasks(taskList.getTasks());
            ui.showLine(Constants.ANSI_GREEN + "\nYour task has been added!" + Constants.ANSI_RESET);

            break;
          }

          // List out current saved tasks
          case '3': {
            ui.showDivider("TASK LIST");

            List<Task> tasksList = taskList.getTasks();
            String searchTerm = parser.parseSearchTerm(input);
            String normalisedSearchTerm = searchTerm.toLowerCase(Locale.ROOT);
            boolean foundMatchingTask = false;

            for (int i = 0; i < tasksList.size(); i++) {
              Task task = tasksList.get(i);
              if (searchTerm.isEmpty()
                  || task.getTaskName().toLowerCase(Locale.ROOT).contains(normalisedSearchTerm)) {
                ui.showLine(String.format("%d: %s", i + 1, task.toString()));
                foundMatchingTask = true;
              }
            }

            if (!searchTerm.isEmpty() && !foundMatchingTask) {
              ui.showLine(String.format("No tasks found matching \"%s\".", searchTerm));
            }

            break;
          }

          // Toggle if task is done or not
          case '4': {
            ui.showPrompt("Enter the index of the task you want to toggle status of: ");
            int index = parser.parseTaskNumber(ui.readLine());

            taskList.toggle(index);
            storage.saveTasks(taskList.getTasks());

            break;
          }

          // Delete task has been selected
          case '5': {
            ui.showPrompt("Enter the index of the task you want to remove: ");
            int index = parser.parseTaskNumber(ui.readLine());

            taskList.delete(index);
            storage.saveTasks(taskList.getTasks());

            break;
          }

          // Quit the loop
          case 'q': {
            isChatRunning = false;
            break;
          }
          default: {
            throw new RamboException("That option doesn't exist, my friend! Try again!");
          }
        }
        // All RamboExceptions will be caught here
      } catch (RamboException e) {
        ui.showLine(Constants.ANSI_RED + e.getMessage() + Constants.ANSI_RESET);
      }
    }

    ui.close();
    ui.showGoodbye();
  }
}
