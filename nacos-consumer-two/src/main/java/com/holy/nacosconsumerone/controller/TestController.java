package com.holy.nacosconsumerone.controller;

import com.holy.nacosconsumerone.pojo.UserInfo;
import com.holy.nacosconsumerone.service.EchoService;
import com.holy.nacosconsumerone.utils.RedisLock;
import com.holy.nacosconsumerone.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author xiaojing
 */
@RestController
public class TestController {

	@Autowired
	private RestTemplate restTemplateCommon;

	@Autowired
	private RestTemplate restTemplateCommon1;

	@Autowired
	private EchoService echoService;

	@Autowired
	private DiscoveryClient discoveryClient;

	@Resource
	private RedisUtil redisUtil;

	@Resource
	private RedisLock redisLock;

	// @PostConstruct
	// public void init() {
	// restTemplate1.setErrorHandler(new ResponseErrorHandler() {
	// @Override
	// public boolean hasError(ClientHttpResponse response) throws IOException {
	// return false;
	// }
	//
	// @Override
	// public void handleError(ClientHttpResponse response) throws IOException {
	// System.err.println("handle error");
	// }
	// });
	// }

	@GetMapping(value = "/echo-rest/{str}")
	public String rest(@PathVariable String str) {
		return restTemplateCommon.getForObject("http://service-provider/echo/" + str,
				String.class);
	}

	@GetMapping(value = "/index")
	public String index() {
		return restTemplateCommon1.getForObject("http://service-provider", String.class);
	}

	@GetMapping(value = "/test")
	public String test() {
		return restTemplateCommon1.getForObject("http://service-provider/test", String.class);
	}

	@GetMapping(value = "/sleep")
	public String sleep() {
		return restTemplateCommon1.getForObject("http://service-provider/sleep", String.class);
	}

	@GetMapping(value = "/notFound-feign")
	public String notFound() {
		return echoService.notFound();
	}

	@GetMapping(value = "/divide-feign")
	public String divide(@RequestParam Integer a, @RequestParam Integer b) {
		return echoService.divide(a, b);
	}

	@GetMapping(value = "/divide-feign2")
	public String divide(@RequestParam Integer a) {
		return echoService.divide(a);
	}

	@GetMapping(value = "/echo-feign/{str}")
	public String feign(@PathVariable String str) {
		return echoService.echo(str);
	}

	@GetMapping(value = "/sleep-feign")
	public String sleepFeign() {
		return echoService.sleep();
	}

	@GetMapping(value = "/services/{service}")
	public Object client(@PathVariable String service) {
		return discoveryClient.getInstances(service);
	}

	@GetMapping(value = "/services")
	public Object services() {
		return discoveryClient.getServices();
	}

	@GetMapping(value = "/redisTest")
	public Object redisTest() {
		redisLock.tryLock("lockKey","888",10,TimeUnit.SECONDS);
		int i = 10 ;
		Map<String,Object> map = new HashMap<>();
		while (i > 0 ){
//			UserInfo userInfo = new UserInfo();
//			userInfo.setUsername("holy"+i);
//			userInfo.setAge(i);
			map.put("holy"+i,i+"0000");
			i--;
		}
		redisUtil.hmset("map",map);
		System.out.println("lockKey:" + redisLock.get("lockKey"));

		return redisUtil.hmget("map");
	}

}
