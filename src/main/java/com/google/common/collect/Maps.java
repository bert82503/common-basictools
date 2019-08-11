package com.google.common.collect;

import static com.google.common.collect.CollectPreconditions.checkNonnegative;

import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

import com.google.common.primitives.Ints;

/**
 * Static utility methods pertaining to {@link Map} instances (including instances of
 * {@link SortedMap}, {@link BiMap}, etc.). Also see this class's counterparts
 * {@link Lists}, {@link Sets} and {@link Queues}.
 *
 * <p>See the Guava User Guide article on <a href=
 * "https://github.com/google/guava/wiki/CollectionUtilitiesExplained#maps">
 * {@code Maps}</a>.
 *
 * @since 2019-08-11
 */
public final class Maps {
  private Maps() {
    super();
  }

  // [Effective Java-第1条] 使用静态工厂方法创建参数化类型实例，类型推导(type inference)

  // HashMap

  /**
   * Creates a <i>mutable</i>, empty {@code HashMap} instance.
   *
   * <p><b>Note:</b> if mutability is not required, use {@link
   * ImmutableMap#of()} instead.
   *
   * <p><b>Note:</b> if {@code K} is an {@code enum} type, use {@link
   * #newEnumMap} instead.
   *
   * @return a new, empty {@code HashMap}
   */
  public static <K, V> HashMap<K, V> newHashMap() {
    return new HashMap<>(16);
  }

  /**
   * Creates a {@code HashMap} instance, with a high enough "initial capacity"
   * that it <i>should</i> hold {@code expectedSize} elements without growth.
   * This behavior cannot be broadly guaranteed, but it is observed to be true
   * for OpenJDK 1.7. It also can't be guaranteed that the method isn't
   * inadvertently <i>oversizing</i> the returned map.
   *
   * @param expectedSize the number of entries you expect to add to the
   *        returned map
   * @return a new, empty {@code HashMap} with enough capacity to hold {@code
   *         expectedSize} entries without resizing
   * @throws IllegalArgumentException if {@code expectedSize} is negative
   */
  public static <K, V> HashMap<K, V> newHashMapWithExpectedSize(int expectedSize) {
    return new HashMap<>(capacity(expectedSize));
  }

  /**
   * Returns a capacity that is sufficient to keep the map from being resized as
   * long as it grows no larger than expectedSize and the load factor is >= its
   * default (0.75).
   */
  static int capacity(int expectedSize) {
    if (expectedSize < 3) {
      expectedSize = checkNonnegative(expectedSize, "expectedSize");
      return expectedSize + 1;
    }
    if (expectedSize < Ints.MAX_POWER_OF_TWO) {
      // This is the calculation used in JDK8 to resize when a putAll
      // happens; it seems to be the most conservative calculation we
      // can make.  0.75 is the default load factor.
      return (int) ((float) expectedSize / 0.75F + 1.0F);
    }
    // any large value
    return Integer.MAX_VALUE;
  }

  // LinkedHashMap

  /**
   * Creates a <i>mutable</i>, empty, insertion-ordered {@code LinkedHashMap}
   * instance.
   *
   * <p><b>Note:</b> if mutability is not required, use {@link
   * ImmutableMap#of()} instead.
   *
   * @return a new, empty {@code LinkedHashMap}
   */
  public static <K, V> LinkedHashMap<K, V> newLinkedHashMap() {
    return new LinkedHashMap<>(16);
  }

  /**
   * Creates a {@code LinkedHashMap} instance, with a high enough
   * "initial capacity" that it <i>should</i> hold {@code expectedSize}
   * elements without growth. This behavior cannot be broadly guaranteed, but
   * it is observed to be true for OpenJDK 1.7. It also can't be guaranteed
   * that the method isn't inadvertently <i>oversizing</i> the returned map.
   *
   * @param expectedSize the number of entries you expect to add to the
   *        returned map
   * @return a new, empty {@code LinkedHashMap} with enough capacity to hold
   *         {@code expectedSize} entries without resizing
   * @throws IllegalArgumentException if {@code expectedSize} is negative
   * @since 19.0
   */
  public static <K, V> LinkedHashMap<K, V> newLinkedHashMapWithExpectedSize(int expectedSize) {
    return new LinkedHashMap<>(capacity(expectedSize));
  }

