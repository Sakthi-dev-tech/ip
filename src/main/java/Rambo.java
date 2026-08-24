import java.util.List;
import java.util.Scanner;
import tasks.*;
import exceptions.*;

import functions.Functions;
import constants.Constants;

public class Rambo {

  public static void main(String[] args) {
    String options = "1) Echo\n"
        + "2) Add Task\n"
        + "3) List Tasks\n"
        + "4) Toggle Task Done Status\n"
        + "5) Delete Task\n"
        + "q) Quit\n";

    Scanner scanner = new Scanner(System.in);

    // When I first start this program, I would like to greet first
    Functions.home();

    boolean chatLoop = true;

    while (chatLoop) {
      // Show the options to the users
      Constants.divider();

      System.out.println(options);
      System.out.println("\n");

      System.out.print("Enter your option: ");
      if (!scanner.hasNextLine()) {
        break;
      }

      String input = scanner.nextLine();
      try {
        if (input.isEmpty()) {
          throw new RamboException("That option doesn't exist, my friend! Try again!");
        }

        char userOpt = input.charAt(0);

        switch (userOpt) {
          // Echo Selected
          case '1': {
            // Start the loop for echo app
            Echo.start(scanner);
            break;
          }

          // Adding task has been chosen
          case '2': {
            Constants.divider("TASK TYPE");
            System.out.println("1) Todo\n"
                + "2) Deadline\n"
                + "3) Event\n");
            System.out.println("\n");

            System.out.print("Choose the type of task you want to add: ");

            int typeOfTask;
            try {
              typeOfTask = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
              throw new RamboException("Give me a valid task type number!", e);
            }

            Task taskToBeAdded = null;

            /*
             * This will dictate the type of task the user chooses
             */
            switch (typeOfTask) {
              case 1: {
                System.out.print("Enter your task name: ");
                String taskName = scanner.nextLine();

                if (taskName.isBlank()) {
                  throw new RamboException("Task name cannot be blank!");
                }
                taskToBeAdded = new Task(taskName);
                break;
              }

              case 2: {
                System.out.print("Enter your task name: ");
                String taskName = scanner.nextLine();
                if (taskName.isBlank()) {
                  throw new RamboException("Task name cannot be blank!");
                }

                System.out.print("Enter your deadline: ");
                String deadline = scanner.nextLine();
                if (deadline.isBlank()) {
                  throw new RamboException("Deadline cannot be blank!");
                }

                taskToBeAdded = new DeadlineTask(taskName, deadline);
                break;
              }

              case 3: {
                System.out.print("Enter your task name: ");
                String taskName = scanner.nextLine();
                if (taskName.isBlank()) {
                  throw new RamboException("Task name cannot be blank!");
                }

                System.out.print("Enter your from date: ");
                String from = scanner.nextLine();
                if (from.isBlank()) {
                  throw new RamboException("From date cannot be blank!");
                }

                System.out.print("Enter your to date: ");
                String to = scanner.nextLine();
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

            Functions.addTaskToFile(taskToBeAdded);
            System.out.println(Constants.ANSI_GREEN + "\nYour task has been added!" + Constants.ANSI_RESET);

            break;
          }

          // List out current saved tasks
          case '3': {
            Constants.divider("TASK LIST");

            List<Task> tasksList = Functions.readTasks();

            for (int i = 0; i < tasksList.size(); i++) {
              System.out.println(String.format("%d: %s", i + 1, tasksList.get(i).toString()));
            }

            // Should read a data file, and create the userTasks array

            break;
          }

          // Toggle if task is done or not
          case '4': {
            System.out.print("Enter the index of the task you want to toggle status of: ");
            int index;

            try {
              index = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
              throw new RamboException("Give me a valid number!", e);
            }

            Functions.toggleTaskInFile(index);

            break;
          }

          // Delete task has been selected
          case '5': {
            System.out.print("Enter the index of the task you want to remove: ");
            int index;

            try {
              index = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
              throw new RamboException("Give me a valid number!", e);
            }

            Functions.deleteTaskFromFile(index);

            break;
          }

          // Quit the loop
          case 'q': {
            chatLoop = false;
            break;
          }
          default: {
            throw new RamboException("That option doesn't exist, my friend! Try again!");
          }
        }
        // All RamboExceptions will be caught here
      } catch (RamboException e) {
        System.out.println(Constants.ANSI_RED + e.getMessage() + Constants.ANSI_RESET);
      }
    }

    scanner.close();
    Functions.bye();
  }
}
