package org.example;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        // Создаём банк
        Bank bank = new Bank();

        // Создаём два аккаунта для банка
        Account account1 = new Account("1", 100000);
        Account account2 = new Account("2", 200000);

        // Добавляем аккаунты в банк
        bank.addAccount(account1);
        bank.addAccount(account2);

        // Создаём коллекцию потоков
        ArrayList<Thread> threads = new ArrayList<>();

        // Добавляем в коллекцию 10 потоков
        for (int i = 0; i < 10; i++) {
            threads.add(new Thread(() -> {
                for (int j = 0; j < 10; j++) { // каждый поток производит 10 переводов между аккаунтами
                    try {
                        bank.transfer(account1.getAccNumber(), account2.getAccNumber(), 60000);
                        bank.transfer(account2.getAccNumber(), account1.getAccNumber(), 60000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                bank.shortReport(); // считаем общую сумму в банке
            }));
        }
        threads.forEach(t -> t.start()); // запускаем все потоки коллекции
    }
}