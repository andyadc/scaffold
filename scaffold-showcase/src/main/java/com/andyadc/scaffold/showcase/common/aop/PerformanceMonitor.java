package com.andyadc.scaffold.showcase.common.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 性能切面
 *
 * @author andaicheng
 */
public class PerformanceMonitor {

    private static final Logger LOG = LoggerFactory.getLogger(PerformanceMonitor.class);

    public Object doAround(ProceedingJoinPoint point) throws Throwable {
        long start = System.nanoTime();
        Object ret = point.proceed();

        LOG.info("{}: {}ms", point.getTarget().getClass() + "." + point.getSignature().getName(),
                (System.nanoTime() - start) / 1000000);
        return ret;
    }
}
