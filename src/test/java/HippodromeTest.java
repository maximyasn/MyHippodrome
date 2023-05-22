import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

class HippodromeTest {

    // -----------------Тесты конструктора--------------------

    @DisplayName("Передача null в конструктор.")
    @Test
    public void nullInConstructor() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
    }

    @DisplayName("Перехват сообщения из исключения при null в конструкторе")
    @Test
    public void messageFromExceptionWhenNull() {
        String expMessage = "Horses cannot be null.";
        String actualMessage = null;
        try {
            new Hippodrome(null);
        } catch (IllegalArgumentException e) {
            actualMessage = e.getMessage();
        }

        assertEquals(expMessage, actualMessage);
    }

    @DisplayName("Пустой список в конструктор.")
    @Test
    public void emptyListInConstructor() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(new ArrayList<>()));
    }

    @DisplayName("Перехват сообщения из исключения при пустом списке в конструкторе")
    @Test
    public void messageWhenEmptyListInConstructor() {
        String expMessage = "Horses cannot be empty.";
        String actualMessage = null;
        try {
            new Hippodrome(new ArrayList<>());
        } catch (IllegalArgumentException e) {
            actualMessage = e.getMessage();
        }
        assertEquals(expMessage, actualMessage);
    }

    // -------------Тест метода getHorses()------------------
    @DisplayName("Равенство списков из метода и из конструктора")
    @Test
    public void getHorsesTest() {
        List<Horse> horses = new ArrayList<>();
        for(int i = 0; i < 30; i++) {
            horses.add(new Horse("Horse " + i, i , i));
        }

        Hippodrome hippodrome = new Hippodrome(horses);
        assertEquals(horses, hippodrome.getHorses());
    }

    // --------------Тест метода move()---------------------
    @DisplayName("Вызывается ли метод move() у всех лошадей")
    @Test
    public void moveTest() {
        List<Horse> horses = new ArrayList<>();
        for(int i = 0; i < 50; i++) {
            horses.add(Mockito.mock(Horse.class));
        }
        Hippodrome hippodrome = new Hippodrome(horses);

        hippodrome.move();

        for(int i = 0; i < horses.size(); i++) {
            Mockito.verify(horses.get(i)).move();
        }
    }

    // -------------Тест метода getWinner()------------------
    @DisplayName("Метод возвращает лошадь с наибольшим значением distance")
    @Test
    public void getWinnerTest() {
        Horse testHorse = new Horse("Ferrari", 30.0, 190.0);

        List<Horse> horses = new ArrayList<>();
        horses.add(new Horse("Stepan", 1.0, 1.0));
        horses.add(new Horse("Ivan", 2.0, 90.0));
        horses.add(testHorse);

        Hippodrome hippodrome = new Hippodrome(horses);

        assertEquals(testHorse, hippodrome.getWinner());

    }

}