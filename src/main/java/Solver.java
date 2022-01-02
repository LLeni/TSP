import java.util.ArrayList;

public class Solver {
    private ArrayList<Solution> solutions; //ноды решений в дереве. Легче представлять в виде списка, т.к нас интересуют только листья
    private int idCurrentSolution;
    private Solution currentSolution;


    public double calculateMinLength(City cities[]){
        return calculateMinLength(MatrixLengths.createC(cities));
    }

    //Основной связующий метод
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

        System.out.println(currentSolution.getLowBorder() + "  231231231");
        solutions = new ArrayList<>();
        solutions.add(currentSolution);
        idCurrentSolution = 0;

        while(currentSolution.getMatrixLengths().getC().length > 1){


            System.out.println("\n__________________НАЧАЛО ЦИКЛА__________________\n");


            System.out.println("МинГраница у всех веток:");
            for (int i = 0; i < solutions.size(); i++) {
                System.out.print(" " + solutions.get(i).getLowBorder());
            }
            if(currentSolution.isEliminatePath()){
                currentSolution.getMatrixLengths().reduceLines();
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


    //Делит текущее решение на два
    private void divideCurrentSolution(Solution solution){

        Evaluation evaluation = calculateMaxEvaluation(solution.getMatrixLengths());
        solution.getMatrixLengths().changeCell(evaluation.getRowId(), evaluation.getColumnId(), MatrixLengths.INFINITY);

        Solution solutionInclude = null;
        Solution solutionExclude = null;
        try {
            solutionInclude = solution.clone();
            solutionExclude = solution.clone();
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
        //showMatrix(C);
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
                        System.out.println(i + " " + j + " " + (currentMinRow + currentMinColumn));
                        evaluation.setEvaluation(currentMinRow + currentMinColumn, i, j);
                    }
                }

            }
        }
        return evaluation;
    }

    private int chooseSolution(){
        double minLowBorder = Double.MAX_VALUE;
        int idBranch = -1;

        System.out.println(solutions.size() + " g34214");
        for (int i = 0; i < solutions.size(); i++) {
            if(solutions.get(i).getLowBorder() < minLowBorder){
                System.out.println(minLowBorder + " asdasda");
                minLowBorder = solutions.get(i).getLowBorder();
                idBranch = i;
            }
        }
        System.out.println("\nВыбралу ту, где minLowBorder = " + minLowBorder);
        return idBranch;
    }


}
