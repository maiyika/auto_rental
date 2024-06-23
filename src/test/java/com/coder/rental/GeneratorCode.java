package com.coder.rental;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;
import java.util.Collections;

/**
 * @author Barry
 * @project auto_rental
 * @date 16/6/2024
 */

@SpringBootTest
public class GeneratorCode {
	private static final String AUTHOR = "Barry";
	private static final String JDBC_URL = "jdbc:mysql://localhost:3306/auto_rental";
	private static final String JDBC_USERNAME = "root";
	private static final String JDBC_PASSWORD = "123456";
	private static final String OutputDir = "./src/main/java";
	private static final String PackageName = "com.coder";
	private static final String ModuleName = "rental";
	private static final String[] TableNames = {
			"auto_maker", "auto_brand", "auto_info",
			"sys_dept", "sys_permission", "sys_role", "sys_user", "sys_user_role", "sys_role_permission",
			"busi_customer", "busi_maintain", "busi_violation", "busi_order", "busi_rental_type"
	};
	private static final String[] Prefix = { "sys_", "busi_" };

	@Test
	void generateCode() {
		FastAutoGenerator.create(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD)
				.globalConfig(builder -> {
					builder.author(AUTHOR)
							.enableSwagger()
							.outputDir(OutputDir);
				})
				.packageConfig(builder -> {
					builder.parent(PackageName)
							.moduleName(ModuleName)
							.pathInfo(Collections.singletonMap(OutputFile.xml,
									"./src/main/resources/mapper"));
				})
				.strategyConfig(builder -> {
					builder.addInclude(TableNames)
							.addTablePrefix(Prefix)
							.entityBuilder()
							.enableLombok()
							.enableChainModel()
							.controllerBuilder()
							.enableRestStyle();
				})
				.templateEngine(new FreemarkerTemplateEngine())
				.execute();
	}
}
