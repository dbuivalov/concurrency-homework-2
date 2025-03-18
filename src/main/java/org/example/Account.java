package org.example;

public class Account { // класс аккаунта

    private String accNumber; // номер аккаунта
    private long money; // сумма на балансе аккаунта
    private boolean isBlocked; // статус блокировки, если false - не заблокировано

    public Account(String accNumber, long money) {
        this.accNumber = accNumber;
        this.money = money;
        this.isBlocked = false; // создаём аккаунт сразу незаблокированным
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        if (isBlocked) { // прежде чем назначить сумму, проверяем, заблокирован ли аккаунт
            System.out.println("Извините! Ваш счёт № " + this.accNumber + " заблокирован. Обратитесь в службу поддержки.");
        } else {
            this.money = money; // если не заблокирован, сумма будет назначена
        }
    }

    public String getAccNumber() {
        return accNumber;
    }

    public void setAccNumber(String accNumber) {
        this.accNumber = accNumber;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    @Override
    public String toString() {
        return "Счёт № " + accNumber
                + "\nБаланс: " + money + " рублей"
                + "\nСтатус блокировки: " + isBlocked
                +"\n";
    }
}