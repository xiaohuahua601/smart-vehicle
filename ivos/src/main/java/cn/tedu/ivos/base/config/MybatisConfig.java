package cn.tedu.ivos.base.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("cn.tedu.ivos.*.mapper")
public class MybatisConfig {
}