package com.whdvlf94.myspringboot.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import javassist.SerialVersionUID;

public class Users implements Serializable {

	private static final long SerialVersionUID = 1L;

	@JacksonXmlProperty(localName = "User")
	@JacksonXmlElementWrapper(useWrapping = false) // <User> 태그 전체를 감싸는 <User> 태그 제거
	private List<User> users = new ArrayList<>();

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

}
