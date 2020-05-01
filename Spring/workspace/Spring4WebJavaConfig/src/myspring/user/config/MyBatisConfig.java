package myspring.user.config;

import java.io.IOException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@MapperScan(
//		annotationClass = UserMapper.class,
        basePackages="myspring.user.dao.mapper",
        sqlSessionFactoryRef="sqlSessionFactoryBean")
public class MyBatisConfig {
	 /**
     * myBatis�� {@link org.apache.ibatis.session.SqlSessionFactory}�� �����ϴ� ���丮���� ����Ѵ�.
     */
    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(
            DataSource dataSource, ApplicationContext applicationContext) throws IOException {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        // ���̹�Ƽ���� ����� DataSource�� ���
        factoryBean.setDataSource(dataSource);
        // ���̹�Ƽ�� �������� ��ġ ����
        factoryBean.setConfigLocation(applicationContext.getResource("classpath:config/SqlMapConfig.xml"));
        // com.lge.apip.mgmt.ocpo.*.model ��Ű�� ������ model Ŭ���� �̸��� ª�� ��Ī���� ���
        factoryBean.setTypeAliasesPackage("myspring.user.vo");
        // META-INF/mybatis/mappers ��Ű�� ������ ��� XML�� ���۷� ���
//        factoryBean.setMapperLocations(applicationContext.getResources("classpath:config/User.xml"));
        return factoryBean;
    }

    /**
     * MyBatis {@link org.apache.ibatis.session.SqlSession} ���� ����Ѵ�.
     *
     * SqlSessionTemplate�� SqlSession�� �����ϰ� �ڵ忡�� SqlSession�� ��ü�ϴ� ������ �Ѵ�.
     * �����忡 �����ϰ� �ۼ��Ǿ��� ������ ���� DAO�� ���ۿ��� ���� �� �� �ִ�.
     */
    @Bean
    public SqlSessionTemplate sqlSession(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
    
	
}
