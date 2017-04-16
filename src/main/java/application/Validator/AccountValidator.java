package application.Validator;

import application.Entity.Account;

/**
 * Created by Vlad on 03-Apr-17.
 */

public class AccountValidator {
    public boolean validate(Account account) {
        return (validateBalance(account.getBalance()) && validateType(account.getType()));
    }

    private boolean validateType(String type) {
        return type.equalsIgnoreCase("debit") || type.equalsIgnoreCase("credit");
    }
    private boolean validateBalance(Double balance) {
        return balance > 0;
    }

}
