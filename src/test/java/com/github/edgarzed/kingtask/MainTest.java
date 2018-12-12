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
    private final String data = "4\n" +
            "-1 -5 1 2\n" +
            "1 2 3 4\n" +
            "5 6 7 8\n" +
            "9 10 11 12\n" +
            "6\n" +
            "1 0 0 0 0\n" +
            "1 1 1 2 1\n" +
            "2 0 0 2\n" +
            "1 0 0 0 0\n" +
            "1 0 2 0 3\n" +
            "1 0 0 3 3\n";

    /*private final String data = "3\n" +
            "0 1 4\n" +
            "2 3 2\n" +
            "1 2 7\n" +
            "11\n" +
            "1 1 1 2 2\n"+
            "2 2 2 0\n"+
            "2 0 0 0\n"+
            "2 1 0 0\n"+
            "2 2 0 0\n"+
            "2 0 1 0\n"+
            "2 0 2 0\n"+
            "1 1 1 2 1\n"+
            "1 1 1 1 2\n"+
            "2 2 2 7\n"+
            "1 1 1 2 2\n";*/

    private final ByteArrayInputStream inContent = new ByteArrayInputStream(data.getBytes());
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setIn(inContent);
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    @Test
    public void shouldAnswerWithTrue() throws Exception {
        Main.main(null);
        assertEquals("-1\r\n5\r\n2\r\n14\r\n78\r\n", outContent.toString());
//        assertEquals("14\r\n5\r\n5\r\n14\r\n", outContent.toString());
    }
}
