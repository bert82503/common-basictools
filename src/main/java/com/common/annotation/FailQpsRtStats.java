package com.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 方法每秒查询率（QPS）、调用失败次数、响应时间（RT）的统计监控 注解。
 *
 * @author xingle
 * @since 2016年04月08日 09:37
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@MethodMonitor
public @interface FailQpsRtStats {

    /**
     * 定义监控方法的签名。
     * <p/>
     * 格式：${类名}.${方法名}()
     *
     * @return 监控方法的签名
     */
    String methodSignature() default "";

    /**
     * 定义方法调用失败的期望返回值。
     *
     * @return 方法调用失败的期望返回值
     * @see #failTags()
     */
    String expectedReturnValue();

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
     * 定义方法调用失败次数的指标度量维度列表。
     *
     * @return 方法调用失败次数的指标度量维度列表
     * @see #expectedReturnValue()
     */
    KVPair[] failTags() default {};

    /**
     * 定义 QPS 的指标度量维度列表。
     *
     * @return QPS 的指标度量维度列表
     */
    KVPair[] qpsTags() default {};

    /**
     * 定义响应时间（RT）的指标度量维度列表。
     *
     * @return 响应时间的指标度量维度列表
     */
    KVPair[] rtTags() default {};

}
