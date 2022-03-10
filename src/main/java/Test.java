import com.atm.myException.RegisterException;
import com.atm.service.Bank;
import com.atm.model.Account;

import javax.security.auth.login.LoginException;

public class Test {
    public static void main(String[] args) {
        Bank bank = Bank.getInstanceDao();
        Account account = null;
        try {
            account = bank.register("123","123","vfvv","154","sfsfsf",2);
        } catch (RegisterException e) {
            e.printStackTrace();
        }
        bank.requestLoan(account.getId(), 100);
        try {
            System.out.println(bank.login(account.getId(), "123"));
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }
}
