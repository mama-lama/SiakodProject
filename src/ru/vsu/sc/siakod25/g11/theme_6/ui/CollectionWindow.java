package ru.vsu.sc.siakod25.g11.theme_6.ui;

import ru.vsu.sc.siakod25.g11.theme_6.manager.JsonProjectManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class CollectionWindow extends JFrame {

    private static final int WIDTH = 1200;
    private static final int HEIGHT = 800;
    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 22);

    private final JsonProjectManager pm = JsonProjectManager.getInstance();
    private final DefaultListModel<String> model = new DefaultListModel<>();
    private JList<String> list;
    private JTextField search;

    public CollectionWindow() {
        super("Projects");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        initUI();
        reload();
    }

    private void initUI() {
        JPanel root = new JPanel(new BorderLayout(12, 12));
        root.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JPanel top = new JPanel(new BorderLayout(8, 8));

        JButton back = new JButton("←");
        back.setFont(TITLE_FONT);
        back.addActionListener(e -> { new StartWindow().setVisible(true); dispose(); });
        top.add(back, BorderLayout.WEST);

        JLabel title = new JLabel("Projects");
        title.setFont(TITLE_FONT);
        JPanel center = new JPanel(new FlowLayout(FlowLayout.LEFT));
        center.add(title);
        top.add(center, BorderLayout.CENTER);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        search = new JTextField(24);
        JButton find = new JButton("Find");
        find.addActionListener(e -> reload());
        right.add(search);
        right.add(find);
        top.add(right, BorderLayout.EAST);

        root.add(top, BorderLayout.NORTH);

        list = new JList<>(model);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setFont(new Font("Arial", Font.PLAIN, 18));
        list.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) openSelected();
            }
        });
        root.add(new JScrollPane(list), BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnNew = new JButton("New");
        btnNew.addActionListener(e -> {
            new NewProjectWindow().setVisible(true);
            dispose();
        });
        JButton btnDelete = new JButton("Delete");
        btnDelete.addActionListener(e -> deleteSelected());
        JButton btnOpen = new JButton("Open");
        btnOpen.addActionListener(e -> openSelected());

        bottom.add(btnNew);
        bottom.add(btnDelete);
        bottom.add(btnOpen);
        root.add(bottom, BorderLayout.SOUTH);

        setContentPane(root);
    }

    private void reload() {
        model.clear();
        String q = search != null ? search.getText().trim().toLowerCase() : "";
        String[] projects = pm.getExistingProjects();
        for (String p : projects) {
            if (q.isEmpty() || p.toLowerCase().contains(q)) model.addElement(p);
        }
    }

    private void openSelected() {
        String name = list.getSelectedValue();
        if (name == null) return;
        pm.loadProject(name);
        new CreateWindow().setVisible(true);
        dispose();
    }

    private void deleteSelected() {
        String name = list.getSelectedValue();
        if (name == null) return;
        int ok = JOptionPane.showConfirmDialog(this,
                "Удалить проект \"" + name + "\"?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (ok == JOptionPane.YES_OPTION) {
            File f = new File(pm.getProgectsDir(), name + ".json");
            if (f.exists()) f.delete();
            reload();
        }
    }
}
