package ru.vsu.sc.siakod25.g11.theme_6.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Окно "Collection": верхняя панель (← | Search) и таблица:
 * [Name | Изменить | Удалить].
 * Стиль унифицирован с остальными окнами приложения.
 */
public class CollectionWindow extends JFrame {

    // ===== Константы оформления =====
    private static final int  WIDTH       = 1200;
    private static final int  HEIGHT      = 1000;
    private static final Font HEADER_FONT = new Font("Arial", Font.BOLD, 16);
    private static final Font CELL_FONT   = new Font("Arial", Font.PLAIN, 16);
    private static final Font BTN_FONT    = new Font("Arial", Font.PLAIN, 14);

    // ===== Компоненты =====
    private JTable table;

    public CollectionWindow() {
        super("Collection");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(buildTopBar(), BorderLayout.NORTH);
        add(buildTable(), BorderLayout.CENTER);
    }

    // ===== Верхняя панель =====
    private JComponent buildTopBar() {
        JPanel top = new JPanel(new BorderLayout(10, 10));
        top.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton backBtn   = new JButton("←");
        JButton searchBtn = new JButton("Search");
        backBtn.setPreferredSize(new Dimension(80, 40));
        searchBtn.setPreferredSize(new Dimension(100, 40));

        top.add(backBtn,   BorderLayout.WEST);
        top.add(searchBtn, BorderLayout.EAST);

        // Навигация и заглушки
        backBtn.addActionListener(e -> { new StartWindow().setVisible(true); dispose(); });
        searchBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "Поиск (заглушка)"));
        return top;
    }

    // ===== Центральная таблица =====
    private JComponent buildTable() {
        String[] cols = {"Name", "Изменить", "Удалить"};
        Object[][] data = {
                {"name 5", null, null},
                {"name 4", null, null},
                {"name 3", null, null},
                {"name 2", null, null},
                {"name 1", null, null}
        };

        DefaultTableModel model = new DefaultTableModel(data, cols) {
            @Override public boolean isCellEditable(int row, int column) { return column != 0; }
        };

        table = new JTable(model);
        table.setRowHeight(40);
        table.setFont(CELL_FONT);
        table.getTableHeader().setFont(HEADER_FONT);
        table.setFillsViewportHeight(true);

        // Широкий столбец Name
        table.getColumnModel().getColumn(0).setPreferredWidth(600);

        // Колонка "Изменить"
        TableColumn editCol = table.getColumnModel().getColumn(1);
        editCol.setCellRenderer(new TextButtonRenderer("Изменить"));
        editCol.setCellEditor(new TextButtonEditor("Изменить", () -> {
            int r = table.getSelectedRow();
            if (r >= 0) {
                String name = String.valueOf(table.getValueAt(r, 0));
                JOptionPane.showMessageDialog(table, "Редактировать: " + name);
            }
        }));

        // Колонка "Удалить" (с подтверждением)
        TableColumn delCol = table.getColumnModel().getColumn(2);
        delCol.setCellRenderer(new TextButtonRenderer("Удалить"));
        delCol.setCellEditor(new TextButtonEditor("Удалить", () -> {
            int r = table.getSelectedRow();
            if (r >= 0) {
                String name = String.valueOf(table.getValueAt(r, 0));
                int choice = JOptionPane.showConfirmDialog(
                        table,
                        "Удалить запись: \"" + name + "\"?",
                        "Подтверждение удаления",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );
                if (choice == JOptionPane.YES_OPTION) {
                    ((DefaultTableModel) table.getModel()).removeRow(r);
                }
            }
        }));

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return scroll;
    }

    // ===== Рендерер кнопки с текстом =====
    private static class TextButtonRenderer extends JButton implements TableCellRenderer {
        TextButtonRenderer(String text) {
            setText(text);
            setFont(BTN_FONT);
        }
        @Override
        public Component getTableCellRendererComponent(JTable t, Object v, boolean s, boolean f, int r, int c) {
            return this;
        }
    }

    // ===== Редактор кнопки с текстом =====
    private static class TextButtonEditor extends DefaultCellEditor {
        private final JButton button = new JButton();
        private final Runnable onClick;

        TextButtonEditor(String text, Runnable onClick) {
            super(new JCheckBox());
            this.onClick = onClick;
            button.setText(text);
            button.setFont(BTN_FONT);
            // слушатель для закрытия редактора ячейки
            button.addActionListener(e -> fireEditingStopped());
        }

        @Override
        public Component getTableCellEditorComponent(JTable t, Object v, boolean s, int r, int c) {
            // очищаем пользовательские слушатели, чтобы не дублировались
            for (ActionListener al : button.getActionListeners()) {
                button.removeActionListener(al);
            }
            // снова добавляем: сначала закрыть редактор, затем выполнить действие
            button.addActionListener(e -> fireEditingStopped());
            button.addActionListener(e -> onClick.run());
            return button;
        }

        @Override public Object getCellEditorValue() { return null; }
    }

    // ===== Тестовый запуск =====
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CollectionWindow().setVisible(true));
    }
}
