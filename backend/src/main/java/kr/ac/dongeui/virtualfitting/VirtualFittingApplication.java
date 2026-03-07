package kr.ac.dongeui.virtualfitting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;

@SpringBootApplication
public class VirtualFittingApplication {
	public static void main(String[] args) {
		SpringApplication.run(VirtualFittingApplication.class, args);
	}
}
