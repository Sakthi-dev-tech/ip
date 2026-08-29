package rambo.task;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests the storage and display formats of {@link EventTask}.
 */
public class EventTaskTest {
    @Test
    void toDataString_eventTask_returnsStorageFormat() {
        EventTask task = new EventTask("project meeting", "2026-09-20", "2026-09-22");

        assertEquals("E||project meeting|2026-09-20|2026-09-22", task.toDataString());
    }

    @Test
    void toDataString_completedEventTask_includesDoneStatus() {
        EventTask task = new EventTask("project meeting", "2026-09-20", "2026-09-22");
        task.toggleDone();

        assertEquals("E|X|project meeting|2026-09-20|2026-09-22", task.toDataString());
    }

    @Test
    void toString_eventTask_returnsFriendlyDateRange() {
        EventTask task = new EventTask("project meeting", "2026-09-20", "2026-09-22");

        assertEquals("[E][] project meeting (from: Sep 20 2026 to: Sep 22 2026)", task.toString());
    }
}
