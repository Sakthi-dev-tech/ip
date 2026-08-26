import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import tasks.Task;
import tasks.TaskList;
import ui.Ui;
import tasks.DeadlineTask;
import tasks.EventTask;

import exceptions.RamboException;

import constants.Constants;
import parser.Parser;
import storage.Storage;

public class Rambo {

  public static void main(String[] args) {
    String options = "1) Echo\n"
        + "2) Add Task\n"
        + "3) List Tasks (use: 3 <keyword> to search)\n"
        + "4) Toggle Task Done Status\n"
        + "5) Delete Task\n"
        + "q) Quit\n";

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
