public class Launcher {
    public static void main(String[] args) {
        Reader reader = Reader.getInstance();
        City cities[] = reader.readByKeyboard();

        Solver solver = new Solver();
        System.out.println("Длина минимального пути у коммивояжера составляет " + solver.calculateMinLength(cities));
    }
}
