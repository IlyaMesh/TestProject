package ru.dataart.cashMachine.exception;

public class NoMoneyOnBalanceException extends RuntimeException {
    public NoMoneyOnBalanceException() {
        super();
    }

    public NoMoneyOnBalanceException(String message) {
        super(message);
    }
}
