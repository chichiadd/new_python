public class Employee {
    private int id;
    private String name;
    private String role;
    private String email;
    private double salary;

    // 构造函数
    public Employee( String name, String role, String email, double salary) {
        this.name = name;
        this.role = role;
        this.email = email;
        this.salary = salary;
    }
    public Employee(int id,String name, String role, String email, double salary) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.email = email;
        this.salary = salary;
    }
    //无参构造函数
    public Employee() {
    }

    // getter和setter方法
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }


}
