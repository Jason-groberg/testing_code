package server;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ServerTest {
    private Server server;
    private Playwright playwright;
    private Page page;
    private Locator textBox;
    private Locator addButton;

    /*
     * The first time you run these tests, Playwright will install. This can take about 5 minutes.  This is a one time
     * installation, after that it will be much faster to run the tests.
     */


    // You may not have created a list before the way this code does.
    // This is a shortcut to creating a list and calling add() multiple times to add the items.
    private final List<String> todoTestList = Arrays.asList(
            "Learn Playwright",
            "Understanding and modifying an existing codebase",
            "Static analysis with IntelliJ Static Analyzer",
            "Code profiling with YourKit",
            "Dependency Management Tools: Maven and Gradle",
            "Unit Testing with JUnit");

    @BeforeEach
    void setUp() throws IOException {
        server = new Server();
        server.start();

        try {
            playwright = Playwright.create();
            /*
             * This creates a new Playwright browser of type chromium. You can replace  it with different browser
             *   types such as playwright.firefox() or playwright.webkit() to test on different browsers.
             *   For this lab, leave it as is.
             */
            BrowserType chromium = playwright.chromium();

            /*
             * This creates a new Playwright browser. setHeadless allows you to see the browser, setSlowMo
             *   slows down the actions so that you can actually see what's going on. If you want it to run in
             *   headless mode, just delete the BrowserType.LaunchOptions passed in as a parameter below.
             */
            Browser browser = chromium.launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(50));

            /*
             * This creates a page object that we will be using to interact with things and test them. After being created,
             *   we tell the page to navigate to the provided url where your server is running.
             */
            page = browser.newPage();
            page.navigate("http://localhost:" + server.getPort());

            /*
             * Gets a reference to the text entry box and stores it in a Locator object.
             *   Since this is the only text box we do not need to specify which one.
             */
            textBox = page.getByRole(AriaRole.TEXTBOX);

            //Gets a reference to the add button by looking for a button with the name "Add"
            addButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add"));

            for(String todo: todoTestList){
                submitTodo(todo);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    @AfterEach
    void teardown() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        page.close();
        playwright.close();
        server.stop();
    }

    private void submitTodo(String todoText) {
        textBox.fill(todoText);
        addButton.click();

        try {
            // A brief pause is needed between submitting and filling the input box again to avoid any overlap
            Thread.sleep(20);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    /*
     * Todos were added in the setup function.  This test checks to make sure the correct amount of todos
     * were added and that they have the correct information.
     */
    @Test
    void testAddTodos() {
        //This gets a reference to every element with a label of the class 'todo-text'
        List<ElementHandle> todoElements = page.querySelectorAll("label.todo-text");

        Assertions.assertEquals(todoTestList.size(), todoElements.size());

        List<String> foundTodoStrings = getTextFromElements(todoElements);

        for(String expectedTodo : todoTestList){
            Assertions.assertTrue(foundTodoStrings.contains(expectedTodo), "Missing todo: " + expectedTodo);
        }
    }

    @Test
    void testMarkAndShowCompleted(){
        List<String> expectedCompletedItems = Arrays.asList("Learn Playwright", "Code profiling with YourKit");

        for(String completedItem : expectedCompletedItems){
            Locator.FilterOptions options = new Locator.FilterOptions()
                    .setHasText(completedItem);
            Locator checkBox = page.getByRole(AriaRole.LISTITEM).filter(options).getByRole(AriaRole.CHECKBOX);
            checkBox.click();
        }

        Locator showCompletedButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Show Completed"));
        Assertions.assertNotNull(showCompletedButton);
        showCompletedButton.click();

        List<ElementHandle> completedTodoElements = page.querySelectorAll("label.todo-text");

        Assertions.assertEquals(expectedCompletedItems.size(), completedTodoElements.size());

        List<String> foundTodoStrings = getTextFromElements(completedTodoElements);

        for(String expectedTodo : expectedCompletedItems){
            Assertions.assertTrue(foundTodoStrings.contains(expectedTodo), "Missing todo: " + expectedTodo);
        }
    }

    @Test
    void testDeleteCompleted(){
        List<String> expectedCompletedItems = todoTestList;
        for (String completedItem : expectedCompletedItems){
            Locator.FilterOptions options = new Locator.FilterOptions().setHasText(completedItem);
            Locator checkBox = page.getByRole(AriaRole.LISTITEM).filter(options).getByRole(AriaRole.CHECKBOX);
            checkBox.click();
        }

        Locator deleteCompletedButton = page.locator("#delete-completed");
        Assertions.assertNotNull(deleteCompletedButton);
        deleteCompletedButton.click();

        List<ElementHandle> completedTodoElements = page.querySelectorAll("label.todo-text");

        Assertions.assertEquals(0, completedTodoElements .size());
    }

    private List<String> getTextFromElements(List<ElementHandle> elementHandles){
        List<String> strings = new ArrayList<>(elementHandles.size());

        for(ElementHandle handle : elementHandles){
            strings.add(handle.textContent());
        }

        return strings;
    }



}