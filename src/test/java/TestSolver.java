import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Duration;
import java.util.stream.Stream;

public class TestSolver {
    private Solver solver;
    @BeforeEach
    public void init(){
        solver = new Solver();
    }

    @ParameterizedTest
    @MethodSource("provideVariousSizes")
    public void testVariousSizes(int id, double C[][], double result){
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(5), () -> {
            Assertions.assertEquals(result,solver.calculateMinLength(C) , "Calculation on " + id + " case was incorrect");
        }, "It was timeout on " + id + " case");
    }

    @ParameterizedTest
    @MethodSource("provideVariousValues")
    public void testVariousValues(int id, double C[][], double result){
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(5), () -> {
            Assertions.assertEquals(result, solver.calculateMinLength(C), "Calculation on " + id + " case was incorrect");
        }, "It was timeout on " + id + " case");

    }

    private static Stream<Arguments> provideVariousSizes() {
        return Stream.of(
                Arguments.of(
                        0, new double[][] {
                                {0}
                        }, 0),
                Arguments.of(
                        1, new double[][] {
                        {Solution.INFINITY, 1},
                        {4, Solution.INFINITY}
                        }, 1),
                Arguments.of(2, new double[][] {
                        {Solution.INFINITY, 5, 2},
                        {7, Solution.INFINITY, 12},
                        {1, 7, Solution.INFINITY}
                }, 16),
                Arguments.of(3, new double[][] {
                        {Solution.INFINITY,20,18,12,8},
                        {5,Solution.INFINITY,14,7,11},
                        {12,18,Solution.INFINITY,6,11},
                        {11,17,11,Solution.INFINITY,12},
                        {5,5,5,5,Solution.INFINITY}
                }, 41)
        );
    }

    private static Stream<Arguments> provideVariousValues() {
        return Stream.of(
                Arguments.of(
                        0, new double[][] {
                                {Solution.INFINITY,2045,1234,3454,5677},
                                {1354,Solution.INFINITY,6952,1234,3453},
                                {5721,9741,Solution.INFINITY,9230,1190},
                                {4831,3481,8172,Solution.INFINITY,1234},
                                {3712,3947,1259,9999,Solution.INFINITY}
                        }, 12436),
                Arguments.of(
                        1, new double[][] {
                                {Solution.INFINITY,20,18,12,8},
                                {5,Solution.INFINITY,14,7,11},
                                {12,18,Solution.INFINITY,6,11},
                                {11,17,11,Solution.INFINITY,12},
                                {5,5,5,5,Solution.INFINITY}
                        }, 41),
                Arguments.of(2, new double[][] {
                        {Solution.INFINITY,2.3,4.6,4.1,7.1},
                        {14.912,Solution.INFINITY,14.7,9.2,7.3},
                        {12.4,12.2,Solution.INFINITY,92.3,23.1},
                        {11.123,21.2,11.6,Solution.INFINITY,56.1},
                        {5.43,5,5,23.1,Solution.INFINITY}
                }, 40.629999999999995),
                Arguments.of(3, new double[][] {
                        {Solution.INFINITY,209213.21,48212.31,39281.2,29391.12},
                        {39291.2,Solution.INFINITY,91012.1,29219.21,28281.23},
                        {12312.12,48121.23,Solution.INFINITY,19191.134,59122.1341},
                        {11,17,49381.24,Solution.INFINITY,81871.12},
                        {38482.131,49382.123,49472.412,48382.5919,Solution.INFINITY}
                }, 129363.96199999997)
        );
    }

}
