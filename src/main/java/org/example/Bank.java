package org.example;

import java.util.*;

public class Bank {

    private Map<String, Account> accounts;
    
    private final Random random = new Random();

    public Bank() {
        accounts = new HashMap<>();
    }

    public synchronized boolean isFraud(String fromAccountNum, String toAccountNum, long amount)
            throws InterruptedException {
        Thread.sleep(1000);
        return random.nextBoolean();
    }

    /**
     * TODO: реализовать метод. Метод переводит деньги между счетами. Если сумма транзакции > 50000,
     * то после совершения транзакции, она отправляется на проверку Службе Безопасности – вызывается
     * метод isFraud. Если возвращается true, то делается блокировка счетов (как – на ваше
     * усмотрение)
     */

    public void transfer(String fromAccountNum, String toAccountNum, long amount) throws InterruptedException {
        if (getBalance(fromAccountNum) >= amount) {
            if (amount > 50_000) { // проверка, после которой банк может заблокировать аккаунт
                boolean isBlocked = isFraud(fromAccountNum, toAccountNum, amount); // рандомное возвращение boolean
                accounts.get(toAccountNum).setBlocked(isBlocked);
                accounts.get(fromAccountNum).setBlocked(isBlocked);
            }
            if (accounts.get(fromAccountNum).isBlocked()) { // проверка счёта отправителя на true/false
                System.out.println(("Извините, в связи с блокировкой счёта № " + fromAccountNum + " невозможно осуществить перевод со счёта № " + fromAccountNum + " на счёт № " + toAccountNum + ".\n"));
                accounts.get(fromAccountNum).setBlocked(false); // разблокируем аккаунт
            } else if (accounts.get(toAccountNum).isBlocked()) { // проверка счёта получателя на true/false
                System.out.println(("Извините, в связи с блокировкой счёта № " + toAccountNum + " невозможно осуществить перевод со счёта № " + fromAccountNum + " на счёт № " + toAccountNum + ".\n"));
                accounts.get(toAccountNum).setBlocked(false); // разблокируем аккаунт
            } else { // реализация перевода
                long before = getBalance(fromAccountNum);
                accounts.get(toAccountNum).setMoney(getBalance(toAccountNum) + amount);
                accounts.get(fromAccountNum).setMoney(getBalance(fromAccountNum) - amount);
                long total = before - getBalance(fromAccountNum);
                System.out.println("Со счёта № " + fromAccountNum + " на счёт № " + toAccountNum + " было переведено " + total + " рублей.\n");
            }
        } else {
            System.out.println("На счёте № " + fromAccountNum + " недостаточно средств, чтобы осуществить перевод на счёт № " + toAccountNum + ".\n");
        }
    }

    public void addAccount (Account account) { // добавить аккаунт в банк
        accounts.put(account.getAccNumber(), account);
    }

    /**
     * TODO: реализовать метод. Возвращает остаток на счёте.
     */

    public long getBalance(String accountNum) { // вернёт баланс указанного аккаунта
        return accounts.get(accountNum).getMoney();
    }

    public long getSumAllAccounts() { // вернёт общую сумму по всем счетам в банке
        Optional<Long> sum = accounts.values().stream()
                .map(Account::getMoney)
                .reduce(Long::sum);
        return sum.get();
    }

    public void detaisReport(){ // детальный отчёт с описанием всех счетов
        accounts.values().forEach(System.out::println);
    }

    public void shortReport (){ // покажет общую сумму по всем счетам в банке
        System.out.println("\nОбщая сумма денег в банке по всем счетам: " + getSumAllAccounts() + " рублей.\n");
    }
}