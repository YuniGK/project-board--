package com.example.projectboard.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@Configuration//설정파일을 만들기 위한 애노테이션 or Bean을 등록하기 위한 애노테이션
@EnableJpaAuditing//자동으로 값을 등록할 수 있게 도와주는 내용에 대한 어노테이
public class JpaConfig {
    /* JPA 설정 모음 */

    /* AuditorAware 작성자를 저장 시키기 위한 설정
    * Entity의 값이 생성 되고 변경 될 때 누가 만들었는지, 누가 수정했는지 까지 자동으로 값을 업데이트 해주는 기능 */
    @Bean
    public AuditorAware<String> auditorAware(){
        return () -> Optional.of("yuni"); // TODO: 스프링 시큐리티로 인증 기능을붙이게 될 때, 수정필요
    }
}
