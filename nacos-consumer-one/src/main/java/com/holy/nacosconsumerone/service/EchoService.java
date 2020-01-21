package com.holy.nacosconsumerone.service;

import com.holy.nacosconsumerone.configuration.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(contextId = "tt", name = "service-provider", fallback = EchoServiceFallback.class, configuration = FeignConfiguration.class)
public interface EchoService {
	@GetMapping(value = "/echo/{str}")
	String echo(@PathVariable("str") String str);

	@GetMapping(value = "/divide")
	String divide(@RequestParam("a") Integer a, @RequestParam("b") Integer b);

	default String divide(Integer a) {
		return divide(a, 0);
	}

	@GetMapping(value = "/notFound")
	String notFound();
}