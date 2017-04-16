package application.Validator;

import application.Entity.Account;

/**
 * Created by Vlad on 03-Apr-17.
 */
public class TransactionValidator {

    public boolean validate(Account from, Double amount) {
        return (from.getBalance() - amount) > 0 && amount > 0;
    }
}
