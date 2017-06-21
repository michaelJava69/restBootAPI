package org.rbsg.controller;


import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rbsg.config.SpringBootWebApplication;
 
 
import org.rbsg.model.PrimesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import org.springframework.boot.test.SpringApplicationConfiguration;

import com.jayway.jsonpath.JsonPath;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
 
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.apache.log4j.Logger;

@RunWith(SpringJUnit4ClassRunner.class)
//ContextConfiguration(classes =  PrimesController.class ) 

/*
 * The below ContextCONFIGURATION NEEDS TO POINT AT THE MAIN MVC CONFIGURATION FILE
 * @ContextConfiguration("file:src/main/webapp/WEB-INF/springrest-servlet.xml") 
 * 
 * If you are using SpringBoot and intializing with a class  (from 
 *       org.springframework.boot.test.SpringApplicationConfiguration)
 *       
 * @ContextConfiguration(classes = SpringBootWebApplication.class)  With Boot
 *     
 * No class found : com/jayway/jsonpath/Predicate
 * <dependency>
           <groupId>com.jayway.jsonpath</groupId>
           <artifactId>json-path</artifactId>
           <version>2.2.0</version>
           <scope>test</scope>
        </dependency>
        
    Although left at 0.9.1 to enable SpringMVC to work in Maven mode
    
    Also becuase Spring Book uses character encoding UT8  need to compare with this    
 */

//@ContextConfiguration("file:src/main/webapp/WEB-INF/springrest-servlet.xml")  

//@SpringApplicationConfiguration(classes = Application.class)
@ContextConfiguration(classes = SpringBootWebApplication.class) 

@RestController
@WebAppConfiguration
public class PrimesControllerTest {

	
	final static Logger logger = Logger.getLogger(PrimesControllerTest.class);
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc ;
    private MvcResult  result; 

    
    
    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    	
    }

    @Test
    public void canFetchPrimeNumbers() throws Exception {
        // Given
        final int upperLimit = 10;

        logger.info(" canFetchPrimeNumbers Michael== "  + mockMvc);
        
      
        // When
        try {  
        	        MockHttpServletRequestBuilder builder =
                    MockMvcRequestBuilders.get(("/primes/" + upperLimit)
               		);
                    
        	        this.mockMvc.perform(builder.accept(MediaType.APPLICATION_JSON))
                        
        	        
        	            .andExpect(MockMvcResultMatchers.status().isOk())
        	            .andExpect(content().contentType("application/json;charset=UTF-8"))
        	             //.andExpect(content().contentType("application/json"))
        	             .andExpect(jsonPath(("$.initial"), is(10)))  
        	             .andExpect(jsonPath("$.primes", contains(2, 3, 5, 7)))   
                         .andDo(MockMvcResultHandlers.print());                      

    
               
        } catch (Exception e) {
            e.printStackTrace();
        }


        logger.info(" canFetchPrimeNumbers == "  + mockMvc);
    }

	 

}