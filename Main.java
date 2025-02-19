
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import javax.swing.Timer;

public class Main extends JFrame {
    private JButton signupButton, loginButton;
    private JLabel headingLabel;
    private Timer timer;
    private int headingY = 0;

    public Main() {
        setTitle("User Management");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        headingLabel = new JLabel("DS COLLEGE ALIGARH", SwingConstants.CENTER);
        headingLabel.setFont(new Font("Arial Black", Font.BOLD, 24));
        headingLabel.setBounds(0, headingY, 400, 50);
        headingLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                headingLabel.setText("Welcome to DS COLLEGE ALIGARH");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                headingLabel.setText("DS COLLEGE ALIGARH");
            }
        });
        add(headingLabel);

        // Timer for popup animation
        timer = new Timer(10, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                headingY += 2;
                if (headingY > 50) {
                    timer.stop();
                }
                headingLabel.setBounds(0, headingY, 400, 50);
            }
        });
        timer.start();

        signupButton = new JButton("Signup");
        signupButton.setBounds(50, 150, 100, 30);
        signupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openSignupForm();
            }
        });
        add(signupButton);

        loginButton = new JButton("Login");
        loginButton.setBounds(250, 150, 100, 30);
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openLoginForm();
            }
        });
        add(loginButton);
    }

    private void openSignupForm() {
        JFrame signupFrame = new JFrame("Signup");
        signupFrame.setSize(400, 400);
        signupFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        signupFrame.setLayout(null);

        JTextField nameText = createTextField(signupFrame, "Name:", 30);
        JTextField fnameText = createTextField(signupFrame, "Father's Name:", 70);
        JTextField rollnoText = createTextField(signupFrame, "Roll No:", 110);
        JTextField ageText = createTextField(signupFrame, "Age:", 150);
        JTextField uniText = createTextField(signupFrame, "University:", 190);

        JButton signupBtn = new JButton("Signup");
        signupBtn.setBounds(150, 230, 100, 30);
        signupBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = generateRandomString(5);
                String password = generateRandomString(5);
                if (signupUser(nameText.getText(), fnameText.getText(), rollnoText.getText(), ageText.getText(), uniText.getText(), username, password)) {
                    JOptionPane.showMessageDialog(signupFrame, "Signup successful! Username: " + username + ", Password: " + password);
                    saveLoginDetails(username, password);
                    signupFrame.dispose();
                } else {
                    JOptionPane.showMessageDialog(signupFrame, "Signup failed!");
                }
            }
        });
        signupFrame.add(signupBtn);
        signupFrame.setVisible(true);
    }

    private void openLoginForm() {
        JFrame loginFrame = new JFrame("Login");
        loginFrame.setSize(300, 200);
        loginFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        loginFrame.setLayout(null);

        JTextField userText = createTextField(loginFrame, "Username:", 30);
        JPasswordField passText = new JPasswordField();
        passText.setBounds(150, 70, 100, 30);
        loginFrame.add(createLabel("Password:", 70));
        loginFrame.add(passText);

        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(100, 110, 100, 30);
        loginBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (loginUser(userText.getText(), new String(passText.getPassword()))) {
                    JOptionPane.showMessageDialog(loginFrame, "Login successful!");
                    loginFrame.dispose();
                } else {
                    JOptionPane.showMessageDialog(loginFrame, "Invalid username or password!");
                }
            }
        });
        loginFrame.add(loginBtn);
        loginFrame.setVisible(true);
    }

    private JTextField createTextField(JFrame frame, String labelText, int y) {
        frame.add(createLabel(labelText, y));
        JTextField textField = new JTextField();
        textField.setBounds(150, y, 150, 30);
        frame.add(textField);
        return textField;
    }

    private JLabel createLabel(String text, int y) {
        JLabel label = new JLabel(text);
        label.setBounds(50, y, 100, 30);
        return label;
    }

    private boolean signupUser(String name, String fname, String rollno, String age, String uni, String username, String password) {
        return executeUpdate("INSERT INTO users (name, fname, rollno, age, uni, username, password) VALUES (?, ?, ?, ?, ?, ?, ?)",
                name, fname, rollno, age, uni, username, password);
    }

    private boolean loginUser(String username, String password) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/login_form", "root", "Mohit@1234");
             PreparedStatement pst = connection.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?")) {
            pst.setString(1, username);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean saveLoginDetails(String username, String password) {
        return executeUpdate("INSERT INTO users (username, password) VALUES (?, ?)", username, password);
    }

    private boolean executeUpdate(String query, String... params) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/login_form", "root", "Mohit@1234");
             PreparedStatement pst = connection.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                pst.setString(i + 1, params[i]);
            }
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}
