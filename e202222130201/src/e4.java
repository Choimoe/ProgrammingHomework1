import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Account {
    private final String userName, accountNumber;
    private long balance;

    public long queryBalance () {
        return balance;
    }

    public String getUserName () {
        return userName;
    }

    public String getAccountNumber () {
        return accountNumber;
    }

    public boolean deposit (long amount) {
        if (amount < 0) return false;
        balance += amount;
        return true;
    }

    public boolean withdraw (long amount) {
        if (amount < 0 || amount > balance) return false;
        balance -= amount;
        return true;
    }

    public boolean addInterest (long amount) {
        if (amount < 0) return false;
        balance += amount;
        return true;
    }

    public Account (String userName, String accountNumber, long balance) {
        this.userName = userName;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public void printCurrent() {
        System.out.println("用户名: " + userName + " 账号: " + accountNumber + " 余额: " + balance);
    }
}

class BankUtil {
    public static void transfer(Account from, Account to, long amount) {
        if (from.withdraw(amount) && to.deposit(amount))
            System.out.println(from.getUserName() + " 向 " + to.getUserName() + " 转账 " + amount + " 成功。");
        else System.out.println(from.getUserName() + " 向 " + to.getUserName() + " 转账 " + amount + " 失败。");
    }
}

public class e4 {
    static final List<Account> users = new ArrayList<>();

    static void question2() {
        System.out.println("----- (2) -----");
        Account ming = new Account("小明", "123", 1000);
        Account hong = new Account("小红", "456", 1000);

        BankUtil.transfer(ming, hong, 100);
        ming.printCurrent(); hong.printCurrent();

        BankUtil.transfer(hong, ming, 10000);
        ming.printCurrent(); hong.printCurrent();

        if (ming.addInterest(1000)) System.out.println("小明账户加利息成功。");
        else System.out.println("小明账户加利息失败。");
        ming.printCurrent();
    }

    private static void question34() throws FileNotFoundException {
        System.out.println("----- (3)(4) -----");
        Random random = new Random();
        List<String[]> data = read("e4.txt");
        for (String[] user : data) users.add(new Account(user[0], user[1], 0));

        for (Account user : users) {
            int amount = random.nextInt(1000) - 300;
            if (user.deposit(amount)) {
                System.out.println("用户 " + user.getUserName() + " 存款 " + amount + " 成功。");
            } else {
                System.out.println("用户 " + user.getUserName() + " 存款 " + amount + " 失败。");
            }
            user.printCurrent();
        }

        System.out.println("---");

        for (int i = 1; i <= 5; i++) {
            Account from = users.get(random.nextInt(users.size()));
            Account to = users.get(random.nextInt(users.size()));
            long amount = random.nextInt(1000) - 300;
            BankUtil.transfer(from, to, amount);
            from.printCurrent(); to.printCurrent();
        }

        System.out.println("---");

        for (int i = 1; i <= 5; i++) {
            Account user = users.get(random.nextInt(users.size()));
            int interest = random.nextInt(1000) - 300;
            if (user.addInterest(interest)) {
                System.out.println("用户 " + user.getUserName() + " 加利息 " + interest + " 成功。");
            } else {
                System.out.println("用户 " + user.getUserName() + " 加利息 " + interest + " 失败。");
            }
            user.printCurrent();
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        question2();
        question34();
    }

    public static List<String[]> read(String fileName) throws FileNotFoundException {
        List<String[]> result = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String t;
        while (true) {
            try {
                t = reader.readLine();
                if (t == null) break;
                result.add(t.split(" "));
            } catch (IOException e) {
                break;
            }
        }
        return result;
    }
}
