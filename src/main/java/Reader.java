import java.util.ArrayList;
import java.util.Scanner;

// Класс Reader отвечает за получение информации о городах из внешний источников
// Реализует паттерн Singleton
public class Reader {
    private Scanner sc;
    private ClassLoader cl;

    private Reader(){
        cl = this.getClass().getClassLoader();
    }

    /**
     * Позволяет считать информацию о городах с клавиатуры
     *
     * @return массив городов
     */
    public City[] readByKeabord() {
        sc = new Scanner(System.in);

        System.out.print("Введите количество городов: ");
        int numberCities = sc.nextInt();
        City cities[] = new City[numberCities];

        int upcomingX, upcomingY;
        for (int i = 0; i < numberCities; i++) {
            System.out.print("Введите через пробел координаты x и y города под номером " + (i + 1) + ": ");
            upcomingX = sc.nextInt();
            upcomingY = sc.nextInt();

            City city = new City(upcomingX, upcomingY);
            if(isCityExist(city, cities)){
                System.out.println("ОШИБКА! Такой город уже есть!");
                i--;
            }
            cities[i] = city;
        }
        sc.close();
        return cities;
    }

    /**
     * Проверяет есть ли в массиве указанный город
     *
     * @param city указанный город
     * @param cities массив городов
     * @return true - указанный город есть в массиве, false - указанного города нет в массиве
     */
    private boolean isCityExist(City city, City cities[]){
        for (int j = 0; j < cities.length; j++) {
            if(cities[j] != null){
                if(city.getX() == cities[j].getX() && city.getY() == cities[j].getY()){
                    return true;
                }
            }
        }
        return  false;
    }

    /**
     * Позволяет считать информацию о городах из файла
     *
     * @param path путь до файла
     * @return массив городов
     */
    public City[] readFromFile(String path){
        ArrayList<City> cities = new ArrayList<>();
        int upcomingX, upcomingY;
            sc = new Scanner(cl.getResourceAsStream(path));
            while(sc.hasNextLine()){
                if(sc.hasNextInt()){
                    upcomingX = sc.nextInt();
                } else {
                   break;
                }

                if(sc.hasNextInt()){
                    upcomingY = sc.nextInt();
                } else {
                    break;
                }

                cities.add(new City(upcomingX, upcomingY));
            }

        sc.close();
        return (City[])cities.toArray();
    }

    /**
     * Позволяет получить единственный при работе программы экземпляр Reader
     * @return экземпляр Reader
     */
    public  static Reader getInstance(){
        return new Reader();
    }
}
