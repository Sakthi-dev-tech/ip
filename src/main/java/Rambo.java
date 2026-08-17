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
          Echo.start(scanner);
          break;
        case '2':
          System.out.print("Enter your task name: ");

          String taskName = scanner.nextLine();
          Task newTask = new Task(taskName);

          userTasks.add(newTask);

          System.out.println(Constants.ANSI_GREEN + "Your task has been added!" + Constants.ANSI_RESET);
          break;

        case '3':
          Constants.divider("TASK LIST");

          for (int i = 0; i < userTasks.size(); i++) {
            System.out.println(String.format("%d: %s", i + 1, userTasks.get(i).toString()));
          }

          break;

        case 'q':
          chatLoop = false;
          break;
        default:
          System.out.println(Constants.ANSI_RED + "That option doesn't exist, my friend! Try again!\n" + Constants.ANSI_RESET);
      }
    }

    scanner.close();
    bye();
  }
}
