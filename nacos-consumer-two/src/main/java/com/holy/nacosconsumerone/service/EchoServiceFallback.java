package com.holy.nacosconsumerone.service;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


public class EchoServiceFallback implements EchoService {
    @Override
    public String echo(@PathVariable("str") String str) {
        return "echo fallback";
    }

    @Override
    public String divide(@RequestParam Integer a, @RequestParam Integer b) {
        return "divide fallback";
    }

    @Override
    public String sleep() {
        return "sleep fallback";
    }

    @Override
    public String notFound() {
        return "notFound fallback";
    }
}
