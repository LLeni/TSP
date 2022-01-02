//Контроллер матрицы длин
public class MatrixLengths implements Cloneable {
    //Максимальное значение Double будет отображать бессконечность в ячейке матрицы
    public static final double INFINITY = Double.MAX_VALUE;

    private double C[][];
    private int rowIds[];
    private int columnIds[];

    private double minsRow[], minsColumn[];

    MatrixLengths(double C[][]){
        this.C = C;

        rowIds = new int[C.length];
        columnIds = new int[C.length];

        for (int i = 0; i < C.length; i++) {
            rowIds[i] = i;
            columnIds[i] = i;
        }
    }

    //Построение матрицы расстояний с исходными данными
    public static double[][] createC(City cities[]){
        double C[][] = new double[cities.length][cities.length];
        for (int i = 0; i < cities.length; i++) {
            for (int j = 0; j < cities.length; j++) {
                if(i == j){
                    C[i][j] = MatrixLengths.INFINITY;
                } else{
                    C[i][j] = Math.sqrt(Math.pow(cities[i].getX() - cities[j].getX(),2) + Math.pow(cities[i].getY() - cities[j].getY(),2));
                }
            }
        }
        return C;
    }

    /**
     * Проводит редуцию линий: как строк, так и столбцов и промежуточные расчеты в виде минимальных значений у каждого столбца и строки
     */
    public void reduceLines(){
        calculateMinsRow();
        reduceRows(minsRow);

        calculateMinsColumn();
        reduceColumns(minsColumn);
    }


    /**
     * Расчитывает минимальные значения у каждой строки
     */
    private void calculateMinsRow(){
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
    }

    /**
     * Проводит редукцию строк
     * @param minsRow массив минимальных значений у каждой строки
     */
    private void reduceRows(double minsRow[]){
        for (int i = 0; i < C.length; i++) {
            for (int j = 0; j < C.length; j++) {
                if(i != j) //избегаем главную диагональ
                    C[i][j] -= minsRow[i];
            }
        }
    }

    /**
     * Расчитывает  минимальные значения у каждого столбца
     */
    private void calculateMinsColumn(){
        minsColumn = new double[C.length];

        double currentMin;
        for (int j = 0; j < minsColumn.length; j++) {
            currentMin = Double.MAX_VALUE;
            for (int i = 0; i < minsColumn.length; i++) {
                if(C[i][j] < currentMin)
                    currentMin = C[i][j];
            }
            minsColumn[j] = currentMin;
        }
    }

    /**
     * Проводит редукцию столбцов
     * @param minsColumn массив минимальных значений у каждого столбца
     */
    private void  reduceColumns(double minsColumn[]){
        for (int i = 0; i < C.length; i++) {
            for (int j = 0; j < C.length; j++) {
                if(i != j) //избегаем главную диагональ
                    C[i][j] -= minsColumn[j];
            }
        }
    }

    /**
     * Проводит редукцию матрицы
     * @param r
     * @param c
     */
    public void reduceMatrix(int r, int c){
        excludePath(r, c);
        double newC[][] = new double[C.length-1][C.length-1];
        int row = r;
        int column = c;
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
                newC[i - Boolean.compare(isRowSkipped, false)][j - Boolean.compare(isColumnSkipped, false)] = C[i][j];
            }
        }
        C = newC;
    }

    /**
     * Исключает путь c -> r
     *
     * @param r строка
     * @param c столбец
     */
    private void excludePath(int r, int c){
        System.out.println(r +  "  " + c);
        int nameCityRow = rowIds[r];
        int nameCityColumn = columnIds[c];

        int newColumnId = -1;
        for (int i = 0; i < columnIds.length; i++) {
            if(nameCityRow == columnIds[i]){
                newColumnId = i;
                break;
            }
        }

        int newRowId = -1;
        for (int i = 0; i < rowIds.length; i++) {
            if(nameCityColumn == rowIds[i]){
                newRowId = i;
                break;
            }
        }

        if(newRowId != -1 && newColumnId != -1){
            C[newRowId][newColumnId] = MatrixLengths.INFINITY;
        }

        boolean isRowSkipped = false ;
        int newRowIds[] = new int[rowIds.length - 1];
        for (int i = 0; i < rowIds.length; i++) {
            if(i == r) {
                isRowSkipped = true;
                continue;
            }

            newRowIds[i - Boolean.compare(isRowSkipped, false)] = rowIds[i];
        }

        rowIds = newRowIds;

        boolean isColumnSkipped = false ;
        int newColumnIds[] = new int[columnIds.length - 1];
        for (int i = 0; i < columnIds.length; i++) {
            if(i == c){
                isColumnSkipped = true;
                continue;
            }
            newColumnIds[i - Boolean.compare(isColumnSkipped, false)] = columnIds[i];
        }
        columnIds = newColumnIds;
    }

    /**
     * Расчитывает оценки нулевых клеток и одновременное нахождение среди них максимальной
     *
     * @return максимальную оценку
     */

     public Evaluation calculateMaxEvaluation(){
        Evaluation evaluation = new Evaluation();

        double currentMinRow;
        double currentMinColumn;
        for (int i = 0; i < C.length; i++) {
            for (int j = 0; j < C.length; j++) {
                //Находим минимум там, где ноль
                //Приходится снова искать минимумы, но иначе
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
                    if(evaluation.getValueEvaluation() < (currentMinRow + currentMinColumn)){
                        evaluation.setEvaluation(currentMinRow + currentMinColumn, i, j);
                    }
                }

            }
        }
        return evaluation;
    }

    /**
     * Меняет значение в конкретно заданной ячейке в матрице расстояний
     * @param r строка
     * @param c колонка
     * @param value значение
     */
    public void changeCell(int r, int c, double value){
        C[r][c] = value;
    }


    /**
     * Позволяет вывести матрицу расстояний на консоль
     */
    public void showMatrix(){
        System.out.print("Матрица: \n\n ");

        for (int i = 0; i < columnIds.length; i++) {
            System.out.printf("%4d ",columnIds[i]);
        }
        System.out.println();
        for (int i = 0; i < C.length; i++) {
            System.out.print(rowIds[i] + " | ");
            for (int j = 0; j < C.length; j++) {
                System.out.print(C[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * Клонирует текущий экземпляр
     * @return склонированный текущий экземпляр MatrixLengths
     * @throws CloneNotSupportedException
     */
    public MatrixLengths clone () throws CloneNotSupportedException
    {
        return (MatrixLengths) super.clone();
    }

    public double[][] getC() {
        return C;
    }

    public void setC(double[][] c) {
        C = c;
    }

    public double[] getMinsRow() {
        return minsRow;
    }

    public double[] getMinsColumn() {
        return minsColumn;
    }
}
