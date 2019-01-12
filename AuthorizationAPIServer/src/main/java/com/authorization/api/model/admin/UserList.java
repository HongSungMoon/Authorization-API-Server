package com.authorization.api.model.admin;

import java.util.List;

import com.authorization.api.model.UserInfo;

public class UserList {
	
	String result;
	
	List<UserInfo> list;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public List<UserInfo> getList() {
		return list;
	}

	public void setList(List<UserInfo> list) {
		this.list = list;
	}

}
