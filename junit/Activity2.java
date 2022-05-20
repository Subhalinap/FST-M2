import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ExpectedExceptionTest {

    @Test
    void notEnoughFunds() {
        // Create an object for BankAccount class
        BankAccount account = new BankAccount(9);

        // Assertion for exception
        assertThrows(BankAccount.NotEnoughFundsException.class, () -> account.withdraw(10),
                "Balance must be greater than amount of withdrawal");
    }

    @Test
    void enoughFunds() {

        BankAccount account = new BankAccount(100);

        assertDoesNotThrow(() -> account.withdraw(100));
    }
}