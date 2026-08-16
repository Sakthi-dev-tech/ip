public class Rambo {
  private static void greeting() {
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
  }

  private static void bye() {
    Constants.divider();
    System.out.println("Bye my friend!");
  }

  public static void main(String[] args) {
    // When I first start this program, I would like to greet first
    greeting();
    bye();
  }
}
