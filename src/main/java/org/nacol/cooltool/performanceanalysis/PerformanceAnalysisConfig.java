package org.nacol.cooltool.performanceanalysis;

import cn.hutool.core.date.StopWatch;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @Author Nacol(姚秋实)
 * @Date 2022/10/27
 * @Description
 *
 * 适用于性能分析的简单场景：
 * 1. 支持：多线程并发下执行；
 * 2. 支持：方法嵌套，每个层级的 StopWatch 互不干扰；
 * 3. 暂不支持：内部方法嵌套（原理：AOP代理失效，第一次调用经过代理，然而内部方法调用是被代理类的直接调用，没有走代理方法）
 */
@Log4j2
@Aspect
@Component
public class PerformanceAnalysisConfig {

    static ConcurrentHashMap<String, StopWatch> watchPool = new ConcurrentHashMap<>(16);

    @Pointcut("@annotation(org.nacol.cooltool.performanceanalysis.PerformanceAnalysis)")
    public void pointCut(){}

    @Around("pointCut()")
    public Object Around(ProceedingJoinPoint point) throws Throwable {
        // 获取注解和注解的值
        PerformanceAnalysis annotation = getPerformanceAnalysis(point);
        // 如果为空则不做处理
        if (annotation == null) {
            return point.proceed();
        }

        // STEP 开始：初始化 StopWatch
        start(annotation);
        // STEP 执行方法
        Object proceed = point.proceed();
        // STEP 结束：打印、关闭 StopWatch
        stop(annotation);

        return proceed;
    }

    /**
     * @Author Nacol(姚秋实)
     * @Date 2022/10/27
     * @Description 获取注解
     */
    private PerformanceAnalysis getPerformanceAnalysis(ProceedingJoinPoint point) {
        Signature signature = point.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method != null) {
            return method.getAnnotation(PerformanceAnalysis.class);
        }
        return null;
    }

    /**
     * @Author Nacol(姚秋实)
     * @Date 2022/10/27
     * @Description 获取当前线程的 key，规则：流程名称 + 线程名称
     */
    private String getKey(PerformanceAnalysis annotation) {
        StringBuilder sb = new StringBuilder();
        sb.append(annotation.processName()).append(Thread.currentThread().getName());
        return sb.toString();
    }

    /**
     * @Author Nacol(姚秋实)
     * @Date 2022/10/27
     * @Description 开始计时
     */
    private void start(PerformanceAnalysis annotation) {
        // 获取 StopWatch
        StopWatch stopWatch = getStopWatch(annotation);
        // 开始计时
        stopWatch.start(annotation.taskName());
    }

    /**
     * @Author Nacol(姚秋实)
     * @Date 2022/10/27
     * @Description 结束：打印和清理
     */
    private void stop(PerformanceAnalysis annotation) {
        String curKey = getKey(annotation);
        StopWatch stopWatch = watchPool.get(curKey);
        stopWatch.stop();
        if (annotation.endFlag()) {
            // TODO 需要抽象 1.此处提供接口和默认的实现; 2.可对接数据存储底层，使用 ES、Mongo 等存储。
            System.out.println(stopWatch.prettyPrint(TimeUnit.MILLISECONDS));
            watchPool.remove(curKey);
        }
    }


    /**
     * @Author Nacol(姚秋实)
     * @Date 2022/10/27
     * @Description 获取 StopWatch
     */
    private StopWatch getStopWatch(PerformanceAnalysis annotation) {
        StopWatch watch;
        // 获取当前线程的
        String curKey = getKey(annotation);
        if (annotation.startFlag()) {
            watch = new StopWatch(annotation.processName() + " | " + Thread.currentThread().getName());
            watchPool.put(curKey, watch);
        } else {
            watch = watchPool.get(curKey);
        }
        return watch;
    }

}
