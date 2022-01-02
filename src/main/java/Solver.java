import java.util.ArrayList;

//Решатель
//Расчитывает минимальный путь и проводит менеджмент решений
public class Solver {
    private ArrayList<Solution> solutions; //ноды решений в дереве. Легче представлять в виде списка, т.к нас интересуют только листья
    private int idCurrentSolution;
    private Solution currentSolution;

    /**
     * Расчитывает минимальный путь проходящий через все города и с возратом в первоначальный город
     * @param cities массив городов
     * @return
     */
    public double calculateMinLength(City cities[]){
        return calculateMinLength(MatrixLengths.createC(cities));
    }

    /**
     * Расчитывает минимальный путь проходящий через все города и с возратом в первоначальный город
     * @param C матрица расстояний между городами
     * @return
     */
    public double calculateMinLength(double C[][]){

        if(C.length == 1){
            return 0;
        }
        if(C.length == 2){
            if(C[0][1] > C[1][0]){
                return C[1][0];
            } else {
                return C[0][1];
            }
        }
        currentSolution = new Solution(new MatrixLengths(C));

        currentSolution.getMatrixLengths().reduceLines();
        currentSolution.calculateLowBorder();

        solutions = new ArrayList<>();
        solutions.add(currentSolution);
        idCurrentSolution = 0;

        while(currentSolution.getMatrixLengths().getC().length > 1){
            if(currentSolution.isEliminatePath()){
                currentSolution.getMatrixLengths().reduceLines();
            }
            divideCurrentSolution();
            solutions.remove(idCurrentSolution); // Убираем текущую ветвь, т.к она ветвлилась
            idCurrentSolution = chooseSolution();
            currentSolution = solutions.get(idCurrentSolution);
        }
        return currentSolution.getLowBorder();
    }


    /**
     * Делит текущее решение на два
     */
    private void divideCurrentSolution(){

        Evaluation evaluation = calculateMaxEvaluation(currentSolution.getMatrixLengths());
        currentSolution.getMatrixLengths().changeCell(evaluation.getRowId(), evaluation.getColumnId(), MatrixLengths.INFINITY);

        Solution solutionInclude = null;
        Solution solutionExclude = null;
        try {
            solutionInclude = currentSolution.clone();
            solutionExclude = currentSolution.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        //Первая ветка, где мы включаем путь
        solutionInclude.getMatrixLengths().reduceMatrix(evaluation.getRowId(), evaluation.getColumnId());
        solutionInclude.getMatrixLengths().reduceLines();
        solutionInclude.calculateLowBorder();

        //Вторая ветка, где мы исключаем путь
        solutionExclude.setLowBorder(solutionExclude.getLowBorder() + evaluation.getValueEvaluation());

        solutionInclude.getMatrixLengths().showMatrix();
        solutionExclude.getMatrixLengths().showMatrix();

        solutionInclude.setEliminatePath(false);
        solutionExclude.setEliminatePath(true);

        solutions.add(solutionInclude);
        solutions.add(solutionExclude);
    }


    //Вычисление оценки нулевых клеток и одновременное нахождение среди них максимальной
    private Evaluation calculateMaxEvaluation(MatrixLengths matrixLengths){
        Evaluation evaluation = new Evaluation();

        double C[][] = matrixLengths.getC();
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
     * Выбрать релевантное решение с минимальной нижней границей
     * @return идентификатор решения
     */
    private int chooseSolution(){
        double minLowBorder = Double.MAX_VALUE;
        int idSolution = -1;

        for (int i = 0; i < solutions.size(); i++) {
            if(solutions.get(i).getLowBorder() < minLowBorder){
                minLowBorder = solutions.get(i).getLowBorder();
                idSolution = i;
            }
        }
        return idSolution;
    }
}
