package cn.itsource.constants;

/**
 * @description: 常量类
 * @auth: tangrufei
 * @date: 2022-08-27 15:13
 */
public class BaseConstants {

    public class MealConstants{
        //套餐状态：正常
        public static final int STATUS_NORMAL = 0;
        //套餐状态：禁用
        public static final int STATUS_DISABLE = 1;
    }

    //员工相关常量配置类
    public class EmployeeConstants{
        //员工状态（状态：0正常，1锁定，2注销）
        public static final int STATUS_EMPLOYEE_0 = 0;
        public static final int STATUS_EMPLOYEE_1 = 1;
        public static final int STATUS_EMPLOYEE_2 = 2;
    }

    //Redis的key常量
    public class RedisConstants{
        public static final String COURSE_TYPE = "CourseType";
    }

    //RabbitMQ常量类
    public class RabbitMQConstants{
        public static final String EMAIL_QUEUE = "email_queue_0907";
    }
}
