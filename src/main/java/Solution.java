public class Solution implements  Cloneable {

    private MatrixLengths matrixLengths;
    private double lowBorder;
    private boolean isEliminatePath;

    Solution(MatrixLengths matrixLengths){
        this.matrixLengths = matrixLengths;
    }

    //Надо подумать
//    Solution(Solution solution){
//        this.C = solution.getC();
//        this.lowBorder = solution.getLowBorder();
//    }


    public Solution clone () throws CloneNotSupportedException
    {
        Solution solution = (Solution) super.clone();
        MatrixLengths matrixLengths = this.getMatrixLengths().clone();
        solution.setMatrixLengths(matrixLengths);
        return solution;
    }


    public MatrixLengths getMatrixLengths() {
        return matrixLengths;
    }

    public void setMatrixLengths(MatrixLengths matrixLengths) {
        this.matrixLengths = matrixLengths;
    }

    public double getLowBorder() {
        return lowBorder;
    }

    public void setLowBorder(double lowBorder) {
        this.lowBorder = lowBorder;
    }

    public boolean isEliminatePath() {
        return isEliminatePath;
    }

    public void setEliminatePath(boolean eliminatePath) {
        isEliminatePath = eliminatePath;
    }

}
