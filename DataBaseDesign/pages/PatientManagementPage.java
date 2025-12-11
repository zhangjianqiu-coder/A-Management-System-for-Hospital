package pages;


import template.Patient;
import utils.Datas;
import utils.Dbinter;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

// 病人管理页面
// 1.所有病人信息展示（默认）
// 2.增删改模块
// 3.查询模块
public class PatientManagementPage extends JPanel{
        private static JTable patientTable;
        private static DefaultTableModel tableModel;
        private static JTextField searchField;
        private static JButton addButton, updateButton, deleteButton, queryButton, refreshButton ,exitButton;
        public static Dbinter db = new Dbinter();

        public PatientManagementPage() throws SQLException {
            setLayout(new BorderLayout());
            initUI();
        }

        // 传入一个ArrayList<Patient>即可把数据渲染到表格上（渲染所有病人信息/渲染查询结果的病人信息）
        public static void renderData(ArrayList<Patient> list) {
            // 先把原先的表清空
            ((DefaultTableModel)patientTable.getModel()).setRowCount(0);
            // 把list转化为二维数组data
            Object[][] data = Patient.convertPatientsToArray(list);
            // 渲染data里面的数据
            for (Object[] row : data) {
                tableModel.addRow(row);
            }
        }

        private void initUI() throws SQLException {
            // 顶部面板 - 搜索和操作按钮
            JPanel topPanel = new JPanel(new BorderLayout());
            topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // 搜索面板
            JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            searchPanel.add(new JLabel("搜索病人:"));
            searchField = new JTextField(20);
            searchPanel.add(searchField);

            queryButton = new JButton("搜索");
            searchPanel.add(queryButton);

            refreshButton = new JButton("刷新");
            searchPanel.add(refreshButton);

            topPanel.add(searchPanel, BorderLayout.WEST);

            // 操作按钮面板
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

            addButton = new JButton("添加病人");
            updateButton = new JButton("修改选中");
            deleteButton = new JButton("删除选中");
            exitButton = new JButton("返回");

            buttonPanel.add(addButton);
            buttonPanel.add(updateButton);
            buttonPanel.add(deleteButton);
            buttonPanel.add(exitButton);

            topPanel.add(buttonPanel, BorderLayout.EAST);

            add(topPanel, BorderLayout.NORTH);

            // 中间面板 - 病人信息表格
            String[] columnNames = {"病人ID", "姓名", "性别", "年龄","入院日期" , "所属科室", "病房号","病床号", "状况", "主治医师"};
            tableModel = new DefaultTableModel(columnNames, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false; // 表格不可编辑
                }
            };

            patientTable = new JTable(tableModel); // 把模板添加到表中
            patientTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            patientTable.setRowHeight(30);
            patientTable.getTableHeader().setFont(new Font("微软雅黑", Font.BOLD, 12));
            patientTable.setFont(new Font("微软雅黑", Font.PLAIN, 12));

            // 设置列宽
            patientTable.getColumnModel().getColumn(0).setPreferredWidth(80);  // ID
            patientTable.getColumnModel().getColumn(1).setPreferredWidth(100); // 姓名
            patientTable.getColumnModel().getColumn(2).setPreferredWidth(60);  // 性别
            patientTable.getColumnModel().getColumn(3).setPreferredWidth(100);// 年龄
            patientTable.getColumnModel().getColumn(4).setPreferredWidth(120); // 入院日期
            patientTable.getColumnModel().getColumn(5).setPreferredWidth(100); // 所属科室
            patientTable.getColumnModel().getColumn(6).setPreferredWidth(100); // 病房号
            patientTable.getColumnModel().getColumn(7).setPreferredWidth(80); // 病床号
            patientTable.getColumnModel().getColumn(8).setPreferredWidth(200); // 状况
            patientTable.getColumnModel().getColumn(9).setPreferredWidth(100); // 主治医师



            JScrollPane scrollPane = new JScrollPane(patientTable);
            scrollPane.setBorder(BorderFactory.createTitledBorder("病人信息详情"));
            scrollPane.setPreferredSize(new Dimension(800, 400));

            add(scrollPane, BorderLayout.CENTER);

            // 渲染数据
            db.getPatientToArray(Datas.patientList);
            renderData(Datas.patientList);
        }


        // 公共方法供外部调用
        public JTable getPatientTable() {
            return patientTable;
        }

        public DefaultTableModel getTableModel() {
            return tableModel;
        }

        public JTextField getSearchField() {
            return searchField;
        }

        public JButton getAddButton() {
            return addButton;
        }

        public JButton getUpdateButton() {
            return updateButton;
        }

        public JButton getDeleteButton() {
            return deleteButton;
        }

        public JButton getQueryButton() {
            return queryButton;
        }

        public JButton getRefreshButton() {
            return refreshButton;
        }

        public JButton getExitButton() {return exitButton;}
}
