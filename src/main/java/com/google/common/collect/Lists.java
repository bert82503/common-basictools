package com.google.common.collect;

import static com.google.common.collect.CollectPreconditions.checkNonnegative;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Static utility methods pertaining to {@link List} instances. Also see this
 * class's counterparts {@link Sets}, {@link Maps} and {@link Queues}.
 *
 * <p>See the Guava User Guide article on <a href=
 * "https://github.com/google/guava/wiki/CollectionUtilitiesExplained#lists">
 * {@code Lists}</a>.
 *
 * @since 2019-08-11
 */
public final class Lists {
  private Lists() {
    super();
  }

  // [Effective Java-第1条] 使用静态工厂方法创建参数化类型实例，类型推导(type inference)

  // ArrayList

  /**
   * Creates a <i>mutable</i>, empty {@code ArrayList} instance (for Java 6 and
   * earlier).
   *
   * <p><b>Note:</b> if mutability is not required, use {@link
   * ImmutableList#of()} instead.
   *
   * <p><b>Note for Java 7 and later:</b> this method is now unnecessary and
   * should be treated as deprecated. Instead, use the {@code ArrayList}
   * {@linkplain ArrayList#ArrayList() constructor} directly, taking advantage
   * of the new <a href="http://goo.gl/iz2Wi">"diamond" syntax</a>.
   */
  public static <E> ArrayList<E> newArrayList() {
    return new ArrayList<>(10);
  }

  /**
   * Creates an {@code ArrayList} instance backed by an array with the specified
   * initial size; simply delegates to {@link ArrayList#ArrayList(int)}.
   *
   * <p><b>Note for Java 7 and later:</b> this method is now unnecessary and
   * should be treated as deprecated. Instead, use {@code new }{@link
   * ArrayList#ArrayList(int) ArrayList}{@code <>(int)} directly, taking
   * advantage of the new <a href="http://goo.gl/iz2Wi">"diamond" syntax</a>.
   * (Unlike here, there is no risk of overload ambiguity, since the {@code
   * ArrayList} constructors very wisely did not accept varargs.)
   *
   * @param initialArraySize the exact size of the initial backing array for
   *                         the returned array list ({@code ArrayList} documentation calls this
   *                         value the "capacity")
   * @return a new, empty {@code ArrayList} which is guaranteed not to resize
   * itself unless its size reaches {@code initialArraySize + 1}
   * @throws IllegalArgumentException if {@code initialArraySize} is negative
   */
  public static <E> ArrayList<E> newArrayListWithCapacity(int initialArraySize) {
    initialArraySize = checkNonnegative(initialArraySize, "initialArraySize");
    return new ArrayList<>(initialArraySize);
  }

  // LinkedList

  /**
   * Creates a <i>mutable</i>, empty {@code LinkedList} instance (for Java 6 and
   * earlier).
   *
   * <p><b>Note:</b> if you won't be adding any elements to the list, use {@link
   * ImmutableList#of()} instead.
   *
   * <p><b>Performance note:</b> {@link ArrayList} and {@link
   * java.util.ArrayDeque} consistently outperform {@code LinkedList} except in
   * certain rare and specific situations. Unless you have spent a lot of time
   * benchmarking your specific needs, use one of those instead.
   *
   * <p><b>Note for Java 7 and later:</b> this method is now unnecessary and
   * should be treated as deprecated. Instead, use the {@code LinkedList}
   * {@linkplain LinkedList#LinkedList() constructor} directly, taking advantage
   * of the new <a href="http://goo.gl/iz2Wi">"diamond" syntax</a>.
   */
  public static <E> LinkedList<E> newLinkedList() {
    return new LinkedList<>();
  }

  // CopyOnWriteArrayList

  /**
   * Creates an empty {@code CopyOnWriteArrayList} instance.
   *
   * <p><b>Note:</b> if you need an immutable empty {@link List}, use
   * {@link Collections#emptyList} instead.
   *
   * @return a new, empty {@code CopyOnWriteArrayList}
   * @since 12.0
   */
  public static <E> CopyOnWriteArrayList<E> newCopyOnWriteArrayList() {
    return new CopyOnWriteArrayList<>();
  }
}
