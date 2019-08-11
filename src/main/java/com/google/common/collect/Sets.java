package com.google.common.collect;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Static utility methods pertaining to {@link Set} instances. Also see this
 * class's counterparts {@link Lists}, {@link Maps} and {@link Queues}.
 *
 * <p>See the Guava User Guide article on <a href=
 * "https://github.com/google/guava/wiki/CollectionUtilitiesExplained#sets">
 * {@code Sets}</a>.
 *
 * @since 2019-08-11
 */
public final class Sets {
  private Sets() {
    super();
  }

  // [Effective Java-第1条] 使用静态工厂方法创建参数化类型实例，类型推导(type inference)

  // EnumSet

  /**
   * Returns a new, <i>mutable</i> {@code EnumSet} instance containing the given elements in their
   * natural order. This method behaves identically to {@link EnumSet#copyOf(Collection)}, but also
   * accepts non-{@code Collection} iterables and empty iterables.
   */
  public static <E extends Enum<E>> EnumSet<E> newEnumSet(Iterable<E> iterable, Class<E> elementType) {
    EnumSet<E> set = EnumSet.noneOf(elementType);
    Iterables.addAll(set, iterable);
    return set;
  }

  // HashSet

  /**
   * Creates a <i>mutable</i>, initially empty {@code HashSet} instance.
   *
   * <p><b>Note:</b> if mutability is not required, use {@link ImmutableSet#of()} instead. If
   * {@code E} is an {@link Enum} type, use {@link EnumSet#noneOf} instead. Otherwise, strongly
   * consider using a {@code LinkedHashSet} instead, at the cost of increased memory footprint, to
   * get deterministic iteration behavior.
   *
   * <p><b>Note for Java 7 and later:</b> this method is now unnecessary and should be treated as
   * deprecated. Instead, use the {@code HashSet} constructor directly, taking advantage of the new
   * <a href="http://goo.gl/iz2Wi">"diamond" syntax</a>.
   */
  public static <E> HashSet<E> newHashSet() {
    return new HashSet<>(16);
  }

  /**
   * Creates a {@code HashSet} instance, with a high enough initial table size that it <i>should</i>
   * hold {@code expectedSize} elements without resizing. This behavior cannot be broadly
   * guaranteed, but it is observed to be true for OpenJDK 1.7. It also can't be guaranteed that the
   * method isn't inadvertently <i>oversizing</i> the returned set.
   *
   * @param expectedSize the number of elements you expect to add to the
   *        returned set
   * @return a new, empty {@code HashSet} with enough capacity to hold {@code
   *         expectedSize} elements without resizing
   * @throws IllegalArgumentException if {@code expectedSize} is negative
   */
  public static <E> HashSet<E> newHashSetWithExpectedSize(int expectedSize) {
    return new HashSet<>(Maps.capacity(expectedSize));
  }

  // ConcurrentHashSet

  /**
   * Creates a thread-safe set backed by a hash map. The set is backed by a
   * {@link ConcurrentHashMap} instance, and thus carries the same concurrency
   * guarantees.
   *
   * <p>Unlike {@code HashSet}, this class does NOT allow {@code null} to be
   * used as an element. The set is serializable.
   *
   * @return a new, empty thread-safe {@code Set}
   * @since 15.0
   */
  public static <E> Set<E> newConcurrentHashSet() {
    return newSetFromMap(new ConcurrentHashMap<>(16));
  }

  private static <E> Set<E> newSetFromMap(Map<E, Boolean> map) {
    return Platform.newSetFromMap(map);
  }

  // LinkedHashSet

  /**
   * Creates a <i>mutable</i>, empty {@code LinkedHashSet} instance.
   *
   * <p><b>Note:</b> if mutability is not required, use {@link
   * ImmutableSet#of()} instead.
   *
   * @return a new, empty {@code LinkedHashSet}
   */
  public static <E> LinkedHashSet<E> newLinkedHashSet() {
    return new LinkedHashSet<>(16);
  }

  /**
   * Creates a {@code LinkedHashSet} instance, with a high enough "initial
   * capacity" that it <i>should</i> hold {@code expectedSize} elements without
   * growth. This behavior cannot be broadly guaranteed, but it is observed to
   * be true for OpenJDK 1.6. It also can't be guaranteed that the method isn't
   * inadvertently <i>oversizing</i> the returned set.
   *
   * @param expectedSize the number of elements you expect to add to the
   *        returned set
   * @return a new, empty {@code LinkedHashSet} with enough capacity to hold
   *         {@code expectedSize} elements without resizing
   * @throws IllegalArgumentException if {@code expectedSize} is negative
   * @since 11.0
   */
  public static <E> LinkedHashSet<E> newLinkedHashSetWithExpectedSize(int expectedSize) {
    return new LinkedHashSet<>(Maps.capacity(expectedSize));
  }

  // TreeSet

  /**
   * Creates a <i>mutable</i>, empty {@code TreeSet} instance sorted by the
   * natural sort ordering of its elements.
   *
   * <p><b>Note:</b> if mutability is not required, use {@link
   * ImmutableSortedSet#of()} instead.
   *
   * @return a new, empty {@code TreeSet}
   */
  public static <E extends Comparable<E>> TreeSet<E> newTreeSet() {
    return new TreeSet<>(Comparator.naturalOrder());
  }

  /**
   * Creates a <i>mutable</i>, empty {@code TreeSet} instance with the given
   * comparator.
   *
   * <p><b>Note:</b> if mutability is not required, use {@code
   * ImmutableSortedSet.orderedBy(comparator).build()} instead.
   *
   * @param comparator the comparator to use to sort the set
   * @return a new, empty {@code TreeSet}
   * @throws NullPointerException if {@code comparator} is null
   */
  public static <E> TreeSet<E> newTreeSet(Comparator<? super E> comparator) {
    return new TreeSet<>(checkNotNull(comparator));
  }

  // IdentityHashSet

  /**
   * Creates an empty {@code Set} that uses identity to determine equality. It
   * compares object references, instead of calling {@code equals}, to
   * determine whether a provided object matches an element in the set. For
   * example, {@code contains} returns {@code false} when passed an object that
   * equals a set member, but isn't the same instance. This behavior is similar
   * to the way {@code IdentityHashMap} handles key lookups.
   *
   * @since 8.0
   */
  public static <E> Set<E> newIdentityHashSet() {
    return newSetFromMap(Maps.newIdentityHashMap());
  }

  // CopyOnWriteArraySet

  /**
   * Creates an empty {@code CopyOnWriteArraySet} instance.
   *
   * <p><b>Note:</b> if you need an immutable empty {@link Set}, use
   * {@link Collections#emptySet} instead.
   *
   * @return a new, empty {@code CopyOnWriteArraySet}
   * @since 12.0
   */
  public static <E> CopyOnWriteArraySet<E> newCopyOnWriteArraySet() {
    return new CopyOnWriteArraySet<>();
  }
}
