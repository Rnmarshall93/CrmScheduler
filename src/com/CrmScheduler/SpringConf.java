package com.CrmScheduler;

import com.CrmScheduler.DAO.*;
import org.springframework.context.annotation.*;

@Configuration
@EnableAspectJAutoProxy
//@ComponentScan(basePackages = "com.CrmScheduler.HelperUtilities")
@ComponentScan("com.CrmScheduler")
public class SpringConf {
/*    @Bean
    public IAppointmentDao appointmentDaoImplSql() {
        return new AppointmentDaoImplSql();
    }

    @Bean
    public ContactsDaoImplSql contactsDaoImplSql() {
        return new ContactsDaoImplSql();
    }

    @Bean
    public CountriesDaoImplSql countriesDaoImplSql(){
        return new CountriesDaoImplSql();
    }

    @Bean
    public CustomerDaoImplSql customerDaoImplSql() {
        return new CustomerDaoImplSql();
    }

    @Bean
    public FirstLevelDivisionsDaoImplSql firstLevelDivisionsDaoImplSql(){
        return new FirstLevelDivisionsDaoImplSql();
    }

*/
}
