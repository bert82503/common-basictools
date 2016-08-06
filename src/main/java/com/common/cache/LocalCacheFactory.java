package com.common.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheBuilderSpec;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.CacheStats;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 本地缓存工厂辅助类。
 * <p/>
 * See the Guava User Guide article on <a href="https://github.com/google/guava/wiki/CachesExplained">
 * caching</a> for a higher-level explanation.
 *
 * @author xingle
 * @see com.google.common.cache.CacheBuilder
 * @see com.google.common.cache.CacheLoader
 * @see com.google.common.cache.LoadingCache
 * @since 2016年06月24日 11:36
 */
public final class LocalCacheFactory {

    /**
     * 本地缓存日志记录器
     * <p/>
     * 注意：若 Logger 找不到，则自动会使用 root Logger。
     */
    private static final Logger LOGGER = LoggerFactory.getLogger("localCache");


    /**
     * 共享的异步任务执行服务
     */
    private static final ExecutorService SHARED_EXECUTOR_SERVICE =
            Executors.newFixedThreadPool(
                    3, new ThreadFactoryBuilder().setNameFormat("local-cache-shared-%d").build());

    /**
     * 日志记录的异步任务执行服务
     */
    private static final ScheduledExecutorService LOGGER_SCHEDULED_EXECUTOR_SERVICE =
            Executors.newScheduledThreadPool(
                    1, new ThreadFactoryBuilder().setNameFormat("local-cache-stats-%d").build());


    private LocalCacheFactory() {
    }


    // static factory method pattern (静态工厂方法模式)

    /**
     * 创建一个新的可自动加载异步刷新的缓存实例。
     * <p/>
     * <p>使用场景：非核心业务。核心业务建议使用“单独的任务执行服务”，使用
     * {@link #newLoadingCache(String, String, CacheLoader, ExecutorService)}。
     * <p/>
     * <p><b><font size="7" color="red">
     * 注意：使用“共享的任务执行服务”来异步地重新加载（reload）缓存数据！
     * </font></b>
     * <p/>
     * <p>内部实现：每隔一分钟定期地记录本地缓存的性能统计信息。
     *
     * @param serviceName     使用方的服务名称（用于区分日志）
     * @param spec            缓存构建者配置的规格
     * @param syncCacheLoader 同步执行的缓存加载器
     * @param <K>             键类型
     * @param <V>             值类型
     * @return 一个新的可自动加载异步刷新的缓存实例
     */
    public static <K, V> LoadingCache<K, V> newLoadingCache(
            String serviceName,
            String spec,
            CacheLoader<K, V> syncCacheLoader) {

        return newLoadingCache(
                serviceName,
                spec,
                syncCacheLoader, SHARED_EXECUTOR_SERVICE);
    }

    /**
     * 创建一个新的可自动加载异步刷新的缓存实例。
     * <p/>
     * <p>使用场景：核心业务。非核心业务建议使用共享的任务执行服务，使用
     * {@link #newLoadingCache(String, String, CacheLoader)}。
     * <p/>
     * <p><b><font size="7" color="red">
     * 注意：每次都新创建一个“单独的任务执行服务”来异步地重新加载（reload）缓存数据！
     * </font></b>
     * <p/>
     * <p>内部实现：每隔一分钟定期地记录本地缓存的性能统计信息。
     *
     * @param serviceName     使用方的服务名称（用于区分日志）
     * @param spec            缓存构建者配置的规格
     * @param syncCacheLoader 同步执行的缓存加载器
     * @param executorService 单独的任务执行服务
     * @param <K>             键类型
     * @param <V>             值类型
     * @return 一个新的可自动加载异步刷新的缓存实例
     */
    public static <K, V> LoadingCache<K, V> newLoadingCache(
            String serviceName,
            String spec,
            CacheLoader<K, V> syncCacheLoader, ExecutorService executorService) {

        CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.from(spec);

        return newLoadingCache(
                serviceName,
                cacheBuilder,
                syncCacheLoader, executorService);
    }

    /**
     * 创建一个新的可自动加载异步刷新的缓存实例。
     * <p/>
     * <p>使用场景：非核心业务。核心业务建议使用“单独的任务执行服务”，使用
     * {@link #newLoadingCache(String, CacheBuilderSpec, CacheLoader, ExecutorService)}。
     * <p/>
     * <p><b><font size="7" color="red">
     * 注意：使用“共享的任务执行服务”来异步地重新加载（reload）缓存数据！
     * </font></b>
     * <p/>
     * <p>内部实现：每隔一分钟定期地记录本地缓存的性能统计信息。
     *
     * @param serviceName     使用方的服务名称（用于区分日志）
     * @param spec            缓存构建者配置的规格
     * @param syncCacheLoader 同步执行的缓存加载器
     * @param <K>             键类型
     * @param <V>             值类型
     * @return 一个新的可自动加载异步刷新的缓存实例
     */
    public static <K, V> LoadingCache<K, V> newLoadingCache(
            String serviceName,
            CacheBuilderSpec spec,
            CacheLoader<K, V> syncCacheLoader) {

        return newLoadingCache(
                serviceName,
                spec,
                syncCacheLoader, SHARED_EXECUTOR_SERVICE);
    }

