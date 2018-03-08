package lambdas;

public class Employee implements Comparable<Employee>{
    private final String name;
    private final String id;
    private int age;
    private String address;
    private double salary;


    public Employee() {
        super();
        this.name="NO_NAME";
        this.id="DEFAULT_ID";
    }
    public Employee(String id,String name) {
        super();
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public int getAge() {
        return age;
    }

    public Employee setAge(int age) {
        this.age = age;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public Employee setAddress(String address) {
        this.address = address;
        return this;
    }

    public double getSalary() {
        return salary;
    }

    public Employee setSalary(double salary) {
        this.salary = salary;
        return this;
    }

    @Override
    public int compareTo(Employee o) {

        return -1*this.id.compareToIgnoreCase(o.id);
    }


    @Override
    public String toString() {
        return "Employee [name=" + name + ", id=" + id + ", age=" + age + ", address=" + address + ", salary="
                + salary + "]";
    }

    public boolean highlyPaid()
    {
        return salary>10000000;
    }
}
