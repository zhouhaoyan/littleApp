package com.megatron.picserver;

import com.google.gson.Gson;
import com.megatron.picserver.utils.UploadUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("com.megatron.picserver.dao")
@EnableTransactionManagement
public class PicServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PicServerApplication.class, args);
	}

	@Bean
	public UploadUtil uploadUtil(){
		return new UploadUtil();
	}

	@Bean
	public Gson gson(){
		return new Gson();

	}
}


