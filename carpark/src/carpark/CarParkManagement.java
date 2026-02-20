package carpark;

import java.util.*;
import java.util.stream.Collectors;

public class CarParkManagement {
    public static void main(String[] args) {
        System.out.println("========== ЗАДАНИЕ №1: Массивы (Работа с парком машин) ==========");
        task1Arrays();

        System.out.println("\n========== ЗАДАНИЕ №2: Коллекции (Управление моделями) ==========");
        task2Collections();

        System.out.println("\n========== ЗАДАНИЕ №3: Equals/hashCode (Сравнение автомобилей) ==========");
        task3EqualsHashCode();

        System.out.println("\n========== ЗАДАНИЕ №4: Stream API (Анализ автопарка) ==========");
        task4StreamAPI();
    }

    // Задание №1: Массивы (Работа с парком машин)
    public static void task1Arrays() {
        // Создаем массив годов выпуска 50 случайных машин (от 2000 до 2025)
        int[] carYears = new int[50];
        Random random = new Random();
        int currentYear = 2025;

        for (int i = 0; i < carYears.length; i++) {
            carYears[i] = 2000 + random.nextInt(26); // 2000-2025 (включительно)
        }

        // Выводим все годы для информации
        System.out.println("Годы выпуска всех 50 машин:");
        System.out.println(Arrays.toString(carYears));

        // Выводим машины, выпущенные после 2015 года
        System.out.println("\nМашины, выпущенные после 2015 года:");
        int countAfter2015 = 0;
        for (int year : carYears) {
            if (year > 2015) {
                System.out.print(year + " ");
                countAfter2015++;
            }
        }
        System.out.println("\nВсего машин после 2015: " + countAfter2015);

        // Считаем средний возраст авто
        int sumYears = 0;
        for (int year : carYears) {
            sumYears += year;
        }
        double averageYear = (double) sumYears / carYears.length;
        double averageAge = currentYear - averageYear;

        System.out.printf("Средний год выпуска: %.1f%n", averageYear);
        System.out.printf("Средний возраст авто: %.1f лет%n", averageAge);
    }

    // Задание №2: Коллекции (Управление моделями)
    public static void task2Collections() {
        // Создаем список с названиями моделей машин (с дубликатами)
        List<String> carModels = new ArrayList<>(Arrays.asList(
                "Toyota Camry", "BMW X5", "Tesla Model S", "Audi A6",
                "Toyota Camry", "Mercedes C-Class", "BMW X5", "Tesla Model 3",
                "Honda Accord", "Volkswagen Passat", "Tesla Model S", "Audi A6"
        ));

        System.out.println("Исходный список моделей (с дубликатами):");
        System.out.println(carModels);

        // Удаляем дубликаты
        Set<String> uniqueModels = new LinkedHashSet<>(carModels);
        List<String> uniqueList = new ArrayList<>(uniqueModels);

        // Сортируем в обратном алфавитном порядке
        uniqueList.sort(Collections.reverseOrder());

        System.out.println("\nПосле удаления дубликатов и сортировки в обратном порядке:");
        System.out.println(uniqueList);

        // Сохраняем в Set
        Set<String> modelsSet = new HashSet<>(uniqueList);
        System.out.println("Сохранено в Set: " + modelsSet);

        // Проверка: если модель содержит "Tesla", заменяем на "ELECTRO_CAR"
        System.out.println("\nЗамена Tesla на ELECTRO_CAR:");
        List<String> processedModels = new ArrayList<>();
        for (String model : carModels) {
            if (model.contains("Tesla")) {
                processedModels.add("ELECTRO_CAR");
            } else {
                processedModels.add(model);
            }
        }
        System.out.println(processedModels);
    }

