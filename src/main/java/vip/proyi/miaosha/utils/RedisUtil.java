package vip.proyi.miaosha.utils;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import vip.proyi.miaosha.comment.KeyPrefix;

import java.util.concurrent.TimeUnit;

@Slf4j
public class RedisUtil {
    private RedisTemplate<String, Object> redisTemplate;

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 设置对象
     */
    public <T> boolean setObj(KeyPrefix prefix, String key, T value) {
        String realKey = prefix.getPrefix() + ":" + key;
        int seconds = prefix.expireSeconds();
        set(realKey, value, seconds);
        return true;
    }

    /**
     * 获取当前对象
     */
    public <T> T getObj(KeyPrefix prefix, String key,  Class<T> clazz) {
        String realKey = prefix.getPrefix() + ":" +key;
        Object obj = get(realKey);
        T t = objToBean(obj, clazz);
        return t;
    }

    /**
     * 判断Key是否存在
     */
    public <T> boolean exists(KeyPrefix prefix, String key) {
        String realKey  = prefix.getPrefix() + ":" +key;
        return hasKey(realKey);
    }
    /**
     * 增加值
     */
    public <T> Long incrObj(KeyPrefix prefix, String key) {
        String realKey  = prefix.getPrefix() + ":" +key;
        return incr(realKey, 1);
    }

    /**
     * 减少值
     */
    public <T> Long decrObj(KeyPrefix prefix, String key) {
        String realKey  = prefix.getPrefix() + ":" +key;
        return decr(realKey, 1);
    }

    private <T> T objToBean(Object obj, Class<T> clazz) {
        if (obj == null || clazz == null) {
            return null;
        }
        return (T) obj;
    }

    //=============================common============================
    /**
     * 指定缓存失效时间
     * @param key 键
     * @param time 时间(秒)
     * @return
     */
    public boolean expire(String key,long time){
        try {
            if(time>0){
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            log.error("redis设置过期时间异常！", e);
            return false;
        }
    }

    /**
     * 根据key 获取过期时间
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public long getExpire(String key){
        return redisTemplate.getExpire(key,TimeUnit.SECONDS);
    }

    /**
     * 判断key是否存在
     * @param key 键
     * @return true 存在 false不存在
     */
    public boolean hasKey(String key){
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            log.error("判断redis缓存是否包含key:{}值异常！", key, e);
            return false;
        }
    }

    /**
     * 删除缓存
     * @param key 可以传一个值 或多个
     */
    @SuppressWarnings("unchecked")
    public void del(String ... key){
        if(key!=null&&key.length>0){
            if(key.length==1){
                redisTemplate.delete(key[0]);
            }else{
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }


    //============================String=============================
    /**
     * 普通缓存获取
     * @param key 键
     * @return 值
     */
    public Object get(String key){
        return key==null?null:redisTemplate.opsForValue().get(key);
    }

    /**
     * 普通缓存放入
     * @param key 键
     * @param value 值
     * @return true成功 false失败
     */
    public boolean set(String key,Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            log.error("[string set] redis缓存设置异常！", e);
            return false;
        }
    }

    /**
     * 普通缓存放入并设置时间
     * @param key 键
     * @param value 值
     * @param time 时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public boolean set(String key,Object value,long time){
        try {
            if(time>0){
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            }else{
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            log.error("[string set time] redis缓存设置异常！", e);
            return false;
        }
    }

    /**
     * 递增
     * @param key 键
     * @param delta 要增加几(大于0)
     * @return
     */
    public long incr(String key, long delta){
        if(delta<0){
            throw new IllegalArgumentException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 递减
     * @param key 键
     * @param delta 要减少几(小于0)
     * @return
     */
    public long decr(String key, long delta){
        if(delta<0){
            throw new IllegalArgumentException("递减因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, -delta);
    }

}
