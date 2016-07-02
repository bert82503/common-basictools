package com.common.util;

import java.util.Comparator;

/**
 * 比较器的静态工具方法，用于排序。
 *
 * @author xingle
 * @see com.google.common.base.Functions
 * @since 1.0
 */
public final class Comparators {
    private Comparators() {
    }

    /**
     * 返回“整型”的升序比较器。
     *
     * @return “整型”的升序比较器
     */
    public static Comparator<Integer> integerAscendComparator() {
        return IntegerAscendComparator.INSTANCE;
    }

    // enum singleton pattern (枚举单实例模式)
    // 完美的解决方案，无法通过反序列化和反射的方式实例化
    // [JLS] 枚举类型不能使用泛型类型参数
    private enum IntegerAscendComparator implements Comparator<Integer> {
        INSTANCE;

        @Override
        public int compare(Integer o1, Integer o2) {
            return o1.compareTo(o2);
        }

        @Override
        public String toString() {
            return "Comparators.integerAscendComparator()";
        }
    }

}
