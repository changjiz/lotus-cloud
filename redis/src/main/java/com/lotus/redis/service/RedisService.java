package com.lotus.redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.*;

/**
 * spring redis 工具类
 *
 * @author changjiz
 **/
@SuppressWarnings(value = {"unchecked", "rawtypes"})
@Component
public class RedisService<T> {
    @Autowired
    public RedisTemplate redisTemplate;
    @Autowired
    private ValueOperations<String, T> valueOperations;
    @Autowired
    private ListOperations<String, T> listOperations;
    @Autowired
    private HashOperations<String, String, T> hashOperations;
    @Autowired
    public SetOperations<String, T> setOperations;
    @Autowired
    public ZSetOperations<String, T> zSetOperations;

    /**
     * 默认过期时长，1天  单位：秒
     */
    public final static long DEFAULT_EXPIRE = 60 * 60 * 24;

    /**
     * 设置有效时间
     *
     * @param key     Redis键
     * @param timeout 超时时间
     * @return true=设置成功；false=设置失败
     */
    public boolean expire(final String key, final long timeout) {
        return expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置有效时间
     *
     * @param key     Redis键
     * @param timeout 超时时间
     * @param unit    时间单位
     * @return true=设置成功；false=设置失败
     */
    public boolean expire(final String key, final long timeout, final TimeUnit unit) {
        return redisTemplate.expire(key, timeout, unit);
    }

    /**
     * 获取有效时间
     *
     * @param key Redis键
     * @return 有效时间
     */
    public long getExpire(final String key) {
        return redisTemplate.getExpire(key);
    }

    /**
     * 判断 key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 获得缓存的基本对象列表
     *
     * @param pattern 字符串前缀
     * @return 对象列表
     */
    public Collection<String> keys(final String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * 删除单个对象
     *
     * @param key
     */
    public boolean deleteObject(final String key) {
        return redisTemplate.delete(key);
    }

    /**
     * 删除集合对象
     *
     * @param collection 多个对象
     * @return
     */
    public long deleteObject(final Collection collection) {
        return redisTemplate.delete(collection);
    }


    //============================object=============================

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key   缓存的键值
     * @param value 缓存的值
     */
    public void setCacheObject(final String key, final T value) {
        valueOperations.set(key, value);
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key      缓存的键值
     * @param value    缓存的值
     * @param timeout  时间
     * @param timeUnit 时间颗粒度
     */
    public void setCacheObject(final String key, final T value, final Long timeout, final TimeUnit timeUnit) {
        valueOperations.set(key, value, timeout, timeUnit);
    }

    /**
     * 获得缓存的基本对象。
     *
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     */
    public T getCacheObject(final String key) {
        return valueOperations.get(key);
    }


    //============================list=============================

    /**
     * 缓存List数据
     *
     * @param key      缓存的键值
     * @param dataList 待缓存的List数据
     * @return 缓存的对象
     */
    public long setCacheList(final String key, final List dataList) {
        Long count = listOperations.rightPushAll(key, dataList);
        return count == null ? 0 : count;
    }

    /**
     * 获得缓存的list对象
     *
     * @param key 缓存的键值
     * @return 缓存键值对应的数据
     */
    public List getCacheList(final String key) {
        return listOperations.range(key, 0, -1);
    }


    //============================hash=============================

    /**
     * 缓存Map
     *
     * @param key
     * @param dataMap
     */
    public void setCacheMap(final String key, final Map<String, T> dataMap) {
        if (dataMap != null) {
            hashOperations.putAll(key, dataMap);
        }
    }

    /**
     * 获得缓存的Map
     *
     * @param key
     * @return
     */
    public Map<String, T> getCacheMap(final String key) {
        return hashOperations.entries(key);
    }

    /**
     * 往Hash中存入数据
     *
     * @param key   Redis键
     * @param hKey  Hash键
     * @param value 值
     */
    public void setCacheMapValue(final String key, final String hKey, final T value) {
        hashOperations.put(key, hKey, value);
    }

    /**
     * 获取Hash中的数据
     *
     * @param key  Redis键
     * @param hKey Hash键
     * @return Hash中的对象
     */
    public T getCacheMapValue(final String key, final String hKey) {
        HashOperations<String, String, T> opsForHash = hashOperations;
        return opsForHash.get(key, hKey);
    }

    /**
     * 获取多个Hash中的数据
     *
     * @param key   Redis键
     * @param hKeys Hash键集合
     * @return Hash对象集合
     */
    public List getMultiCacheMapValue(final String key, final Collection<String> hKeys) {
        return hashOperations.multiGet(key, hKeys);
    }



    //============================set=============================

    /**
     * 根据key获取Set中的所有值
     *
     * @param key 键
     * @return
     */
    public Set<T> sGet(String key) {
        try {
            return setOperations.members(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key   键
     * @param value 值
     * @return true 存在 false不存在
     */
    public boolean sHasKey(String key, T value) {
        try {
            return setOperations.isMember(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将数据放入set缓存
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long sSet(String key, T... values) {
        try {
            return sSetAndTime(key, DEFAULT_EXPIRE, values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 将set数据放入缓存
     *
     * @param key    键
     * @param time   时间(秒)
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long sSetAndTime(String key, long time, T... values) {
        try {
            Long count = setOperations.add(key, values);
            if (time > 0) {
                expire(key, time);
            }
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取set缓存的长度
     *
     * @param key 键
     * @return
     */
    public long sGetSetSize(String key) {
        try {
            return setOperations.size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 移除值为value的
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    public long setRemove(String key, T... values) {
        try {
            Long count = setOperations.remove(key, values);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


    //===============================zset=================================

    /**
     * 根据key添加到zSet中并带上排序值
     *
     * @param key 键
     * @return
     */
    public Boolean zsAdd(String key, T value, double score) {
        try {
            return zSetOperations.add(key, value, score);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据value从递增排序值
     *
     * @param key   键
     * @param value 值
     * @return 排序值
     */
    public Double zsIncrScore(String key, T value, double incr) {
        try {
            return zSetOperations.incrementScore(key, value, incr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据key添加到zSet中的位置倒序
     *
     * @param key 键
     * @return
     */
    public Long zsRank(String key, T value) {
        try {
            return zSetOperations.rank(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return -1L;
        }
    }

    /**
     * 根据key添加到zSet中的位置倒序
     *
     * @param key 键
     * @return
     */
    public Long zsReverseRank(String key, T value) {
        try {
            return zSetOperations.reverseRank(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return -1L;
        }
    }

    /**
     * 获取多个值，根据排序值从小到大
     *
     * @param key 键
     * @return
     */
    public Set<T> zsRange(String key, long start, long end) {
        try {
            return zSetOperations.range(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取多个值，根据排序值从大到小
     *
     * @param key 键
     * @return
     */
    public Set<T> zsReverseRange(String key, long start, long end) {
        try {
            return zSetOperations.reverseRange(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 键为key的集合元素个数
     *
     * @param key 键
     * @return
     */
    public Long zsSize(String key) {
        try {
            return setOperations.size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    /**
     * 删除，键为K的集合，values的元素
     *
     * @param key 键
     * @return
     */
    public Long szRemove(String key, T... values) {
        try {
            return setOperations.remove(key, values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }
}
