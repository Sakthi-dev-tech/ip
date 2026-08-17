package constants;

// This class will have constant items that I will use throughout the project
public class Constants {
  /**
   * A simple divider to clearly define different sections
   */
  public static void divider() {
    System.out.println("===========================================================");
  }

  public static void divider(String appName) {
    System.out.println(String.format("============================%s==============================", appName));
    System.out.println("\n");
  }

  public static final String ANSI_GREEN = "\u001B[32m";
  public static final String ANSI_RED = "\033[91m";
  public static final String ANSI_RESET = "\u001B[0m";
}
