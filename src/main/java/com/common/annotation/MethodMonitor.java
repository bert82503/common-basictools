package com.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 方法监控 注解。
 *
 * @author xingle
 * @see org.springframework.web.bind.annotation.Mapping
 * @since 2016年04月05日 21:47
 */
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MethodMonitor {
    // 标记注解
}
