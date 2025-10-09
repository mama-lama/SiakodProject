package ru.vsu.sc.siakod25.g11.theme_6.ui;

import ru.vsu.sc.siakod25.g11.theme_6.manager.JsonProjectManager;

import javax.swing.*;
import java.awt.*;

public class NewProjectWindow extends JFrame {
    private JTextField nameField;

    public NewProjectWindow() {
        super("Новый проект");
        setSize(400, 200);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        nameField = new JTextField();
        JButton createBtn = new JButton("Create");

        panel.add(new JLabel("Name:"), BorderLayout.NORTH);
        panel.add(nameField, BorderLayout.CENTER);
        panel.add(createBtn, BorderLayout.SOUTH);

        createBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            if (!name.isEmpty()) {
                JsonProjectManager manager = JsonProjectManager.getInstance();
                manager.createNewProject(name);
                new CreateWindow().setVisible(true);
                dispose();
            }
        });

        add(panel);
    }
}
