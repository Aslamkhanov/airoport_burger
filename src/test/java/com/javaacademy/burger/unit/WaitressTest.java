package com.javaacademy.burger.unit;

import com.javaacademy.burger.Kitchen;
import com.javaacademy.burger.Waitress;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static com.javaacademy.burger.dish.DishType.BURGER;
import static com.javaacademy.burger.dish.DishType.FUAGRA;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WaitressTest {

    @Test
    @DisplayName("Ситуация №1: Был запрошен бургер, официант принял заказ")
    public void waiterTookTheOrderSuccess() {
        Kitchen kitchen = Mockito.mock(Kitchen.class);
        Waitress waitress = new Waitress();

        boolean result = waitress.giveOrderToKitchen(BURGER, kitchen);
        assertTrue(result);
    }

    @Test
    @DisplayName("Ситуация №2: Была запрошена фуагра, официант не принял заказ")
    public void waiterTookTheOrderFailure() {
        Kitchen kitchen = Mockito.mock(Kitchen.class);
        Waitress waitress = new Waitress();

        boolean result = waitress.giveOrderToKitchen(FUAGRA, kitchen);
        assertFalse(result);
    }
}
