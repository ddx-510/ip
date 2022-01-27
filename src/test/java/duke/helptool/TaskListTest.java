package duke.helptool;

import duke.task.Task;
import duke.task.ToDo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TaskListTest {

    ArrayList<Task> tasks = new ArrayList<>();
    TaskList taskList = new TaskList();

    @Test
    @DisplayName("Determine size should work")
    void testTaskListSize() {
        assertEquals(tasks.size(),taskList.getSize(), "Default should be empty");
        ToDo temp = new ToDo("hi");
        tasks.add(temp);
        taskList.addTask(temp);
        assertEquals(tasks.size(),taskList.getSize(), "Add is successful");
        tasks.remove(0);
        taskList.delete(0);
        assertEquals(tasks.size(),taskList.getSize(), "Delete is successful");
    }

    @Test
    @DisplayName("Determine add should work")
    void testTaskListGet() {
        ToDo temp = new ToDo("hi");
        tasks.add(temp);
        taskList.addTask(temp);
        assertEquals(tasks.get(0),taskList.getTask(0), "Add is successful");
        tasks.remove(0);
        taskList.delete(0);
    }
}