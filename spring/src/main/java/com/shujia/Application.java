package com.shujia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 注解：  对类方法，和属性的一个标记，  可以通过返回获取到标记，对比不同的标记做不同的处理
 *
 */


@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
