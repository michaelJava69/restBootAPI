package org.rbsg.controller;

import org.apache.log4j.Logger;

 
import org.rbsg.model.PrimesResponse;
import org.rbsg.service.PrimeNumberService;
//import org.rbsg.service.PrimeService;
import org.springframework.cache.annotation.Cacheable;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
//import java.util.ArrayList;  
//import java.util.List;  

//import net.sf.ehcache.Cache;
//import net.sf.ehcache.CacheManager;
//import net.sf.ehcache.Element;

/***
 * @author micha
 * 
 * I will use Spring 4.0. I do not need to provide any view information in�
 * the springrest-servlet.xml.
 * In Spring 3 (Spring MVC) if we need to directly get resource from controller we
 * would return�@ResponseBody. However with Spring 4, we can use�@RestController for that.
 * In spring 4.0, we can use�@RestController which is combination of�@Controller + @ResponseBody.
 * 
 * Spring will load�Jackson2JsonMessageConverter into its application context automatically. 
 * Whenever you request resource as json with accept�headers=�Accept=application/json�, then 
 * Jackson2JsonMessageConverter comes into picture and convert resource to json format.
 *  
 *  Run : http://localhost:8080/primesanoncache/10
 *  
 */
 
//@Controller 

/*
 * will not work. Results in https//localhost:8080/restServiceApi/primes/primes/10 
 */

@RestController  
@RequestMapping("/primesanoncache")
public class PrimesControllerAnonCache {

	final static Logger logger = Logger.getLogger(PrimesControllerAnonCache.class);

	    /*
         * Method for the PrimesController class
         *
  	     * @param upperLimit - Prime Number upper limit  
  	     *                     UpperLimit be (final) too indicate that it can not be changed)
         *  
         * 
         * 
         */
	
	     @RequestMapping(value = "/{upperLimit}", method = RequestMethod.GET,headers="Accept=application/json")
	     @Cacheable(value = "primes", key = "#upperLimit")
	     public PrimesResponse getPrimeNumbers(@PathVariable Integer upperLimit ) {
	   	
	        
	   	 logger.info(" **** Test : Inside PrimeController  ***"); 
	   	  
	   	 /*
	   	  * Custom cache
	   	  * 
	   	  * This is where the caching logic resides. I check cache and if not populated i carry out the 
	   	  * PrimesNumber task. If it is I get it from the Cache.
	   	  * 
	   	  * To emphasis the wait time when not getting from cache I have deliberately delayed by 2 secs 
	   	  * the call outside of the cache
	   	  * 
	   	  */
	   	 
	       PrimesResponse primesResponse; 
	       //Cache xyz = CacheManager.getInstance().getCache("primes");
	       PrimeNumberService primeService = new PrimeNumberService();
	       logger.info( "Getting data outside of the cachce.********....." );
	   	  
       	       
	       primesResponse = new PrimesResponse(upperLimit, primeService.getPrimeNumbers(upperLimit));           
	       return primesResponse   ;
	     }
}

