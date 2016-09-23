package com.common.aspect;

import com.common.annotation.FailQpsRtStats;
import com.common.annotation.FailStats;
import com.common.annotation.QpsRtStats;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * 性能监控切面。
 *
 * @author xingle
 * @since 2016年04月05日 18:43
 */
// 2. Declaring an aspect
@Aspect
public class PerformanceMonitorAspect {

//    /**
//     * Sentry 监控器
//     */
//    @Setter
//    private SentryMonitor sentryMonitor;

    // 3. Declaring a pointcut
//    @Pointcut("@annotation(com.common.annotation.QpsRtStats)")
//    private void statsQpsRtPointcut() {
//        // the pointcut signature
//    }

    // 4. Declaring advice
    @Around(value = "@annotation(qpsRtStats)")
    public Object statsQpsRtAdvice(ProceedingJoinPoint pjp, QpsRtStats qpsRtStats)
            throws Throwable {
        long startTime = System.nanoTime();

        try {
            return pjp.proceed(); // 继续进行下一个通知或目标方法调用
        } finally {
            long estimatedTime = System.nanoTime() - startTime;
            String metric = qpsRtStats.metric();
            if (qpsRtStats.isMonitorEnable()) {
//                sentryMonitor.putAvg(metric, qpsRtStats.rtTags(), estimatedTime);
//                sentryMonitor.putSum(metric, qpsRtStats.qpsTags(), 1);
            }
        }
    }

    @Around(value = "@annotation(failStats)")
    public Object statsFailAdvice(ProceedingJoinPoint pjp, FailStats failStats)
            throws Throwable {
        Object retValue = pjp.proceed();
        if (failStats.expectedReturnValue().equals(String.valueOf(retValue))) {
            if (failStats.isMonitorEnable()) {
//                sentryMonitor.putSum(failStats.metric(), failStats.tags(), 1);
            }
        }
        return retValue;
    }

    @Around(value = "@annotation(failQpsRtStats)")
    public Object statsFailQpsRtAdvice(ProceedingJoinPoint pjp, FailQpsRtStats failQpsRtStats)
            throws Throwable {
        long startTime = System.nanoTime();

        try {
            Object retValue = pjp.proceed();
            if (failQpsRtStats.expectedReturnValue().equals(String.valueOf(retValue))) {
                if (failQpsRtStats.isMonitorEnable()) {
//                    sentryMonitor.putSum(failQpsRtStats.metric(), failQpsRtStats.failTags(), 1);
                }
            }
            return retValue;
        } finally {
            long estimatedTime = System.nanoTime() - startTime;
            if (failQpsRtStats.isMonitorEnable()) {
                String metric = failQpsRtStats.metric();
//                sentryMonitor.putAvg(metric, failQpsRtStats.rtTags(), estimatedTime);
//                sentryMonitor.putSum(metric, failQpsRtStats.qpsTags(), 1);
            }
        }
    }

}
