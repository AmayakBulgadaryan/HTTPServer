import java.io.*;

public class User implements Serializable{

    private static final Long serialVersionUID = 11235813L;

    private String name;

    private int age;

    private long salary;

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setSalary(long salary) {
        this.salary = salary;
    }

    public static Long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public long getSalary() {
        return salary;
    }

}
