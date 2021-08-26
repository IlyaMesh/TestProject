package ru.dataart.cashMachine.entities;


public enum Error {
    WRONG_CARD_NUMBER(1,"Неверно введен номер карты или она заблокирована"),
    CARD_BLOCKED(2,"Серия неудачных вводов пин-кода. Ваша карта заблокирована"),
    OPERATION_NOT_SUPPORTED(3,"Операция не поддерживается"),
    NOT_ENOUGH_MONEY(4,"Недостаточно средств на карте");

    private final Integer code;
    private final String description;


    private Error(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

}
