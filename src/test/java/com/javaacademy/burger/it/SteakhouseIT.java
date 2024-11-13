package com.javaacademy.burger.it;

import com.javaacademy.burger.*;
import com.javaacademy.burger.dish.Dish;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static com.javaacademy.burger.Currency.*;
import static com.javaacademy.burger.dish.DishType.*;
import static java.math.BigDecimal.valueOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
public class SteakhouseIT {
    private Waitress waitress;
    private Kitchen kitchen;
    private PayTerminal payTerminal;
    private Steakhouse steakhouse;

    @Test
    @DisplayName("Показываем работу владельцу: Клиент покупает бургер за рубли")
    public void makeOrder() {
        Kitchen kitchen = new Kitchen();
        Waitress waitress = new Waitress();
        PayTerminal payTerminal = new PayTerminal();
        Steakhouse steakhouse = new Steakhouse(waitress, kitchen, payTerminal);

        Paycheck paycheck = steakhouse.makeOrder(BURGER, RUB);
        assertNotNull(paycheck);
        assertEquals(BURGER, paycheck.getDishType());

        Dish dish = steakhouse.takeOrder(paycheck);

        assertNotNull(dish);
        assertEquals(BURGER, dish.getDishType());
    }

    @Test
    @DisplayName("Проверка из санэпидемстанции")
    public void makeOrderBySes() {

        Kitchen kitchen = new Kitchen();
        Waitress waitress = new Waitress();
        PayTerminal payTerminal = Mockito.spy(PayTerminal.class);
        Steakhouse steakhouse = new Steakhouse(waitress, kitchen, payTerminal);

        Mockito.when(payTerminal.pay(RIBS, RUB))
                .thenReturn(new Paycheck(valueOf(700), RUB, RIBS));

        Paycheck paycheck = steakhouse.makeOrder(RIBS, RUB);
        Dish dish = steakhouse.takeOrder(paycheck);

        assertNotNull(payTerminal);
        assertEquals(RIBS, dish.getDishType());
        assertEquals(valueOf(700), paycheck.getTotalAmount());
        assertEquals(RUB, paycheck.getCurrency());
    }

    @BeforeEach
    void setUp() {
        kitchen = Mockito.mock(Kitchen.class);
        waitress = Mockito.mock(Waitress.class);
        payTerminal = Mockito.spy(PayTerminal.class);
        steakhouse = new Steakhouse(waitress, kitchen, payTerminal);

        Mockito.when(waitress.giveOrderToKitchen(Mockito.any(), Mockito.any())).thenReturn(true);
    }

    @Test
    @DisplayName("Ситуация 1: клиент заказал ребра за рубли")
    public void makeOrderByTaxRibs() {

        Mockito.when(waitress.giveOrderToKitchen(Mockito.any(), Mockito.any())).thenReturn(true);
        Mockito.doReturn(new Paycheck(valueOf(700), RUB, RIBS))
                .when(payTerminal).pay(RIBS, RUB);

        Paycheck paycheck = steakhouse.makeOrder(RIBS, RUB);
        assertNotNull(paycheck);
        assertEquals(valueOf(700), paycheck.getTotalAmount());
        assertEquals(RUB, paycheck.getCurrency());
        assertEquals(RIBS, paycheck.getDishType());
    }

    @Test
    @DisplayName("Ситуация 2: клиент захотел купить картошку за доллары")
    public void makeOrderByTaxPotato() {

        Mockito.when(waitress.giveOrderToKitchen(Mockito.any(), Mockito.any())).thenReturn(true);
        Mockito.doReturn(new Paycheck(valueOf(1), USD, FRIED_POTATO))
                .when(payTerminal).pay(FRIED_POTATO, USD);

        Paycheck paycheck = steakhouse.makeOrder(FRIED_POTATO, USD);
        assertNotNull(paycheck);
        assertEquals(valueOf(1), paycheck.getTotalAmount());
        assertEquals(FRIED_POTATO, paycheck.getDishType());
        assertEquals(USD, paycheck.getCurrency());
    }

    @Test
    @DisplayName("Ситуация 3: клиент захотел купить бургер за мозамбикский доллар")
    public void makeOrderByTaxMozambicansDollar() {

        Mockito.when(waitress.giveOrderToKitchen(Mockito.any(), Mockito.any())).thenReturn(true);
        Mockito.doReturn(new Paycheck(valueOf(1), MOZAMBICAN_DOLLARS, BURGER))
                .when(payTerminal).pay(BURGER, MOZAMBICAN_DOLLARS);

        Paycheck paycheck = steakhouse.makeOrder(BURGER, MOZAMBICAN_DOLLARS);
        assertNotNull(paycheck);
        assertEquals(valueOf(1), paycheck.getTotalAmount());
        assertEquals(BURGER, paycheck.getDishType());
        assertEquals(MOZAMBICAN_DOLLARS, paycheck.getCurrency());
    }
}
