package application.Controller;

import application.Entity.Employee;
import application.Entity.Log;
import application.Entity.Role;
import application.Entity.User;
import application.Repository.EmployeeRepository;
import application.Repository.LogRepository;
import application.Repository.RoleRepository;
import application.Repository.UserRepository;
import application.Validator.EmployeeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Vlad on 25-Mar-17.
 */
@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private LogRepository logRepository;

    private EmployeeValidator validator;

    @RequestMapping(value = "new",method = RequestMethod.GET)
    public String showForm(ModelAndView modelAndView) {
        return "admin/new";
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public String registerUser(HttpServletRequest request) {
        List<Employee> employees = employeeRepository.findAll();


        Employee employee = new Employee();
        String salary = request.getParameter("salary");

        employee.setSalary(Double.parseDouble(salary));
        employee.setName(request.getParameter("name"));
        employee.setPhone(request.getParameter("phone"));
        employee.setEmail(request.getParameter("email"));
        validator = new EmployeeValidator();
        if(validator.validate(employee))
            employeeRepository.save(employee);
        else
            return "redirect:/admin/new?error=true";

        User user = new User();
        user.setUsername(request.getParameter("username"));
        user.setPassword(request.getParameter("password"));

        Role role = roleRepository.findByRole(request.getParameter("role"));
        user.setRoles(new HashSet<Role>(Arrays.asList(role)));
        userRepository.save(user);

        Log log = new Log();
        log.setOperation("Employee added: ID: " + employee.getId());
        log.setTimestamp(new Timestamp(System.currentTimeMillis()));

        String username = request.getRemoteUser();

        User activeUser = userRepository.findByUsername(username);
        log.setUser(activeUser);

        logRepository.save(log);
        return "redirect:/index";
    }

    @RequestMapping(value = "{id}/edit",method = RequestMethod.GET)
    public String update(@PathVariable long id, Model model) {
        Employee employee = employeeRepository.findOne(id);
        model.addAttribute("employee",employee);
        return "admin/edit";
    }

    @RequestMapping(value = "update",method = RequestMethod.POST)
    public String updateEmployee(HttpServletRequest request) {
        Employee employee = employeeRepository.findOne(Long.parseLong(request.getParameter("id")));
        employee.setSalary(Double.parseDouble(request.getParameter("salary")));
        employee.setEmail(request.getParameter("email"));
        employee.setName(request.getParameter("name"));
        employee.setPhone(request.getParameter("phone"));


        validator = new EmployeeValidator();
        if(validator.validate(employee))
            employeeRepository.save(employee);
        else
            return "redirect:/admin/new?error=true";
        Log log = new Log();
        log.setOperation("Employee updated. ID: " + employee.getId());
        log.setTimestamp(new Timestamp(System.currentTimeMillis()));

        String username = request.getRemoteUser();

        User user = userRepository.findByUsername(username);
        log.setUser(user);

        logRepository.save(log);

        return "redirect:/index";
    }

    @RequestMapping(value = "/search",method = RequestMethod.GET)
    public String displaySearch(){
        return "admin/search";
    }
    @RequestMapping(value = "/search",method = RequestMethod.POST)
    public String search(HttpServletRequest request) {
        Employee employee = employeeRepository.findOne(Long.parseLong(request.getParameter("id")));
        String option = request.getParameter("option");
        if(option.equals("VIEW")) {
            return "redirect:/admin/" + employee.getId() + "/view";
        }
        if(option.equals("EDIT")) {
            return "redirect:/admin/" + employee.getId() + "/edit";
        }
        if(option.equals("DELETE")) {
            return "redirect:/admin/" + employee.getId() + "/delete";
        }
        return "redirect:/admin/search";
    }

    @RequestMapping(value = "/{id}/view",method = RequestMethod.GET)
    public String view(@PathVariable long id, Model model) {
        Employee employee = employeeRepository.findOne(id);
        model.addAttribute("employee",employee);
        return "admin/view";
    }

    @RequestMapping(value = "/{id}/delete",method = RequestMethod.GET)
    public String delete(@PathVariable long id) {
        employeeRepository.delete(id);
        return "redirect:/index";
    }

    @RequestMapping(value = "/report",method = RequestMethod.GET)
    public String showForm() {
        return "admin/report";
    }

    @RequestMapping(value = "/report",method = RequestMethod.POST)
    public ModelAndView searchEmployee(HttpServletRequest request) throws Exception {
        ModelAndView modelAndView = new ModelAndView("admin/showReport");
        User user = userRepository.findOne(Long.parseLong(request.getParameter("id")));
        DateFormat format = new SimpleDateFormat("yyyy-M-dd");
        Date from = format.parse(request.getParameter("from"));
        Date to = format.parse(request.getParameter("to"));

        List<Log> report = logRepository.findByUserAndTimestampBetween(user,from,to);
        modelAndView.addObject("report",report);
        return modelAndView;
    }

    @RequestMapping(value = "/showReport",method = RequestMethod.GET)
    public String showReport() {
        return "admin/showReport";
    }

}
