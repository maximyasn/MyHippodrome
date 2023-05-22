import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class HorseTest {
    private String horseName = "Dmitriy";
    private double horseSpeed = 1.0;
    private double horseDistance = 1.0;

    private Horse testHorse = new Horse(horseName, horseSpeed, horseDistance);

    @Mock
    Horse horseMock;


    // ----------------Тесты конструктора---------------------

    @Test
    @DisplayName("Передача null в конструктор.")
    public void nullInHorseConstructor() {
        assertThrows(IllegalArgumentException.class, () -> new Horse(null, 0, 0));
    }

    @Test
    @DisplayName("Перехват сообщения из исключения.")
    public void messageFromExceptionWhenNameIsNull() {
        String expMessage = "Name cannot be null.";
        String actualMessage = null;
        try {
            Horse horse = new Horse(null, 0, 0);
        } catch(IllegalArgumentException e) {
            actualMessage = e.getMessage();
        }
        assertEquals(expMessage, actualMessage);
    }

    @DisplayName("Пустая строка или пробельные символы в конструкторе.")
    @ParameterizedTest
    @ValueSource(strings = {"", "   ", "\t\t\t", "\n\n\n"})
    public void whitespacesInConstructor(String name) {
        assertThrows(IllegalArgumentException.class, () -> new Horse(name, 0, 0));
    }

    @DisplayName("Перехват сообщения из исключения при пустой строке.")
    @ParameterizedTest
    @ValueSource(strings = {"", "   ", "\t\t\t", "\n\n\n"})
    public void messageFromExceptionWhenNameIsBlank(String name) {
        String expMessage = "Name cannot be blank.";
        String actualMessage = null;
        try {
            Horse horse = new Horse(name, 0, 0);
        } catch (IllegalArgumentException e) {
            actualMessage = e.getMessage();
        }
        assertEquals(expMessage, actualMessage);
    }

    @DisplayName("Отрицательное число на месте второго параметра конструктора.")
    @Test
    public void whenSecondParamIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> new Horse("Dmitriy", -1.0, 1.0));
    }

    @DisplayName("Перехват сообщения из исключения при отрицательной скорости.")
    @Test
    public void messageFromExceptionWhenSpeedIsNegative() {
        String expMessage = "Speed cannot be negative.";
        String actualMessage = null;
        try {
            Horse horse = new Horse("Dmitriy", -1.0, 1.0);
        } catch (IllegalArgumentException e) {
            actualMessage = e.getMessage();
        }
        assertEquals(expMessage, actualMessage);
    }

    @DisplayName("Отрицательное число на месте третьего параметра конструктора.")
    @Test
    public void whenThirdParamIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> new Horse("Dmitriy", 1.0, -1.0));
    }
    @DisplayName("Перехват сообщения из исключения при отрицательной дистанции.")
    @Test
    public void messageFromExceptionWhenDistanceIsNegative() {
        String expMessage = "Distance cannot be negative.";
        String actualMessage = null;
        try {
            Horse horse = new Horse("Dmitriy", 1.0, -1.0);
        } catch (IllegalArgumentException e) {
            actualMessage = e.getMessage();
        }
        assertEquals(expMessage, actualMessage);
    }

    // ---------------Тест метода getName()-------------------

    @DisplayName("Тест метода getName()")
    @Test
    public void getNameTest() {
        assertEquals(horseName, testHorse.getName());
    }

    //----------------Тест метода getSpeed()--------------------

    @DisplayName("Тест метода getSpeed()")
    @Test
    public void getSpeedTest() {
        assertEquals(horseSpeed, testHorse.getSpeed());
    }

    //----------------Тесты метода getDistance()------------------

    @DisplayName("Тест метода getDistance()")
    @Test
    public void getDistanceTest() {
        assertEquals(horseDistance, testHorse.getDistance());
    }

    @DisplayName("distance == 0 при использовании конструктора с 2 параметрами.")
    @Test
    public void getDistanceWhenUsingTwoParamConst() {
        Horse horse = new Horse("Dmitriy", 1.0);
        assertEquals(0, horse.getDistance());
    }

    //--------------Тесты метода move()-------------------
    @DisplayName("Метод move() использует внутри getRandomDouble(0.2, 0.9)")
    @Test
    public void moveUsesGetRandomDouble() {
        try(MockedStatic<Horse> staticHorse = Mockito.mockStatic(Horse.class)) {
            testHorse.move();

            staticHorse.verify(() -> Horse.getRandomDouble(0.2, 0.9), Mockito.times(1));
        }
    }

    @DisplayName("Метод move() присваивает дистанции значение из формулы.")
    @ParameterizedTest
    @CsvSource({"1.0, 2.0, 5.0"})
    public void moveUsesFormula(double distance, double speed, double random) {
        try(MockedStatic<Horse> staticHorse = Mockito.mockStatic(Horse.class)) {
            staticHorse.when(()->Horse.getRandomDouble(0.2,0.9)).thenReturn(random);

            double expectedDist = distance + speed * Horse.getRandomDouble(0.2,0.9);
            Horse horse = new Horse("Ivan", speed, distance);
            horse.move();

            assertEquals(expectedDist, horse.getDistance());
        }
    }
}