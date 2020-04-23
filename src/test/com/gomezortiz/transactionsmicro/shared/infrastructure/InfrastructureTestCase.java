package com.gomezortiz.transactionsmicro.shared.infrastructure;

import org.flywaydb.test.annotation.FlywayTest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@FlywayTest
@MybatisTest
@ActiveProfiles("test")
public class InfrastructureTestCase {
}
