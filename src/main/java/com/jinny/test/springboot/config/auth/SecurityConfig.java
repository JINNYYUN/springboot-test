package com.jinny.test.springboot.config.auth;

import com.jinny.test.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable() //h2-console화면을 사용하기 위해 해당 옵션들을 disable한다.
                .and()
                    .authorizeRequests()//URL별 권한 관리를 설정하는 옵션. authorizeRequests가 선언되어야만 antMatchers옵션을 사용할 수 있다.
                    .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile").permitAll()
                    .antMatchers("/api/v1/**").hasRole(Role.USER.name())//"/api/v1/**"주소를 가진 API는 USER권한을 가진 사람만 가능
                    .anyRequest().authenticated() //설정값들 이외의 나머지 URL은 모두 인증된 사용자(로그인한 사용자)들에게만 서용
                .and()
                    .logout()
                        .logoutSuccessUrl("/")//로그아웃 성공시 /주소로 이동합니다.
                .and()
                    .oauth2Login()//OAuth2로그인 기능에 대한 여러 설정의 진입점
                        .userInfoEndpoint()//OAuth로그인 성공 이후 사용자 정보를 가져올 떄의 설정들을 담당합니다.
                            .userService(customOAuth2UserService);   //소셜로그인 성공 시 후속 조치를 진행할 UserService인터페이스의 구현체를 등록한다.
                                //리소스 서버(즉, 소셜 서비스들)에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능을 명시할 수 있다.
    }
}