/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import common.Library;
import java.util.ArrayList;
import java.util.Collections;
import model.Report;
import model.Student;
import model.CourseOfStudent;
import view.Menu;

/**
 *
 * @author DiepTCNN
 */
public class StudentManagement extends Menu {

    static String[] mc = {"Create", "Find and Sort", "Update/Delete", "Report", "Exit"};
    Library l;
    ArrayList<Student> list_s;
    ArrayList<CourseOfStudent> list_cs;
    Student s;

    public StudentManagement() {
        super("PROGRAMMING", mc);
        l = new Library();
        list_s = new ArrayList<>();
        list_cs = new ArrayList<>();
        s = new Student();
        createFakeData();
    }

    public void execute(int n) {
        switch (n) {
            case 1:
                createStudent();
                break;
            case 2:
                findSort();
                break;
            case 3:
                update_Delete();
                break;
            case 4:
                report();
                break;
            case 5:
                System.exit(0);
        }
    }

    public void report() {
        if (list_s == null) {
            System.err.println("List empty");
            return;
        }
        ArrayList<Report> list_Rp = new ArrayList<>();
        for (CourseOfStudent cs : list_cs) {
            int total = 0;
            int id = cs.getId();
            String courseName = cs.getCourseName();
            if (!checkReport(list_Rp, id, courseName, total)) {
                list_Rp.add(new Report(id, courseName, total + 1));
            } else {
                for (Report r : list_Rp) {
                    if (cs.getId() == r.getId() && r.getCourseName().equalsIgnoreCase(courseName)) {
                        total++;
//                        r.setId(id);
//                        r.setCourseName(courseName);
                        r.setTotalCourse(total);
                    }

                }
            }
        }
        for (int i = 0; i < list_Rp.size(); i++) {
            System.out.println("Id: " + list_Rp.get(i).getId() + " - Course: " + list_Rp.get(i).getCourseName() + " - Total: " + list_Rp.get(i).getTotalCourse());
        }
    }

    public boolean checkReport(ArrayList<Report> list_Rp, int id, String courseName, int total) {
        for (Report rp : list_Rp) {
            if (id == rp.getId() && courseName.equalsIgnoreCase(rp.getCourseName()) && total == rp.getTotalCourse()) {
                return true;
            }
        }
        return false;
    }

    public boolean checkSize() {
        if (list_s.size() > 10) {
            String s = l.getString("Do you want to continue (Y/N)? ");
            if ("Y".equals(s)) {
                return true;
            }
            return false;
        }
        return true;
    }

    public void createStudent() {
        if (checkSize()) {
            String name;
            int id = l.getInt("Enter Student Id", 1, 1000);
            if (!checkID(list_s, id)) {
                name = l.getString("Enter Student name: ");
                list_s.add(new Student(id, name));
            }
            int semester = l.getInt("Enter semester", 1, 10);
            String courseName = l.getString("Enter courseName: ");
            list_cs.add(new CourseOfStudent(id, semester, courseName));
        }
    }

    public void createFakeData() {
        list_s.add(new Student(10, "Huy"));
        list_cs.add(new CourseOfStudent(10, 2, "Java"));
        list_s.add(new Student(11, "Diep"));
        list_cs.add(new CourseOfStudent(11, 3, "Java OOP"));
        list_s.add(new Student(12, "Thach"));
        list_cs.add(new CourseOfStudent(12, 4, ".Net"));
        list_s.add(new Student(13, "Thinh"));
        list_cs.add(new CourseOfStudent(13, 2, "Javaa"));
        list_s.add(new Student(14, "Chu Cho"));
        list_cs.add(new CourseOfStudent(14, 2, "Javaaa"));
        list_s.add(new Student(15, "Tai"));
        list_cs.add(new CourseOfStudent(15, 2, "Javaaaa"));
        list_s.add(new Student(16, "Linh"));
        list_cs.add(new CourseOfStudent(16, 2, "Javaaaa"));
        list_s.add(new Student(17, "Linh"));
        list_cs.add(new CourseOfStudent(17, 2, "Javaaaa"));
        list_s.add(new Student(18, "Linh"));
        list_cs.add(new CourseOfStudent(18, 2, "Javaaaa"));
    }

    public void displayStudent(ArrayList<Student> list_s) {
        for (Student s : list_s) {
            System.out.println("Id: " + s.getId() + " - Name: " + s.getName());
            for (CourseOfStudent cs : list_cs) {
                if (s.getId() == cs.getId()) {
                    System.out.println("Semester: " + cs.getSemester() + " - courseName: " + cs.getCourseName());
                }
            }
        }
    }

    public void findSort() {
        if (list_s == null) {
            System.err.println("List empty");
            return;
        }
        ArrayList<Student> list_ByName = listByName(list_s);
        if (list_ByName.isEmpty()) {
            System.err.println("Not exist");
        } else {
            Collections.sort(list_ByName);
            displayStudent(list_ByName);
        }
    }

    public ArrayList<Student> listByName(ArrayList<Student> list_s) {
        ArrayList<Student> list_Found = new ArrayList<Student>();
        String name = l.getString("Enter name to search: ");
        for (Student s : list_s) {
            if (s.getName().contains(name)) {
                list_Found.add(s);
            }
        }
        return list_Found;
    }

    public void update_Delete() {
        if (list_s == null) {
            System.err.println("List empty");
            return;
        }
        int id = l.getInt("Enter id to search", 1, 1000);
        ArrayList<Student> list_ById = listById(list_s, id);
        ArrayList<CourseOfStudent> list_ById_cs = listByIdCS(list_cs, id);
        if (list_ById.isEmpty() || list_ById_cs.isEmpty()) {
            System.err.println("Not exist");
        } else {
            System.out.println("Do you want to update or delete?");
            System.out.println("1. Update");
            System.out.println("2. Delete");
            Student s = list_ById.get(0);
            CourseOfStudent cs = list_ById_cs.get(0);
            int c = l.getInt("Enter choice", 1, 2);
            switch (c) {
                case 1:
                    //Exec update
                    s.setId(id);
                    s.setName(l.getString("Enter name"));
                    cs.setId(id);
                    cs.setSemester(l.getInt("Enter Semester", 1, 10));
                    cs.setCourseName(l.getString("Enter Course name"));
                    System.out.println("Update Succcess");
                    break;
                case 2:
                    list_cs.remove(cs);
                    list_s.remove(s);
                    System.out.println("Delete success");
                    break;
                default:
                    return;
            }
        }
    }

    public ArrayList<CourseOfStudent> listByIdCS(ArrayList<CourseOfStudent> list_s, int id) {
        ArrayList<CourseOfStudent> list_Found = new ArrayList<CourseOfStudent>();

        for (CourseOfStudent s : list_s) {
            if (s.getId() == id) {
                list_Found.add(s);
            }
        }
        return list_Found;
    }

    public ArrayList<Student> listById(ArrayList<Student> list_s, int id) {
        ArrayList<Student> list_Found = new ArrayList<Student>();

        for (Student s : list_s) {
            if (s.getId() == id) {
                list_Found.add(s);
            }
        }
        return list_Found;
    }

    public Student GetById(int id) {
        for (Student st : list_s) {
            if (st.getId() == id) {
                return st;
            }
        }
        return null;
    }

    public boolean checkID(ArrayList<Student> list, int id) {
        if (list.isEmpty()) {
            return false;
        } else {
            for (Student s : list_s) {
                if (s.getId() == id) {
                    return true;
                }
            }
            return false;
        }
    }
}
