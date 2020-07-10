package UI;

import Common.Users;
import DataBase.SecurePassword;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class LoginWindow extends JFrame {
    private static String user_login;
    public static boolean isLogged = false;

    public static String getLogin(){
        return user_login;
    }

    private JButton login;
    private JButton registration;
    private JTextField login_field;
    private JPasswordField password_field;

    public LoginWindow() {
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        JLabel register_label = new JLabel("Or register, if you haven't yet");
        JLabel login_label = new JLabel("Input your login: ");
        JLabel password_label = new JLabel("Input your password: ");

        login_field = new JTextField(10);
        password_field = new JPasswordField(10);

        //--------------------------------------------------------------------------------------------------------------

        login = new JButton("Login");
        login.addActionListener((e -> {
            String login_in = login_field.getText().trim();
            if (!Users.isRegistered(login_in)){
                InfoWindow er_log = new InfoWindow(" User with this login doesn't exist, try again or register ", "error");
                return;
            }
            String password_in = SecurePassword.generate(String.valueOf(password_field.getPassword()).trim());
            if (Users.isCorrectPassword(login_in, password_in)){
                Users.addLoggedUser(login_in, password_in);
                user_login = login_in;
                dispose();
                isLogged = true;
            }else {
                InfoWindow er_pas = new InfoWindow(" Incorrect password, try again ", "error");
                return;
            }
        }));

        //--------------------------------------------------------------------------------------------------------------

        registration = new JButton("Registration");
        registration.addActionListener((e -> {
            setVisible(false);
            RegisterWindow window = new RegisterWindow(this);
        }));

        //--------------------------------------------------------------------------------------------------------------

        Panel panel1 = new Panel();
        panel1.setLayout(new FlowLayout());
        panel1.add(login_label);
        panel1.add(login_field);

        Panel panel2 = new Panel();
        panel2.setLayout(new FlowLayout());
        panel2.add(password_label);
        panel2.add(password_field);

        GridBagConstraints constraints = new GridBagConstraints();
        Insets insets = new Insets(5, 2, 2, 2);
        constraints.insets = insets;
        constraints.anchor = GridBagConstraints.EAST;
        add(panel1, constraints);
        constraints.gridy += 2;
        add(panel2, constraints);
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets.top = 15;
        constraints.insets.bottom = 10;
        constraints.gridy += 2;
        add(login, constraints);
        constraints.gridy += 2;
        add(register_label, constraints);
        constraints.gridy += 2;
        add(registration, constraints);
        pack();
        setVisible(true);
    }

        private class RegisterWindow extends JFrame{

            private JButton register;
            private JTextField login_field;
            private JPasswordField password_field;

            RegisterWindow(LoginWindow window){
                setResizable(false);
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setLocationRelativeTo(null);
                setLayout(new GridBagLayout());

                JLabel login_label = new JLabel("Input login: ");
                JLabel password_label = new JLabel("Input password: ");

                login_field = new JTextField(10);
                password_field = new JPasswordField(10);

                //------------------------------------------------------------------------------------------------------

                register = new JButton("register");
                register.addActionListener((e -> {
                    String login_reg = login_field.getText().trim();
                    while (Users.isRegistered(login_reg)) {
                        InfoWindow er_log = new InfoWindow(" This login already exists, create new ", "error");
                        return;
                    }
                    if(login_reg.chars().count()>50){
                        InfoWindow er_log = new InfoWindow(" Login have to be not more than 50 chars ", "error");
                        return;
                    }
                    String password_reg = SecurePassword.generate(String.valueOf(password_field.getPassword()).trim());
                    Users.addRegisteredUser(login_reg, password_reg);
                    this.setVisible(false);
                    window.setVisible(true);
                }));

                //------------------------------------------------------------------------------------------------------

                Panel panel1 = new Panel();
                panel1.setLayout(new FlowLayout());
                panel1.add(login_label);
                panel1.add(login_field);

                Panel panel2 = new Panel();
                panel2.setLayout(new FlowLayout());
                panel2.add(password_label);
                panel2.add(password_field);

                GridBagConstraints constraints = new GridBagConstraints();
                Insets insets = new Insets(5, 2, 2, 2);
                constraints.insets = insets;
                constraints.anchor = GridBagConstraints.EAST;
                add(panel1, constraints);
                constraints.gridy+=2;
                add(panel2, constraints);
                constraints.anchor = GridBagConstraints.CENTER;
                constraints.insets.top = 15;
                constraints.insets.bottom = 10;
                constraints.gridy+=2;
                add(register, constraints);
                pack();
                setVisible(true);

            }
        }

}
