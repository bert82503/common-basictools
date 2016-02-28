package com.common.util;

import java.util.Comparator;

/**
 * 比较器的工厂和工具方法，用于排序。
 *
 * @author xingle
 * @since 1.0
 */
public abstract class Comparators {

    /**
     * 返回“整数”的升序比较器。
     *
     * @return “整数”的升序比较器
     */
    public static Comparator<Integer> integerAscendComparator() {
        return IntegerAscendComparator.INSTANCE;
    }

    // enum singleton pattern (枚举单实例模式)
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
