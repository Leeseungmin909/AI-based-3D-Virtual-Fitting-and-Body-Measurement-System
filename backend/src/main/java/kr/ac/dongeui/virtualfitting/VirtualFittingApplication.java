package kr.ac.dongeui.virtualfitting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync // 비동기 백그라운드 작업 허용하는 어노테이션
@EnableJpaAuditing
@SpringBootApplication
public class VirtualFittingApplication {
	public static void main(String[] args) {
		SpringApplication.run(VirtualFittingApplication.class, args);
	}
}
