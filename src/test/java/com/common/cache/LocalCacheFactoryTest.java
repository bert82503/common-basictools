package com.common.cache;

import com.common.lang.StringUtil;
import com.google.common.base.Joiner;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheBuilderSpec;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.CacheLoader.InvalidCacheLoadException;
import com.google.common.cache.CacheStats;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit test of {@link LocalCacheFactory}.
 * <p/>
 * 经验：
 * <ul>
 * <li>注意：maximumSize 必须要显示设置，防止内存被撑爆！(容错机制)</li>
 * <li>maximumSize、initialCapacity、concurrencyLevel 的值根据具体的业务场景而定</li>
 * <li>refreshAfterWrite 一般以分钟为单位，保证数据能准实时刷新</li>
 * <li>expireAfterWrite 一般以天为单位，数值要比 refreshAfterWrite 大很多，让老数据自动过期失效</li>
 * <li>expireAfterAccess 设置过长的话，频繁被访问的数据会一直有效</li>
 * </ul>
 *
 * Run 'testng.xml'.
 *
 * @author xingle
 * @since 2016年06月24日 16:18
 */
@Test(singleThreaded = false)
public class LocalCacheFactoryTest {

    private static final Logger logger = LoggerFactory.getLogger(LocalCacheFactoryTest.class);


    /**
     * 同步缓存加载器
     */
    private static final CacheLoader<String, Object> syncCacheLoader;

    static { // 静态初始化
        syncCacheLoader = new CacheLoader<String, Object>() {
            @Override
            public Object load(String key) throws Exception {
                if (StringUtil.isEmpty(key)) {
                    return null;
                }

                return "load";
            }

            @Override
            public ListenableFuture<Object> reload(String key, Object oldValue)
                    throws Exception {
                if (StringUtil.isEmpty(key)) {
                    return null;
                }

//                return super.reload(key, oldValue);
                Object newValue = super.reload(key, oldValue).get(10, TimeUnit.MILLISECONDS);
                if (newValue == null) { // 请求新的数据失败，则返回旧的值
                    return Futures.immediateFuture(oldValue);
                } else {
                    return Futures.immediateFuture((Object) "reload");
                }
            }
        };
    }


    @Test(description = "get('') 空字符串会抛出无效缓存加载异常",
            expectedExceptions = InvalidCacheLoadException.class, expectedExceptionsMessageRegExp = "CacheLoader returned null for key .")
    public void getEmptyKey() {
        /**
         * 默认值
         * @see com.google.common.cache.CacheBuilder
         */
        String spec = Joiner.on(',').join(Arrays.asList(
                "initialCapacity=16", // 初始容量
                "concurrencyLevel=4", // 并发级别
                "expireAfterWrite=0m", // 写入过期时间(立刻失效)
                // 经验：expireAfterAccess 设置过长的话，频繁被访问的数据会一直有效
                "expireAfterAccess=0h" // 访问过期时间(立刻失效)
        ));
        LoadingCache<String, Object> loadingCache = LocalCacheFactory
                .newLoadingCache("getEmptyKey", spec, syncCacheLoader);
        // test
        loadingCache.getUnchecked("");
    }

    @Test(description = "定期同步刷新则调用 reload，过期失效而重新加载则调用 load")
    public void newLoadingCache_String() throws ExecutionException {
        /**
         * 配置的具体数值根据业务特性来决定
         * @see com.google.common.cache.CacheBuilder
         */
        String spec = Joiner.on(',').join(Arrays.asList(
                // 经验：maximumSize 必须要显示设置，防止内存被撑爆！(容错机制)
                // 经验：maximumSize、initialCapacity、concurrencyLevel 的值根据具体的业务场景而定
                "maximumSize=256", // 容量最大大小
                "initialCapacity=16", // 初始容量
                "concurrencyLevel=16", // 并发级别
                // 经验：refreshAfterWrite 一般以分钟为单位，保证数据能准实时刷新
//                "refreshAfterWrite=1m", // 刷新周期(有效期)
                "refreshAfterWrite=3s", // 测试专用
                // 经验：expireAfterWrite 一般以天为单位，数值要比 refreshAfterWrite 大很多，让老数据自动过期失效
//                "expireAfterWrite=1d" // 写入过期时间
                "expireAfterWrite=4s" // 测试专用
        ));
        // 非核心业务，使用共享的任务执行服务
        LoadingCache<String, Object> loadingCache = LocalCacheFactory
                .newLoadingCache("newLoadingCache_String", spec, syncCacheLoader);
        internalTest(loadingCache);
    }

    /**
     * 异步任务执行服务
     */
    private static final ExecutorService executorService =
            Executors.newFixedThreadPool(
                    2, new ThreadFactoryBuilder().setNameFormat("local-cache-test-%d").build());

