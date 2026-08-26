package storage;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import exceptions.RamboException;
import tasks.DeadlineTask;
import tasks.EventTask;
import tasks.Task;

/**
 * Loads tasks from a file and saves tasks to that file.
 */
public class Storage {
    private final Path dataFile;

    /**
     * Creates a storage manager for the given file path.
     *
     * @param filePath path of the task data file
     */
    public Storage(String filePath) {
        this.dataFile = Paths.get(filePath);
    }

    /**
     * Loads saved tasks and creates the corresponding task objects.
     *
     * @return tasks stored in the data file
     * @throws RamboException if the file cannot be read or contains an invalid task record
     */
    public List<Task> loadTasks() throws RamboException {
        List<Task> tasks = new ArrayList<>();

        if (!Files.exists(dataFile)) {
            return tasks;
        }

        List<String> lines;
        try {
            lines = Files.readAllLines(dataFile);
        } catch (IOException e) {
            throw new RamboException("Could not read your saved tasks!", e);
        }

        for (String line : lines) {
            if (line.isBlank()) {
                continue;
            }

            tasks.add(createTask(line));
        }

        return tasks;
    }

    /**
     * Appends one task to the data file, creating the file if needed.
     *
     * @param task task to save
     * @throws RamboException if the data directory or file cannot be written
     */
    public void appendTask(Task task) throws RamboException {
        try {
            Files.createDirectories(dataFile.getParent());
            Files.writeString(
                    dataFile,
                    task.toDataString() + System.lineSeparator(),
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RamboException("Could not save your task!", e);
        }
    }

    /**
     * Replaces the data file contents with the supplied tasks.
     *
     * @param tasks tasks to save
     * @throws RamboException if the data file cannot be written
     */
    public void saveTasks(List<Task> tasks) throws RamboException {
        List<String> records = new ArrayList<>();
        for (Task task : tasks) {
            records.add(task.toDataString());
        }

        try {
            Files.write(dataFile, records, StandardCharsets.UTF_8,
                    StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
        } catch (IOException e) {
            throw new RamboException("Could not update your saved tasks!", e);
        }
    }

    /**
     * Converts one data-file record into a task.
     */
    private Task createTask(String line) throws RamboException {
        String[] fields = line.split("\\|");
        for (int i = 0; i < fields.length; i++) {
            fields[i] = fields[i].trim();
        }

        Task task;
        switch (fields[0]) {
        case "T":
            if (fields.length != 3) {
                throw new RamboException("Invalid task record: " + line);
            }
            task = new Task(fields[2]);
            break;
        case "D":
            if (fields.length != 4) {
                throw new RamboException("Invalid task record: " + line);
            }
            task = new DeadlineTask(fields[2], fields[3]);
            break;
        case "E":
            if (fields.length != 5) {
                throw new RamboException("Invalid task record: " + line);
            }
            task = new EventTask(fields[2], fields[3], fields[4]);
            break;
        default:
            throw new RamboException("Invalid task record: " + line);
        }

        if (!fields[1].isEmpty() && !fields[1].equals("X")) {
            throw new RamboException("Invalid task record: " + line);
        }

        if (fields[1].equals("X")) {
            task.toggleDone();
        }

        return task;
    }
}