    // Задание №3: Equals/hashCode (Сравнение автомобилей)
    public static void task3EqualsHashCode() {
        // Создаем несколько машин
        Car car1 = new Car("VIN123", "Camry", "Toyota", 2020, 45000, 25000);
        Car car2 = new Car("VIN456", "X5", "BMW", 2022, 30000, 55000);
        Car car3 = new Car("VIN123", "Camry", "Toyota", 2020, 45000, 25000); // Дубликат по VIN
        Car car4 = new Car("VIN789", "A6", "Audi", 2021, 35000, 48000);
        Car car5 = new Car("VIN456", "X5", "BMW", 2022, 28000, 56000); // Дубликат по VIN

        // Создаем HashSet и добавляем машины
        Set<Car> carSet = new HashSet<>();
        carSet.add(car1);
        carSet.add(car2);
        carSet.add(car3); // Не должен добавиться (дубликат car1)
        carSet.add(car4);
        carSet.add(car5); // Не должен добавиться (дубликат car2)

        System.out.println("Размер HashSet после добавления (должен быть 3): " + carSet.size());
        System.out.println("\nМашины в HashSet (без дубликатов по VIN):");
        for (Car car : carSet) {
            System.out.println(car);
        }

        // Проверка equals
        System.out.println("\nПроверка equals:");
        System.out.println("car1.equals(car3): " + car1.equals(car3)); // true
        System.out.println("car1.equals(car2): " + car1.equals(car2)); // false

        // Сортировка по году выпуска (от новых к старым)
        List<Car> carList = new ArrayList<>(carSet);
        Collections.sort(carList); // Используем Comparable

        System.out.println("\nСортировка по году выпуска (от новых к старым):");
        for (Car car : carList) {
            System.out.println(car.getYear() + " - " + car.getModel() + " (" + car.getManufacturer() + ")");
        }
    }

    // Задание №4: Stream API (Анализ автопарка)
    public static void task4StreamAPI() {
        // Создаем список машин
        List<Car> cars = createCarList();

        System.out.println("Исходный список машин:");
        cars.forEach(System.out::println);

        // 1. Фильтруем машины с пробегом меньше 50.000 км
        System.out.println("\n1. Машины с пробегом меньше 50.000 км:");
        List<Car> lowMileageCars = cars.stream()
                .filter(car -> car.getMileage() < 50000)
                .collect(Collectors.toList());
        lowMileageCars.forEach(System.out::println);

        // 2. Сортируем по цене (по убыванию) и выводим топ-3 самые дорогие
        System.out.println("\n2. Топ-3 самые дорогие машины:");
        cars.stream()
                .sorted(Comparator.comparingDouble(Car::getPrice).reversed())
                .limit(3)
                .forEach(System.out::println);

        // 3. Считаем средний пробег всех машин
        System.out.println("\n3. Статистика по пробегу:");
        double avgMileage = cars.stream()
                .mapToDouble(Car::getMileage)
                .average()
                .orElse(0);
        System.out.printf("Средний пробег всех машин: %.1f км%n", avgMileage);

        // Дополнительная статистика по пробегу
        DoubleSummaryStatistics mileageStats = cars.stream()
                .mapToDouble(Car::getMileage)
                .summaryStatistics();
        System.out.println("Статистика по пробегу: " + mileageStats);

        // 4. Группируем машины по производителю
        System.out.println("\n4. Группировка машин по производителю:");
        Map<String, List<Car>> carsByManufacturer = cars.stream()
                .collect(Collectors.groupingBy(Car::getManufacturer));

        carsByManufacturer.forEach((manufacturer, carList) -> {
            System.out.println("\n" + manufacturer + ":");
            carList.forEach(car -> System.out.println("  " + car));
        });

        // Дополнительно: статистика по производителям
        System.out.println("\nДополнительно: Количество машин по производителям:");
        Map<String, Long> countByManufacturer = cars.stream()
                .collect(Collectors.groupingBy(Car::getManufacturer, Collectors.counting()));
        countByManufacturer.forEach((manufacturer, count) ->
                System.out.println(manufacturer + ": " + count + " шт."));
    }

    // Вспомогательный метод для создания списка машин для задания 4
    private static List<Car> createCarList() {
        List<Car> cars = new ArrayList<>();

        cars.add(new Car("VIN001", "Camry", "Toyota", 2023, 15000, 32000));
        cars.add(new Car("VIN002", "X5", "BMW", 2022, 25000, 65000));
        cars.add(new Car("VIN003", "A6", "Audi", 2021, 48000, 45000));
        cars.add(new Car("VIN004", "C-Class", "Mercedes", 2023, 8000, 55000));
        cars.add(new Car("VIN005", "Accord", "Honda", 2020, 52000, 28000));
        cars.add(new Car("VIN006", "Model S", "Tesla", 2023, 12000, 85000));
        cars.add(new Car("VIN007", "Passat", "Volkswagen", 2019, 68000, 22000));
        cars.add(new Car("VIN008", "Q5", "Audi", 2022, 35000, 52000));
        cars.add(new Car("VIN009", "3 Series", "BMW", 2021, 42000, 43000));
        cars.add(new Car("VIN010", "RAV4", "Toyota", 2023, 18000, 35000));
        cars.add(new Car("VIN011", "GLE", "Mercedes", 2022, 28000, 72000));
        cars.add(new Car("VIN012", "Civic", "Honda", 2021, 45000, 25000));

        return cars;
    }
}