    @Test(description = "定期同步刷新则调用 reload，过期失效而重新加载则调用 load")
    public void newLoadingCache_String_ExecutorService() throws ExecutionException {
        /**
         * 配置的具体数值根据业务特性来决定
         * @see com.google.common.cache.CacheBuilder
         */
        String spec = Joiner.on(',').join(Arrays.asList(
                // 经验：maximumSize 必须要显示设置，防止内存被撑爆！(容错机制)
                // 经验：maximumSize、initialCapacity、concurrencyLevel 的值根据具体的业务场景而定
                "maximumSize=256", // 容量最大大小
                "initialCapacity=16", // 初始容量
                "concurrencyLevel=16", // 并发级别
                // 经验：refreshAfterWrite 一般以分钟为单位，保证数据能准实时刷新
//                "refreshAfterWrite=1m", // 刷新周期(有效期)
                "refreshAfterWrite=3s", // 测试专用
                // 经验：expireAfterWrite 一般以天为单位，数值要比 refreshAfterWrite 大很多，让老数据自动过期失效
//                "expireAfterWrite=1d" // 写入过期时间
                "expireAfterWrite=4s" // 测试专用
        ));
        // 示例 核心业务，使用单独的任务执行服务
        LoadingCache<String, Object> loadingCache = LocalCacheFactory
                .newLoadingCache("newLoadingCache_String_ExecutorService", spec, syncCacheLoader, executorService);
        internalTest(loadingCache);
    }

    @Test(description = "定期同步刷新则调用 reload，过期失效而重新加载则调用 load")
    public void newLoadingCache_CacheBuilderSpec() throws ExecutionException {
        /**
         * 配置的具体数值根据业务特性来决定
         * @see com.google.common.cache.CacheBuilder
         */
        String spec = Joiner.on(',').join(Arrays.asList(
                // 经验：maximumSize 必须要显示设置，防止内存被撑爆！(容错机制)
                // 经验：maximumSize、initialCapacity、concurrencyLevel 的值根据具体的业务场景而定
                "maximumSize=256", // 容量最大大小
                "initialCapacity=16", // 初始容量
                "concurrencyLevel=16", // 并发级别
                // 经验：refreshAfterWrite 一般以分钟为单位，保证数据能准实时刷新
//                "refreshAfterWrite=1m", // 刷新周期(有效期)
                "refreshAfterWrite=3s", // 测试专用
                // 经验：expireAfterWrite 一般以天为单位，数值要比 refreshAfterWrite 大很多，让老数据自动过期失效
//                "expireAfterWrite=1d" // 写入过期时间
                "expireAfterWrite=4s" // 测试专用
        ));
        CacheBuilderSpec cacheBuilderSpec = CacheBuilderSpec.parse(spec);
        // 非核心业务，使用共享的任务执行服务
        LoadingCache<String, Object> loadingCache = LocalCacheFactory
                .newLoadingCache("newLoadingCache_CacheBuilderSpec", cacheBuilderSpec, syncCacheLoader);
        internalTest(loadingCache);
    }

    @Test(description = "定期同步刷新则调用 reload，过期失效而重新加载则调用 load")
    public void newLoadingCache_CacheBuilderSpec_ExecutorService() throws ExecutionException {
        /**
         * 配置的具体数值根据业务特性来决定
         * @see com.google.common.cache.CacheBuilder
         */
        String spec = Joiner.on(',').join(Arrays.asList(
                // 经验：maximumSize 必须要显示设置，防止内存被撑爆！(容错机制)
                // 经验：maximumSize、initialCapacity、concurrencyLevel 的值根据具体的业务场景而定
                "maximumSize=256", // 容量最大大小
                "initialCapacity=16", // 初始容量
                "concurrencyLevel=16", // 并发级别
                // 经验：refreshAfterWrite 一般以分钟为单位，保证数据能准实时刷新
//                "refreshAfterWrite=1m", // 刷新周期(有效期)
                "refreshAfterWrite=3s", // 测试专用
                // 经验：expireAfterWrite 一般以天为单位，数值要比 refreshAfterWrite 大很多，让老数据自动过期失效
//                "expireAfterWrite=1d" // 写入过期时间
                "expireAfterWrite=4s" // 测试专用
        ));
        CacheBuilderSpec cacheBuilderSpec = CacheBuilderSpec.parse(spec);
        // 核心业务，使用单独的任务执行服务
        LoadingCache<String, Object> loadingCache = LocalCacheFactory
                .newLoadingCache("newLoadingCache_CacheBuilderSpec", cacheBuilderSpec, syncCacheLoader, executorService);
        internalTest(loadingCache);
    }

