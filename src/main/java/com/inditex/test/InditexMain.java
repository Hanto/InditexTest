package com.inditex.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class InditexMain
{
	public static void main(String[] args)
	{	SpringApplication.run(InditexMain.class, args); }
}