package tracker;

import java.util.*;

public class Student {
    private final String name;
    private final String surname;
    private final String email;
    private final int id;
    private int java;
    private int dsa;
    private int db;
    private int spring;
    Map<String, Boolean> notifiedCourses = new HashMap<>();
    public Student(String name, String surname, String email, int id, int java, int dsa, int db, int spring) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.id = id;
        this.java = java;
        this.dsa = dsa;
        this.db = db;
        this.spring = spring;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Student student)) {
            return false;
        }
        return Objects.equals(email, student.email);
    }

    @Override
    public int hashCode() {
        int result = 20;
        result = 31 * result + (email == null ? 0 : email.hashCode());
        return result;
    }
    public int getId() {
        return id;
    }
    public int getJava() {
        return java;
    }

    public void setJava(int java) {
        this.java = java;
    }

    public int getDsa() {
        return dsa;
    }

    public void setDsa(int dsa) {
        this.dsa = dsa;
    }

    public int getDb() {
        return db;
    }

    public void setDb(int db) {
        this.db = db;
    }

    public int getSpring() {
        return spring;
    }

    public void setSpring(int spring) {
        this.spring = spring;
    }
    public boolean isNotified(String course) {
        return notifiedCourses.getOrDefault(course, false);
    }

    public void setNotified(String course, boolean notified) {
        notifiedCourses.put(course, notified);
    }

    @Override
    public String toString() {
        return id + " points: " +
                "Java=" + java + ";" +
                " DSA=" + dsa + ";" +
                " Databases=" + db + ";" +
                " Spring=" + spring;
    }
    public String generateMailContent() {
        return
                "To: " + email + "" +
                "\nRe: Your learning Progress" +
                "\nHello, " + name + " " + surname + "! You have accomplished our ";
    }


}
