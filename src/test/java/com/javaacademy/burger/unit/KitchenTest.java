package com.javaacademy.burger.unit;

import com.javaacademy.burger.Kitchen;
import com.javaacademy.burger.dish.Dish;
import com.javaacademy.burger.dish.DishType;
import com.javaacademy.burger.exception.KitchenHasNoGasException;
import com.javaacademy.burger.exception.UnsupportedDishTypeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

public class KitchenTest {
    private Kitchen kitchen;

    @BeforeEach
    void setUp() {
        kitchen = new Kitchen();
    }

    @Test
    @DisplayName("Ситуация №1: Кухня успешно приготовила бургер")
    void cookWithValidDishTypeSuccess() {
        DishType dishType = DishType.BURGER;
        kitchen.cook(dishType);

        Map<DishType, Queue<Dish>> completedDishes = kitchen.getCompletedDishes();
        assertTrue(completedDishes.containsKey(dishType));
        assertEquals(1, completedDishes.get(dishType).size());
    }

    @Test
    @DisplayName("Ситуация №3: Приготовление фуагра, ошибка UnsupportedDishTypeException")
    void testCookWithUnsupportedDishTypeFailure() {
        DishType dishType = DishType.FUAGRA;

        Exception exception = assertThrows(UnsupportedDishTypeException.class, () -> {
            kitchen.cook(dishType);
        });

        assertEquals("Повар не умеет готовить фуагра", exception.getMessage());
    }

    @Test
    @DisplayName("Ситуация №2: На кухне выключили газ, ошибка KitchenHasNoGasException")
    void testCookWhenNoGasFailure() {
        kitchen.setHasGas(false);

        Exception exception = assertThrows(KitchenHasNoGasException.class, () -> {
            kitchen.cook(DishType.BURGER);
        });

        assertEquals("На кухне нет газа", exception.getMessage());
    }
}
