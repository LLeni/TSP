import java.util.ArrayList;

public class BranchAndBoundAlgorithm {

    //TODO: СРОЧНО НУЖЕН РЕФАКТОРИНГ :D
    //private City cities[];
//    private double C[][];
//    private double minsRow[];
//    private double minsColumn[];
//    private double localLowBorder;
//    private double maxEvaluation[];

    private ArrayList<Branch> branches;
    private int idCurrentBranch;

    BranchAndBoundAlgorithm(){

    }

    public double calculateMinLength(City cities[]){
        C = createMatrixLengths(cities);
        return calculateMinLength(C);
    }

    //Основной связующий метод
    public double calculateMinLength(double C[][]){
        minsRow = findMinsRow(C);
        C = reduceRows(C);
        minsColumn = findMinsColumn(C);
        C = reduceColumns(C);
        localLowBorder =  findLocalLowBorder(C);
        branches = new ArrayList<>();
        branches.add(new Branch(C, localLowBorder, false));
        idCurrentBranch = 0;
        Branch currentBranch = branches.get(idCurrentBranch);

        while(currentBranch.getC().length > 1){ //0?
            System.out.println("\nНАЧАЛО ЦИКЛА\n");

            System.out.println("МинГраница у всех веток:");
            for (int i = 0; i < branches.size(); i++) {
                System.out.print(" " + branches.get(i).getLowBorder());
            }
            if(currentBranch.isEliminatePath()){
                System.out.println("О нет, мы тут");
                minsRow = findMinsRow(currentBranch.getC());
                currentBranch.setC(reduceRows(currentBranch.getC()));
                minsColumn = findMinsColumn(currentBranch.getC());
                currentBranch.setC(reduceColumns(currentBranch.getC()));
                currentBranch.setLowBorder(currentBranch.getLowBorder() + findLocalLowBorder(currentBranch.getC()));
            }
            System.out.println("Перед нахождением максимума среди нулевых клеток");
            showMatrix(currentBranch.getC());
            maxEvaluation = findMaxEvaluationNullCells(currentBranch.getC()); //TODO: В класс ветви добавить maxEvaluation
            System.out.println(maxEvaluation[0] + " " + maxEvaluation[1] + " " + maxEvaluation[2]);
            currentBranch.setMaxEvaluation(maxEvaluation);
            System.out.println("1Количество веток: " + branches.size());
            System.out.println("айди ветки: " + idCurrentBranch);
            divideCurrentBranch(currentBranch.getC(), maxEvaluation, currentBranch.getLowBorder()); //TODO: передавать саму ветвь
            System.out.println("2Количество веток: " + branches.size());
            System.out.println("айди ветки: " + idCurrentBranch);
            branches.remove(idCurrentBranch); // Убираем текущую ветвь, т.к она ветвлилась
            System.out.println("3Количество веток: " + branches.size());
            System.out.println("айди ветки: " + idCurrentBranch);
            System.out.println("Значение нижней границы: " + currentBranch.getLowBorder());
            idCurrentBranch = chooseBranch();
            currentBranch = branches.get(idCurrentBranch);
        }

        return currentBranch.getLowBorder();
    }

    //Построение матрицы с исходными данными
    public double[][] createMatrixLengths(City cities[]){

        C = new double[cities.length][cities.length];
        for (int i = 0; i < cities.length; i++) {
            for (int j = 0; j < cities.length; j++) {
                if(i == j){
                    C[i][j] = Double.MAX_VALUE; //аналог бессконечности
                } else{
                    C[i][j] = Math.sqrt(Math.pow(cities[i].getX() - cities[j].getX(),2) + Math.pow(cities[i].getY() - cities[j].getY(),2));
                }
            }
        }
        return C;
    }
    //Нахождение минимума по строкам
    public double[] findMinsRow(double C[][]){
        minsRow = new double[C.length];
        double currentMin;
        System.out.print("\nMinsRow: ");
        for (int i = 0; i < minsRow.length; i++) {
            currentMin = Double.MAX_VALUE;
            for (int j = 0; j < minsRow.length; j++) {
                if(C[i][j] < currentMin)
                    currentMin = C[i][j];
            }
            minsRow[i] = currentMin;
            System.out.print(" " + minsRow[i]);
        }
        return minsRow;
    }
    //Редукция строк
    public double[][] reduceRows(double C[][]){
        for (int i = 0; i < C.length; i++) {
            for (int j = 0; j < C.length; j++) {
                if(i != j) //избегаем главную диагональ
                    C[i][j] -= minsRow[i];
            }
        }
        return C;
    }
    //Нахождение минимума по столбцам
    public double[] findMinsColumn(double C[][]){
        double currentMin;
        minsColumn = new double[C.length];
        System.out.print("\nMinsColumn: ");
        for (int j = 0; j < minsColumn.length; j++) {
            currentMin = Double.MAX_VALUE;
            for (int i = 0; i < minsColumn.length; i++) {
                if(C[i][j] < currentMin)
                    currentMin = C[i][j];
            }
            minsColumn[j] = currentMin;

            System.out.print(" " + minsColumn[j]);
        }
        return minsColumn;
    }
    //Редукция столбцов
    public double[][] reduceColumns(double C[][]){
        for (int i = 0; i < C.length; i++) {
            for (int j = 0; j < C.length; j++) {
                if(i != j) //избегаем главную диагональ
                    C[i][j] -= minsColumn[i];
            }
        }
        return C;
    }

