package pages;

import utils.Datas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPage extends JFrame {

    public MainPage() {
        // 设置窗口
        setTitle("医院管理系统");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 初始化界面
        initUI();
    }

    JButton btn1 = new JButton("住院部");
    JButton btn2 = new JButton("门诊部");
    JButton btn3 = new JButton("员工管理");
    JButton btn4 = new JButton("药品与仪器");

    private JLabel userLabel;

    private void initUI() {
        // 1. 用户名标签 - 右上角
        userLabel = new JLabel("当前用户: 张三");
        userLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        JPanel userPanel = new JPanel(new BorderLayout());
        userPanel.add(userLabel, BorderLayout.EAST);
        userPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 2. 欢迎标签 - 中间
        JLabel welcomeLabel = new JLabel("欢迎使用医院系统", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("微软雅黑", Font.BOLD, 24));
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(50, 0, 50, 0));

        // 3. 四个按钮 - 下面
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 50, 50));

        buttonPanel.add(btn1);
        buttonPanel.add(btn2);
        buttonPanel.add(btn3);
        buttonPanel.add(btn4);

        // 添加到窗口
        add(userPanel, BorderLayout.NORTH);
        add(welcomeLabel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    // 实现跳转时的用户名自动更新
    public void updateUsername() {
        userLabel.setText("当前用户: " + Datas.username);
    }

    // get按钮的方法
    public JButton getBtn1() {
        return btn1;
    }
    public JButton getBtn2() {
        return btn2;
    }
    public JButton getBtn3() {
        return btn3;
    }
    public JButton getBtn4() {
        return btn4;
    }
}