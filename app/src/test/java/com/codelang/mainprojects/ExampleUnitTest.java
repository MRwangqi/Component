package com.codelang.mainprojects;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);

        String url = "DDComp://share/shareBook";
        if (!url.contains("://") &&
                (!url.startsWith("tel:") || !url.startsWith("smsto:") || !url.startsWith("file:"))) {

            url = "http://" + url;
        }

        System.out.print(url);
    }
}