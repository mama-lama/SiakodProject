package ru.vsu.sc.siakod25.g11.theme_6.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;

public class FeedbackWindow extends JFrame {

    private static final int WIDTH = 1200;
    private static final int HEIGHT = 800;
    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 22);
    private static final Font LABEL_FONT = new Font("Arial", Font.BOLD, 14);

    private final JTextField emailField = new JTextField(32);
    private final JTextField subjectField = new JTextField(32);
    private final JTextArea  messageArea = new JTextArea(12, 50);

    private static final String DEFAULT_TO = "ksu21sh@gmail.com";

    public FeedbackWindow() {
        super("Feedback");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() {
        JPanel root = new JPanel(new BorderLayout(12, 12));
        root.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JPanel top = new JPanel(new BorderLayout(8, 8));
        JButton back = new JButton("←");
        back.setFont(TITLE_FONT);
        back.addActionListener(e -> { new StartWindow().setVisible(true); dispose(); });
        top.add(back, BorderLayout.WEST);

        JLabel title = new JLabel("Feedback");
        title.setFont(TITLE_FONT);
        JPanel center = new JPanel(new FlowLayout(FlowLayout.LEFT));
        center.add(title);
        top.add(center, BorderLayout.CENTER);

        root.add(top, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8, 8, 8, 8);
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;

        int row = 0;

        c.gridx = 0; c.gridy = row; c.weightx = 0; form.add(label("Your email:"), c);
        c.gridx = 1; c.gridy = row; c.weightx = 1; form.add(emailField, c);
        row++;

        c.gridx = 0; c.gridy = row; c.weightx = 0; form.add(label("Subject:"), c);
        c.gridx = 1; c.gridy = row; c.weightx = 1; form.add(subjectField, c);
        row++;

        c.gridx = 0; c.gridy = row; c.weightx = 0; c.anchor = GridBagConstraints.NORTHWEST;
        form.add(label("Message:"), c);
        c.gridx = 1; c.gridy = row; c.weightx = 1;
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        JScrollPane sp = new JScrollPane(messageArea);
        form.add(sp, c);

        root.add(form, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton sendBtn  = new JButton("Send");
        JButton cancelBtn = new JButton("Cancel");

        sendBtn.addActionListener(e -> sendViaMailto());

        cancelBtn.addActionListener(e -> {
            new StartWindow().setVisible(true);
            dispose();
        });

        sendBtn.setMnemonic(KeyEvent.VK_ENTER);

        bottom.add(sendBtn);
        bottom.add(cancelBtn);
        root.add(bottom, BorderLayout.SOUTH);

        setContentPane(root);
    }

    private JLabel label(String s) {
        JLabel l = new JLabel(s);
        l.setFont(LABEL_FONT);
        return l;
    }

    private String nvl(String s) { return s == null ? "" : s.trim(); }

    private void sendViaMailto() {
        try {
            String to = DEFAULT_TO;
            String subject = URLEncoder.encode(nvl(subjectField.getText()), "UTF-8");
            String body    = URLEncoder.encode(
                    "From: " + nvl(emailField.getText()) + "\n\n" + nvl(messageArea.getText()),
                    "UTF-8"
            );

            String uri = "mailto:" + to + "?subject=" + subject + "&body=" + body;
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().mail(new URI(uri));
            } else {
                JOptionPane.showMessageDialog(this, "Mailto не поддерживается на этой системе.");
            }
        } catch (UnsupportedEncodingException ignored) {
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Не удалось открыть почтовый клиент:\n" + ex.getMessage(),
                    "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }
}
