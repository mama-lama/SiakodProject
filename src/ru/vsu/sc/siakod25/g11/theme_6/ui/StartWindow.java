package ru.vsu.sc.siakod25.g11.theme_6.ui;

import javax.swing.*;
import java.awt.*;

/**
 * Стартовое окно приложения.
 * Стиль: единые шрифты, отступы, именование и структура.
 */
public class StartWindow extends JFrame {

    // ===== Константы оформления =====
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 1000;
    private static final Font TITLE_FONT  = new Font("Arial", Font.BOLD, 48);
    private static final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 28);
    private static final BorderLayout LAYOUT_BORDER = new BorderLayout();
    private static final Insets GRID_INSETS = new Insets(0, 0, 0, 0);

    // ===== Компоненты =====
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

    // ===== Сборка интерфейса =====
    private JComponent buildContent() {
        // Центральная панель для центрирования всего контента
        JPanel main = new JPanel(new GridBagLayout());
        main.setBorder(BorderFactory.createEmptyBorder(100, 200, 100, 200));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.weightx = 1; gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(20, 0, 20, 0);

        // Заголовок
        JLabel title = new JLabel("Our Project", SwingConstants.CENTER);
        title.setFont(TITLE_FONT);
        gbc.gridy = 0;
        main.add(title, gbc);

        // Блок кнопок
        JPanel buttons = new JPanel(new GridLayout(3, 1, 30, 30));
        createBtn     = makePrimaryButton("Create");
        collectionBtn = makePrimaryButton("Collection");
        feedbackBtn   = makePrimaryButton("FeedBack");

        buttons.add(createBtn);
        buttons.add(collectionBtn);
        buttons.add(feedbackBtn);

        gbc.gridy = 1;
        main.add(buttons, gbc);

        // Действия
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
            new CreateWindow().setVisible(true);
            dispose();
        });

        collectionBtn.addActionListener(e -> {
            new CollectionWindow().setVisible(true);
            dispose();
        });

        feedbackBtn.addActionListener(e ->
            JOptionPane.showMessageDialog(this, "Feedback: заглушка")
        );
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StartWindow().setVisible(true));
    }
}
