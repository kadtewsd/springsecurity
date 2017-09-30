package com.kasakaid.security.spring;

import com.kasakaid.security.infrastructure.authenticationprovider.SecurityAuthenticationProvider;
import com.kasakaid.security.infrastructure.session.SessionExpiredStrategy;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

@EnableWebSecurity
// @EnableWebMvc とか書くと、static のリソースのアクセスができなくなるので注意。
//@EnableWebMvc
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public final static String LOGIN_PAGE = "/login";

    //@Autowired
    private DataSource dataSource;

    @Autowired
    private Environment env;
    @Autowired
    private SessionExpiredStrategy sessionInformationExpiredStragegy;

    @Autowired
    private SecurityConfiguration securityConfiguration;

    @Autowired
    //private SecurityAuthenticationProvider authenticationProvider;
    private AuthenticationProvider authenticationProvider;

    final static String LOGIN_PROCESS_URL = "/authentication";

    @Override
    public void configure(WebSecurity web) throws Exception {
        // セキュリティ設定を無視するリクエスト設定
        // 静的リソース(images、css、javascript)に対するアクセスはセキュリティ設定を無視する
        web.ignoring().antMatchers(
                "/images/**",
                "/css/",
                "/js/**", // js はワイルドカードをつけないとブラウザーでエラーが発生する。
                "/webjars/**");
    }

    @SneakyThrows
    @Override
    protected void configure(HttpSecurity http) {

        http
                .authorizeRequests()
                // TODO Debug は削除
                .antMatchers(LOGIN_PAGE, LOGIN_PROCESS_URL, "/debug").permitAll()
//                .antMatchers("/css/**", "js/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf()
                .requireCsrfProtectionMatcher(csrfRequestMatcher())
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and()
                .formLogin()
                .loginProcessingUrl(LOGIN_PROCESS_URL)
                .loginPage(LOGIN_PAGE)
                .usernameParameter("username")
                .passwordParameter("password")
                .and()
                .logout()
                .deleteCookies("JSESSIONID")
                .logoutUrl("/auth/logout")
                .logoutSuccessUrl(LOGIN_PAGE + "?logout");

//        http.cors().disable();
        // NULL になる。
//        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
        http.addFilterBefore(securityConfiguration.authenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        // 生成されるセッションについて定義する。
//        http.sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
//                .maximumSessions(100)
//                .expiredSessionStrategy(sessionInformationExpiredStragegy)
//                // maximumSessions を指定しただけだと、上限を超えた場合は、古いセッションが破棄される。
//                // そのため、上限を超えてログインしてこようとしたらエラーを発生させる。
////                .maxSessionsPreventsLogin(true)
//                .expiredUrl(LOGIN_PAGE);
    }

    /**
     * 認証プロバイダーを登録します。
     * この場合、PaaS にアクセスして、ユーザー情報を取得し、その結果を持って認証可否を実施します。
     */
    @Override
    @SneakyThrows
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider);
        auth.eraseCredentials(false);
//        auth.userDetailsService(userDetailsService);
    }

    private RequestMatcher csrfRequestMatcher() {

        RequestMatcher csrfRequestMatcher = new RequestMatcher() {
            // CSRF対象外URL:
            private AntPathRequestMatcher[] requestMatchers = {
                    new AntPathRequestMatcher(LOGIN_PROCESS_URL),
                    new AntPathRequestMatcher(LOGIN_PAGE),
                    new AntPathRequestMatcher("/"),
                    new AntPathRequestMatcher("/debug")
//                    new AntPathRequestMatcher("/css/**"),
//                    new AntPathRequestMatcher("/js/**")
            };

            @Override
            public boolean matches(HttpServletRequest request) {
                for (AntPathRequestMatcher rm : requestMatchers) {
                    if (rm.matches(request)) {
                        return false;
                    }
                }
                return true;
            }
        };

        return csrfRequestMatcher;
    }
}

