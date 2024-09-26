package Database;

import java.io.Serializable;
import java.text.DecimalFormat;

public class Player implements Serializable {
    private String name;
    private String country;
    private int age, number;
    private double height, weeklySalary;
    private String club;
    private String position;
    private String price;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public void setNumber(int number) {
        this.number = number;
    }
    public void setHeight(double height) {
        this.height = height;
    }
    public void setWeeklySalary(double weeklySalary) {
        this.weeklySalary = weeklySalary;
    }
    public void setClub(String club) {
        this.club = club;
    }
    public void setPosition(String position) {
        this.position = position;
    }


    public String getName() {
        return name;
    }
    public String getCountry() {
        return country;
    }
    public int getAge() {
        return age;
    }
    public int getNumber() {
        return number;
    }
    public double getHeight() {
        return height;
    }
    public double getWeeklySalary() {
        return weeklySalary;
    }
    public String getClub() {
        return club;
    }
    public String getPosition() {
        return position;
    }

    public String toString() {
        DecimalFormat df = new DecimalFormat("#");
        df.setMaximumFractionDigits(4);
        return ("Name          : " + name +
                "\nCountry       : " + country +
                "\nAge           : " + age + " years" +
                "\nHeight        : " + height + " m" +
                "\nClub          : " + club +
                "\nPosition      : " + position +
                "\nNumber        : " + number +
                "\nWeekly Salary : " + df.format(weeklySalary) + "\n");
    }
}
