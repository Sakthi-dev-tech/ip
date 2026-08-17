import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import tasks.*;
import exceptions.*;
import constants.Constants;

public class Rambo {

  private static void bye() {
    Constants.divider();
    System.out.println("Bye my friend!");
  }

  private static void home() {
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

  public static void main(String[] args) {
    /*
     * App state will be stored here
     */
    final List<Task> userTasks = new ArrayList<>();

    String options = "1) Echo\n"
        + "2) Add Task\n"
        + "3) List Tasks\n"
        + "4) Toggle Task Done Status\n"
        + "5) Delete Task\n"
        + "q) Quit\n";

    Scanner scanner = new Scanner(System.in);

    // When I first start this program, I would like to greet first
    home();

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
      if (input.isEmpty()) {
        System.out.println(
            Constants.ANSI_RED + "That option doesn't exist, my friend! Try again!" + Constants.ANSI_RESET);
        continue;
      }

      char userOpt = input.charAt(0);

      try {
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
              System.out.println(Constants.ANSI_RED + "Rambo needs a number to toggle!" + Constants.ANSI_RESET);
              break;
            }

            Task taskToBeAdded = null;

            /*
             * This will dictate the type of task the user chooses
             *
             * @param An integer to choose the type
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

            userTasks.add(taskToBeAdded);
            System.out.println(Constants.ANSI_GREEN + "Your task has been added!" + Constants.ANSI_RESET);

            break;
          }

          // List out current saved tasks
          case '3': {
            Constants.divider("TASK LIST");

            for (int i = 0; i < userTasks.size(); i++) {
              System.out.println(String.format("%d: %s", i + 1, userTasks.get(i).toString()));
            }

            break;
          }

          // Toggle if task is done or not
          case '4': {
            System.out.print("Enter the index of the task you want to toggle status of: ");
            int noOfTasks = userTasks.size();
            int index;

            try {
              index = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
              throw new RamboException("Give Rambo a valid number!");
            }

            if (index - 1 < 0 || index - 1 >= noOfTasks) {
              throw new RamboException("I cannot find this task! Give a valid index!");
            }

            userTasks.get(index - 1).toggleDone();
            break;
          }

          // Delete task has been selected
          case '5': {
            System.out.print("Enter the index of the task you want to remove: ");
            int noOfTasks = userTasks.size();
            int index;

            try {
              index = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
              throw new RamboException("Give Rambo a valid number!");
            }

            if (index - 1 < 0 || index - 1 >= noOfTasks) {
              throw new RamboException("I cannot find this task! Give a valid index!");
            }

            userTasks.remove(index - 1);
            break;
          }

          // Quit the loop
          case 'q': {
            chatLoop = false;
            break;
          }
          default: {
            System.out.println(
                Constants.ANSI_RED + "That option doesn't exist, my friend! Try again!\n" + Constants.ANSI_RESET);
          }
        }
      } catch (RamboException e) {
        System.out.println(Constants.ANSI_RED + "Rambo: " + e.getMessage() + Constants.ANSI_RESET);
      }
    }

    scanner.close();
    bye();
  }
}
