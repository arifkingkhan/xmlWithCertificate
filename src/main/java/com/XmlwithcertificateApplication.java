package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.ngb.xml.ui.LoginForm;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.awt.*;

@SpringBootApplication
public class XmlwithcertificateApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = (new SpringApplicationBuilder(new Class[]{XmlwithcertificateApplication.class})).headless(false).run(args);
		EventQueue.invokeLater(() -> {

			SpringApplication.run(XmlwithcertificateApplication.class, args);

			LoginForm.run();
			//BillGenerator ex = (BillGenerator)ctx.getBean(BillGenerator.class);
			//ex.startPdfBillGenerator();
		});


	}

}