    /**
     * 创建一个新的可自动加载异步刷新的缓存实例。
     * <p/>
     * <p>使用场景：核心业务。非核心业务建议使用共享的任务执行服务，使用
     * {@link #newLoadingCache(String, CacheBuilderSpec, CacheLoader)}。
     * <p/>
     * <p><b><font size="7" color="red">
     * 注意：每次都新创建一个“单独的任务执行服务”来异步地重新加载（reload）缓存数据！
     * </font></b>
     * <p/>
     * <p>内部实现：每隔一分钟定期地记录本地缓存的性能统计信息。
     *
     * @param serviceName     使用方的服务名称（用于区分日志）
     * @param spec            缓存构建者配置的规格
     * @param syncCacheLoader 同步执行的缓存加载器
     * @param executorService 单独的任务执行服务
     * @param <K>             键类型
     * @param <V>             值类型
     * @return 一个新的可自动加载异步刷新的缓存实例
     */
    public static <K, V> LoadingCache<K, V> newLoadingCache(
            String serviceName,
            CacheBuilderSpec spec,
            CacheLoader<K, V> syncCacheLoader, ExecutorService executorService) {

        CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.from(spec);

        return newLoadingCache(
                serviceName,
                cacheBuilder,
                syncCacheLoader, executorService);
    }

    /**
     * 创建一个新的可自动加载异步刷新的缓存实例。
     * <p/>
     * <p>使用场景：非核心业务。核心业务建议使用“单独的任务执行服务”，使用
     * {@link #newLoadingCache(String, CacheBuilder, CacheLoader, ExecutorService)}。
     * <p/>
     * <p><b><font size="7" color="red">
     * 注意：使用“共享的任务执行服务”来异步地重新加载（reload）缓存数据！
     * </font></b>
     * <p/>
     * <p>内部实现：每隔一分钟定期地记录本地缓存的性能统计信息。
     *
     * @param serviceName     使用方的服务名称（用于区分日志）
     * @param cacheBuilder    缓存构建者
     * @param syncCacheLoader 同步执行的缓存加载器
     * @param <K>             键类型
     * @param <V>             值类型
     * @return 一个新的可自动加载异步刷新的缓存实例
     */
    public static <K, V> LoadingCache<K, V> newLoadingCache(
            String serviceName,
            CacheBuilder<Object, Object> cacheBuilder,
            CacheLoader<K, V> syncCacheLoader) {

        return newLoadingCache(
                serviceName,
                cacheBuilder,
                syncCacheLoader, SHARED_EXECUTOR_SERVICE);
    }

    /**
     * 创建一个新的可自动加载异步刷新的缓存实例。
     * <p/>
     * <p>使用场景：核心业务。非核心业务建议使用共享的任务执行服务，使用
     * {@link #newLoadingCache(String, CacheBuilder, CacheLoader)}。
     * <p/>
     * <p><b><font size="7" color="red">
     * 注意：每次都新创建一个“单独的任务执行服务”来异步地重新加载（reload）缓存数据！
     * </font></b>
     * <p/>
     * <p>内部实现：每隔一分钟定期地记录本地缓存的性能统计信息。
     *
     * @param serviceName     使用方的服务名称（用于区分日志）
     * @param cacheBuilder    缓存构建者
     * @param syncCacheLoader 同步执行的缓存加载器
     * @param executorService 单独的任务执行服务
     * @param <K>             键类型
     * @param <V>             值类型
     * @return 一个新的可自动加载异步刷新的缓存实例
     */
    public static <K, V> LoadingCache<K, V> newLoadingCache(
            final String serviceName,
            CacheBuilder<Object, Object> cacheBuilder,
            CacheLoader<K, V> syncCacheLoader, ExecutorService executorService) {

        final LoadingCache<K, V> loadingCache = cacheBuilder
                .recordStats() // 记录缓存性能的累积统计信息
                .build(
                        CacheLoader.asyncReloading(syncCacheLoader, executorService) // 封装成异步 reload
                );

        // 定期地记录本地缓存的统计信息
        LOGGER_SCHEDULED_EXECUTOR_SERVICE.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                CacheStats cacheStats = loadingCache.stats();
                LOGGER.info("{}'s {}", serviceName, cacheStats);
            }
        }, 1, 1, TimeUnit.MINUTES); // 每隔一分钟执行一次

        return loadingCache;
    }

}