  // ConcurrentHashMap

  /**
   * Returns a general-purpose instance of {@code ConcurrentHashMap}, which supports
   * all optional operations of the ConcurrentMap interface. It does not permit
   * null keys or values. It is serializable.
   *
   * @return a new, empty {@code ConcurrentHashMap}
   */
  public static <K, V> ConcurrentHashMap<K, V> newConcurrentHashMap() {
    return new ConcurrentHashMap<>(16);
  }

  public static <K, V> ConcurrentHashMap<K, V> newConcurrentHashMapWithExpectedSize(int expectedSize) {
    return new ConcurrentHashMap<>(capacity(expectedSize));
  }

  // ConcurrentSkipListMap

  public static <K extends Comparable<K>, V> ConcurrentSkipListMap<K, V> newConcurrentSkipListMap() {
    return new ConcurrentSkipListMap<>(Comparator.naturalOrder());
  }

  public static <K extends Comparable<K>, V> ConcurrentSkipListMap<K, V> newConcurrentSkipListMap(
      Comparator<? super K> comparator) {
    return new ConcurrentSkipListMap<>(comparator);
  }

  // TreeMap

  /**
   * Creates a <i>mutable</i>, empty {@code TreeMap} instance using the natural
   * ordering of its elements.
   *
   * <p><b>Note:</b> if mutability is not required, use {@link
   * ImmutableSortedMap#of()} instead.
   *
   * @return a new, empty {@code TreeMap}
   */
  public static <K extends Comparable<K>, V> TreeMap<K, V> newTreeMap() {
    return new TreeMap<>(Comparator.naturalOrder());
  }

  /**
   * Creates a <i>mutable</i> {@code TreeMap} instance with the same mappings as
   * the specified map and using the same ordering as the specified map.
   *
   * <p><b>Note:</b> if mutability is not required, use {@link
   * ImmutableSortedMap#copyOfSorted(SortedMap)} instead.
   *
   * @param map the sorted map whose mappings are to be placed in the new map
   *        and whose comparator is to be used to sort the new map
   * @return a new {@code TreeMap} initialized with the mappings from {@code
   *         map} and using the comparator of {@code map}
   */
  public static <K, V> TreeMap<K, V> newTreeMap(SortedMap<K, ? extends V> map) {
    return new TreeMap<>(map);
  }

  /**
   * Creates a <i>mutable</i>, empty {@code TreeMap} instance using the given
   * comparator.
   *
   * <p><b>Note:</b> if mutability is not required, use {@code
   * ImmutableSortedMap.orderedBy(comparator).build()} instead.
   *
   * @param comparator the comparator to sort the keys with
   * @return a new, empty {@code TreeMap}
   */
  public static <K extends Comparable<K>, V> TreeMap<K, V> newTreeMap(Comparator<? super K> comparator) {
    return new TreeMap<>(comparator);
  }

  // EnumMap

  /**
   * Creates an {@code EnumMap} instance.
   *
   * @param keyType the key type for this map
   * @return a new, empty {@code EnumMap}
   */
  public static <K extends Enum<K>, V> EnumMap<K, V> newEnumMap(Class<K> keyType) {
    return new EnumMap<>(keyType);
  }

  // IdentityHashMap

  /**
   * Creates an {@code IdentityHashMap} instance.
   *
   * @return a new, empty {@code IdentityHashMap}
   */
  public static <K, V> IdentityHashMap<K, V> newIdentityHashMap() {
    return new IdentityHashMap<>();
  }
}
