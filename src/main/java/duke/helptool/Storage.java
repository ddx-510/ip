package duke.helptool;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import duke.tag.Tag;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.ToDo;


/**
 * The type Storage.
 */
public class Storage {
    private final String filePath;

    /**
     * Instantiates a new Storage.
     *
     * @param filePath the file path
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Create file.
     *
     * @throws DukeException the duke exception
     */
    public void create() throws DukeException {
        try {
            File myObj = new File(this.filePath);
            boolean isCreated = myObj.createNewFile();
        } catch (IOException e) {
            throw new DukeException("IO exception occurred.");
        }
    }

    /**
     * Write to file.
     *
     * @param taskList the task list
     * @throws DukeException the duke exception
     */
    public void write(TaskList taskList) throws DukeException {
        try {
            FileWriter myWriter = new FileWriter(filePath, false);

            for (int i = 0; i < taskList.getSize(); i++) {
                String output = String.format("%s\n", taskList.getTask(i).toString());
                myWriter.write(output);
            }
            myWriter.close();
        } catch (IOException e) {
            throw new DukeException("IO exception occurred when write to file.");
        }
    }

    /**
     * Gets tag.
     *
     * @param data the data
     * @return the tag
     */
    public Tag getTag(String data) {
        int tagPos = data.indexOf("#{");
        int tagEnd = data.indexOf("}");
        String tagDescription = data.substring(tagPos + 2, tagEnd);
        return new Tag(tagDescription);
    }

    /**
     * Load from file.
     *
     * @return the array list
     * @throws DukeException the duke exception
     */
    public ArrayList<Task> load() throws DukeException {
        try {
            File myObj = new File(this.filePath);
            Scanner myReader = new Scanner(myObj);
            ArrayList<Task> tempTaskList = new ArrayList<>();
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String tempType = data.substring(1, 2);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy hh:mm a");
                switch (tempType) {
                case "T":
                    Tag tag = getTag(data);
                    int desPos = data.indexOf("}") + 1;
                    Task tempTask = new ToDo(data.substring(desPos), tag);
                    if (data.charAt(4) == 'X') {
                        tempTask.markAsDone();
                    }
                    tempTaskList.add(tempTask);
                    break;
                case "D":
                    int byPos = data.indexOf("(by:");
                    String by = data.substring(byPos + 5, data.length() - 1);
                    desPos = data.indexOf("}") + 1;
                    String description = data.substring(desPos, byPos - 1);
                    tag = getTag(data);
                    tempTask = new Deadline(description, LocalDateTime.parse(by, formatter), tag);
                    if (data.charAt(4) == 'X') {
                        tempTask.markAsDone();
                    }
                    tempTaskList.add(tempTask);
                    break;
                case "E":
                    int atPos = data.indexOf("(at:");
                    String at = data.substring(atPos + 5, data.length() - 1);
                    desPos = data.indexOf("}") + 1;
                    description = data.substring(desPos, atPos - 1);
                    tag = getTag(data);
                    tempTask = new Event(description, LocalDateTime.parse(at, formatter), tag);
                    if (data.charAt(4) == 'X') {
                        tempTask.markAsDone();
                    }
                    tempTaskList.add(tempTask);
                    break;
                default:
                    break;
                }
            }
            myReader.close();
            return tempTaskList;
        } catch (FileNotFoundException e) {
            this.create();
            return null;
        }
    }
}
