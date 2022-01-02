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

    @ParameterizedTest
    @MethodSource("provideCities")
    public void testCreateC(int id, City cities[], double expected[][]){
        Assertions.assertTimeoutPreemptively(Duration.ofMillis(500), () -> {
            System.out.println(id + "  11");
            double C[][] = MatrixLengths.createC(cities);
            for (int i = 0; i < cities.length; i++) {
                for (int j = 0; j < cities.length; j++) {
                    Assertions.assertEquals(expected[i][j], C[i][j], "Value on [" + i + ", " + j + "]  different from expected" );
                }
            }
        }, "It was timeout on " + id + " case");
    }

    private static Stream<Arguments> provideCities() {
        return Stream.of(
                Arguments.of(
                        0, new City[]{
                              new City(0,0),
                              new City(5, 0),
                              new City(10, 0),
                        },new double[][]{
                                {MatrixLengths.INFINITY, 5.0, 10.0},
                                {5.0, MatrixLengths.INFINITY, 5.0},
                                {10.0, 5.0, MatrixLengths.INFINITY}
                        }),
                Arguments.of(
                        1, new City[]{
                            new City(-2,0),
                            new City(12, 12),
                            new City(-10, -20),
                        }, new double[][]{
                            {MatrixLengths.INFINITY, 18.439088914585774, 21.540659228538015},
                            {18.439088914585774, MatrixLengths.INFINITY, 38.8329756778952},
                            {21.540659228538015, 38.8329756778952, MatrixLengths.INFINITY},
                        }),
                Arguments.of(
                        2, new City[]{
                            new City(0,0),
                            new City(1, 1),
                            new City(2, 2),
                            new City(3, 3),
                            new City(4, 4),

                        },new double[][]{
                            {MatrixLengths.INFINITY, 1.4142135623730951, 2.8284271247461903, 4.242640687119285, 5.656854249492381},
                            {1.4142135623730951, MatrixLengths.INFINITY, 1.4142135623730951, 2.8284271247461903, 4.242640687119285},
                            {2.8284271247461903, 1.4142135623730951, MatrixLengths.INFINITY, 1.4142135623730951, 2.8284271247461903},
                            {4.242640687119285, 2.8284271247461903, 1.4142135623730951, MatrixLengths.INFINITY,1.4142135623730951},
                            {5.656854249492381, 4.242640687119285, 2.8284271247461903, 1.4142135623730951, MatrixLengths.INFINITY}
                        }),
                Arguments.of(
                        3, new City[]{
                            new City(40,12),
                            new City(512, 11),
                            new City(34, 39),
                            new City(12, 291),
                            new City(421, 493),
                        },
                        new double[][]{
                            {MatrixLengths.INFINITY, 472.0010593208452, 27.65863337187866, 280.40149785619906, 613.6138851101725},
                            {472.0010593208452, MatrixLengths.INFINITY, 478.81938139553205, 573.0619512757761, 490.5150354474366},
                            {27.65863337187866, 478.81938139553205, MatrixLengths.INFINITY, 252.9584946191766, 596.560977604134},
                            {280.40149785619906, 573.0619512757761, 252.9584946191766, MatrixLengths.INFINITY,456.1633479358025},
                            {613.6138851101725, 490.5150354474366, 596.560977604134,456.1633479358025,MatrixLengths.INFINITY}
                        })
        );
    }

}
