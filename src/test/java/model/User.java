package model;

/**
 * Created by Song on 2015/12/5.
 */
public class User {

    private String name;

    private int age ;

    private String password;

    private Father father;

    public User(String name, int age, String password) {
        this.name = name;
        this.age = age;
        this.password = password;
    }

    public User(String name, int age, String password, Father father) {
        this.name = name;
        this.age = age;
        this.password = password;
        this.father = father;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Father getFather() {
        return father;
    }

    public void setFather(Father father) {
        this.father = father;
    }
}
