package com.squareup.lib;

import com.squareup.lib.utils.IProguard;

import java.lang.ref.WeakReference;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2017/05/25 0025.
 */

public class ThreadManager implements IProguard.ProtectClassAndMembers {
    static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);


    public static void execute(Runnable runnable) {
        fixedThreadPool.execute(runnable);
    }

    public static void submit(final Runnable runnable) {
        WeakReference<Runnable> s = new WeakReference<Runnable>(runnable);
        fixedThreadPool.submit(s.get());
    }
}
