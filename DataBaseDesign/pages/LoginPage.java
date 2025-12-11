package pages;

import utils.Datas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class LoginPage extends JFrame {
    // 声明按钮为成员变量
    private JButton loginButton;
    private JButton registerButton;
    private JButton refreshCaptchaButton;  // 新增：刷新验证码按钮
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField codeField; // 验证码输入框
    private JLabel captchaImageLabel;  // 新增：验证码图片标签

    // 验证码生成相关
    private String currentCaptchaCode;
    private static final String CAPTCHA_CHARS = "ABCDEFGHJKLMNPQRSTUVWXYZabcdefghjkmnpqrstuvwxyz23456789";
    private static final int CAPTCHA_WIDTH = 80;  // 减小图片宽度
    private static final int CAPTCHA_HEIGHT = 30; // 减小图片高度

    public LoginPage() {
        // 设置窗口属性
        setTitle("医院管理系统");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 380);  // 稍微调整窗口大小
        setLocationRelativeTo(null);
        setResizable(false);

        // 创建主面板
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 245, 250));

        // 添加各个面板
        mainPanel.add(createTitlePanel(), BorderLayout.NORTH);
        mainPanel.add(createFormPanel(), BorderLayout.CENTER);
        mainPanel.add(createButtonPanel(), BorderLayout.SOUTH);

        add(mainPanel);

        // 初始化验证码
        refreshCaptcha();
    }

    // 提供获取登录按钮的方法
    public JButton getLoginButton() {
        return loginButton;
    }

    // 提供获取注册按钮的方法
    public JButton getRegisterButton() {
        return registerButton;
    }

    // 提供获取刷新验证码按钮的方法
    public JButton getRefreshCaptchaButton() {
        return refreshCaptchaButton;
    }

    // 提供获取用户名输入框的方法
    public JTextField getUsernameField() {
        return usernameField;
    }

    // 提供获取密码输入框的方法
    public JPasswordField getPasswordField() {
        return passwordField;
    }

    // 提供获取验证码输入框的方法
    public JTextField getCodeField() {
        return codeField;
    }

    // 提供获取当前验证码的方法
    public String getCurrentCaptchaCode() {
        return currentCaptchaCode;
    }

    // 刷新验证码
    public void refreshCaptcha() {
        currentCaptchaCode = generateRandomCode(4);
        Datas.code = currentCaptchaCode;
        System.out.println(Datas.code);
        BufferedImage captchaImage = generateCaptchaImage(currentCaptchaCode);
        captchaImageLabel.setIcon(new ImageIcon(captchaImage));
    }

    // A.titlePanel详细配置
    private JPanel createTitlePanel() {
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(70, 130, 180));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JLabel titleLabel = new JLabel("医院管理系统");
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);

        titlePanel.add(titleLabel);
        return titlePanel;
    }

    // B.fromPanel详细配置
    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(240, 245, 250));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 8, 8, 8);

        // B.a 用户名标签
        JLabel userLabel = new JLabel("用户名");
        userLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(userLabel, gbc);

        // B.b 用户名输入框
        usernameField = new JTextField(15);
        usernameField.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;  // 占据两列
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(usernameField, gbc);

        // B.c 密码标签
        JLabel passLabel = new JLabel("密  码");
        passLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(passLabel, gbc);

        // B.d 密码输入框
        passwordField = new JPasswordField(15);
        passwordField.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(passwordField, gbc);

        // B.e 验证码标签
        JLabel captchaLabel = new JLabel("验证码");
        captchaLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(captchaLabel, gbc);

        // B.f 验证码输入框
        codeField = new JTextField(10);  // 增加列数，使输入框变大
        codeField.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(codeField, gbc);

        // B.g 验证码图片
        captchaImageLabel = new JLabel();
        captchaImageLabel.setPreferredSize(new Dimension(CAPTCHA_WIDTH, CAPTCHA_HEIGHT));
        captchaImageLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(captchaImageLabel, gbc);

        // B.h 刷新验证码按钮（放在验证码图片下方）
        refreshCaptchaButton = new JButton("换一张");
        refreshCaptchaButton.setFont(new Font("微软雅黑", Font.PLAIN, 11));
        refreshCaptchaButton.setForeground(new Color(70, 130, 180));
        refreshCaptchaButton.setBackground(new Color(240, 245, 250));
        refreshCaptchaButton.setBorderPainted(false);
        refreshCaptchaButton.setFocusPainted(false);
        refreshCaptchaButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        refreshCaptchaButton.setPreferredSize(new Dimension(70, 20));
        refreshCaptchaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshCaptcha();
            }
        });

        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(2, 0, 0, 0);  // 减小上下边距
        formPanel.add(refreshCaptchaButton, gbc);

        return formPanel;
    }

    // C. JButton按钮详细配置
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(240, 245, 250));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // C.a 登录按钮
        loginButton = new JButton("登录");
        loginButton.setFont(new Font("微软雅黑", Font.BOLD, 14));
        loginButton.setBackground(new Color(70, 130, 180));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setPreferredSize(new Dimension(100, 35));

        // C.b 注册按钮
        registerButton = new JButton("注册");
        registerButton.setFont(new Font("微软雅黑", Font.BOLD, 14));
        registerButton.setBackground(new Color(93, 163, 74));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);
        registerButton.setPreferredSize(new Dimension(100, 35));

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        return buttonPanel;
    }

    // 生成验证码图片
    private BufferedImage generateCaptchaImage(String code) {
        BufferedImage image = new BufferedImage(CAPTCHA_WIDTH, CAPTCHA_HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();

        // 设置抗锯齿
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 设置背景色
        g.setColor(getRandomLightColor());
        g.fillRect(0, 0, CAPTCHA_WIDTH, CAPTCHA_HEIGHT);

        // 添加噪点
        addNoise(g);

        // 设置字体，根据图片高度调整字体大小
        g.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, CAPTCHA_HEIGHT - 10));

        // 绘制字符
        for (int i = 0; i < code.length(); i++) {
            // 随机颜色
            g.setColor(getRandomDarkColor());

            // 计算位置
            int x = CAPTCHA_WIDTH / (code.length() + 1) * (i + 1) - 8;
            int y = CAPTCHA_HEIGHT - 8;

            // 添加随机旋转
            double angle = (Math.random() - 0.5) * Math.PI / 6; // ±15度
            g.rotate(angle, x, y);

            // 绘制字符
            g.drawString(String.valueOf(code.charAt(i)), x, y);

            // 恢复旋转
            g.rotate(-angle, x, y);
        }

        // 添加干扰线
        addInterferenceLines(g);

        // 添加边框
        g.setColor(Color.GRAY);
        g.drawRect(0, 0, CAPTCHA_WIDTH - 1, CAPTCHA_HEIGHT - 1);

        g.dispose();
        return image;
    }

    // 生成随机验证码字符串
    private String generateRandomCode(int length) {
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * CAPTCHA_CHARS.length());
            code.append(CAPTCHA_CHARS.charAt(index));
        }
        return code.toString();
    }

    // 添加噪点
    private void addNoise(Graphics2D g) {
        for (int i = 0; i < 30; i++) {  // 减少噪点数量
            int x = (int) (Math.random() * CAPTCHA_WIDTH);
            int y = (int) (Math.random() * CAPTCHA_HEIGHT);
            g.setColor(getRandomColor());
            g.drawLine(x, y, x, y);
        }
    }

    // 添加干扰线
    private void addInterferenceLines(Graphics2D g) {
        for (int i = 0; i < 3; i++) {  // 减少干扰线数量
            int x1 = (int) (Math.random() * CAPTCHA_WIDTH);
            int y1 = (int) (Math.random() * CAPTCHA_HEIGHT);
            int x2 = (int) (Math.random() * CAPTCHA_WIDTH);
            int y2 = (int) (Math.random() * CAPTCHA_HEIGHT);
            g.setColor(getRandomColor());
            g.drawLine(x1, y1, x2, y2);
        }
    }

    // 获取随机浅色（用于背景）
    private Color getRandomLightColor() {
        int r = 200 + (int) (Math.random() * 55); // 200-255
        int g = 200 + (int) (Math.random() * 55);
        int b = 200 + (int) (Math.random() * 55);
        return new Color(r, g, b);
    }

    // 获取随机深色（用于文字）
    private Color getRandomDarkColor() {
        int r = (int) (Math.random() * 100); // 0-100
        int g = (int) (Math.random() * 100);
        int b = (int) (Math.random() * 100);
        return new Color(r, g, b);
    }

    // 获取随机颜色
    private Color getRandomColor() {
        return new Color(
                (int) (Math.random() * 256),
                (int) (Math.random() * 256),
                (int) (Math.random() * 256)
        );
    }
}