package com.ylw.viewdemo;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void calendar_test() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        System.out.println(format.format(calendar.getTime()));
        calendar.setTime(new Date(curMillis()));
        System.out.println(format.format(calendar.getTime()));
        calendar.set(Calendar.YEAR, 2001);
        System.out.println(format.format(calendar.getTime()));
        calendar.set(Calendar.MONTH, 3);
        System.out.println(format.format(calendar.getTime()));
        System.out.println();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        System.out.println(format.format(calendar.getTime()));
//        calendar.set(Calendar.DAY_OF_WEEK, 36);
//        System.out.println(format.format(calendar.getTime()));
        calendar.set(Calendar.DAY_OF_WEEK_IN_MONTH, 13);
        System.out.println(format.format(calendar.getTime()));
        calendar.set(Calendar.DAY_OF_YEAR, 13);
        System.out.println(format.format(calendar.getTime()));
        System.out.println();
        calendar.add(Calendar.YEAR, 1);
        System.out.println(format.format(calendar.getTime()));

    }

    private long curMillis() {
        return System.currentTimeMillis();
    }
}