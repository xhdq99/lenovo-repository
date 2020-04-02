package com.hwm.controller.teacher;

import com.hwm.jdbc.HomeworkJdbc;
import com.hwm.jdbc.StudentJdbc;
import com.hwm.jdbc.SubmitHomeworkJdbc;
import com.hwm.model.Homework;
import com.hwm.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@Controller
@RequestMapping("/teacher")
public class TeacherController {
    private HomeworkJdbc homeworkJdbc;
    private StudentJdbc studentJdbc;
    private SubmitHomeworkJdbc submitHomeworkJdbc;

    @Autowired
    public TeacherController(HomeworkJdbc homeworkJdbc, StudentJdbc studentJdbc, SubmitHomeworkJdbc submitHomeworkJdbc) {
        this.homeworkJdbc = homeworkJdbc;
        this.studentJdbc = studentJdbc;
        this.submitHomeworkJdbc = submitHomeworkJdbc;
    }

    @RequestMapping(value = "/add_homework", method = RequestMethod.POST)
    public String addHomework(HttpServletRequest req) {
        try {
            req.setCharacterEncoding("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Homework homework = new Homework();
        homework.setTitle(req.getParameter("title"));
        homework.setContent(req.getParameter("content"));

        String info;
        if (homeworkJdbc.addHomework(homework)) {
            info = "添加作业成功";
        } else {
            info = "添加失败";
        }

        req.setAttribute("info", info);
        return "/return.jsp";
    }

    @RequestMapping(value = "/add_student", method = RequestMethod.POST)
    public String addStudent(HttpServletRequest req) {
        try {
            req.setCharacterEncoding("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Student student = new Student();
        student.setId(Integer.parseInt(req.getParameter("id")));
        student.setName(req.getParameter("name"));

        String info;
        if (studentJdbc.addStudent(student)) {
            info = "添加学生成功";
        } else {
            info = "添加失败，学号已经存在";
        }

        req.setAttribute("info", info);
        return "/return.jsp";
    }

    @RequestMapping("/homework_list")
    public String homeworkList(HttpServletRequest req) {
        var list = homeworkJdbc.selectAll();
        req.setAttribute("list", list);
        return "homeworkList.jsp";
    }

    @RequestMapping("/student_list")
    public String studentList(HttpServletRequest req) {
        var list = studentJdbc.selectAll();
        req.setAttribute("list", list);
        return "studentList.jsp";
    }

    @RequestMapping("/submit_homework_list")
    public String submitHomeworkList(HttpServletRequest req) {
        var list = submitHomeworkJdbc.select(Integer.parseInt(req.getParameter("homeworkId")));
        req.setAttribute("list", list);
        return "submitHomeworkList.jsp";
    }
}
