package com;

import com.ngb.xml.globals.Helper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.ngb.xml.ui.LoginForm;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

@SpringBootApplication
public class XmlwithcertificateApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = (new SpringApplicationBuilder(new Class[]{XmlwithcertificateApplication.class})).headless(false).run(args);
		EventQueue.invokeLater(() -> {

			SpringApplication.run(XmlwithcertificateApplication.class, args);





			LoginForm.run();
			Timer timer = new Timer();
			TimerTask task = new Helper();
			timer.schedule(task,25*60000,25*60000);
			//BillGenerator ex = (BillGenerator)ctx.getBean(BillGenerator.class);
			//ex.startPdfBillGenerator();
		});


	}

}
