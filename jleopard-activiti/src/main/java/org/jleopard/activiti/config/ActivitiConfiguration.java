/**
 * @author (c) 2019. Chen_9g jleopard@126.com.
 * @date 2020/2/9  18:00
 * @version 1.0
 *
 * <p>
 * Find a way for success and not make excuses for failure.
 * </p>
 */
package org.jleopard.activiti.config;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class ActivitiConfiguration {

    @Autowired
    private DataSource dataSource;

    @Bean
    public ProcessEngineConfiguration processEngineConfiguration() {
        ProcessEngineConfiguration configuration = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();
        //配置数据源
        configuration.setDataSource(dataSource);
        //如果表不存在就创建
        configuration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        //关闭计时器
        configuration.setAsyncExecutorActivate(false);
        configuration.setProcessDiagramGenerator(new DefaultProcessDiagramGenerator());
        return configuration;
    }
    //得到程序执行引擎 所有操作对应的表几乎都是通过他进行获取
    @Bean
    public ProcessEngine processEngine() {
        return processEngineConfiguration().buildProcessEngine();
    }
}
