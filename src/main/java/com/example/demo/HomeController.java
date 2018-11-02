package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class HomeController {

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @RequestMapping("/")
    public String listDepartments(Model model) {
        model.addAttribute("departments", departmentRepository.findAll());
        return "listdepartments";
    }

    @GetMapping("/adddepartment")
    public String departmentForm(Model model) {
        model.addAttribute("department", new Department());
        return "departmentform";
    }

    @PostMapping("/processdepartment")
    public String processDepartmentForm(@Valid Department department, BindingResult result) {
        if (result.hasErrors()) {
            return "departmentform";
        }

        departmentRepository.save(department);
        return "redirect:/";
    }

    @GetMapping("/addemployee")
    public String employeeForm(Model model, @RequestParam("id") Long departmentId) {
        Employee employee = new Employee();
        Optional<Department> department = departmentRepository.findById(departmentId);
        employee.setDepartment(department.isPresent() ? department.get() : null);

        model.addAttribute("employee", employee);
        model.addAttribute("departments", departmentRepository.findAll());
        return "employeeform";
    }

    @PostMapping("/processemployee")
    public String processEmployeeForm(@Valid Employee employee, BindingResult result) {
        if (result.hasErrors()) {
            return "employeeform";
        }
        employeeRepository.save(employee);
        return "redirect:/";
    }

    @RequestMapping("/listemployees")
    public String listEmployees(Model model) {
        model.addAttribute("employees", employeeRepository.findAll());
        return "listemployees";
    }

    @RequestMapping("/detail/{id}")
    public String showEmployee(@PathVariable("id") long id, Model model) {
        model.addAttribute("employee", employeeRepository.findById(id).get());
        return "employeeview";
    }

    @RequestMapping("/update/{id}")
    public String updateEmployee(@PathVariable("id") long id, Model model) {
        model.addAttribute("employee", employeeRepository.findById(id).get());
        model.addAttribute("departments", departmentRepository.findAll());
        return "employeeform";
    }

    @RequestMapping("/delete/{id}")
    public String delEmployee(@PathVariable("id") long id) {
        employeeRepository.deleteById(id);
        return "redirect:/";
    }


}