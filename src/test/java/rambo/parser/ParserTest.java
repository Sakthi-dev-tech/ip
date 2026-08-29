package rambo.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import rambo.exception.RamboException;

/**
 * Tests the conversion and validation of user input performed by {@link Parser}.
 */
public class ParserTest {
    private final Parser parser = new Parser();

    @Test
    void parseCommand_validCommand_returnsCommandCharacter() {
        assertEquals('q', parser.parseCommand("q"));
    }

    @Test
    void parseCommand_unknownCommand_throwsRamboException() {
        assertThrows(RamboException.class, () -> parser.parseCommand("x"));
    }

    @Test
    void parseCommand_blankInput_throwsRamboException() {
        assertThrows(RamboException.class, () -> parser.parseCommand(""));
    }

    @Test
    void parseTaskType_numericInput_returnsTaskType() {
        assertEquals(2, parser.parseTaskType("2"));
    }

    @Test
    void parseTaskType_nonNumericInput_throwsRamboException() {
        assertThrows(RamboException.class, () -> parser.parseTaskType("not-a-number"));
    }

    @Test
    void parseTaskNumber_numericInput_returnsTaskNumber() {
        assertEquals(3, parser.parseTaskNumber("3"));
    }

    @Test
    void parseTaskNumber_nonNumericInput_throwsRamboException() {
        assertThrows(RamboException.class, () -> parser.parseTaskNumber("first"));
    }

    @Test
    void parseSearchTerm_commandWithKeyword_returnsTrimmedKeyword() {
        assertEquals("milk", parser.parseSearchTerm("3   milk "));
    }

    @Test
    void parseDate_isoDate_returnsLocalDate() {
        assertEquals(LocalDate.of(2026, 9, 15), Parser.parseDate("2026-09-15"));
    }

    @Test
    void parseDate_invalidDate_throwsRamboException() {
        assertThrows(RamboException.class, () -> Parser.parseDate("2026-02-30"));
    }
}
