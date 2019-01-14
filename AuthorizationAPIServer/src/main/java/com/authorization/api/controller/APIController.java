package com.authorization.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.authorization.api.model.UserInfo;
import com.authorization.api.model.admin.UserList;
import com.authorization.api.redis.RedisService;
import com.authorization.api.service.UserService;
import com.authorization.api.utils.IPAddressUtil;

@Controller
@CrossOrigin
@RequestMapping("/api") 
public class APIController {
	
	@Autowired
	private RedisService redisService;
	
	@Autowired
	private UserService userService;
	
	private IPAddressUtil ipAddressUtil = new IPAddressUtil();
	
	@RequestMapping(value = "/hello", method = RequestMethod.POST)
	public ResponseEntity<String> hello(@RequestBody String access_token) {
		
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		
		String ip = ipAddressUtil.getIPAddress();
		
		// ip와 token을 통한 token check
		if (redisService.tokenCheck(access_token, ip) != null) {
			status = HttpStatus.OK;
		}
		
		return new ResponseEntity<String>("Hello Word!", status);
		
	}
	
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public ResponseEntity<String> modify(@RequestBody UserInfo userInfo) {
		
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		String result = "Fail";
		
		String ip = ipAddressUtil.getIPAddress();
		
		if (redisService.tokenCheck(userInfo.getAccess_token(), ip) != null) {
			status = HttpStatus.OK;
			userService.modifyUser(userInfo);	
			result = "success";
		}
		
		return new ResponseEntity<String>(result, status);
		
	}
	
	@CrossOrigin
	@RequestMapping(value = "/get/user", method = RequestMethod.POST)
	public ResponseEntity<UserList> getUserList(@RequestBody UserInfo userInfo) {
		
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		UserList userList = new UserList();
		userList.setResult("fail");
	
		String ip = ipAddressUtil.getIPAddress();
		String userType = redisService.tokenCheck(userInfo.getAccess_token(), ip);
		
		if (userType.equals("admin")) {
			if(userService.checkAdmin(userInfo)) {
				status = HttpStatus.OK;
				userList.setList(userService.getUserList());	
				userList.setResult("success");
			} 
		}
		
		return new ResponseEntity<UserList>(userList, status);
		
	}
	
	
	
}
