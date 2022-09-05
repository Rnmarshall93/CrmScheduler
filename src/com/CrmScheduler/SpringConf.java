package com.CrmScheduler;

import com.CrmScheduler.DAO.*;
import com.CrmScheduler.HelperUtilties.TimeTools;
import org.springframework.context.annotation.*;

@Configuration
@EnableAspectJAutoProxy
//@ComponentScan(basePackages = "com.CrmScheduler.HelperUtilities")
@ComponentScan("com.CrmScheduler")
public class SpringConf {
    @Bean
    public TimeTools timeTools()
    {
        return new TimeTools();
    }

}
