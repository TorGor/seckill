package com.holy.nacosconsumerone.controller;

import com.holy.nacosconsumerone.pojo.SeckillResult;
import com.holy.nacosconsumerone.pojo.UserInfo;
import com.holy.nacosconsumerone.service.SecKillService;
import com.holy.nacosconsumerone.utils.RedisLock;
import com.holy.nacosconsumerone.utils.RedisUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author holy
 */
@RestController
public class SecKillController {

	@Resource
	private RedisUtils redisUtil;

	@Resource
	private RedisLock redisLock;

	@Resource
	private SecKillService secKillService;

	/**
	 *
	 * @return
	 */
	@GetMapping(value = "/seckillAll")
	@ResponseBody
	public SeckillResult redisTest() {
		SeckillResult result ;
		try {
			result = secKillService.getAllSecKill();
		}catch (Exception e){
			result = new SeckillResult(false,e.getMessage());
			return result;
		}
		return result;
	}

	@GetMapping(value = "/{secKillId}/exposer")
	@ResponseBody
	public SeckillResult getSecKillExposer(@PathVariable("secKillId") Long secKillId){
		SeckillResult result ;
		try{
			result = secKillService.getExposer(secKillId);
		}catch (Exception e){
			result = new SeckillResult(false,e.getMessage());
			return result;
		}
		return result;
	}

	@PostMapping(value = "/{secKillId}/{md5}/seckill")
	@ResponseBody
	public SeckillResult execution(@PathVariable("secKillId") Long secKillId,
								   @PathVariable("md5") String md5,
								   @RequestParam(name = "phone") Long phone){
		SeckillResult result ;
		try{
			result = secKillService.executeSeckill(secKillId,md5,phone);
		}catch (Exception e){
			e.printStackTrace();
			result = new SeckillResult(false,e.getMessage());
			return result;
		}
		return result;
	}

}
