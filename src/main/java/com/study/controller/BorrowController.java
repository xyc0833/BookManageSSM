package com.study.controller;

import com.study.service.BookService;
import com.study.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BorrowController {

    @Resource
    BookService service;

    @Resource
    UserService userservice;

    @GetMapping({"/borrow","/"})
    //这里不仅仅可以是Model，还可以是Map、ModelMap
    //可以单独添加一个Model作为形参进行设置，SpringMVC通过依赖注入会自动帮助我们传递实例对象
    //https://www.itbaima.cn/zh-CN/document/eve8gq72qmdb46sg
    public String borrow(Model model){
        /***
         * // 拆解1：获取安全上下文（SecurityContext）
         * SecurityContext context = SecurityContextHolder.getContext();
         * // 拆解2：从上下文获取认证信息（Authentication）
         * Authentication authentication = context.getAuthentication();
         * // 拆解3：从认证信息获取用户主体（Principal）
         * Object principal = authentication.getPrincipal();
         * // 拆解4：强转为User对象（Spring Security的User类或自定义User类）
         * User user = (User) principal;
         */
        //直接使用SecurityContext对象来获取当前的认证信息
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //把后端的数据 通过 model这个对象 送到前端
        model.addAttribute("nickname",user.getUsername());
        model.addAttribute("borrow_list",service.getBorrowList());
        model.addAttribute("book_count",service.getBookList().size());
        model.addAttribute("student_count",userservice.getStudentList().size());
        return "borrow";
    }

    //添加借阅信息
    @GetMapping("/add-borrow")
    public String addBorrow(Model model){
        //直接使用SecurityContext对象来获取当前的认证信息
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("nickname",user.getUsername());
        model.addAttribute("book_list",service.getActiveBookList());
        model.addAttribute("student_list",userservice.getStudentList());
        return "add-borrow";
    }

    @PostMapping("/add-borrow")
    public String addBorrow(int student,int book){
        service.addBorrow(student,book);
        //添加成功之后直接重定向
        return "redirect:/borrow";
    }

    @GetMapping("/return-book")
    public String returnBook(String id){
        service.returnBook(id);
        return "redirect:/borrow";
    }
}
