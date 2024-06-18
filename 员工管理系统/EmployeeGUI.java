import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
public class EmployeeGUI {
    private JFrame frame;
    private JPanel panel;
    private JTextField inputField;
    private JButton submitButton = new JButton("提交");
    private JButton deleteButton = new JButton("删除员工");
    private Map<String, JTextField> employeeFields = new HashMap<>(); // 只定义一次

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                EmployeeGUI window = new EmployeeGUI();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public EmployeeGUI() {
        initialize();//设置窗口的初始状态
    }

    private void initialize() {
        frame = new JFrame();
        frame.setTitle("员工管理系统");
        frame.setBounds(100, 100, 500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        displayMainMenu();
    }

    private void displayMainMenu() {
        panel.removeAll();
        panel.revalidate();
        panel.repaint();

        JLabel titleLabel = new JLabel("********** 员工管理系统 **********");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titleLabel);

        JLabel optionsLabel = new JLabel("<html>1. 录入员工信息<br>2. 删除员工信息<br>3. 修改员工信息<br>4. 显示所有员工信息<br>5. 退出系统<br>请选择（1-5）：</html>");
        optionsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(optionsLabel);

        inputField = new JTextField();
        panel.add(inputField);
        inputField.setColumns(10);

        JLabel messageLabel = new JLabel(" ");
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(messageLabel);

        inputField.addActionListener(e -> processInput(inputField.getText(), messageLabel));
    }

    private void processInput(String inputText, JLabel messageLabel) {
        switch (inputText) {
            case "1":
                showEmployeeEntryForm();
                break;
            case "2":
                showEmployeeDeletionForm();
                break;
            case "3":
                showEmployeeUpdateForm();
                break;
            case "4":
                showAllEmployees();
                break;
            case "5":
                System.exit(0);
            default:
                messageLabel.setText("输入错误：只能输入1-5之间的数字。");
                break;
        }
    }

    private void showEmployeeEntryForm() {
        panel.removeAll();

        JLabel formTitle = new JLabel("录入员工信息");
        formTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(formTitle);

        createEntryField("姓名", true);
        createEntryField("职务", true);  // 注意这里使用“职务”
        createEntryField("电子邮件", true);
        createEntryField("薪资", true);

        // 移除所有已存在的ActionListeners，避免重复添加
        for (ActionListener al : submitButton.getActionListeners()) {
            submitButton.removeActionListener(al);
        }
        submitButton.addActionListener(this::submitEmployeeInfo);

        panel.add(submitButton);

        panel.revalidate();
        panel.repaint();
    }

    private void createEntryField(String label, boolean addToMap) {
        panel.add(new JLabel(label + ":"));
        JTextField field = new JTextField(20);
        panel.add(field);
        if (addToMap) {
            employeeFields.put(label, field);
        }
    }

