import java.time.LocalDateTime;

class BankAccount {
    // Поля класса
    private String ownerName;
    private int balance;
    private LocalDateTime openingDate;
    private boolean isBlocked;

    // Конструктор с одним параметром - имя владельца
    public BankAccount(String ownerName) {
        this.ownerName = ownerName;
        this.balance = 0;
        this.openingDate = LocalDateTime.now();
        this.isBlocked = false;
    }

    // Геттеры для проверки результатов
    public String getOwnerName() {
        return ownerName;
    }

    public int getBalance() {
        return balance;
    }

    public LocalDateTime getOpeningDate() {
        return openingDate;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    // Метод пополнения счета
    public boolean deposit(int amount) {
        // Проверяем, что сумма пополнения положительная и счет не заблокирован
        if (amount > 0 && !isBlocked) {
            balance += amount;
            return true;
        }
        return false;
    }

    // Метод снятия денег
    public boolean withdraw(int amount) {
        // Проверяем, что сумма положительная, счет не заблокирован и достаточно средств
        if (amount > 0 && !isBlocked && balance >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }

    // Метод перевода денег на другой счет
    public boolean transfer(BankAccount otherAccount, int amount) {
        // Проверяем, что другой счет существует
        if (otherAccount == null) {
            return false;
        }

        // Проверяем, что счет не заблокирован
        if (isBlocked) {
            return false;
        }

        // Пытаемся снять деньги с текущего счета
        if (this.withdraw(amount)) {
            // Если успешно сняли, пополняем другой счет
            if (otherAccount.deposit(amount)) {
                return true;
            } else {
                // Если не удалось пополнить другой счет, возвращаем деньги
                this.balance += amount;
                return false;
            }
        }

        return false;
    }
}
public class Main {
    public static void main(String[] args) {
        System.out.println("=== Тестирование банковских операций ===\n");

        // Создаем два счета
        BankAccount account1 = new BankAccount("Иван Иванов");
        BankAccount account2 = new BankAccount("Петр Петров");

        // Выводим информацию о созданных счетах
        System.out.println("Созданы счета:");
        System.out.println("Счет 1: Владелец - " + account1.getOwnerName() +
                ", Баланс - " + account1.getBalance() +
                ", Дата открытия - " + account1.getOpeningDate());
        System.out.println("Счет 2: Владелец - " + account2.getOwnerName() +
                ", Баланс - " + account2.getBalance() +
                ", Дата открытия - " + account2.getOpeningDate() + "\n");

        // Тест 1: Пополнение счета
        System.out.println("Тест 1: Пополнение счета 1 на 1000");
        boolean depositResult = account1.deposit(1000);
        System.out.println("Результат: " + (depositResult ? "Успешно" : "Неуспешно"));
        System.out.println("Баланс счета 1: " + account1.getBalance() + "\n");

        // Тест 2: Снятие со счета
        System.out.println("Тест 2: Снятие со счета 1 300");
        boolean withdrawResult = account1.withdraw(300);
        System.out.println("Результат: " + (withdrawResult ? "Успешно" : "Неуспешно"));
        System.out.println("Баланс счета 1: " + account1.getBalance() + "\n");

        // Тест 3: Снятие суммы больше баланса
        System.out.println("Тест 3: Попытка снять 1000 (больше баланса)");
        withdrawResult = account1.withdraw(1000);
        System.out.println("Результат: " + (withdrawResult ? "Успешно" : "Неуспешно"));
        System.out.println("Баланс счета 1: " + account1.getBalance() + "\n");

        // Тест 4: Перевод денег
        System.out.println("Тест 4: Перевод 200 со счета 1 на счет 2");
        boolean transferResult = account1.transfer(account2, 200);
        System.out.println("Результат: " + (transferResult ? "Успешно" : "Неуспешно"));
        System.out.println("Баланс счета 1: " + account1.getBalance());
        System.out.println("Баланс счета 2: " + account2.getBalance() + "\n");

        // Тест 5: Попытка перевода суммы больше баланса
        System.out.println("Тест 5: Попытка перевести 1000 (больше баланса)");
        transferResult = account1.transfer(account2, 1000);
        System.out.println("Результат: " + (transferResult ? "Успешно" : "Неуспешно"));
        System.out.println("Баланс счета 1: " + account1.getBalance());
        System.out.println("Баланс счета 2: " + account2.getBalance() + "\n");

        // Тест 6: Пополнение счета 2
        System.out.println("Тест 6: Пополнение счета 2 на 500");
        account2.deposit(500);
        System.out.println("Баланс счета 2: " + account2.getBalance() + "\n");

        // Тест 7: Перевод со счета 2 на счет 1
        System.out.println("Тест 7: Перевод 300 со счета 2 на счет 1");
        transferResult = account2.transfer(account1, 300);
        System.out.println("Результат: " + (transferResult ? "Успешно" : "Неуспешно"));
        System.out.println("Баланс счета 1: " + account1.getBalance());
        System.out.println("Баланс счета 2: " + account2.getBalance() + "\n");

        // Тест 8: Проверка отрицательных сумм
        System.out.println("Тест 8: Попытка пополнить счет на отрицательную сумму");
        depositResult = account1.deposit(-100);
        System.out.println("Результат: " + (depositResult ? "Успешно" : "Неуспешно"));
        System.out.println("Баланс счета 1: " + account1.getBalance() + "\n");

        System.out.println("=== Тестирование завершено ===");
    }
}