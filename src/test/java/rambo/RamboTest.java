package rambo;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

/**
 * Tests representative complete Rambo sessions using simulated input and captured output.
 */
public class RamboTest {
    private static final Path DATA_FILE = Path.of("./data/Rambo.txt");

    @Test
    void run_invalidInputs_rejectsInputsAndContinuesSession() throws IOException {
        String output = runRambo("x\n\n2\nnot-a-number\nq\n");

        assertOutputContainsInOrder(output,
                "Rambo: That option doesn't exist, my friend! Try again!",
                "Rambo: That option doesn't exist, my friend! Try again!",
                "Rambo: Give me a valid task type number!",
                "Bye my friend!");
    }

    @Test
    void run_commandLikeAndUnicodeEchoText_preservesText() throws IOException {
        String output = runRambo("1\nq; rm -rf /\n你好 👋\n/exit\nq\n");

        assertOutputContainsInOrder(output,
                "You: Rambo: q; rm -rf /",
                "You: Rambo: 你好 👋",
                "You: Back to home!",
                "Bye my friend!");
    }

    @Test
    void run_taskWorkflow_addsTogglesDeletesAndSearchesTasks() throws IOException {
        String input = "2\n1\nbuy milk\n"
                + "2\n1\nread book\n"
                + "4\n1\n"
                + "5\n2\n"
                + "3 MILK\nq\n";
        String output = runRambo(input);

        assertOutputContainsInOrder(output,
                "Your task has been added!",
                "Your task has been added!",
                "Enter the index of the task you want to toggle status of:",
                "Enter the index of the task you want to remove:",
                "1: [T][X] buy milk",
                "Bye my friend!");
        assertFalse(output.contains("2: [T][] read book"));
    }

    @Test
    void run_addDeadlineAndEvent_displaysFriendlyDates() throws IOException {
        String input = "2\n2\nsubmit report\n2026-09-15\n"
                + "2\n3\nproject meeting\n2026-09-20\n2026-09-22\n"
                + "3\nq\n";
        String output = runRambo(input);

        assertOutputContainsInOrder(output,
                "1: [D][] submit report (by: Sep 15 2026)",
                "2: [E][] project meeting (from: Sep 20 2026 to: Sep 22 2026)",
                "Bye my friend!");
    }

    @Test
    void run_endOfInputAtMainMenu_exitsCleanly() throws IOException {
        String output = runRambo("");

        assertOutputContainsInOrder(output,
                "Enter your option:",
                "Bye my friend!");
    }

    @Test
    void run_byeAtMainMenu_exitsCleanly() throws IOException {
        String output = runRambo("bye\n");

        assertOutputContainsInOrder(output,
                "q or bye) Quit",
                "Bye my friend!");
    }

    @Test
    void getResponse_taskCommands_updatesAndListsTasks() throws IOException {
        Files.deleteIfExists(DATA_FILE);
        try {
            Rambo rambo = new Rambo();

            assertTrue(rambo.getResponse("todo buy milk").contains("[T][] buy milk"));
            assertTrue(rambo.getResponse("deadline submit report /by 2026-09-15")
                    .contains("[D][] submit report (by: Sep 15 2026)"));
            assertTrue(rambo.getResponse("done 1").contains("[T][X] buy milk"));
            assertTrue(rambo.getResponse("list").contains("2. [D][] submit report (by: Sep 15 2026)"));
        } finally {
            Files.deleteIfExists(DATA_FILE);
        }
    }

    @Test
    void getWelcomeMessage_returnsCommandsToShowAtStartup() {
        Rambo rambo = new Rambo();

        assertTrue(rambo.getWelcomeMessage().contains("todo TASK_NAME"));
        assertTrue(rambo.getWelcomeMessage().contains("deadline TASK_NAME /by YYYY-MM-DD"));
        assertTrue(rambo.getWelcomeMessage().contains("bye"));
    }

    /**
     * Runs Rambo with in-memory input and output in Gradle's disposable test working directory.
     *
     * @param input simulated lines entered by the user
     * @return all text displayed during the session
     */
    private String runRambo(String input) throws IOException {
        ByteArrayOutputStream outputBytes = new ByteArrayOutputStream();
        InputStream originalInput = System.in;
        PrintStream originalOutput = System.out;

        Files.deleteIfExists(DATA_FILE);
        try (ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
                PrintStream testOutput = new PrintStream(outputBytes, true, StandardCharsets.UTF_8)) {
            System.setIn(testInput);
            System.setOut(testOutput);
            Rambo.main(new String[0]);
        } finally {
            System.setIn(originalInput);
            System.setOut(originalOutput);
            Files.deleteIfExists(DATA_FILE);
        }
        return outputBytes.toString(StandardCharsets.UTF_8);
    }

    /**
     * Checks that each expected fragment occurs after the previous fragment.
     * The captured output is included in the failure message to simplify debugging.
     */
    private static void assertOutputContainsInOrder(String output, String... expectedFragments) {
        int nextSearchIndex = 0;
        for (String expectedFragment : expectedFragments) {
            int fragmentIndex = output.indexOf(expectedFragment, nextSearchIndex);
            assertTrue(fragmentIndex >= 0,
                    "Expected output fragment not found in order: " + expectedFragment
                            + System.lineSeparator() + "Captured output:" + System.lineSeparator() + output);
            nextSearchIndex = fragmentIndex + expectedFragment.length();
        }
    }
}
