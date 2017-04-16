package application.Controller;

import application.Entity.Account;
import application.Entity.Log;
import application.Entity.User;
import application.Repository.AccountRepository;
import application.Repository.LogRepository;
import application.Repository.UserRepository;
import application.Validator.TransactionValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.LocaleContextResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;

/**
 * Created by Vlad on 28-Mar-17.
 */
@Controller
@RequestMapping("/transfer")
public class TransferController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private LogRepository logRepository;

    @Autowired
    private UserRepository userRepository;

    private TransactionValidator validator;

    @RequestMapping(method = RequestMethod.GET)
    public String transfer(){
        return "transfer";
    }
    @RequestMapping(value = "/search",method = RequestMethod.GET)
    public String search(@RequestParam(value = "id1") long id1, @RequestParam(value = "id2") long id2, Model model) {
        Account from = accountRepository.getOne(id1);
        Account to = accountRepository.getOne(id2);
        model.addAttribute("from",from);
        model.addAttribute("to",to);
        return "transfer/search";
    }

    @RequestMapping(value = "process", method = RequestMethod.POST)
    public String processTransaction(HttpServletRequest request) {
        Double amount = Double.parseDouble(request.getParameter("amount"));
        Account from = accountRepository.getOne(Long.parseLong(request.getParameter("id1")));
        Account to = accountRepository.getOne(Long.parseLong(request.getParameter("id2")));

        validator = new TransactionValidator();
        if(validator.validate(from,amount)) {
            from.setBalance(from.getBalance() - amount);
            to.setBalance(to.getBalance() + amount);

            accountRepository.save(from);
            accountRepository.save(to);
        }
        else
            return "redirect:/transfer?error=true";


        Log log = new Log();
        log.setOperation("Transaction: From " + from.getId() + " To " + to.getId() + " Amount " + amount);
        log.setTimestamp(new Timestamp(System.currentTimeMillis()));

        String username = request.getRemoteUser();

        User user = userRepository.findByUsername(username);
        log.setUser(user);

        logRepository.save(log);

        return "redirect:/index";
    }

}
