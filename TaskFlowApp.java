import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

// Login Window
class LoginFrame {
    Frame loginFrame = new Frame("Login");

    Label userLabel = new Label("Username:");
    Label passLabel = new Label("Password:");
    TextField userField = new TextField(20);
    TextField passField = new TextField(20);
    Button loginButton = new Button("Login");
    Label message = new Label("");

    public LoginFrame() {
        loginFrame.setLayout(new FlowLayout());

        passField.setEchoChar('*');

        loginFrame.add(userLabel);
        loginFrame.add(userField);
        loginFrame.add(passLabel);
        loginFrame.add(passField);
        loginFrame.add(loginButton);
        loginFrame.add(message);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText();
                String password = passField.getText();

                if (username.equals("admin") && password.equals("admin")) {
                    loginFrame.setVisible(false);
                    new TaskFlowApp();
                } else {
                    message.setText("Invalid login! Try again.");
                }
            }
        });

        loginFrame.setSize(400, 250);
        loginFrame.setVisible(true);
        loginFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }
}

// Task Flow App Window
public class TaskFlowApp {
    Frame frame = new Frame("Task Flow");

    Label taskLabel = new Label("Task Title:");
    TextField taskField = new TextField(25);

    Label descLabel = new Label("Description:");
    TextField descField = new TextField(25);

    Label dateLabel = new Label("Deadline Date (YYYY-MM-DD):");
    TextField dateField = new TextField(15);

    Label timeLabel = new Label("Deadline Time (HH:MM):");
    TextField timeField = new TextField(10);

    Button addButton = new Button("Add Task");
    TextArea taskListArea = new TextArea();

    ArrayList<String> tasks = new ArrayList<>();

    public TaskFlowApp() {
        frame.setLayout(new FlowLayout());

        frame.add(taskLabel);
        frame.add(taskField);
        frame.add(descLabel);
        frame.add(descField);
        frame.add(dateLabel);
        frame.add(dateField);
        frame.add(timeLabel);
        frame.add(timeField);
        frame.add(addButton);
        frame.add(taskListArea);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String title = taskField.getText();
                String desc = descField.getText();
                String date = dateField.getText();
                String time = timeField.getText();

                if (!title.isEmpty() && !date.isEmpty() && !time.isEmpty()) {
                    String task = "Title: " + title + "\nDescription: " + desc + 
                                  "\nDeadline: " + date + " " + time + "\n";
                    tasks.add(task);
                    updateTaskList();
                    taskField.setText("");
                    descField.setText("");
                    dateField.setText("");
                    timeField.setText("");
                }
            }
        });

        frame.setSize(550, 450);
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    private void updateTaskList() {
        StringBuilder sb = new StringBuilder();
        int index = 1;
        for (String task : tasks) {
            sb.append("Task ").append(index++).append(":\n").append(task).append("\n");
        }
        taskListArea.setText(sb.toString());
    }

    public static void main(String[] args) {
        new LoginFrame();
    }
}
