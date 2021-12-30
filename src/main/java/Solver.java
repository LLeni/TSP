import java.util.ArrayList;

public class Solver {

    //TODO: СРОЧНО НУЖЕН РЕФАКТОРИНГ :D

    private ArrayList<Solution> solutions; //ноды решений в дереве
    private int idCurrentSolution;
    private Solution currentSolution;

    Solver(){

    }

    public double calculateMinLength(City cities[]){
        return calculateMinLength(MatrixLengths.createC(cities));
    }

    //Основной связующий метод
    //TODO: Может назвать Solve
    public double calculateMinLength(double C[][]){

        currentSolution = new Solution(new MatrixLengths(C));

        double minsRow[] = calculateMinsRow(currentSolution.getMatrixLengths());
        currentSolution.getMatrixLengths().setC(reduceRows(currentSolution.getMatrixLengths(), minsRow));
        double minsColumn[] = calculateMinsColumn(currentSolution.getMatrixLengths());
        currentSolution.getMatrixLengths().setC(reduceColumns(currentSolution.getMatrixLengths(), minsColumn));
        currentSolution.setLowBorder(calculateLowBorder(currentSolution.getMatrixLengths(), minsRow, minsColumn));
        idCurrentSolution = 0;

        solutions = new ArrayList<>();
        solutions.add(currentSolution);

        while(currentSolution.getMatrixLengths().getC().length > 1){ //0?


            System.out.println("\n__________________НАЧАЛО ЦИКЛА__________________\n");


            System.out.println("МинГраница у всех веток:");
            for (int i = 0; i < solutions.size(); i++) {
                System.out.print(" " + solutions.get(i).getLowBorder());
            }
            if(currentSolution.isEliminatePath()){
                minsRow = calculateMinsRow(currentSolution.getMatrixLengths());
                currentSolution.getMatrixLengths().setC(reduceRows(currentSolution.getMatrixLengths(), minsRow));
                minsColumn = calculateMinsColumn(currentSolution.getMatrixLengths());
                currentSolution.getMatrixLengths().setC(reduceColumns(currentSolution.getMatrixLengths(), minsColumn));

                currentSolution.getMatrixLengths().showMatrix();

            }
            divideCurrentSolution(currentSolution);

            solutions.remove(idCurrentSolution); // Убираем текущую ветвь, т.к она ветвлилась

            for (int i = 0; i < solutions.size(); i++) {
                System.out.print(" " + solutions.get(i).getLowBorder());
            }
            idCurrentSolution = chooseSolution();
            currentSolution = solutions.get(idCurrentSolution);
        }

        return currentSolution.getLowBorder();
    }
    private double calculateLowBorder(MatrixLengths matrixLengths, double minsRow[], double minsColumn[]){

        double localLowBorder = 0;
        double C[][] = matrixLengths.getC();
        for (int i = 0; i < C.length; i++) {
            localLowBorder += minsRow[i] + minsColumn[i];
        }

        return localLowBorder;
    }

    //Нахождение минимальные значения в строках
    private double[] calculateMinsRow(MatrixLengths matrixLengths){
        double C[][] = matrixLengths.getC();
        double minsRow[] = new double[C.length];
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
    private double[][] reduceRows(MatrixLengths matrixLengths, double minsRow[]){
        double C[][] = matrixLengths.getC();
        for (int i = 0; i < C.length; i++) {
            for (int j = 0; j < C.length; j++) {
                if(i != j) //избегаем главную диагональ
                    C[i][j] -= minsRow[i];
            }
        }
        return C;
    }

    //Редукция столбцов
    private double[][] reduceColumns(MatrixLengths matrixLengths, double minsColumn[]){
        double C[][] = matrixLengths.getC();
        for (int i = 0; i < C.length; i++) {
            for (int j = 0; j < C.length; j++) {
                if(i != j) //избегаем главную диагональ
                    C[i][j] -= minsColumn[j];
            }
        }
        return C;
    }

    //Нахождение минимальных значений в колонках
    private double[] calculateMinsColumn(MatrixLengths matrixLengths){
        double C[][] = matrixLengths.getC();
        double currentMin;
        double minsColumn[] = new double[C.length];
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


    //Делит текущее решение на два
    private void divideCurrentSolution(Solution solution){

        double maxEvaluation[] = calculateMaxEvaluation(solution.getMatrixLengths());
        solution.getMatrixLengths().changeCell((int)maxEvaluation[1], (int)maxEvaluation[2], Double.MAX_VALUE);

        Solution solutionInclude = null;
        Solution solutionExclude = null;
        try {
            solutionInclude = solution.clone();
            solutionExclude = solution.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        //Первая ветка, где мы включаем путь
        solutionInclude.getMatrixLengths().excludePath((int)maxEvaluation[1], (int)maxEvaluation[2]); // TODO: может все же здесь в этом классе оставить логику этого метода
        solutionInclude.getMatrixLengths().setC(reduceMatrix(solutionInclude, (int)maxEvaluation[1], (int)maxEvaluation[2]));

        double minsRow[] = calculateMinsRow(solutionInclude.getMatrixLengths());
        solutionInclude.getMatrixLengths().setC(reduceRows(solutionInclude.getMatrixLengths(), minsRow));
        double minsColumn[] = calculateMinsColumn(solutionInclude.getMatrixLengths());
        solutionInclude.getMatrixLengths().setC(reduceColumns(solutionInclude.getMatrixLengths(), minsColumn));
        solutionInclude.setLowBorder(solutionInclude.getLowBorder() + calculateLowBorder(solutionInclude.getMatrixLengths(), minsRow, minsColumn));


        //Вторая ветка, где мы исключаем путь
        solutionExclude.setLowBorder(solutionExclude.getLowBorder() + maxEvaluation[0]);

        solutionInclude.getMatrixLengths().showMatrix();
        solutionExclude.getMatrixLengths().showMatrix();

        solutionInclude.setEliminatePath(false);
        solutionExclude.setEliminatePath(true);
        solutions.add(solutionInclude);
        solutions.add(solutionExclude);
    }


    //Вычисление оценки нулевых клеток и одновременное нахождение среди них максимальной
    private double[] calculateMaxEvaluation(MatrixLengths matrixLengths){
        double maxEvaluation[] = new double[3];
        maxEvaluation[0] = Double.MIN_VALUE;

        double C[][] = matrixLengths.getC();
        //showMatrix(C);
        double currentMinRow;
        double currentMinColumn;
        for (int i = 0; i < C.length; i++) {
            for (int j = 0; j < C.length; j++) {
                //Находим минимум там, где ноль
                //Приходится снова искать минимумы (но иначе),  т.к происходила редукция строк и столбцов
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
        return maxEvaluation;
    }



    private int chooseSolution(){
        double minLowBorder = Double.MAX_VALUE;
        int idBranch = -1;
        for (int i = 0; i < solutions.size(); i++) {
            if(solutions.get(i).getLowBorder() < minLowBorder){
                minLowBorder = solutions.get(i).getLowBorder();
                idBranch = i;
            }
        }
        System.out.println("\nВыбралу ту, где minLowBorder = " + minLowBorder);
        return idBranch;
    }


    private double[][] reduceMatrix(Solution solution, int r, int c){
        double newC[][] = new double[solution.getMatrixLengths().getC().length-1][solution.getMatrixLengths().getC().length-1];
        double C[][] = solution.getMatrixLengths().getC();
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



        return newC;
    }


}
