package com.authorization.api.redis;

import java.util.HashMap;
import java.util.Map;

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

		hash.putAll(access_token, map);
		
		System.out.println(access_token + " : " + hash.entries(access_token));

	}

	public Map<String, String> getUserInfo(String access_token) {

		HashOperations<String, String, String> hash = redisTemplate.opsForHash();

		Map<String, String> map = hash.entries(access_token);
//		System.out.println(access_token + " : " + map);

		return map;
	}

	public boolean tokenCheck(String access_token, String ip) {
		Map<String, String> map = getUserInfo(access_token);
		if (map == null || map.get("ip") == null || !map.get("ip").equals(ip)) {
			return false;
		}
		return true;
	}

}