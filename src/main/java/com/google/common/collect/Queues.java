package com.google.common.collect;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * Static utility methods pertaining to {@link Queue} and {@link Deque} instances.
 * Also see this class's counterparts {@link Lists}, {@link Sets}, and {@link Maps}.
 *
 * @since 2019-08-11
 */
public final class Queues {
  private Queues() {
    super();
  }

  // [Effective Java-第1条] 使用静态工厂方法创建参数化类型实例，类型推导(type inference)

  // SynchronousQueue

  /**
   * Creates an empty {@code SynchronousQueue} with nonfair access policy.
   */
  public static <E> SynchronousQueue<E> newSynchronousQueue() {
    return new SynchronousQueue<>();
  }

  // ArrayBlockingQueue

  /**
   * Creates an empty {@code ArrayBlockingQueue} with the given (fixed) capacity
   * and nonfair access policy.
   */
  public static <E> ArrayBlockingQueue<E> newArrayBlockingQueue(int capacity) {
    return new ArrayBlockingQueue<>(capacity);
  }

  // ArrayDeque

  /**
   * Creates an empty {@code ArrayDeque}.
   *
   * @since 12.0
   */
  public static <E> ArrayDeque<E> newArrayDeque() {
    return new ArrayDeque<>(16);
  }

  /**
   * Creates an empty {@code ArrayDeque}.
   *
   * @since 12.0
   */
  public static <E> ArrayDeque<E> newArrayDeque(int numElements) {
    return new ArrayDeque<>(numElements);
  }

  // ConcurrentLinkedQueue

  /**
   * Creates an empty {@code ConcurrentLinkedQueue}.
   */
  public static <E> ConcurrentLinkedQueue<E> newConcurrentLinkedQueue() {
    return new ConcurrentLinkedQueue<>();
  }

  // LinkedBlockingDeque

  /**
   * Creates an empty {@code LinkedBlockingDeque} with the given (fixed) capacity.
   *
   * @throws IllegalArgumentException if {@code capacity} is less than 1
   * @since 12.0
   */
  public static <E> LinkedBlockingDeque<E> newLinkedBlockingDeque(int capacity) {
    return new LinkedBlockingDeque<>(capacity);
  }

  // LinkedBlockingQueue

  /**
   * Creates an empty {@code LinkedBlockingQueue} with the given (fixed) capacity.
   *
   * @throws IllegalArgumentException if {@code capacity} is less than 1
   */
  public static <E> LinkedBlockingQueue<E> newLinkedBlockingQueue(int capacity) {
    return new LinkedBlockingQueue<>(capacity);
  }

  // LinkedList

  public static <E> LinkedList<E> newLinkedList() {
    return new LinkedList<>();
  }

  // PriorityBlockingQueue

  /**
   * Creates an empty {@code PriorityBlockingQueue} with the ordering given by its
   * elements' natural ordering.
   *
   * @since 11.0 (requires that {@code E} be {@code Comparable} since 15.0).
   */
  public static <E extends Comparable<E>> PriorityBlockingQueue<E> newPriorityBlockingQueue(
      int initialCapacity) {
    return new PriorityBlockingQueue<>(initialCapacity, Comparator.naturalOrder());
  }

  public static <E extends Comparable<E>> PriorityBlockingQueue<E> newPriorityBlockingQueue(
      int initialCapacity, Comparator<? super E> comparator) {
    return new PriorityBlockingQueue<>(initialCapacity, comparator);
  }

  // PriorityQueue

  /**
   * Creates an empty {@code PriorityQueue} with the ordering given by its
   * elements' natural ordering.
   *
   * @since 11.0 (requires that {@code E} be {@code Comparable} since 15.0).
   */
  public static <E extends Comparable<E>> PriorityQueue<E> newPriorityQueue(
      int initialCapacity) {
    return new PriorityQueue<>(initialCapacity, Comparator.naturalOrder());
  }

  public static <E extends Comparable<E>> PriorityQueue<E> newPriorityQueue(
      int initialCapacity, Comparator<? super E> comparator) {
    return new PriorityQueue<>(initialCapacity, comparator);
  }
}
