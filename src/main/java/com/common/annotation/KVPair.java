package com.common.annotation;

/**
 * 指标度量维度配置（{@literal <key, value>}）。
 *
 * @author xingle
 * @since 2016年04月06日 14:54
 */
public @interface KVPair {

    /**
     * 定义指标度量维度的键。
     *
     * @return 指标度量维度的键
     */
    String key();

    /**
     * 定义指标度量维度的值。
     *
     * @return 指标度量维度的值
     */
    String value();

}
