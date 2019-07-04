package com.selene.common.util;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;

/**
 * RedisTemplate tool classes for spring and redis
 * 
 * @author shaobo shih
 * @version 1.0
 */
public class RedisClient {
	private RedisTemplate<String, Object> redisTemplate;

	// methods for keys
	/**
	 * Set time to live for given {@code key} in milliseconds.
	 *
	 * @param key
	 *            must not be {@literal null}.
	 * @param millis
	 * @return
	 * @see <a href="http://redis.io/commands/pexpire">Redis Documentation:
	 *      PEXPIRE</a>
	 */
	public boolean expire(String key, long time) {
		try {
			if (time > 0) {
				redisTemplate.expire(key, time, TimeUnit.SECONDS);
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Get the precise time to live for {@code key} in and convert it to the
	 * given {@link TimeUnit}.
	 *
	 * @param key
	 *            must not be {@literal null}.
	 * @return
	 * @see <a href="http://redis.io/commands/pttl">Redis Documentation:
	 *      PTTL</a>
	 */
	public long getExpire(String key) {
		return redisTemplate.getExpire(key, TimeUnit.SECONDS);
	}

	/**
	 * Determine if given {@code key} exists.
	 *
	 * @param key
	 *            must not be {@literal null}.
	 * @return
	 * @see <a href="http://redis.io/commands/exists">Redis Documentation:
	 *      EXISTS</a>
	 */
	public boolean hasKey(String key) {
		try {
			return redisTemplate.hasKey(key);
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Delete the value of {@code keys}.
	 *
	 * @param key
	 *            must not be {@literal null}.
	 * @see <a href="http://redis.io/commands/get">Redis Documentation: GET</a>
	 */
	@SuppressWarnings("unchecked")
	public void delete(String... key) {
		if (key != null && key.length > 0) {
			if (key.length == 1) {
				redisTemplate.delete(key[0]);
			} else {
				redisTemplate.delete(CollectionUtils.arrayToList(key));
			}
		}
	}

	// methods for string
	/**
	 * Get the value of {@code key}.
	 *
	 * @param key
	 *            must not be {@literal null}.
	 * @see <a href="http://redis.io/commands/get">Redis Documentation: GET</a>
	 */
	public Object get(String key) {
		return key == null ? null : redisTemplate.opsForValue().get(key);
	}

	/**
	 * Set {@code value} for {@code key}.
	 *
	 * @param key
	 *            must not be {@literal null}.
	 * @param value
	 * @see <a href="http://redis.io/commands/set">Redis Documentation: SET</a>
	 */
	public boolean set(String key, Object value) {
		try {
			redisTemplate.opsForValue().set(key, value);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Set the {@code value} and expiration {@code timeout} for {@code key}.
	 *
	 * @param key
	 *            must not be {@literal null}.
	 * @param value
	 * @param timeout
	 * @param unit
	 *            must not be {@literal null}.
	 * @see <a href="http://redis.io/commands/setex">Redis Documentation:
	 *      SETEX</a>
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
			return false;
		}
	}

	/**
	 * Increment an integer value stored as string value under {@code key} by
	 * {@code delta}.
	 * 
	 * @param key
	 *            must not be {@literal null}.
	 * @param delta
	 * @see <a href="http://redis.io/commands/incr">Redis Documentation:
	 *      INCR</a>
	 */
	public long incr(String key, long delta) {
		if (delta < 0) {
			throw new RuntimeException("递增因子必须大于0");
		}
		return redisTemplate.opsForValue().increment(key, delta);
	}

	/**
	 * Decrease an integer value stored as string value under {@code key} by
	 * {@code delta}.
	 * 
	 * @param key
	 *            must not be {@literal null}.
	 * @param delta
	 * @see <a href="http://redis.io/commands/incr">Redis Documentation:
	 *      INCR</a>
	 */
	public long decr(String key, long delta) {
		if (delta < 0) {
			throw new RuntimeException("递减因子必须大于0");
		}
		return redisTemplate.opsForValue().increment(key, -delta);
	}

	// methods for hash
	/**
	 * Get value for given {@code hashKey} from hash at {@code key}.
	 *
	 * @param key
	 *            must not be {@literal null}.
	 * @param hashKey
	 *            must not be {@literal null}.
	 * @return
	 */
	public Object hget(String key, String hashKey) {
		return redisTemplate.opsForHash().get(key, hashKey);
	}

	/**
	 * Get entire hash stored at {@code key}.
	 *
	 * @param key
	 *            must not be {@literal null}.
	 * @return
	 */
	public Map<Object, Object> hmget(String key) {
		return redisTemplate.opsForHash().entries(key);
	}

	/**
	 * Set multiple hash fields to multiple values using data provided in
	 * {@code m}.
	 *
	 * @param key
	 *            must not be {@literal null}.
	 * @param m
	 *            must not be {@literal null}.
	 */
	public boolean hmset(String key, Map<String, Object> m) {
		try {
			redisTemplate.opsForHash().putAll(key, m);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Set multiple hash fields to multiple values using data provided in
	 * {@code m}.
	 *
	 * @param key
	 *            must not be {@literal null}.
	 * @param m
	 *            must not be {@literal null}.
	 * @param time
	 */
	public boolean hmset(String key, Map<String, Object> m, long time) {
		try {
			redisTemplate.opsForHash().putAll(key, m);
			if (time > 0) {
				expire(key, time);
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Set the {@code value} of a hash {@code hashKey}.
	 *
	 * @param key
	 *            must not be {@literal null}.
	 * @param hashKey
	 *            must not be {@literal null}.
	 * @param value
	 */
	public boolean hset(String key, String hashKey, Object value) {
		try {
			redisTemplate.opsForHash().put(key, hashKey, value);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Set the {@code value} of a hash {@code hashKey}.
	 *
	 * @param key
	 *            must not be {@literal null}.
	 * @param hashKey
	 *            must not be {@literal null}.
	 * @param value
	 * @param time
	 */
	public boolean hset(String key, String hashKey, Object value, long time) {
		try {
			redisTemplate.opsForHash().put(key, hashKey, value);
			if (time > 0) {
				expire(key, time);
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Delete given hash {@code hashKeys}.
	 *
	 * @param key
	 *            must not be {@literal null}.
	 * @param hashKeys
	 *            must not be {@literal null}.
	 * @return
	 */
	public void hdel(String key, Object... hashKeys) {
		redisTemplate.opsForHash().delete(key, hashKeys);
	}

	/**
	 * Determine if given hash {@code hashKey} exists.
	 *
	 * @param key
	 *            must not be {@literal null}.
	 * @param hashKey
	 *            must not be {@literal null}.
	 * @return
	 */
	public boolean hHasKey(String key, String hashKey) {
		return redisTemplate.opsForHash().hasKey(key, hashKey);
	}

	/**
	 * Increment {@code value} of a hash {@code hashKey} by the given
	 * {@code delta}.
	 *
	 * @param key
	 *            must not be {@literal null}.
	 * @param hashKey
	 *            must not be {@literal null}.
	 * @param delta
	 * @return
	 */
	public double hincr(String key, String hashKey, double delta) {
		return redisTemplate.opsForHash().increment(key, hashKey, delta);
	}

	/**
	 * Decrease {@code value} of a hash {@code hashKey} by the given
	 * {@code delta}.
	 *
	 * @param key
	 *            must not be {@literal null}.
	 * @param hashKey
	 *            must not be {@literal null}.
	 * @param delta
	 * @return
	 */
	public double hdecr(String key, String hashKey, double delta) {
		return redisTemplate.opsForHash().increment(key, hashKey, -delta);
	}

	// methods for set
	/**
	 * Get all elements of set at {@code key}.
	 *
	 * @param key
	 *            must not be {@literal null}.
	 * @return
	 * @see <a href="http://redis.io/commands/smembers">Redis Documentation:
	 *      SMEMBERS</a>
	 */
	public Set<Object> sGet(String key) {
		try {
			return redisTemplate.opsForSet().members(key);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Check if set at {@code key} contains {@code value}.
	 *
	 * @param key
	 *            must not be {@literal null}.
	 * @param value
	 * @return
	 * @see <a href="http://redis.io/commands/sismember">Redis Documentation:
	 *      SISMEMBER</a>
	 */
	public boolean sHasKey(String key, Object value) {
		try {
			return redisTemplate.opsForSet().isMember(key, value);
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Add given {@code values} to set at {@code key}.
	 *
	 * @param key
	 *            must not be {@literal null}.
	 * @param values
	 * @return
	 * @see <a href="http://redis.io/commands/sadd">Redis Documentation:
	 *      SADD</a>
	 */
	public long sSet(String key, Object... values) {
		try {
			return redisTemplate.opsForSet().add(key, values);
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * Add given {@code values} to set at {@code key}.
	 *
	 * @param key
	 *            must not be {@literal null}.
	 * @param values
	 * @return
	 * @see <a href="http://redis.io/commands/sadd">Redis Documentation:
	 *      SADD</a>
	 */
	public long sSetAndTime(String key, long time, Object... values) {
		try {
			Long count = redisTemplate.opsForSet().add(key, values);
			if (time > 0)
				expire(key, time);
			return count;
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * Get size of set at {@code key}.
	 *
	 * @param key
	 *            must not be {@literal null}.
	 * @return
	 * @see <a href="http://redis.io/commands/scard">Redis Documentation:
	 *      SCARD</a>
	 */
	public long sGetSetSize(String key) {
		try {
			return redisTemplate.opsForSet().size(key);
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * Remove given {@code values} from set at {@code key} and return the number
	 * of removed elements.
	 *
	 * @param key
	 *            must not be {@literal null}.
	 * @param values
	 * @return
	 * @see <a href="http://redis.io/commands/srem">Redis Documentation:
	 *      SREM</a>
	 */
	public long setRemove(String key, Object... values) {
		try {
			Long count = redisTemplate.opsForSet().remove(key, values);
			return count;
		} catch (Exception e) {
			return 0;
		}
	}

	// methods for list
	/**
	 * Get elements between {@code begin} and {@code end} from list at
	 * {@code key}.
	 *
	 * @param key
	 *            must not be {@literal null}.
	 * @param start
	 * @param end
	 * @return
	 * @see <a href="http://redis.io/commands/lrange">Redis Documentation:
	 *      LRANGE</a>
	 */
	public List<Object> lGet(String key, long start, long end) {
		try {
			return redisTemplate.opsForList().range(key, start, end);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Get the size of list stored at {@code key}.
	 *
	 * @param key
	 *            must not be {@literal null}.
	 * @return
	 * @see <a href="http://redis.io/commands/llen">Redis Documentation:
	 *      LLEN</a>
	 */
	public long lGetListSize(String key) {
		try {
			return redisTemplate.opsForList().size(key);
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * Get element at {@code index} form list at {@code key}.
	 *
	 * @param key
	 *            must not be {@literal null}.
	 * @param index
	 * @return
	 * @see <a href="http://redis.io/commands/lindex">Redis Documentation:
	 *      LINDEX</a>
	 */
	public Object lGetIndex(String key, long index) {
		try {
			return redisTemplate.opsForList().index(key, index);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Append {@code value} to {@code key}.
	 *
	 * @param key
	 *            must not be {@literal null}.
	 * @param value
	 * @return
	 * @see <a href="http://redis.io/commands/rpush">Redis Documentation:
	 *      RPUSH</a>
	 */
	public boolean lSet(String key, Object value) {
		try {
			redisTemplate.opsForList().rightPush(key, value);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Append {@code value} to {@code key}.
	 *
	 * @param key
	 *            must not be {@literal null}.
	 * @param value
	 * @param time
	 * @return
	 * @see <a href="http://redis.io/commands/rpush">Redis Documentation:
	 *      RPUSH</a>
	 */
	public boolean lSet(String key, Object value, long time) {
		try {
			redisTemplate.opsForList().rightPush(key, value);
			if (time > 0) {
				expire(key, time);
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Append {@code values} to {@code key}.
	 *
	 * @param key
	 *            must not be {@literal null}.
	 * @param value
	 * @return
	 * @see <a href="http://redis.io/commands/rpush">Redis Documentation:
	 *      RPUSH</a>
	 */
	public boolean lSet(String key, List<Object> value) {
		try {
			redisTemplate.opsForList().rightPushAll(key, value);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Append {@code values} to {@code key}.
	 *
	 * @param key
	 *            must not be {@literal null}.
	 * @param value
	 * @param time
	 * @return
	 * @see <a href="http://redis.io/commands/rpush">Redis Documentation:
	 *      RPUSH</a>
	 */
	public boolean lSet(String key, List<Object> value, long time) {
		try {
			redisTemplate.opsForList().rightPushAll(key, value);
			if (time > 0) {
				expire(key, time);
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Set the {@code value} list element at {@code index}.
	 *
	 * @param key
	 *            must not be {@literal null}.
	 * @param index
	 * @param value
	 * @see <a href="http://redis.io/commands/lset">Redis Documentation:
	 *      LSET</a>
	 */
	public boolean lUpdateIndex(String key, long index, Object value) {
		try {
			redisTemplate.opsForList().set(key, index, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Removes the first {@code count} occurrences of {@code value} from the
	 * list stored at {@code key}.
	 *
	 * @param key
	 *            must not be {@literal null}.
	 * @param count
	 * @param value
	 * @return
	 * @see <a href="http://redis.io/commands/lrem">Redis Documentation:
	 *      LREM</a>
	 */
	public long lRemove(String key, long count, Object value) {
		try {
			Long remove = redisTemplate.opsForList().remove(key, count, value);
			return remove;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	// Properties into by spring bean
	public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public RedisTemplate<String, Object> getRedisTemplate() {
		return redisTemplate;
	}

}
