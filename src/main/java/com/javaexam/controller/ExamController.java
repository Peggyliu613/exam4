package com.javaexam.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.javaexam.models.Task;
import com.javaexam.models.User;
import com.javaexam.repositories.TaskRepo;
import com.javaexam.repositories.UserRepo;
import com.javaexam.services.TaskServ;
import com.javaexam.services.UserServ;
import com.javaexam.validator.UserValidator;

@Controller
public class ExamController {
    private final UserServ userServ;
    private final UserRepo userRepo;
    private final UserValidator userValidator;
    private final TaskServ taskServ;
    private final TaskRepo taskRepo;
    
    public ExamController(UserServ userServ, UserRepo userRepo, UserValidator userValidator, TaskServ taskServ, TaskRepo taskRepo) {
        this.userServ = userServ;
        this.userRepo = userRepo;
        this.userValidator = userValidator;
        this.taskServ = taskServ;
        this.taskRepo = taskRepo;
    }
    
    @RequestMapping("")
    public String registerForm(@ModelAttribute("user") User user) {
        return "index.jsp";
    }
    
    @RequestMapping(value="/registration", method=RequestMethod.POST)
    public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result, HttpSession session) {
    	userValidator.validate(user, result);
    	if (result.hasErrors()) {
            return "index.jsp";
        } else {
        	userServ.registerUser(user);
        	session.setAttribute("user", user.getId());
            return "redirect:/home";
        }
    }
    
    @RequestMapping(value="/login", method=RequestMethod.POST)
    public String loginUser(@RequestParam("email") String email, @RequestParam("password") String password, Model model, HttpSession session) {
    	Boolean result = userServ.authenticateUser(email, password);
    	
    	if (result==false) {
    		model.addAttribute("error", "email and password not matched");
    		model.addAttribute("user", new User());
            return "index.jsp";
        } else {
        	User user =userServ.findByEmail(email);
        	session.setAttribute("user", user.getId());
            return "redirect:/home";
        }
    }
    @RequestMapping("/home")
    public String home(HttpSession session, Model model) {
    	Long userId = (Long) session.getAttribute("user");
    	if (userId==null) {
    		return "redirect:/";
    	}
    	User theOne = userServ.findUserById(userId);
    	model.addAttribute("user", theOne);
    	
    	List<Task> tasks = taskRepo.findAll();
        model.addAttribute("tasks", tasks);
    	return "home.jsp";
    }
    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
    	return "redirect:/";
    }
    @GetMapping("/createTask")
    public String createTaskPage(HttpSession session, @ModelAttribute("task") Task task, Model model) {
    	Long userId = (Long) session.getAttribute("user");
    	User user = userServ.findUserById(userId);
    	List<User> users = userRepo.findAll();
        model.addAttribute("users", users);
    	return "create.jsp";
    }
    @PostMapping("/createTask")
    public String createTask(HttpSession session, @Valid @ModelAttribute("task") Task task, BindingResult result) {
    	Long userId = (Long) session.getAttribute("user");
    	User user = userServ.findUserById(userId);
    	if (result.hasErrors()) {
            return "create.jsp";
        } else {
        	task.setUser(user);
            taskRepo.save(task);
            return "redirect:/home";
        }
    }
    @GetMapping("/{taskid}")
    public String showPage(HttpSession session, @PathVariable("taskid") Long id, Model model) {
    	Long userId = (Long) session.getAttribute("user");
    	User user = userServ.findUserById(userId);
    	model.addAttribute("user", user);
    	Task theOne = taskServ.findOne(id);
    	model.addAttribute("task", theOne);
    	return "show.jsp";
    }
    @GetMapping("/edit/{taskid}")
    public String editPage(HttpSession session, @PathVariable("taskid") Long id, Model model) {
    	Long userId = (Long) session.getAttribute("user");
    	Task thetask = taskServ.findOne(id);
    	if (thetask.getUser().getId()!=userId) {
        	return "redirect:/home";
        }
        Task task = taskServ.findOne(id);
        model.addAttribute("task", task);
        List<User> users = userRepo.findAll();
        model.addAttribute("users", users);
        return "edit.jsp";
    }
    @PostMapping("/edit/{taskid}")
    public String edit(@PathVariable("taskid") Long id, 
    		@Valid @ModelAttribute("task") Task task, BindingResult result) {
    	if (result.hasErrors()) {
            return "edit.jsp";
        } else {
            task.setId(id);
            taskRepo.save(task);
            return "redirect:/books";
        }
    }
    @GetMapping("/delete/{taskid}")
    public String delete(@PathVariable("taskid") Long id) {
    	taskRepo.deleteById(id);
    	return "redirect:/home";
    }
    @GetMapping("/complete/{taskid}")
    public String complete(@PathVariable("taskid") Long id) {
    	taskRepo.deleteById(id);
    	return "redirect:/home";
    }
    
    
    
}