package pages;

import javax.swing.*;
import java.awt.*;

public class RegisterPage extends JPanel {
    private JButton submitButton;
    private JButton exitButton;
    private JTextField inputField1;
    private JTextField inputField2;

    public RegisterPage() {
        setPreferredSize(new Dimension(500, 400));

        // 创建主面板
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 245, 250));

        // 添加各个面板
        mainPanel.add(createTitlePanel(), BorderLayout.NORTH);
        mainPanel.add(createFormPanel(), BorderLayout.CENTER);
        mainPanel.add(createButtonPanel(), BorderLayout.SOUTH);

        add(mainPanel);
    }

    // 提供获取按钮的方法
    public JButton getSubmitButton() {
        return submitButton;
    }

    public JButton getExitButton() {
        return exitButton;
    }

    // 提供获取输入框的方法
    public JTextField getInputField1() {
        return inputField1;
    }

    public JTextField getInputField2() {
        return inputField2;
    }

    // A. 标题面板
    private JPanel createTitlePanel() {
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(70, 130, 180));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));

        JLabel titleLabel = new JLabel("新用户注册");
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);

        titlePanel.add(titleLabel);
        return titlePanel;
    }

    // B. 表单面板
    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(240, 245, 250));
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 20, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(12, 8, 12, 8);

        // 第一个输入框标签
        JLabel label1 = new JLabel("用户名");
        label1.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(label1, gbc);

        // 第一个输入框
        inputField1 = new JTextField(15);
        inputField1.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(inputField1, gbc);

        // 第二个输入框标签
        JLabel label2 = new JLabel("密 码");
        label2.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(label2, gbc);

        // 第二个输入框
        inputField2 = new JTextField(15);
        inputField2.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(inputField2, gbc);

        return formPanel;
    }

    // C. 按钮面板
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(240, 245, 250));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // 2个按钮
        submitButton = new JButton("注册");
        submitButton.setFont(new Font("微软雅黑", Font.BOLD, 14));
        submitButton.setBackground(new Color(27, 131, 28));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFocusPainted(false);
        submitButton.setPreferredSize(new Dimension(100, 35));

        exitButton = new JButton("返回");
        exitButton.setFont(new Font("微软雅黑", Font.BOLD, 14));
        exitButton.setBackground(new Color(27, 131, 28));
        exitButton.setForeground(Color.WHITE);
        exitButton.setFocusPainted(false);
        exitButton.setPreferredSize(new Dimension(100, 35));

        buttonPanel.add(submitButton);
        buttonPanel.add(exitButton);
        return buttonPanel;
    }
}