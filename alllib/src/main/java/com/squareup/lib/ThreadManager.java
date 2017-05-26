package com.squareup.lib;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2017/05/25 0025.
 */

public class ThreadManager {
    static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);


    public static void post(Runnable runnable) {
        fixedThreadPool.execute(runnable);
    }
}
