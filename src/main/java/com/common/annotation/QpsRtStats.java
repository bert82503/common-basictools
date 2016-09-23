package com.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 方法每秒查询率（QPS）、响应时间（RT）的统计监控 注解。
 *
 * @author xingle
 * @see org.springframework.web.bind.annotation.RequestMapping
 * @see org.springframework.web.bind.annotation.RestController
 * @since 2016年04月05日 22:00
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@MethodMonitor
public @interface QpsRtStats {

    /**
     * 定义监控方法的签名。
     * <p>
     * 格式：${类名}.${方法名}()
     *
     * @return 监控方法的签名
     */
    String methodSignature() default "";

    /**
     * 定义是否记录到本地日志文件，默认关闭。
     *
     * @return {@code true}：记录到本地日志文件；{@code false}：不记录到本地日志文件
     */
    boolean isLoggerEnable() default false;

    /**
     * 定义是否记录到监控平台，默认开启。
     *
     * @return {@code true}：记录到监控平台；{@code false}：不记录到监控平台
     */
    boolean isMonitorEnable() default true;

    /**
     * 定义指标度量。
     *
     * @return 指标度量
     */
    String metric() default "";

    /**
     * 定义指标度量维度列表。
     *
     * @return 指标度量维度列表
     */
    KVPair[] rtTags() default {};

    /**
     * 定义 QPS 的指标度量维度列表。
     *
     * @return QPS 的指标度量维度列表
     */
    KVPair[] qpsTags() default {};

}
