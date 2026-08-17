import java.util.Scanner;

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
    String options = "1) Echo\n"
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
      char userOpt = scanner.next().charAt(0);

      switch (userOpt) {
        case '1':
          Echo.start(scanner);
          break;
        case 'q':
          chatLoop = false;
          break;
        default:
          System.out.println("That option doesn't exist, my friend! Try again!\n");
      }
    }

    scanner.close();
    bye();
  }
}
