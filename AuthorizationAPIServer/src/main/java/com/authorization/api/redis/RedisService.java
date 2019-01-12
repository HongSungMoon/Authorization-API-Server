package com.authorization.api.redis;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.authorization.api.model.RedisUserInfo;

@Service
public class RedisService {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	public void insertToken(String access_token, RedisUserInfo redisUserInfo) {

		HashOperations<String, String, String> hash = redisTemplate.opsForHash();

		Map<String, String> map = new HashMap<>();
		map.put("ip", redisUserInfo.getIp());
		map.put("timeStamp", redisUserInfo.getTimestamp());
		map.put("id", redisUserInfo.getTimestamp());

		hash.putAll(access_token, map);
		
		System.out.println(access_token + " : " + hash.entries(access_token));

	}

	public Map<String, String> getUserInfo(String access_token) {
		
		if(access_token == null)
			return null;

		HashOperations<String, String, String> hash = redisTemplate.opsForHash();

		Map<String, String> map = hash.entries(access_token);

		return map;
	}

	public String tokenCheck(String access_token, String ip) {
		
		Map<String, String> map = getUserInfo(access_token);
		
		if (map == null || map.get("ip") == null || !map.get("ip").equals(ip)) {
			return null;
		}
		// 토큰 만료 시간 연장
		redisTemplate.expire(access_token, 600L, TimeUnit.SECONDS);
		return map.get("user_type");
	}

}