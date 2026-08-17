import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import tasks.*;
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
      // An indicator to show user input is required here
      System.out.print("Enter your option: ");
      char userOpt = scanner.nextLine().charAt(0);

      switch (userOpt) {
        case '1':
          // Echo Selected
          // Start the loop for echo app
          Echo.start(scanner);
          break;
        case '2':
          Constants.divider("TASK TYPE");
          // Add task function
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

          switch (typeOfTask) {
            case 1:
              System.out.print("Enter your task name: ");
              String taskName = scanner.nextLine();

              taskToBeAdded = new Task(taskName);
              break;

            case 2:
              System.out.print("Enter your task name: ");
              taskName = scanner.nextLine();

              System.out.print("Enter your deadline: ");
              String deadline = scanner.nextLine();

              taskToBeAdded = new DeadlineTask(taskName, deadline);
              break;

            case 3:
              System.out.print("Enter your task name: ");
              taskName = scanner.nextLine();

              System.out.print("Enter your from date: ");
              String from = scanner.nextLine();

              System.out.print("Enter your to date: ");
              String to = scanner.nextLine();

              taskToBeAdded = new EventTask(taskName, from, to);
              break;

            default:
              System.out.println(Constants.ANSI_RED + "Not a valid task type!" + Constants.ANSI_RESET);
              break;
          }

          if (taskToBeAdded != null) {
            userTasks.add(taskToBeAdded);
            System.out.println(Constants.ANSI_GREEN + "Your task has been added!" + Constants.ANSI_RESET);
          } else {
            System.out.println(Constants.ANSI_RED + "Your task has not been added!" + Constants.ANSI_RESET);
          }

          break;

        case '3':
          // List out current saved tasks
          Constants.divider("TASK LIST");

          for (int i = 0; i < userTasks.size(); i++) {
            System.out.println(String.format("%d: %s", i + 1, userTasks.get(i).toString()));
          }

          break;

        case '4':
          // Toggle if task is done or not
          System.out.print("Enter the index of the task you want to toggle status of: ");
          int noOfTasks = userTasks.size();
          int index;

          try {
            index = Integer.parseInt(scanner.nextLine());
          } catch (NumberFormatException e) {
            System.out.println(Constants.ANSI_RED + "Rambo needs a number to toggle!" + Constants.ANSI_RESET);
            break;
          }

          if (index - 1 < 0 || index - 1 >= noOfTasks) {
            System.out.println(Constants.ANSI_RED + "Index does not exist!" + Constants.ANSI_RESET);
            break;
          }

          userTasks.get(index - 1).toggleDone();
          break;

        case 'q':
          chatLoop = false;
          break;
        default:
          System.out.println(
              Constants.ANSI_RED + "That option doesn't exist, my friend! Try again!\n" + Constants.ANSI_RESET);
      }
    }

    scanner.close();
    bye();
  }
}
