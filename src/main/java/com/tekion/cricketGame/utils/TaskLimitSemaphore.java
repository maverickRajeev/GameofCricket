package com.tekion.cricketGame.utils;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;

public class TaskLimitSemaphore {

    private final ExecutorService executorService;
    private final Semaphore semaphore;

    public TaskLimitSemaphore(ExecutorService executorService , int limit){
        this.executorService = executorService;
        this.semaphore = new Semaphore(limit);
    }

    public <T> Future<T> submit(final Callable<T> task) throws InterruptedException {
        this.semaphore.acquire();
        return executorService.submit(() -> {
            try {
                return task.call();
            } finally {
                this.semaphore.release();
            }
        });
    }

}
