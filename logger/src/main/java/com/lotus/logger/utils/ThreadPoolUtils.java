package com.lotus.logger.utils;

import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import java.util.concurrent.*;

/**
 * @program: logger
 * @description: 线程池工具
 * @author: changjiz
 * @create: `2019-11-18 10:13
 **/
public class ThreadPoolUtils {

    public static ExecutorService getExecutor() {
        return LazyThreadPool.EXECUTOR;
    }

    public static void execute(Runnable command) {
        getExecutor().execute(command);
    }

    public static int getQueueSize() {
        return LazyThreadPool.getQueueSize();
    }

    // 静态内部类
    private static class LazyThreadPool {
        private final static String THREAD_NAME_PREFIX = "sys-log-pool-";
        private final static int CORE_POOL_SIZE = 2;
        private final static int MAXIMUM_POOL_SIZE = 5;
        private final static int KEEP_ALIVE_TIME = 5;
        private final static TimeUnit UNIT = TimeUnit.SECONDS;
        private final static int QUEUE_SIZE = 100;
        private final static ArrayBlockingQueue ARRAY_BLOCKING_QUEUE = new ArrayBlockingQueue<>(QUEUE_SIZE);
        private final static ThreadFactory DEFAULT_THREAD_FACTORY = new CustomizableThreadFactory(THREAD_NAME_PREFIX);
        private final static RejectedExecutionHandler HANDLER = new ThreadPoolExecutor.CallerRunsPolicy();

        private static final ThreadPoolExecutor EXECUTOR = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_TIME, UNIT, ARRAY_BLOCKING_QUEUE, DEFAULT_THREAD_FACTORY, HANDLER);

        private static int getQueueSize() {
            return EXECUTOR.getQueue().size();
        }
    }
}