    private void submitEmployeeInfo(ActionEvent e) {
        try {
            for (Map.Entry<String, JTextField> entry : employeeFields.entrySet()) {
                if (entry.getValue().getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "请输入所有字段");
                    return;
                }
            }

            Employee employee = new Employee(
                    employeeFields.get("姓名").getText().trim(),
                    employeeFields.get("职务").getText().trim(), // 使用了“职务”
                    employeeFields.get("电子邮件").getText().trim(),
                    Double.parseDouble(employeeFields.get("薪资").getText().trim())
            );

            EmployeeManagementSystem.EmployeeDAO.addEmployee(employee);

            JOptionPane.showMessageDialog(frame, "员工信息已提交");
            displayMainMenu(); // 提交后返回主菜单
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "请确保ID和薪资输入正确的数字格式");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "提交时发生错误：" + ex.getMessage());
        }
    }
    private void showEmployeeDeletionForm() {
        panel.removeAll();

        JLabel formTitle = new JLabel("删除员工信息");
        formTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(formTitle);

        createDeletionField("员工ID", true);

        // 移除所有已存在的ActionListeners，避免重复添加
        for (ActionListener al : deleteButton.getActionListeners()) {
            deleteButton.removeActionListener(al);
        }
        deleteButton.addActionListener(this::deleteEmployeeInfo);

        panel.add(deleteButton);

        panel.revalidate();
        panel.repaint();
    }

    private void createDeletionField(String label, boolean addToMap) {
        panel.add(new JLabel(label + ":"));
        JTextField field = new JTextField(20);
        panel.add(field);
        if (addToMap) {
            employeeFields.put(label, field);
        }
    }

    private void deleteEmployeeInfo(ActionEvent e) {
        try {
            // 获取员工ID，并验证它是否被输入
            String employeeIdText = employeeFields.get("员工ID").getText().trim();
            if (employeeIdText.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "请输入员工ID");
                return;
            }
            int employeeId = Integer.parseInt(employeeIdText);

            // 调用deleteEmployee方法删除员工信息
            EmployeeManagementSystem.EmployeeDAO.deleteEmployee(employeeId);

            JOptionPane.showMessageDialog(frame, "员工信息已删除");
            displayMainMenu(); // 删除后返回主菜单
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "请确保输入的员工ID是正确的数字格式");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "删除时发生错误：" + ex.getMessage());
        }
    }

    private void showEmployeeUpdateForm() {
        panel.removeAll();

        JLabel formTitle = new JLabel("更新员工信息");
        formTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(formTitle);

        createEntryField("员工ID", true); // 首先需要输入员工ID来确定是哪位员工
        createEntryField("姓名", true);
        createEntryField("职务", true);
        createEntryField("电子邮件", true);
        createEntryField("薪资", true);

        // 移除所有已存在的ActionListeners，避免重复添加
        for (ActionListener al : submitButton.getActionListeners()) {
            submitButton.removeActionListener(al);
        }
        submitButton.addActionListener(this::submitUpdatedEmployeeInfo);

        panel.add(submitButton);

        panel.revalidate();
        panel.repaint();
    }

    private void submitUpdatedEmployeeInfo(ActionEvent e) {
        try {
            for (Map.Entry<String, JTextField> entry : employeeFields.entrySet()) {
                if (entry.getValue().getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "请输入所有字段");
                    return;
                }
            }

            int id = Integer.parseInt(employeeFields.get("员工ID").getText().trim());
            Employee updatedEmployee = new Employee(
                    id,
                    employeeFields.get("姓名").getText().trim(),
                    employeeFields.get("职务").getText().trim(),
                    employeeFields.get("电子邮件").getText().trim(),
                    Double.parseDouble(employeeFields.get("薪资").getText().trim())
            );

            EmployeeManagementSystem.EmployeeDAO.updateEmployee(updatedEmployee);

            JOptionPane.showMessageDialog(frame, "员工信息已更新");
            displayMainMenu(); // 更新后返回主菜单
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "请确保ID和薪资输入正确的数字格式");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "更新时发生错误：" + ex.getMessage());
        }
    }

    private void showAllEmployees() {
        panel.removeAll();

        JLabel formTitle = new JLabel("显示所有员工信息");
        formTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(formTitle);

        // 从数据库获取所有员工信息
        List<Employee> employees = EmployeeManagementSystem.EmployeeDAO.getAllEmployees();

        // 检查是否获取到员工信息
        if (employees.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "没有员工信息可显示");
            displayMainMenu();
            return;
        }

        // 设置表格的列名
        String[] columnNames = {"ID", "姓名", "职务", "电子邮件", "薪资"};

        // 将员工列表转换为表格数据
        Object[][] data = new Object[employees.size()][5];
        for (int i = 0; i < employees.size(); i++) {
            Employee employee = employees.get(i);
            data[i][0] = employee.getId();
            data[i][1] = employee.getName();
            data[i][2] = employee.getRole();
            data[i][3] = employee.getEmail();
            data[i][4] = employee.getSalary();
        }

        // 创建表格
        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        panel.add(scrollPane);

        panel.revalidate();
        panel.repaint();
    }
}
