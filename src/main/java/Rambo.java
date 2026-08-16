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

    String options = "1) Echo\n"
      + "q) Quit\n"
      + "Enter your option: ";

    Constants.divider();
    System.out.println(banner);
    System.out.println("Hello! I am Rambo.\nWhat can I do for you?");
    System.out.println("\n");
    System.out.println(options);
    System.out.println("\n");
  }

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    // When I first start this program, I would like to greet first
    home();
    
    System.out.print("> ");
    
    boolean chatLoop = true;
    while (chatLoop) {
      char userOpt = scanner.next().charAt(0);

      switch (userOpt) {
        case '1':
          System.out.println("Echo Selected!");
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
