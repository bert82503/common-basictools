package com.common.json;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import java.io.IOException;
import java.util.Locale;

import javax.annotation.CheckForNull;
import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 基于Jackson的JSON辅助方法集。
 *
 * @since 2017-05-17
 */
public final class JacksonUtil {
  private static final Logger logger = LoggerFactory.getLogger(JacksonUtil.class);

  /**
   * Mapper instances are fully thread-safe
   */
  private static final ObjectMapper objectMapper;

  static {
    objectMapper = new ObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
        .setSerializationInclusion(Include.NON_NULL) // 忽略所有为null的字段，节省网络传输
        .setLocale(Locale.CHINA) // Date & Time
    ;
  }

  private JacksonUtil() {
    super();
  }

  @CheckForNull
  @Nullable
  public static <T> T parseObject(String json, Class<T> clazz) {
    try {
      return objectMapper.readValue(json, clazz);
    } catch (IOException e) {
      logger.error("parse JSON error, json:{}, clazz:{}", json, clazz, e);
    }
    return null;
  }

  @CheckForNull
  @Nullable
  public static String toJson(Object value) {
    if (value == null) {
      return null;
    }

    try {
      return objectMapper.writeValueAsString(value);
    } catch (JsonProcessingException e) {
      logger.error("format to JSON error, value:{}", value, e);
    }
    return null;
  }

  // serialize & deserialize

  @CheckForNull
  @Nullable
  @SuppressWarnings("resource")
  public static byte[] serialize(Object value) {
    if (value == null) {
      return null;
    }

    try {
      return objectMapper.writeValueAsBytes(value);
    } catch (JsonProcessingException e) {
      logger.error("serialize Object error, value:{}", value, e);
    }
    return null;
  }

  @CheckForNull
  @Nullable
  public static <T> T deserialize(byte[] src, Class<T> valueType) {
    if (src == null) {
      return null;
    }

    try {
      return objectMapper.readValue(src, valueType);
    } catch (IOException e) {
      logger.error("deserialize Object error, valueType:{}", valueType, e);
    }
    return null;
  }
}
