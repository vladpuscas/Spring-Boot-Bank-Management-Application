package application.Validator;

import application.Entity.Client;

/**
 * Created by Vlad on 03-Apr-17.
 */
public class ClientValidator {

    public boolean validate(Client client) {
        return validateCNP(client.getCnp()) && validateEmail(client.getEmail()) && validateName(client.getName());
    }

    private boolean validateName(String name) {
        return name.matches("[a-zA-Z\\s]");
    }

    private boolean validateCNP(String cnp){
        return (cnp.startsWith("1") || cnp.startsWith("2")) && (cnp.length()==13);
    }

    private boolean validateEmail(String email) {
        return email.contains("@") && email.contains(".com");
    }
}
