import java.util.ArrayList;

//Решатель
//Расчитывает минимальный путь и проводит менеджмент решений
public class Solver {
    private ArrayList<Solution> solutions; //ноды решений в дереве. Легче представлять в виде списка, т.к нас интересуют только листья
    private int idCurrentSolution;

    /**
     * Расчитывает минимальный путь проходящий через все города и с возратом в первоначальный город
     * @param cities массив городов
     * @return длину минимального пути
     */
    public double calculateMinLength(City cities[]){
        return calculateMinLength(Solution.createC(cities));
    }

    /**
     * Расчитывает минимальный путь проходящий через все города и с возратом в первоначальный город
     * @param C матрица расстояний между городами
     * @return длину минимального пути
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

        solutions = new ArrayList<>();
        solutions.add(new Solution(C));
        idCurrentSolution = 0;

        Solution currentSolution = solutions.get(idCurrentSolution);
        currentSolution.reduceLines();
        currentSolution.calculateLowBorder();

        while(currentSolution.getC().length > 1){
            if(currentSolution.isEliminatePath()){
                currentSolution.reduceLines();
            }
            Evaluation evaluation = currentSolution.calculateMaxEvaluation();
            currentSolution.changeCell(evaluation.getRowId(), evaluation.getColumnId(), Solution.INFINITY);
            divideCurrentSolution();
            reworkLastSolutions(evaluation);
            idCurrentSolution = chooseSolution();
            currentSolution = solutions.get(idCurrentSolution);
        }
        return currentSolution.getLowBorder();
    }

    /**
     * Делит текущее решение на два
     */
    private void divideCurrentSolution(){

        Solution currentSolution = solutions.get(idCurrentSolution);

        Solution solutionInclude = null;
        Solution solutionExclude = null;
        try {
            solutionInclude = currentSolution.clone();
            solutionExclude = currentSolution.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        solutions.remove(idCurrentSolution); // Убираем текущее решение, т.к оно породило остальные два
        solutions.add(solutionInclude);
        solutions.add(solutionExclude);
    }

    /**
     * Проводит изменения над последними двумя решениями
     * @param evaluation
     */
    private void reworkLastSolutions(Evaluation evaluation){
        Solution solutionInclude = solutions.get(solutions.size()-2);
        Solution solutionExclude = solutions.get(solutions.size()-1);

        //Первая ветка, где мы включаем путь
        solutionInclude.reduceC(evaluation.getRowId(), evaluation.getColumnId());
        solutionInclude.reduceLines();
        solutionInclude.calculateLowBorder();
        solutionInclude.setEliminatePath(false);

        //Вторая ветка, где мы исключаем путь
        solutionExclude.setLowBorder(solutionExclude.getLowBorder() + evaluation.getValue());
        solutionExclude.setEliminatePath(true);
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
