import java.util.*;

public class BankAccount {
    private Double balance;
    private View view;

    public BankAccount(Connection connection) {
        this.view = new View();
        this.update(connection);
    }

    public void update(Connection connection){
        this.balance = 0.0;
        List recordsList = connection.getAll("BankRecord");
        if (recordsList.isEmpty()){
            this.balance = 0.0;
        }else {
            recordsList.forEach(record -> {
                BankRecord bankRecord = (BankRecord)record;
                this.balance = (bankRecord.getRecordTypeId() == 1) ? this.balance + bankRecord.getAmount() : this.balance - bankRecord.getAmount();
            });
        }

        this.view.print("Cuenta calculada");

    }

    public void printBalance(Connection connection){
        this.update(connection);
        this.view.print("ESTADO DE CUENTA");
        this.view.print("-".repeat(10));
        this.view.print("Q " + this.balance);
        this.view.print("-".repeat(10));
    }
}
