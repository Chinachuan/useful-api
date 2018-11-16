package com.useful.api.user;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.useful.api.BaseApiResult;

@FeignClient(name = "api", path = "user", url = "${project.gateway.hostName}")
public interface UserHander {

	@RequestMapping(value = "/adduser",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	BaseApiResult adduser(@RequestParam("userName") String userName);

}
