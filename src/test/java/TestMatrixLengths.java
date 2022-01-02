import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Duration;
import java.util.ArrayList;
import java.util.stream.Stream;

public class TestMatrixLengths {

    @Test
    @ParameterizedTest
    @MethodSource("provideCities")
    public void testCreateC(int id, City cities[], int result){
        Assertions.assertTimeoutPreemptively(Duration.ofMillis(500), () -> {

           MatrixLengths matrixLengths = new MatrixLengths(MatrixLengths.createC(cities));
           matrixLengths.showMatrix();
            Assertions.assertEquals(MatrixLengths.createC(cities), result, "Calculation on " + id + " case was incorrect");
        }, "It was timeout on " + id + " case");
    }

    private static Stream<Arguments> provideCities() {
        return Stream.of(
                Arguments.of(
                        0, new City[]{
                          new City(0,0),
                          new City(5, 0),
                          new City(10, 0),
                        },
                        0),
                Arguments.of(
                        1, new City[]{
                                new City(-2,0),
                                new City(12, 12),
                                new City(-10, -20),
                        },
                        0),
                Arguments.of(
                        2, new City[]{
                                new City(0,0),
                                new City(1, 1),
                                new City(2, 2),
                                new City(3, 3),
                                new City(4, 4),

                        },
                        new double[][]{
                                {
                                    MatrixLengths.INFINITY,
                                }
                        }
                        )
                ,
                Arguments.of(
                        3, new City[]{
                                new City(40,12),
                                new City(512, 11),
                                new City(34, 39),
                                new City(12, 291),
                                new City(421, 493),
                        },
                        0)
        );
    }

}
