package com.inditex.test;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication @EnableRetry
@Log4j2
public class InditexMain
{
	public static void main(String[] args)
	{
		SpringApplication.run(InditexMain.class, args);

		log.info("---------------------------------------------------------------------------------------------------");
		log.info("                         ,,    ,,                                                                   ");
		log.info("`7MMF'                 `7MM    db    mm                              mm                       mm    ");
		log.info("  MM                     MM          MM                              MM                       MM    ");
		log.info("  MM  `7MMpMMMb.    ,M\"\"bMM  `7MM  mmMMmm   .gP\"Ya  `7M'   `MF'    mmMMmm   .gP\"Ya  ,pP\"Ybd mmMMmm  ");
		log.info("  MM    MM    MM  ,AP    MM    MM    MM    ,M'   Yb   `VA ,V'        MM    ,M'   Yb 8I   `\"   MM    ");
		log.info("  MM    MM    MM  8MI    MM    MM    MM    8M\"\"\"\"\"\"     XMX          MM    8M\"\"\"\"\"\" `YMMMa.   MM    ");
		log.info("  MM    MM    MM  `Mb    MM    MM    MM    YM.    ,   ,V' VA.        MM    YM.    , L.   I8   MM    ");
		log.info(".JMML..JMML  JMML. `Wbmd\"MML..JMML.  `Mbmo  `Mbmmd' .AM.   .MA.      `Mbmo  `Mbmmd' M9mmmP'   `Mbmo");
		log.info("");
		log.info("---------------------------------------------------------------------------------------------------");
		log.info("Inditext test application UP AND RUNNING at port 8080");
		log.info("ALL StartUp initialization completed succesfully...");
		log.info("---------------------------------------------------------------------------------------------------");
	}
}