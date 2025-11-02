package ru.vsu.sc.siakod25.g11.theme_6.ui;

import ru.vsu.sc.siakod25.g11.theme_6.manager.JsonProjectManager;
import ru.vsu.sc.siakod25.g11.theme_6.structure_classes.ArrayList;
import ru.vsu.sc.siakod25.g11.theme_6.unit_classes.Character;
import ru.vsu.sc.siakod25.g11.theme_6.unit_classes.Item;
import ru.vsu.sc.siakod25.g11.theme_6.unit_classes.Location;
import ru.vsu.sc.siakod25.g11.theme_6.unit_classes.Plot;
import ru.vsu.sc.siakod25.g11.theme_6.unit_classes.Project;

import javax.swing.*;
import java.awt.*;

public class EditEntityWindow extends JFrame {

    private static final int WIDTH = 1200;
    private static final int HEIGHT = 800;
    private static final Font TITLE_FONT  = new Font("Arial", Font.BOLD, 22);
    private static final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 16);
    private static final Font TEXT_FONT   = new Font("Arial", Font.PLAIN, 16);

    private final JsonProjectManager pm = JsonProjectManager.getInstance();
    private final EntityType type;
    private final String originalName;
    private Integer editingId = null;

    private JTextField headerName;

    private JLabel imagePreview;

    private JTextField nameField;
    private JComboBox<String> paramCombo;

    private JTextArea descriptionArea;

    public EditEntityWindow(EntityType type, String entityName) {
        this.type = type;
        this.originalName = entityName;

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setTitle("Edit");

        if (pm.getCurrentProject() == null) {
            JOptionPane.showMessageDialog(this, "Сначала выберите/создайте проект");
            new CollectionWindow().setVisible(true);
            dispose();
            return;
        }

        buildUI();
        loadEntityIntoForm();
    }

    private void buildUI() {
        JPanel root = new JPanel(new BorderLayout(12, 12));
        root.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JPanel top = new JPanel(new BorderLayout(8, 8));

        JButton back = new JButton("←");
        back.setFont(TITLE_FONT);
        back.addActionListener(e -> { new EntitiesWindow(type).setVisible(true); dispose(); });
        top.add(back, BorderLayout.WEST);

        Project pr = pm.getCurrentProject();
        headerName = new JTextField(pr != null ? pr.getProjectName() : "— проект не выбран —");
        headerName.setHorizontalAlignment(SwingConstants.CENTER);
        headerName.setEditable(false);
        headerName.setFocusable(false);
        headerName.setBorder(null);
        headerName.setOpaque(false);
        headerName.setFont(TITLE_FONT);
        top.add(headerName, BorderLayout.CENTER);

        JButton save = new JButton("Save");
        save.setFont(BUTTON_FONT);
        save.addActionListener(e -> onSave());
        top.add(save, BorderLayout.EAST);

        root.add(top, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8, 8, 8, 8);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.NORTHWEST;

        JPanel left = new JPanel(new GridBagLayout());
        GridBagConstraints lc = new GridBagConstraints();
        lc.insets = new Insets(8, 8, 8, 8);
        lc.fill = GridBagConstraints.HORIZONTAL;
        lc.anchor = GridBagConstraints.NORTHWEST;
        lc.gridx = 0; lc.gridy = 0; lc.weightx = 1;

        imagePreview = new JLabel("Foto", SwingConstants.CENTER);
        imagePreview.setPreferredSize(new Dimension(520, 360));
        imagePreview.setFont(TEXT_FONT);
        imagePreview.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180)));
        left.add(imagePreview, lc);

        lc.gridy = 1;
        JButton chooseBtn = new JButton("Выбрать...");
        chooseBtn.setFont(BUTTON_FONT);
        chooseBtn.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "Выбор/замена фото пока недоступны.",
                        "Инфо", JOptionPane.INFORMATION_MESSAGE));
        left.add(chooseBtn, lc);


        JPanel right = new JPanel(new GridBagLayout());
        GridBagConstraints rc = new GridBagConstraints();
        rc.insets = new Insets(6, 6, 6, 6);
        rc.fill = GridBagConstraints.HORIZONTAL;
        rc.anchor = GridBagConstraints.NORTHWEST;
        rc.gridx = 0; rc.gridy = 0; rc.weightx = 1;

        JLabel nameTitle = new JLabel("Name");
        nameTitle.setFont(BUTTON_FONT);
        right.add(nameTitle, rc);

        rc.gridy++;
        nameField = new JTextField(30);
        nameField.setFont(TEXT_FONT);
        right.add(nameField, rc);

        rc.gridy++;
        JLabel paramTitle = new JLabel("Parameter");
        paramTitle.setFont(BUTTON_FONT);
        right.add(paramTitle, rc);

        rc.gridy++;
        paramCombo = new JComboBox<>(new String[]{"Hero", "Item", "Location", "Plot"});
        paramCombo.setFont(TEXT_FONT);
        paramCombo.setEnabled(false); // тип в редакторе не меняем
        right.add(paramCombo, rc);

        c.gridx = 0; c.gridy = 0; c.weightx = 0; center.add(left, c);
        c.gridx = 1; c.gridy = 0; c.weightx = 1; center.add(right, c);

        JPanel bottom = new JPanel(new GridBagLayout());
        GridBagConstraints bc = new GridBagConstraints();
        bc.insets = new Insets(8, 8, 8, 8);
        bc.anchor = GridBagConstraints.WEST;
        bc.fill = GridBagConstraints.HORIZONTAL;

        JLabel descTitle = new JLabel("Description");
        descTitle.setFont(BUTTON_FONT);
        bc.gridx = 0; bc.gridy = 0; bc.weightx = 0;
        bottom.add(descTitle, bc);

        descriptionArea = new JTextArea(8, 30);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setFont(TEXT_FONT);
        JScrollPane sp = new JScrollPane(descriptionArea);

        bc.gridx = 0; bc.gridy = 1; bc.weightx = 1; bc.weighty = 1;
        bc.fill = GridBagConstraints.BOTH;
        bottom.add(sp, bc);

        root.add(center, BorderLayout.CENTER);
        root.add(bottom, BorderLayout.SOUTH);

        setContentPane(root);
    }

    private void loadEntityIntoForm() {
        Project pr = pm.getCurrentProject();

        switch (type) {
            case CHARACTERS -> {
                ArrayList<Character> list = pr.getCharacters();
                for (int i = 0; i < list.getSize(); i++) {
                    Character ch = list.get(i);
                    if (eq(ch.getName(), originalName)) {
                        editingId = ch.getId();
                        nameField.setText(nvl(ch.getName()));
                        descriptionArea.setText(nvl(ch.getDescription()));
                        paramCombo.setSelectedItem("Hero");
                        return;
                    }
                }
            }
            case ITEMS -> {
                ArrayList<Item> list = pr.getItems();
                for (int i = 0; i < list.getSize(); i++) {
                    Item it = list.get(i);
                    if (eq(it.getName(), originalName)) {
                        editingId = it.getId();
                        nameField.setText(nvl(it.getName()));
                        descriptionArea.setText(nvl(it.getDescription()));
                        paramCombo.setSelectedItem("Item");
                        return;
                    }
                }
            }
            case LOCATIONS -> {
                ArrayList<Location> list = pr.getLocations();
                for (int i = 0; i < list.getSize(); i++) {
                    Location loc = list.get(i);
                    if (eq(loc.getName(), originalName)) {
                        editingId = loc.getId();
                        nameField.setText(nvl(loc.getName()));
                        descriptionArea.setText(nvl(loc.getDescription()));
                        paramCombo.setSelectedItem("Location");
                        return;
                    }
                }
            }
            case PLOTS -> {
                ArrayList<Plot> list = pr.getPlots();
                for (int i = 0; i < list.getSize(); i++) {
                    Plot pl = list.get(i);
                    if (eq(pl.getName(), originalName)) {
                        editingId = pl.getId();
                        nameField.setText(nvl(pl.getName()));
                        descriptionArea.setText(nvl(pl.getDescription()));
                        paramCombo.setSelectedItem("Plot");
                        return;
                    }
                }
            }
        }

        JOptionPane.showMessageDialog(this, "Сущность не найдена", "Ошибка", JOptionPane.ERROR_MESSAGE);
        new EntitiesWindow(type).setVisible(true);
        dispose();
    }

    private void onSave() {
        if (pm.getCurrentProject() == null || editingId == null) return;

        String newName = nameField.getText().trim();
        String newDesc = descriptionArea.getText() != null ? descriptionArea.getText().trim() : "";

        if (newName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Поле Name пустое");
            return;
        }

        Project pr = pm.getCurrentProject();
        switch (type) {
            case CHARACTERS -> {
                ArrayList<Character> list = pr.getCharacters();
                for (int i = 0; i < list.getSize(); i++) {
                    Character ch = list.get(i);
                    if (ch.getId() == editingId) {
                        try {
                            ch.setName(newName);
                            ch.setDescription(newDesc);
                        } catch (Throwable t) {
                            list.set(i, new Character(ch.getId(), newName, newDesc));
                        }
                        pm.saveProject();
                        done();
                        return;
                    }
                }
            }
            case ITEMS -> {
                ArrayList<Item> list = pr.getItems();
                for (int i = 0; i < list.getSize(); i++) {
                    Item it = list.get(i);
                    if (it.getId() == editingId) {
                        try {
                            it.setName(newName);
                            it.setDescription(newDesc);
                        } catch (Throwable t) {
                            list.set(i, new Item(it.getId(), newName, newDesc, it.getOwnerId()));
                        }
                        pm.saveProject();
                        done();
                        return;
                    }
                }
            }
            case LOCATIONS -> {
                ArrayList<Location> list = pr.getLocations();
                for (int i = 0; i < list.getSize(); i++) {
                    Location loc = list.get(i);
                    if (loc.getId() == editingId) {
                        try {
                            loc.setName(newName);
                            loc.setDescription(newDesc);
                        } catch (Throwable t) {
                            list.set(i, new Location(loc.getId(), newName, newDesc));
                        }
                        pm.saveProject();
                        done();
                        return;
                    }
                }
            }
            case PLOTS -> {
                ArrayList<Plot> list = pr.getPlots();
                for (int i = 0; i < list.getSize(); i++) {
                    Plot pl = list.get(i);
                    if (pl.getId() == editingId) {
                        try {
                            pl.setName(newName);
                            pl.setDescription(newDesc);
                        } catch (Throwable t) {
                            list.set(i, new Plot(pl.getId(), newName, newDesc));
                        }
                        pm.saveProject();
                        done();
                        return;
                    }
                }
            }
        }

        JOptionPane.showMessageDialog(this, "Не удалось сохранить изменения", "Ошибка", JOptionPane.ERROR_MESSAGE);
    }

    private void done() {
        JOptionPane.showMessageDialog(this, "Изменения сохранены");
        new EntitiesWindow(type).setVisible(true);
        dispose();
    }

    private static boolean eq(String a, String b) { return a == null ? b == null : a.equals(b); }
    private static String nvl(String s) { return s == null ? "" : s; }
}
