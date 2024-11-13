package com.javaacademy.burger.unit;

import com.javaacademy.burger.PayTerminal;
import com.javaacademy.burger.Paycheck;
import com.javaacademy.burger.exception.NotAcceptedCurrencyException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.javaacademy.burger.Currency.MOZAMBICAN_DOLLARS;
import static com.javaacademy.burger.Currency.RUB;
import static com.javaacademy.burger.dish.DishType.BURGER;
import static java.math.BigDecimal.valueOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PayTerminalTest {

    @Test
    @DisplayName("Ситуация №1: Успешно оплатил бургер, оплата в рублях")
    public void payRubSuccess() {
        PayTerminal payTerminal = new PayTerminal();
        Paycheck result = payTerminal.pay(BURGER, RUB);
        Paycheck paycheck = new Paycheck(valueOf(300), RUB, BURGER);
        assertEquals(paycheck, result);
    }

    @Test
    @DisplayName("Ситуация №2: Оплата в мозамбикских долларах, ошибка NotAcceptedCurrencyException")
    public void payFailure() {
        PayTerminal payTerminal = new PayTerminal();
        assertThrows(NotAcceptedCurrencyException.class,
                () -> payTerminal.pay(BURGER, MOZAMBICAN_DOLLARS));
    }
}
