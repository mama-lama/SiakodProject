package ru.vsu.sc.siakod25.g11.theme_6.ui;

import ru.vsu.sc.siakod25.g11.theme_6.manager.JsonProjectManager;
import ru.vsu.sc.siakod25.g11.theme_6.structure_classes.ArrayList;
import ru.vsu.sc.siakod25.g11.theme_6.unit_classes.Character;
import ru.vsu.sc.siakod25.g11.theme_6.unit_classes.Item;
import ru.vsu.sc.siakod25.g11.theme_6.unit_classes.Location;
import ru.vsu.sc.siakod25.g11.theme_6.unit_classes.Plot;
import ru.vsu.sc.siakod25.g11.theme_6.unit_classes.Project;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;


public class EntitiesWindow extends JFrame {

    private static final int WIDTH = 1200;
    private static final int HEIGHT = 800;
    private static final Font TITLE_FONT  = new Font("Arial", Font.BOLD, 22);
    private static final Font CELL_FONT   = new Font("Arial", Font.PLAIN, 18);
    private static final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 16);

    private final EntityType type;
    private final JsonProjectManager pm = JsonProjectManager.getInstance();

    private final DefaultTableModel model =
            new DefaultTableModel(new Object[]{"Name", "Change", "Delete"}, 0) {
                @Override public boolean isCellEditable(int r, int c) { return c > 0; }
            };

    private JTable table;
    private JTextField search;

    public EntitiesWindow(EntityType type) {
        this.type = type;
        setTitle("Entities — " + type);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        initUI();
        reload();
    }

    private void initUI() {
        if (pm.getCurrentProject() == null) {
            JOptionPane.showMessageDialog(this, "First, select/create a project");
            new CollectionWindow().setVisible(true); // окно проектов
            dispose();
            return;
        }

        JPanel root = new JPanel(new BorderLayout(12, 12));
        root.setBorder(BorderFactory.createEmptyBorder(12,12,12,12));

        JPanel top = new JPanel(new BorderLayout(8,8));
        JButton back = new JButton("←");
        back.setFont(TITLE_FONT);
        back.addActionListener(e -> { new CreateWindow().setVisible(true); dispose(); });
        top.add(back, BorderLayout.WEST);

        JLabel title = new JLabel(titleForType() + " " + pm.getCurrentProject().getProjectName());
        title.setFont(TITLE_FONT);
        JPanel center = new JPanel(new FlowLayout(FlowLayout.LEFT));
        center.add(title);
        top.add(center, BorderLayout.CENTER);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        search = new JTextField(28);
        JButton find = new JButton("Find");
        find.addActionListener(e -> reload());
        right.add(search);
        right.add(find);
        top.add(right, BorderLayout.EAST);

        root.add(top, BorderLayout.NORTH);

        table = new JTable(model);
        table.setRowHeight(44);
        table.setFont(CELL_FONT);
        table.getTableHeader().setFont(TITLE_FONT.deriveFont(18f));

        installButtonColumn(1, "Change", this::openEditor);
        installButtonColumn(2, "Delete", this::deleteSelected);

        root.add(new JScrollPane(table), BorderLayout.CENTER);
        setContentPane(root);
    }

    private String titleForType() {
        return switch (type) {
            case CHARACTERS -> "Characters";
            case ITEMS -> "Items";
            case LOCATIONS -> "Locations";
            case PLOTS -> "Plots";
        };
    }

    private void reload() {
        model.setRowCount(0);
        Project pr = pm.getCurrentProject();
        if (pr == null) return;

        String q = search != null ? search.getText().trim().toLowerCase() : "";

        switch (type) {
            case CHARACTERS -> {
                ArrayList<Character> list = pr.getCharacters();
                for (int i = 0; i < list.getSize(); i++) {
                    Character c = list.get(i);
                    if (matches(q, c.getName())) model.addRow(new Object[]{c.getName(), "Изменить", "Удалить"});
                }
            }
            case ITEMS -> {
                ArrayList<Item> list = pr.getItems();
                for (int i = 0; i < list.getSize(); i++) {
                    Item it = list.get(i);
                    if (matches(q, it.getName())) model.addRow(new Object[]{it.getName(), "Изменить", "Удалить"});
                }
            }
            case LOCATIONS -> {
                ArrayList<Location> list = pr.getLocations();
                for (int i = 0; i < list.getSize(); i++) {
                    Location loc = list.get(i);
                    if (matches(q, loc.getName())) model.addRow(new Object[]{loc.getName(), "Изменить", "Удалить"});
                }
            }
            case PLOTS -> {
                ArrayList<Plot> list = pr.getPlots();
                for (int i = 0; i < list.getSize(); i++) {
                    Plot pl = list.get(i);
                    if (matches(q, pl.getName())) model.addRow(new Object[]{pl.getName(), "Изменить", "Удалить"});
                }
            }
        }
        autoResizeColumns();
    }

    private boolean matches(String q, String name) {
        return q.isEmpty() || (name != null && name.toLowerCase().contains(q));
    }

    private void autoResizeColumns() {
        for (int c = 0; c < table.getColumnCount(); c++) {
            TableColumn col = table.getColumnModel().getColumn(c);
            int preferred = 120;
            for (int r = 0; r < table.getRowCount(); r++) {
                TableCellRenderer renderer = table.getCellRenderer(r, c);
                Component comp = table.prepareRenderer(renderer, r, c);
                preferred = Math.max(preferred, comp.getPreferredSize().width + 16);
            }
            col.setPreferredWidth(preferred);
        }
    }

    private void installButtonColumn(int colIndex, String text, Runnable onClick) {
        TableColumn col = table.getColumnModel().getColumn(colIndex);
        col.setCellRenderer((tbl, value, isSelected, hasFocus, row, column) -> {
            JButton b = new JButton(text);
            b.setFont(BUTTON_FONT);
            return b;
        });
        col.setCellEditor(new DefaultCellEditor(new JCheckBox()) {
            @Override
            public Component getTableCellEditorComponent(JTable t, Object v, boolean sel, int r, int c) {
                JButton b = new JButton(text);
                b.setFont(BUTTON_FONT);
                b.addActionListener(e -> {
                    fireEditingStopped();
                    onClick.run();
                });
                return b;
            }
        });
    }

    private void openEditor() {
        int row = table.getSelectedRow();
        if (row < 0) return;
        String name = (String) model.getValueAt(row, 0);
        if (name == null || name.isEmpty()) return;

        new EditEntityWindow(type, name).setVisible(true);
        dispose();
    }

    private void deleteSelected() {
        int row = table.getSelectedRow();
        if (row < 0) return;

        String name = (String) model.getValueAt(row, 0);
        Project pr = pm.getCurrentProject();
        if (pr == null || name == null) return;

        switch (type) {
            case CHARACTERS -> {
                ArrayList<Character> list = pr.getCharacters();
                for (int i = 0; i < list.getSize(); i++) {
                    if (name.equals(list.get(i).getName())) { list.remove(i); break; }
                }
            }
            case ITEMS -> {
                ArrayList<Item> list = pr.getItems();
                for (int i = 0; i < list.getSize(); i++) {
                    if (name.equals(list.get(i).getName())) { list.remove(i); break; }
                }
            }
            case LOCATIONS -> {
                ArrayList<Location> list = pr.getLocations();
                for (int i = 0; i < list.getSize(); i++) {
                    if (name.equals(list.get(i).getName())) { list.remove(i); break; }
                }
            }
            case PLOTS -> {
                ArrayList<Plot> list = pr.getPlots();
                for (int i = 0; i < list.getSize(); i++) {
                    if (name.equals(list.get(i).getName())) { list.remove(i); break; }
                }
            }
        }

        pm.saveProject();
        reload();
    }
}
