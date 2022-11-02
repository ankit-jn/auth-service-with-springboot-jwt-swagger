package com.arjstack.tech;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.arjstack.tech.RestApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RestApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class RestApplicationTest {

	/**
	 * This method is to test the application context loading
	 */
	@Test
	public void contextLoads() {
	}

}
