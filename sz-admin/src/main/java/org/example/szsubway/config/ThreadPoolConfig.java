package org.example.szsubway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author fengyadong
 * @date 2022/5/25 16:42
 * @Description
 */
@Configuration
public class ThreadPoolConfig {
    @Bean("syncDataTaskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 核心线程数10：线程池创建时候初始化的线程数
        executor.setCorePoolSize(10);
        // 最大线程数20：线程池最大的线程数，只有在缓冲队列满了之后才会申请超过核心线程数的线程
        executor.setMaxPoolSize(20);
        // 缓冲队列200：用来缓冲执行任务的队列
        executor.setQueueCapacity(200);
        //允许线程的空闲时间60秒：当超过了核心线程出之外的线程在空闲时间到达之后会被销毁
        executor.setKeepAliveSeconds(60);
        //异步线程前缀
        executor.setThreadNamePrefix("syncDataTaskExecutor-");
        //直接在 execute 方法的调用线程中运行被拒绝的任务；如果执行程序已关闭，则会丢弃该任务,抛出异常
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        return executor;
    }
}
