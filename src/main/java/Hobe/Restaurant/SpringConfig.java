
package Hobe.Restaurant;

import Hobe.Restaurant.Service.*;
import Hobe.Restaurant.Repository.*;

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


    @Bean
    public TableService tableService() {
        return new TableService(tableRepository());
    }
    @Bean
    public TableRepository tableRepository() {
        //return new TableMemoryRepository();
        return new JdbcTableRepository(dataSource);
    }


    @Bean
    public BookingService bookingService() {
        return new BookingService(bookingRepository(), tableService(), memberService());
    }
    @Bean
    public BookingRepository bookingRepository() {
        //return new BookingMemory();
        return new JdbcBookingRepository(dataSource);
    }
    @Bean
    public AdminRepository adminRepository(){
        return new JdbcAdminRepository(dataSource);
    }

    @Bean
    public AdminService adminService(){
        return new AdminService(adminRepository());
    }

    @Bean
    public ReviewRepository reviewRepository(){
        return new JdbcReviewRepository(dataSource);
    }

    @Bean
    public ReviewService reviewService() {
        return new ReviewService(reviewRepository(), memberRepository());
    }
    }





