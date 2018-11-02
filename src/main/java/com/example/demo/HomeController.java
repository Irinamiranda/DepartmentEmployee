package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class HomeController {

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @RequestMapping("/")
    public String listDepartments(Model model){
        model.addAttribute("Departments", departmentRepository.findAll());
        return "listdepartments";
    }

    @GetMapping("/adddepartment")
    public String departmentForm(Model model){
        model.addAttribute("department", new Department());
        return "departmentform";
    }

    @PostMapping("/processdepartment")
    public String processDepartmentForm(@Valid Department department, BindingResult result){
        if (result.hasErrors()){
            return "departmentform";
        }
        departmentRepository.save(department);
        return "redirect:/";
    }
    @GetMapping("/addemployee")
    public String employeeForm(Model model){
        model.addAttribute("employee", new Employee());
        return "employeeform";
    }

    @PostMapping("/processemployee")
    public String processEmployeeForm(@Valid Employee employee, BindingResult result){
        if (result.hasErrors()){
            return "employeeform";
        }
        employeeRepository.save(employee);
        return "listemployee";
    }

    @RequestMapping("/detail/{id}")
    public String showEmployee(@PathVariable("id") long id, Model model){
        model.addAttribute("employee", employeeRepository.findById(id));
        return "listemployee";
    }

    @RequestMapping("/update/{id}")
    public String updateEmployee(@PathVariable("id") long id, Model model){
        model.addAttribute("employee", employeeRepository.findById(id));
        return "employeeform";
    }

    @RequestMapping("/delete/{id}")
    public String delEmployee(@PathVariable("id") long id){
        employeeRepository.deleteById(id);
        return "redirect:/";
    }


}
