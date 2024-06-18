import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;

public class EmployeeManagementSystem {
    public static class DatabaseConnection {
        private static final String URL = "jdbc:mysql://localhost:3306/employeemanagement?useSSL=false";
        private static final String USER = "root";
        private static final String PASSWORD = "null";

        public static Connection getConnection() throws SQLException {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        }
    }
    public static class EmployeeDAO {
        public static void addEmployee(Employee employee) {
            // 使用JDBC插入员工数据到数据库
            Connection connection = null;
            PreparedStatement statement = null;
            try {
                // 获取数据库连接
                connection = DatabaseConnection.getConnection();

                String sql = "INSERT INTO employees (name, role, email, salary) VALUES ( ?, ?, ?, ?)";

                // 创建PreparedStatement对象
                statement = connection.prepareStatement(sql);

                // 设置SQL语句中的参数
                statement.setString(1, employee.getName());
                statement.setString(2, employee.getRole());
                statement.setString(3, employee.getEmail());
                statement.setDouble(4, employee.getSalary());

                // 执行SQL语句
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                // 关闭资源，避免资源泄露
                if (statement != null) {
                    try {
                        statement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        public static void deleteEmployee(int id) {
            // 使用JDBC从数据库删除员工
            Connection connection = null;
            PreparedStatement statement = null;
            try {
                // 获取数据库连接
                connection = DatabaseConnection.getConnection();

                // 准备SQL删除语句
                String sql = "DELETE FROM employees WHERE id = ?";

                // 创建PreparedStatement对象
                statement = connection.prepareStatement(sql);

                // 设置SQL语句中的参数，即员工的ID
                statement.setInt(1, id);

                // 执行SQL语句
                statement.executeUpdate();
            } catch (SQLException e) {
                // 处理可能出现的异常
                e.printStackTrace();
            } finally {
                // 关闭资源，避免资源泄露
                if (statement != null) {
                    try {
                        statement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        public static void updateEmployee(Employee employee) {
            // 使用JDBC更新数据库中的员工信息
            Connection connection = null;
            PreparedStatement statement = null;
            try {
                // 获取数据库连接
                connection = DatabaseConnection.getConnection();

                String sql = "UPDATE employees SET name = ?, role = ?, email = ?, salary = ? WHERE id = ?";

                // 创建PreparedStatement对象
                statement = connection.prepareStatement(sql);

                // 设置SQL语句中的参数，这些参数来自Employee对象
                statement.setString(1, employee.getName());
                statement.setString(2, employee.getRole());
                statement.setString(3, employee.getEmail());
                statement.setDouble(4, employee.getSalary());
                // 最后设置该员工的ID，作为更新的条件
                statement.setInt(5, employee.getId());

                // 执行SQL语句
                statement.executeUpdate();
            } catch (SQLException e) {
                // 处理可能出现的异常
                e.printStackTrace();
            } finally {
                // 关闭资源，避免资源泄露
                if (statement != null) {
                    try {
                        statement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        public static List<Employee> getAllEmployees() {
            // 使用JDBC查询数据库中所有员工的信息
            List<Employee> employees = new ArrayList<>();
            Connection connection = null;
            PreparedStatement statement = null;
            ResultSet resultSet = null;
            try {
                // 获取数据库连接
                connection = DatabaseConnection.getConnection();

                // 准备SQL查询语句。这里选择查询employees表中的所有记录
                String sql = "SELECT id, name, role, email, salary FROM employees";

                // 创建PreparedStatement对象
                statement = connection.prepareStatement(sql);

                // 执行查询，获取结果集
                resultSet = statement.executeQuery();

                // 遍历结果集，将每条记录转化为Employee对象，并添加到列表中
                while (resultSet.next()) {
                    Employee employee = new Employee();
                    employee.setId(resultSet.getInt("id"));
                    employee.setName(resultSet.getString("name"));
                    employee.setRole(resultSet.getString("role"));
                    employee.setEmail(resultSet.getString("email"));
                    employee.setSalary(resultSet.getDouble("salary"));

                    employees.add(employee);
                }
            } catch (SQLException e) {
                // 处理可能出现的异常
                e.printStackTrace();
            } finally {
                // 关闭资源，避免资源泄露
                if (resultSet != null) {
                    try {
                        resultSet.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if (statement != null) {
                    try {
                        statement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            return employees;
        }
    }
}
