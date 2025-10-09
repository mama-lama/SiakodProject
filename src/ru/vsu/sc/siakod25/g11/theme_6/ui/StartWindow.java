package ru.vsu.sc.siakod25.g11.theme_6.ui;

import javax.swing.*;
import java.awt.*;


public class StartWindow extends JFrame {

    private static final int WIDTH = 1200;
    private static final int HEIGHT = 800;
    private static final Font TITLE_FONT  = new Font("Arial", Font.BOLD, 48);
    private static final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 28);
    private static final BorderLayout LAYOUT_BORDER = new BorderLayout();

    private JButton createBtn;
    private JButton collectionBtn;
    private JButton feedbackBtn;

    public StartWindow() {
        super("Our Project");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(LAYOUT_BORDER);

        add(buildContent(), BorderLayout.CENTER);
    }
    private JComponent buildContent() {
        JPanel main = new JPanel(new GridBagLayout());
        main.setBorder(BorderFactory.createEmptyBorder(100, 200, 100, 200));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.weightx = 1; gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(20, 0, 20, 0);

        JLabel title = new JLabel("Our Project", SwingConstants.CENTER);
        title.setFont(TITLE_FONT);
        gbc.gridy = 0;
        main.add(title, gbc);

        JPanel buttons = new JPanel(new GridLayout(3, 1, 30, 30));
        createBtn     = makePrimaryButton("Create");
        collectionBtn = makePrimaryButton("Collection");
        feedbackBtn   = makePrimaryButton("FeedBack");

        buttons.add(createBtn);
        buttons.add(collectionBtn);
        buttons.add(feedbackBtn);

        gbc.gridy = 1;
        main.add(buttons, gbc);

        wireActions();

        return main;
    }

    private JButton makePrimaryButton(String text) {
        JButton b = new JButton(text);
        b.setFont(BUTTON_FONT);
        b.setPreferredSize(new Dimension(600, 100));
        return b;
    }

    private void wireActions() {
        createBtn.addActionListener(e -> {
            new NewProjectWindow().setVisible(true);
            dispose();
        });

        collectionBtn.addActionListener(e -> {
            new CollectionWindow().setVisible(true);
            dispose();
        });

        feedbackBtn.addActionListener(e -> {
            new FeedbackWindow().setVisible(true);
            dispose();
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StartWindow().setVisible(true));
    }
}
