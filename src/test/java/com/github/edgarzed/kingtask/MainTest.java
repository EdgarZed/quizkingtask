package com.github.edgarzed.kingtask;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class MainTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStreams() {
        outContent.reset();
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    @Test
    public void one() throws Exception {
        String data = "4\n"+
                "-1 -5 1 2\n"+
                "1 2 3 4\n"+
                "5 6 7 8\n"+
                "9 10 11 12\n"+
                "5\n"+
                "1 0 0 0 0\n"+
                "1 1 1 2 1\n"+
                "2 0 0 2\n"+
                "1 0 0 0 0\n"+
                "1 0 0 3 3";
        System.setOut(new PrintStream(outContent));
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        Main.main(null);
        assertEquals("-1\r\n" +
                "5\r\n" +
                "2\r\n" +
                "78\r\n", outContent.toString());
    }

    @Test
    public void two() throws Exception {
        String data = "1\n"+
                "15231109\n"+
                "1\n"+
                "1 0 0 0 0";
        System.setOut(new PrintStream(outContent));
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        Main.main(null);
        assertEquals("15231109\r\n", outContent.toString());
    }

    @Test
    public void three() throws Exception {
        String data = "1\n"+
                "20557091\n"+
                "0";
        System.setOut(new PrintStream(outContent));
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        Main.main(null);
        assertEquals("", outContent.toString());
    }
    /*колонка строка колонка строка*/
    @Test
    public void five() throws Exception {

        String data = "10\n"+
                "78486369 17537186 57650348 28324668 51084752 78720711 51996688 96354627 19887051 80652634\n"+
                "69251957 40090451 81009237 68132548 7818300 75359382 77227543 45150221 51646326 29795926\n"+
                "35860883 48824612 42645373 38735520 44930777 45751086 53349960 89513390 98375656 99066540\n"+
                "70850339 69289751 84743031 33620968 46396351 76471009 65678885 90759449 76095884 58503401\n"+
                "9343826 83920863 98370349 99987664 1268112 74954816 86674138 7214100 49804530 49848584\n"+
                "56350178 3825358 85007293 59777267 15576327 86035553 72298631 40408801 29151172 19129736\n"+
                "10018629 31768773 97571364 28887468 38567080 7069062 94807229 78000980 46459511 48981721\n"+
                "42008992 92744829 92342438 55978141 40183130 14808297 64242133 62801812 2233341 24183229\n"+
                "4521901 50088220 18428176 56115485 11710506 15588941 53027046 61982251 48388066 71785917\n"+
                "71224568 5091005 80099419 30425221 34421792 45963494 94163803 31893889 61648461 81992468\n"+
                "24\n"+
                "1 2 2 3 5\n" +
                "1 0 0 1 1\n" +
                "1 1 4 1 4\n" +
                "2 2 3 82590584\n" +
                "2 9 4 25119692\n" +
                "2 0 1 77030246\n" +
                "1 0 4 3 4\n" +
                "1 0 0 1 4\n" +
                "1 4 1 5 3\n" +
                "2 4 9 58733557\n" +
                "1 3 0 5 2\n" +
                "1 1 0 5 3\n" +
                "1 0 1 4 5\n" +
                "1 0 1 3 1\n" +
                "1 1 0 2 4\n" +
                "2 9 5 71630187\n" +
                "2 4 6 28053189\n" +
                "1 0 1 0 1\n" +
                "1 1 0 1 0\n" +
                "1 0 0 0 0\n" +
                "2 9 7 54403828\n" +
                "2 2 4 19694324\n" +
                "2 4 7 93410923\n" +
                "2 2 2 23241763\n";
        System.setOut(new PrintStream(outContent));
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        Main.main(null);
        assertEquals("542887465\r\n" +
                "205365963\r\n" +
                "83920863\r\n" +
                "291622702\r\n" +
                "531234526\r\n" +
                "296726905\r\n" +
                "438857744\r\n" +
                "1034983614\r\n" +
                "1301253177\r\n" +
                "266262482\r\n" +
                "621928754\r\n" +
                "77030246\r\n" +
                "17537186\r\n" +
                "78486369\r\n", outContent.toString());
    }
}
