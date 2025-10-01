package ru.vsu.sc.siakod25.g11.theme_6.ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Окно добавления сущности: фото слева, справа Name+Parameter, снизу Description.
 */
public class AddEntityWindow extends JFrame {

    // ===== Константы оформления =====
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 1000;
    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 18);
    private static final Color UNDERLINE = new Color(120, 120, 120);

    // ===== Компоненты =====
    private JButton backBtn;
    private JTextField headerName;
    private JButton saveBtn;

    private JLabel photoPreview;
    private JButton choosePhotoBtn;
    private BufferedImage photoImage;

    private JTextField nameField;
    private JComboBox<String> paramCombo;
    private JTextArea descriptionArea;

    public AddEntityWindow() {
        super("Add");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(buildTopBar(), BorderLayout.NORTH);
        add(buildCenter(), BorderLayout.CENTER);
    }

    private JComponent buildTopBar() {
        JPanel top = new JPanel(new BorderLayout(12, 12));
        top.setBorder(BorderFactory.createEmptyBorder(12, 16, 12, 16));

        backBtn = new JButton("←");
        saveBtn = new JButton("Save");
        backBtn.setPreferredSize(new Dimension(90, 40));
        saveBtn.setPreferredSize(new Dimension(90, 40));

        headerName = new JTextField("Название проекта");
        headerName.setHorizontalAlignment(SwingConstants.CENTER);
        headerName.setEditable(false);
        headerName.setFont(TITLE_FONT);

        top.add(backBtn, BorderLayout.WEST);
        top.add(headerName, BorderLayout.CENTER);
        top.add(saveBtn, BorderLayout.EAST);

        backBtn.addActionListener(e -> { new CreateWindow().setVisible(true); dispose(); });
        saveBtn.addActionListener(e -> onSave());

        return top;
    }

    private JComponent buildCenter() {
        JPanel center = new JPanel(new GridBagLayout());
        center.setBorder(BorderFactory.createEmptyBorder(16, 24, 24, 24));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8, 8, 8, 8);
        c.fill = GridBagConstraints.BOTH;

        // Левая колонка: фото
        JPanel photoPanel = new JPanel(new BorderLayout(8, 8));
        photoPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        photoPreview = new JLabel("Foto", SwingConstants.CENTER);
        photoPreview.setPreferredSize(new Dimension(260, 320));
        photoPreview.setOpaque(true);
        photoPreview.setBackground(new Color(245, 245, 245));
        photoPreview.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        photoPreview.setFont(new Font("Arial", Font.PLAIN, 16));

        choosePhotoBtn = new JButton("Выбрать…");
        choosePhotoBtn.addActionListener(e -> choosePhoto());

        photoPanel.add(photoPreview, BorderLayout.CENTER);
        photoPanel.add(choosePhotoBtn, BorderLayout.SOUTH);

        c.gridx = 0; c.gridy = 0; c.weightx = 0.35; c.weighty = 0;
        center.add(photoPanel, c);

        // Правая колонка: Name + Parameter
        JPanel rightForm = new JPanel();
        rightForm.setLayout(new BoxLayout(rightForm, BoxLayout.Y_AXIS));
        rightForm.setBorder(BorderFactory.createEmptyBorder(8, 24, 8, 24));

        JLabel nameLbl = new JLabel("Name");
        nameField = new JTextField();
        nameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        nameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 2, 0, UNDERLINE),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)
        ));

        rightForm.add(nameLbl);
        rightForm.add(Box.createVerticalStrut(6));
        rightForm.add(nameField);
        rightForm.add(Box.createVerticalStrut(20));

        JLabel paramLbl = new JLabel("Parameter");
        paramCombo = new JComboBox<>(new String[]{"Hero", "Item", "Location", "Plot"});
        paramCombo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        JPanel paramWrap = new JPanel();
        paramWrap.setLayout(new BoxLayout(paramWrap, BoxLayout.X_AXIS));
        paramWrap.add(Box.createHorizontalGlue());
        paramWrap.add(paramCombo);
        paramWrap.add(Box.createHorizontalGlue());

        rightForm.add(paramLbl);
        rightForm.add(Box.createVerticalStrut(6));
        rightForm.add(paramWrap);

        c.gridx = 1; c.gridy = 0; c.weightx = 0.65; c.weighty = 0;
        center.add(rightForm, c);

        // Нижний блок: Description на всю ширину
        JLabel descLbl = new JLabel("Description");
        JPanel descLblWrap = new JPanel(new BorderLayout());
        descLblWrap.add(descLbl, BorderLayout.WEST);

        descriptionArea = new JTextArea(10, 60);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        JScrollPane descScroll = new JScrollPane(
                descriptionArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );
        descScroll.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        c.gridx = 0; c.gridy = 1; c.gridwidth = 2; c.weightx = 1; c.weighty = 0;
        center.add(descLblWrap, c);

        c.gridx = 0; c.gridy = 2; c.gridwidth = 2; c.weightx = 1; c.weighty = 1;
        center.add(descScroll, c);

        return center;
    }

    // ===== Действия =====
    private void choosePhoto() {
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Выберите изображение");
        fc.setFileFilter(new FileNameExtensionFilter("Images (PNG, JPG)", "png", "jpg", "jpeg"));
        int res = fc.showOpenDialog(this);
        if (res == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            try {
                photoImage = ImageIO.read(file);
                updatePhotoPreview();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Не удалось загрузить изображение:\n" + ex.getMessage(),
                        "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void updatePhotoPreview() {
        if (photoImage == null) {
            photoPreview.setIcon(null);
            photoPreview.setText("Foto");
            return;
        }
        int w = photoPreview.getWidth()  > 0 ? photoPreview.getWidth()  : 260;
        int h = photoPreview.getHeight() > 0 ? photoPreview.getHeight() : 320;
        Image scaled = photoImage.getScaledInstance(w, h, Image.SCALE_SMOOTH);
        photoPreview.setText(null);
        photoPreview.setIcon(new ImageIcon(scaled));
    }

    private void onSave() {
        String msg = "Сохранение…\n" +
                "Name: " + nameField.getText().trim() + "\n" +
                "Parameter: " + paramCombo.getSelectedItem() + "\n" +
                "Description: " + (descriptionArea.getText().trim().isEmpty() ? "<empty>" : descriptionArea.getText().trim()) + "\n" +
                "Photo selected: " + (photoImage != null);
        JOptionPane.showMessageDialog(this, msg);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AddEntityWindow().setVisible(true));
    }
}