    public double findLocalLowBorder(double C[][]){
        for (int i = 0; i < C.length; i++) {
            localLowBorder += minsRow[i] + minsColumn[i];
        }
        return localLowBorder;
    }

    //Вычисление оценки нулевых клеток и одновременное нахождение среди них максимальной
    public double[] findMaxEvaluationNullCells(double C[][]){
        double maxEvaluation[] = new double[3];
        maxEvaluation[0] = Double.MIN_VALUE;

        System.out.println("ЯЯЯ УСТАл");
        showMatrix(C);
        double currentMinRow = Double.MAX_VALUE ;
        double currentMinColumn = Double.MAX_VALUE;
        for (int i = 0; i < C.length; i++) {
            for (int j = 0; j < C.length; j++) {
                //Находим минимум там, где ноль
                //Приходится снова искать минимумы, т.к происходила редукция строк и столбцов
                if(C[i][j] == 0) {
                    currentMinRow = Double.MAX_VALUE;
                    currentMinColumn = Double.MAX_VALUE;
                    for (int k = 0; k < C.length; k++) {
                        if (currentMinRow > C[k][j] && k != i) {
                            currentMinRow = C[k][j];
                        }
                    }
                    for (int k = 0; k < C.length; k++) {
                        if (currentMinColumn > C[i][k] && k != j) {
                            currentMinColumn = C[i][k];
                        }
                    }
                    if(maxEvaluation[0] < (currentMinRow + currentMinColumn)){
                        System.out.println(i + " " + j + " " + (currentMinRow + currentMinColumn));
                        maxEvaluation[0] = (currentMinRow + currentMinColumn);
                        maxEvaluation[1] = i;
                        maxEvaluation[2] = j;
                    }
                }

            }
        }
        System.out.println("Помогите");
        return maxEvaluation;
    }

    //TODO:Плохой метод для тестирования
    //Делит текущую ветку на две
    public void divideCurrentBranch(double C[][], double maxEvaluation[], double localLowBorder){

        System.out.println((int)maxEvaluation[1] + " " + (int)maxEvaluation[2]);
        //Первая ветка, где мы включаем путь
        C[(int)maxEvaluation[1]][(int)maxEvaluation[2]] = Double.MAX_VALUE;

        double newC[][] = C;
        showMatrix(newC);
        newC[(int)maxEvaluation[2]][(int)maxEvaluation[1]] = Double.MAX_VALUE;
        showMatrix(newC);
        newC = reduceMatrix(C,(int)maxEvaluation[1], (int)maxEvaluation[2]);

        System.out.println("Матрица после редукции");
        showMatrix(newC);

        //TODO: противное повторение кода. Лучше избавиться. Трижды повторяется
        minsRow = findMinsRow(newC);
        newC = reduceRows(newC);
        minsColumn = findMinsColumn(newC);
        newC = reduceColumns(newC);

        double lowBorderFirst =  findLocalLowBorder(newC);
        //Вторая ветка, где мы исключаем путь
        double lowBorderSecond = localLowBorder + maxEvaluation[0];

        branches.add(new Branch(newC,lowBorderFirst, false));
        branches.add(new Branch(C, lowBorderSecond, true));

        System.out.println("Значение нижней границы (включая путь): " + lowBorderFirst);
        System.out.println("Значение нижней границы: (НЕ включая путь)" + lowBorderSecond);
        showMatrix(C);
        showMatrix(newC);
    }

    public int chooseBranch(){
        double minLowBorder = Double.MAX_VALUE;
        int idBranch = -1;
        System.out.print("MinBorders:");
        for (int i = 0; i < branches.size(); i++) {
            if(branches.get(i).getLowBorder() < minLowBorder){
                minLowBorder = branches.get(i).getLowBorder();
                idBranch = i;
            }
            System.out.print(" " + minLowBorder );
        }
        System.out.println("\nВыбралу ту, где minLowBorder = " + minLowBorder);
        return idBranch;
    }


    public double[][] reduceMatrix(double C[][], int row, int column){
        double newC[][] = new double[C.length-1][C.length-1];
        boolean isRowSkipped = false;
        boolean isColumnSkipped;

        for (int i = 0; i < C.length; i++) {
            if(i == row){
                isRowSkipped = true;
                continue;
            }

            isColumnSkipped = false;
            for (int j = 0; j < C.length; j++) {
                if(j == column){
                    isColumnSkipped = true;
                    continue;
                }
                newC[i - Boolean.compare(isRowSkipped, false)][j - Boolean.compare(isColumnSkipped, false)] =  C[i][j];
            }
        }



        return newC;
    }

    //Для удобства во время разработки кода
    public void showMatrix(double C[][]){
        System.out.println("Матрица:");
        for (int i = 0; i < C.length; i++) {
            for (int j = 0; j < C.length; j++) {
                System.out.print(C[i][j]+ " ");
            }
            System.out.println();
        }
    }
}
