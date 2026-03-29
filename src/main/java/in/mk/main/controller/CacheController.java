package in.mk.main.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.mk.main.endpoint.CacheEndPoint;
import in.mk.main.service.CacheManagerService;
import in.mk.main.util.CommonUtil;

@RestController
public class CacheController implements CacheEndPoint {

	@Autowired
	private CacheManagerService cacheManagerService;
	
	
	
	
	@Override
	public ResponseEntity<?> getAllCache() {
	Collection<String>	cache=cacheManagerService.getCache();
		return CommonUtil.createBuildResponse(cache,HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> getCache(String cache_name) {
	Cache cacheName =cacheManagerService.getCacheName(cache_name);
		return CommonUtil.createBuildResponse(cacheName, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> removeAllCache() {
		=cacheManagerService.removeAllCache();
		return CommonUtil.createBuildResponseMessage("Remove caches", HttpStatus.OK);
	}

	
	
	
}
