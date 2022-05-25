package org.example.szsubway.common.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author fengyadong
 * @date 2022/5/25 17:17
 * @Description
 */
@Slf4j
@Component
public class RedisUtils {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 设定缓存失效时间
     * <p>秒级时间，time要大于0，time小于等于0，将设置失败</p>
     * <p> 成功返回true，失败返回false </p>
     * @param key 键
     * @param time 时间(秒)
     * @return
     */
    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                return redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
        } catch (Exception e) {
            log.error("RedisUtils expire Error", e);
        }
        return false;
    }

    /**
     * 设定缓存失效时间
     * <p>毫秒级时间，time要大于0，time小于等于0，将设置失败</p>
     * <p>成功返回true，失败返回false</p>
     * @param key 键
     * @param time 时间(毫秒)
     * @return
     */
    public boolean expireMs(String key, long time) {
        try {
            if (time > 0) {
                return redisTemplate.expire(key, time, TimeUnit.MILLISECONDS);
            }
        } catch (Exception e) {
            log.error("RedisUtils expireMs Error", e);
        }
        return false;
    }

    /**
     * 获取缓存过期时间
     * <p>返回秒级时间，返回0代表为永久有效</p>
     * @param key 不能为null
   * @return 时间(秒)
      
    
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 获取缓存过期时间
     * <p>返回毫秒级时间，返回0代表为永久有效</p>
     * @param key 不能为null
   * @return 时间(毫秒)
      
    
     */
    public long getExpireMs(String key) {
        return redisTemplate.getExpire(key, TimeUnit.MILLISECONDS);
    }

    /**
     * 判断缓存是否存在
     * <p>true存在，false不存在</p>
     * @param key 键
     * @return
     */
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 删除缓存
     * <p>可以传一个值或多个值</p>
     * @param key 键
      
    
     */
    @SuppressWarnings("unchecked")
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }

    /**
     * 普通缓存获取
     * @param key 键
     * @return
     */
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 普通缓存存储
     * <p>true成功，false失败</p>
     * @param key 键
     * @param value 值
     * @return
     */
    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            log.error("RedisUtils set Error", e);
            return false;
        }
    }

    /**
     * 普通缓存存储并设置过期时间
     * <p>秒级时间，time要大于0，如果time小于等于0，将设置无限期</p>
     * <p>返回true成功，返回false失败</p>
     * @param key 键
     * @param value 值
     * @param time 过期时间(秒)
     * @return
     */
    public boolean set(String key, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            log.error("RedisUtils set Error", e);
            return false;
        }
    }
    /**
     * 普通缓存存储并设置过期时间
     * <p>毫秒级时间，time要大于0，如果time小于等于0，将设置无限期</p>
     * <p>返回true成功，返回false失败</p>
     * @param key 键
     * @param value 值
     * @param time 过期时间(毫秒)
     * @return
     */
    public boolean setMs(String key, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.MILLISECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            log.error("RedisUtils setMs Error", e);
            return false;
        }
    }

    /**
     * 缓存递增
     * <p>递增因子必须大于0，返回递增后键值</p>
     * @param key 键
     * @param delta 递增因子
     * @return
     */
    public long incr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 缓存递减
     * <p>递减因子必须大于0，返回递减后键值</p>
     * @param key 键
     * @param delta 递减因子
     * @return
     */
    public long decr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, -delta);
    }

    /**
     * hash缓存，获取hashKey对应的键值
     * <p>从缓存中获得给定的hashKey值</p>
     * @param key 键 不能为null
     * @param hashKey 项 不能为null
     * @return
     */
    public Object hget(String key, Object hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    /**
     * hash缓存，获取hashKey对应的所有键值
     * <p>返回对应的所有键值</p>
     * @param key 键
     * @return
     */
    public Map<Object, Object> hmget(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * hash缓存，设置缓存值
     * <p>返回true成功，返回false失败</p>
     * @param key 键
     * @param map 值
     * @return
     */
    public boolean hmset(String key, Map<String, Object> map) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            log.error("RedisUtils hmset Error", e);
            return false;
        }
    }

    /**
     * hash缓存，设置缓存值并设置过期时间
     * <p>秒级时间，time要大于0，time小于等于0，将设置无限期</p>
     * <p>返回true成功，返回false失败</p>
     * @param key 键
     * @param map 值
     * @param time 时间(秒)
     * @return
     */
    public boolean hmset(String key, Map<String, Object> map, long time) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("RedisUtils hmset Error ",e);
            return false;
        }
    }

    /**
     * hash缓存，设置缓存值并设置过期时间
     * <p>毫秒级时间，time要大于0，time小于等于0，将设置无限期</p>
     * <p>返回true成功，返回false失败</p>
     * @param key 键
     * @param map 值
     * @param time 时间(毫秒)
     * @return
     */
    public boolean hmsetMs(String key, Map<String, Object> map, long time) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if (time > 0) {
                expireMs(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("RedisUtils hmsetMs Error", e);
            return false;
        }
    }

    /**
     * hash缓存中存放数据，如果不存在则创建
     * <p>true成功，false失败</p>
     * @param key 键
     * @param hashKey 项
     * @param value 值
     * @return
     */
    public boolean hset(String key, Object hashKey, Object value) {
        try {
            redisTemplate.opsForHash().put(key, hashKey, value);
            return true;
        } catch (Exception e) {
            log.error("RedisUtils hset Error", e);
            return false;
        }
    }

    /**
     * hash缓存中存放数据并设置过期时间，如果不存在则创建
     * <p>时间秒级，如果time大于0，将设置过期时间，原有时间将被替换；</p>
     * <p>如果time小于等于0，过期时间不做改变</p>
     * <p>true成功，false失败</p>
     * @param key 键
     * @param item 项
     * @param value 值
     * @param time 时间(秒)
     * @return
     */
    public boolean hset(String key, String item, Object value, long time) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("RedisUtils hset Error", e);
            return false;
        }
    }

    /**
     * hash缓存中存放数据并设置过期时间，如果不存在则创建
     * <p>时间毫秒级，如果time大于0，将设置过期时间，原有时间将被替换；</p>
     * <p>如果time小于等于0，过期时间不做改变</p>
     * <p>true成功，false失败</p>
     * @param key 键
     * @param item 项
     * @param value 值
     * @param time 时间(毫秒)
     * @return
     */
    public boolean hsetMs(String key, String item, Object value, long time) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            if (time > 0) {
                expireMs(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("RedisUtils hsetMs Error", e);
            return false;
        }
    }

    /**
     * 删除hash缓存中hashkey的值
     * <p>返回删除成功个数</p>
     * @param key 键 不能为null
     * @param hashKeys 项多个，不能为null
     * @return
     */
    public long hdel(String key, Object... hashKeys) {
        return redisTemplate.opsForHash().delete(key, hashKeys);
    }

    /**
     * 判断hash缓存中是否有该项的值
     * <p>true存在，false不存在</p>
     * @param key 键 不能为null
     * @param item 项 不能为null
     * @return
     */
    public boolean hHasKey(String key, String item) {
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * hash递增
     * <p>如果不存在，会新创建一个并把递增后的值返回</p>
     * @param key 键
     * @param hashKey 项
     * @param delta 递增因子(大于0)
     * @return
     */
    public double hincr(String key, Object hashKey, double delta) {
        return redisTemplate.opsForHash().increment(key, hashKey, delta);
    }

    /**
     * hash递减
     * <p>如果不存在，会新创建一个并把递减后的值返回</p>
     * @param key 键
     * @param delta 递减因子(大于0)
     * @return
     */
    public double hdecr(String key, String item, double delta) {
        return redisTemplate.opsForHash().increment(key, item, -delta);
    }

    /**
     * 根据key获取Set中的所有值
     * <p>失败返回null</p>
     * @param key 键
     * @return
     */
    public Set<Object> sGet(String key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            log.error("RedisUtils sGet Error", e);
            return null;
        }
    }

    /**
     * 根据value从一个set中查询，是否存在
     * <p>true存在，false不存在</p>
     * @param key 键
     * @param value 值
     * @return
     */
    public boolean sHasKey(String key, Object value) {
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            log.error("RedisUtils sHasKey Error", e);
            return false;
        }
    }

    /**
     * 将数据放入set缓存
     * <p>返回成功个数</p>
     * @param key 键
     * @param values 值 可以是多个
     * @return
     */
    public long sSet(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            log.error("RedisUtils sSet Error ", e);
            return 0;
        }
    }

    /**
     * 将set数据放入缓存并设置过期时间
     * <p>秒级时间，如果time大于0，将设置过期时间</p>
     * <p>返回成功个数</p>
     * @param key 键
     * @param time 时间(秒)
     * @param values 值 可以是多个
     * @return
     */
    public long sSetAndTime(String key, long time, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().add(key, values);
            if (time > 0) {
                expire(key, time);
            }
            return count;
        } catch (Exception e) {
            log.error("RedisUtils sSetAndTime Error", e);
            return 0;
        }
    }
    /**
     * 将set数据放入缓存并设置过期时间
     * <p>毫秒级时间，如果time大于0，将设置过期时间</p>
     * <p>返回成功个数</p>
     * @param key 键
     * @param time 时间(毫秒)
     * @param values 值 可以是多个
     * @return
     */
    public long sSetAndMsTime(String key, long time, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().add(key, values);
            if (time > 0) {
                expireMs(key, time);
            }
            return count;
        } catch (Exception e) {
            log.error("RedisUtils sSetAndMsTime Error", e);
            return 0;
        }
    }

    /**
     * 获取set缓存的长度
     * <p>如果失败返回0</p>
     * @param key 键
     * @return
     */
    public long sGetSize(String key) {
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            log.error("RedisUtils sGetSize Error", e);
            return 0;
        }
    }

    /**
     * 移除值为value的
     * <p>返回成功移除的个数，如果失败返回0</p>
     * @param key 键
     * @param values 值 可以多个
     * @return
     */
    public long setRemove(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().remove(key, values);
        } catch (Exception e) {
            log.error("RedisUtils setRemove Error", e);
            return 0;
        }
    }

    /**
     * 获取list缓存的内容
     * <p>start到end 为0到-1代表获取所有值</p>
     * <p>失败返回null</p>
     * @param key 键
     * @param start 开始
     * @param end 结束
     * @return
     */
    public List<Object> lGet(String key, long start, long end) {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            log.error("RedisUtils setRemove Error", e);
            return null;
        }
    }

    /**
     * 获取list缓存的长度
     * <p>失败返回0</p>
     * @param key 键
     * @return
     */
    public long lGetListSize(String key) {
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            log.error("RedisUtils lGetListSize Error", e);
            return 0;
        }
    }

    /**
     * 通过索引获取list中的值
     * <p>索引index>=0时， 0表头，1第二个元素，依次类推；index<0时，-1表尾，-2倒数第二个元素，依次类推</p>
     * <p>失败返回null</p>
     * @param key 键
     * @param index 索引
     * @return
     */
    public Object lGetIndex(String key, long index) {
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            log.error("RedisUtils lGetIndex Error", e);
            return null;
        }
    }

    /**
     * 将list放入缓存
     * <p>true成功，false失败</p>
     * @param key 键
     * @param value 值
     * @return
     */
    public boolean lSet(String key, Object value) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            log.error("RedisUtils lSet Error", e);
            return false;
        }
    }

    /**
     * 将list放入缓存并设置过期时间
     * <p>秒级时间，time大于0设置过期时间；time小于等于0不设置过期时间</p>
     * <p>true成功，false失败</p>
     * @param key 键
     * @param value 值
     * @param time 时间(秒)
     * @return
     */
    public boolean lSet(String key, Object value, long time) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("RedisUtils lSet Error", e);
            return false;
        }
    }

    /**
     * 将list放入缓存并设置过期时间
     * <p>毫秒级时间，time大于0设置过期时间；time小于等于0不设置过期时间</p>
     * <p>true成功，false失败</p>
     * @param key 键
     * @param value 值
     * @param time 时间(毫秒)
     * @return
     */
    public boolean lSetMs(String key, Object value, long time) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            if (time > 0) {
                expireMs(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("RedisUtils lSetMs Error ", e);
            return false;
        }
    }

    /**
     * 将list放入缓存
     * <p>true成功，false失败</p>
     * @param key 键
     * @param value 值
     * @return
     */
    public boolean lSet(String key, List<Object> value) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception e) {
            log.error("RedisUtils lSet Error", e);
            return false;
        }
    }

    /**
     * 将list放入缓存并设置过期时间
     * <p>秒级时间，time大于0设置过期时间，time小于0不设置过期时间</p>
     * <p>true成功，false失败</p>
     * @param key 键
     * @param value 值
     * @param time 时间(秒)
     * @return
     */
    public boolean lSet(String key, List<Object> value, long time) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("RedisUtils lSet Error", e);
            return false;
        }
    }

    /**
     * 将list放入缓存并设置过期时间
     * <p>毫秒级时间，time大于0设置过期时间，time小于0不设置过期时间</p>
     * <p>true成功，false失败</p>
     * @param key 键
     * @param value 值
     * @param time 时间(毫秒)
     * @return
     */
    public boolean lSetMs(String key, List<Object> value, long time) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            if (time > 0) {
                expireMs(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("RedisUtils lSetMs Error", e);
            return false;
        }
    }

    /**
     * 根据索引修改list中的某条数据
     * <p>true成功，false失败</p>
     * @param key 键
     * @param index 索引
     * @param value 值
     * @return
     */
    public boolean lUpdateIndex(String key, long index, Object value) {
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            log.error("RedisUtils lUpdateIndex Error", e);
            return false;
        }
    }

    /**
     * 移除N个值为value
     * <p>返回移除的个数，如果失败返回0</p>
     * @param key 键
     * @param count 移除数量
     * @param value 值
     * @return
     */
    public long lRemove(String key, long count, Object value) {
        try {
            Long remove = redisTemplate.opsForList().remove(key, count, value);
            return remove;
        } catch (Exception e) {
            log.error("RedisUtils lRemove Error", e);
            return 0;
        }
    }
}

