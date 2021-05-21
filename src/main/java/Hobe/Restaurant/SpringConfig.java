
package Hobe.Restaurant;

import Hobe.Restaurant.Service.MemberService;
import Hobe.Restaurant.Repository.JdbcMemberRepository;
import Hobe.Restaurant.Repository.MemberMemoryRepository;
import Hobe.Restaurant.Repository.MemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;


@Configuration
public class SpringConfig {
    private DataSource dataSource;

    public SpringConfig(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }
    @Bean
    public MemberRepository memberRepository() {
        //return new MemberMemoryRepository();
        return new JdbcMemberRepository(dataSource);
    }
}
