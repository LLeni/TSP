
public class BranchAndBoundAlgorithm {

  //  private City cities[];
    private double C[][];
    private double minsRow[];
    private double minsColumn[];
    private double localLowBorder;
    private double maxEvaluation[];

    BranchAndBoundAlgorithm(){
    }

    //Основной связующий метод
    public double calculateMinLength(City cities[]){
        C = createMatrixLengths(cities);
        minsRow = findMinsRow(C);
        C = reduceRows(C);
        minsColumn = findMinsColumn(C);
        C = reduceColumns(C);
        localLowBorder =  findLocalLowBorder(C);
        maxEvaluation = findMaxEvaluationNullCells(C);
        return 3;
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
        for (int i = 0; i < minsRow.length; i++) {
            currentMin = Double.MAX_VALUE;
            for (int j = 0; j < minsRow.length; j++) {
                if(C[i][j] < currentMin)
                    currentMin = C[i][j];
            }
            minsRow[i] = currentMin;
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
        for (int j = 0; j < minsColumn.length; j++) {
            currentMin = Double.MAX_VALUE;
            for (int i = 0; i < minsColumn.length; i++) {
                if(C[i][j] < currentMin)
                    currentMin = C[i][j];
            }
            minsColumn[j] = currentMin;
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
    //Нахождение локальной нижней границы
    //Вначале находит минимум строк, затем проводит редукцию строк
    //И под конец повторяет шаги, но только над столбцами
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
        int row = -1;
        int column = -1;

        for (int i = 0; i < C.length; i++) {
            for (int j = 0; j < C.length; j++) {
                if(C[i][j] == 0 && C[i][j] > maxEvaluation[0] ){
                    maxEvaluation[0] = minsRow[i] + minsColumn[j];
                    maxEvaluation[1] = i;
                    maxEvaluation[2] = j;
                }
            }
        }
        return maxEvaluation;
    }

    public double[][] findCorrectBratch(double C[][], double maxEvaluation[]){

        //Первая ветка
        C[(int)maxEvaluation[1]][(int)maxEvaluation[2]] = Double.MAX_VALUE;
        C[(int)maxEvaluation[2]][(int)maxEvaluation[1]] = Double.MAX_VALUE;

        double newC[][]= reduceMatrix(C,(int)maxEvaluation[1], (int)maxEvaluation[2]);

        return C;
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
        showMatrix(C);
        showMatrix(newC);
        return newC;
    }

    //Для удобства во время разработки кода
    public void showMatrix(double C[][]){
        System.out.println("Матрица:");
        for (int i = 0; i < C.length; i++) {
            for (int j = 0; j < C.length; j++) {
                System.out.print(C[i][j] + " ");
            }
            System.out.println();
        }
    }
}
