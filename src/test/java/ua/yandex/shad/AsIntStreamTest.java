package ua.yandex.shad;

import org.junit.Assert;
import org.junit.Test;
import ua.yandex.shad.stream.AsIntStream;

/**
 * @author Vasyl Kazimirov
 */
public class AsIntStreamTest {

    @Test
    public void AsIntStreamPackUnpackTest() {
        AsIntStream str = (AsIntStream) AsIntStream.of(1, 2, 3, 4, 0);
        int[] exp = {1, 2, 3, 4, 0};
        int[] arr = str.toArray();
        Assert.assertArrayEquals(arr, exp);
    }

    @Test
    public void AsIntStreamAverageTest_someElements() {
        AsIntStream str = (AsIntStream) AsIntStream.of(-1, 2, 10, -4, 0);
        double exp = 1.4;
        double curr = str.average();
        Assert.assertEquals("Assertion fail", exp, curr, 10e-7);

    }

    @Test
    public void AsIntStreamMaxTest_someElements() {
        AsIntStream str = (AsIntStream) AsIntStream.of(10, 2, 20, -40, 0);
        double exp = 20;
        double curr = str.max();
        Assert.assertEquals("Assertion fail", exp, curr, 10e-7);
    }

    @Test
    public void AsIntStreamMinTest_someElements() {
        AsIntStream str = (AsIntStream) AsIntStream.of(-10, 2, -20, 4, 0);
        double exp = -20;
        double curr = str.min();
        Assert.assertEquals("Assertion fail", exp, curr, 10e-7);
    }

    @Test
    public void AsIntStreamCountTest_someElements() {
        AsIntStream str = (AsIntStream) AsIntStream.of(0, 2, -20, 4, 0, 1);
        double exp = 6;
        double curr = str.count();
        Assert.assertEquals("Assertion fail", exp, curr, 10e-7);
    }

    @Test
    public void AsIntStreamSumTest_someElements() {
        AsIntStream str = (AsIntStream) AsIntStream.of(0, 20, -20, 4, 0, 1);
        double exp = 5;
        double curr = str.sum();
        Assert.assertEquals("Assertion fail", exp, curr, 10e-7);
    }

    @Test
    public void AsIntStreamForEachTest_someElements() {
        AsIntStream str = (AsIntStream) AsIntStream.of(0, 20, -20, 4, 0, 1);
        StringBuilder s = new StringBuilder();
        String exp = "020-20401";
        str.forEach(x -> s.append(x));
        Assert.assertEquals("Assertion fail", exp, s.toString());
    }

    @Test
    public void AsIntStreamMapTest_someElements() {
        AsIntStream str = (AsIntStream) AsIntStream.of(0, 20, -20, 4, 0, 1);
        int[] exp = {0, 400, 400, 16, 0, 1};
        int[] curr = str.map(x -> x * x).toArray();
        Assert.assertArrayEquals("Assertion fail", exp, curr);
    }

    @Test
    public void AsIntStreamFilterTest_someElements() {
        AsIntStream str = (AsIntStream) AsIntStream.of(0, 21, -20, 5, 0, 1, 7);
        int[] exp = {21, 5, 1, 7};
        int[] curr = str.filter(x -> x % 2 == 1).toArray();
        Assert.assertArrayEquals("Assertion fail", exp, curr);
    }

    @Test
    public void AsIntStreamReduceTest_someElements() {
        AsIntStream str = (AsIntStream) AsIntStream.of(21, -20, 5, 1, 7);
        int exp = -420 * 35;
        int curr = str.reduce(1, (x, y) -> x *= y);
        Assert.assertEquals("Assertion fail", exp, curr);
    }

    @Test
    public void AsIntStreamFlatMapTest_someElements() {
        AsIntStream str = (AsIntStream) AsIntStream.of(21, -20, 5, 1, 7);
        int[] exp = {21, 42, 23, -20, -40, -18, 5, 10, 7, 1, 2, 3, 7, 14, 9};
        int[] curr = str.flatMap(x -> AsIntStream
                .of(x, x * 2, x + 2)).toArray();
        Assert.assertArrayEquals("Assertion fail", exp, curr);
    }

    @Test(expected = IllegalArgumentException.class)
    public void AsIntStreamAverageTest_empty() {
        AsIntStream str = (AsIntStream) AsIntStream.of(new int[0]);
        str.average();
    }

    @Test(expected = IllegalArgumentException.class)
    public void AsIntStreamMinTest_empty() {
        AsIntStream str = (AsIntStream) AsIntStream.of(new int[0]);
        str.min();
    }

    @Test(expected = IllegalArgumentException.class)
    public void AsIntStreamMaxTest_empty() {
        AsIntStream str = (AsIntStream) AsIntStream.of(new int[0]);
        str.max();
    }

    @Test(expected = IllegalArgumentException.class)
    public void AsIntStreamSumTest_empty() {
        AsIntStream str = (AsIntStream) AsIntStream.of(new int[0]);
        str.sum();
    }

}
