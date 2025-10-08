package ru.vsu.sc.siakod25.g11.theme_6.ui;

import ru.vsu.sc.siakod25.g11.theme_6.manager.JsonProjectManager;

import javax.swing.*;
import java.awt.*;

/**
 * Окно "Create": верхняя панель (назад, name, поиск), справа разделы, в центре кнопка добавления.
 */
public class CreateWindow extends JFrame {

    // ===== Константы оформления =====
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 1000;
    private static final Font TITLE_FONT   = new Font("Arial", Font.BOLD, 22);
    private static final Font BUTTON_FONT  = new Font("Arial", Font.BOLD, 20);
    private static final Font SIDEBAR_FONT = new Font("Arial", Font.BOLD, 20);

    // ===== Компоненты =====
    private JButton backBtn;
    private JTextField nameField;
    private JButton searchBtn;

    private JButton addBtn;

    private JButton heroesBtn;
    private JButton itemsBtn;
    private JButton locationsBtn;
    private JButton plotBtn;

    public CreateWindow() {
        super("Create");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(buildTopBar(), BorderLayout.NORTH);
        add(buildCenter(), BorderLayout.CENTER);
        add(buildSidebar(), BorderLayout.EAST);

        //-----------------------------------------------------
        JsonProjectManager manager = JsonProjectManager.getInstance();
        if (manager.getCurrentProject() != null) {
            nameField.setText(manager.getCurrentProject().getProjectName());
        }
        //-----------------------------------------------------
    }

    // ===== Сборка интерфейса =====
    private JComponent buildTopBar() {
        JPanel top = new JPanel(new BorderLayout(15, 15));
        top.setBorder(BorderFactory.createEmptyBorder(20, 20, 12, 20));

        backBtn = new JButton("←");
        backBtn.setPreferredSize(new Dimension(80, 46));
        backBtn.setFont(BUTTON_FONT);

        nameField = new JTextField("name");
        nameField.setHorizontalAlignment(SwingConstants.CENTER);
        nameField.setFont(TITLE_FONT);
        nameField.setEditable(false);

        searchBtn = new JButton("Search");
        searchBtn.setPreferredSize(new Dimension(100, 46));
        searchBtn.setFont(BUTTON_FONT);

        top.add(backBtn, BorderLayout.WEST);
        top.add(nameField, BorderLayout.CENTER);
        top.add(searchBtn, BorderLayout.EAST);

        // Навигация
        backBtn.addActionListener(e -> { new StartWindow().setVisible(true); dispose(); });
        searchBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "Поиск (заглушка)"));

        return top;
    }

    private JComponent buildCenter() {
        JPanel center = new JPanel(new GridBagLayout());
        center.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        addBtn = new JButton("+");
        addBtn.setFont(new Font("Arial", Font.BOLD, 48));
        addBtn.setPreferredSize(new Dimension(260, 110));
        center.add(addBtn);

        addBtn.addActionListener(e -> new AddEntityWindow().setVisible(true));
        return center;
    }

    private JComponent buildSidebar() {
        JPanel right = new JPanel(new GridLayout(4, 1, 20, 20));
        right.setBorder(BorderFactory.createEmptyBorder(40, 20, 40, 20));

        heroesBtn    = makeSideButton("Heroes");
        itemsBtn     = makeSideButton("Items");
        locationsBtn = makeSideButton("Locations");
        plotBtn      = makeSideButton("Plot");

        right.add(heroesBtn);
        right.add(itemsBtn);
        right.add(locationsBtn);
        right.add(plotBtn);

        // Заглушки
        heroesBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "Heroes (заглушка)"));
        itemsBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "Items (заглушка)"));
        locationsBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "Locations (заглушка)"));
        plotBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "Plot (заглушка)"));

        return right;
    }

    private JButton makeSideButton(String text) {
        JButton b = new JButton(text);
        b.setFont(SIDEBAR_FONT);
        b.setPreferredSize(new Dimension(220, 64));
        return b;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CreateWindow().setVisible(true));
    }
}