    @Test(description = "定期同步刷新则调用 reload，过期失效而重新加载则调用 load")
    public void newLoadingCache_CacheBuilder() throws ExecutionException {
        /**
         * 配置的具体数值根据业务特性来决定
         * @see com.google.common.cache.CacheBuilder
         */
        String spec = Joiner.on(',').join(Arrays.asList(
                // 经验：maximumSize 必须要显示设置，防止内存被撑爆！(容错机制)
                // 经验：maximumSize、initialCapacity、concurrencyLevel 的值根据具体的业务场景而定
                "maximumSize=256", // 容量最大大小
                "initialCapacity=16", // 初始容量
                "concurrencyLevel=16", // 并发级别
                // 经验：refreshAfterWrite 一般以分钟为单位，保证数据能准实时刷新
//                "refreshAfterWrite=1m", // 刷新周期(有效期)
                "refreshAfterWrite=3s", // 测试专用
                // 经验：expireAfterWrite 一般以天为单位，数值要比 refreshAfterWrite 大很多，让老数据自动过期失效
//                "expireAfterWrite=1d" // 写入过期时间
                "expireAfterWrite=4s" // 测试专用
        ));
        CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.from(spec);
        // 非核心业务，使用共享的任务执行服务
        LoadingCache<String, Object> loadingCache = LocalCacheFactory
                .newLoadingCache("newLoadingCache_CacheBuilderSpec", cacheBuilder, syncCacheLoader);
        internalTest(loadingCache);
    }

    @Test(description = "定期同步刷新则调用 reload，过期失效而重新加载则调用 load")
    public void newLoadingCache_CacheBuilder_ExecutorService() throws ExecutionException {
        /**
         * 配置的具体数值根据业务特性来决定
         * @see com.google.common.cache.CacheBuilder
         */
        String spec = Joiner.on(',').join(Arrays.asList(
                // 经验：maximumSize 必须要显示设置，防止内存被撑爆！(容错机制)
                // 经验：maximumSize、initialCapacity、concurrencyLevel 的值根据具体的业务场景而定
                "maximumSize=256", // 容量最大大小
                "initialCapacity=16", // 初始容量
                "concurrencyLevel=16", // 并发级别
                // 经验：refreshAfterWrite 一般以分钟为单位，保证数据能准实时刷新
//                "refreshAfterWrite=1m", // 刷新周期(有效期)
                "refreshAfterWrite=3s", // 测试专用
                // 经验：expireAfterWrite 一般以天为单位，数值要比 refreshAfterWrite 大很多，让老数据自动过期失效
//                "expireAfterWrite=1d" // 写入过期时间
                "expireAfterWrite=4s" // 测试专用
        ));
        CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.from(spec);
        // 核心业务，使用单独的任务执行服务
        LoadingCache<String, Object> loadingCache = LocalCacheFactory
                .newLoadingCache("newLoadingCache_CacheBuilderSpec", cacheBuilder, syncCacheLoader, executorService);
        internalTest(loadingCache);
    }


    private void internalTest(final LoadingCache<String, ?> loadingCache)
            throws ExecutionException {
        for (int i = 0; i < 3; i++) {
            if (i == 2) {
                assertThat(loadingCache.get("expireKey")).isEqualTo("load");
            } else {
                assertThat(loadingCache.get("refreshKey")).isEqualTo("load");
            }
        }
        CacheStats stats = loadingCache.stats();
        assertThat(stats.hitCount()).isEqualTo(1L);
        assertThat(stats.missCount()).isEqualTo(2L);
        assertThat(stats.loadSuccessCount()).isEqualTo(2L);

        // 定期同步刷新则调用 reload
        try {
            TimeUnit.SECONDS.sleep(3L);
        } catch (InterruptedException e) {
            logger.warn("Thread.sleep() has interrupted", e);
        }
        assertThat(loadingCache.get("refreshKey")).isEqualTo("reload"); // 异步刷新
        stats = loadingCache.stats();
        assertThat(stats.hitCount()).isEqualTo(2L); // 刷新操作算命中
        assertThat(stats.missCount()).isEqualTo(2L);
        assertThat(stats.loadSuccessCount()).isEqualTo(3L); // 重新加载
        assertThat(stats.evictionCount()).isEqualTo(0L); // 未过期

        // 过期失效而重新加载则调用 load
        try {
            TimeUnit.SECONDS.sleep(1L);
        } catch (InterruptedException e) {
            logger.warn("Thread.sleep() has interrupted", e);
        }
        assertThat(loadingCache.get("expireKey")).isEqualTo("load"); // 过期失效则重新加载
        stats = loadingCache.stats();
        assertThat(stats.hitCount()).isEqualTo(2L);
        assertThat(stats.missCount()).isEqualTo(3L); // 过期失效算未命中
        assertThat(stats.loadSuccessCount()).isEqualTo(4L); // 重新加载
        assertThat(stats.evictionCount()).isEqualTo(1L); // 1个条目过期
    }

}
