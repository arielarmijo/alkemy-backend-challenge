package tk.monkeycode.backendchallenge.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@PropertySource("classpath:application.properties")
public class SendGridEmailService implements EmailService {
	
	@Value("${sendgrid.api.key}") 
	private String SENDGRID_API_KEY;

	@Override
	public void sendTextEmail(String para) {
		// the sender email should be the same as we used to Create a Single Sender Verification
		    Email from = new Email("tumorlike@gmail.com");
		    String subject = "Alkemy Backend Challenge";
		    Email to = new Email(para);
		    Content content = new Content("text/plain", "Bienvenido a la API de Disney");
		    Mail mail = new Mail(from, subject, to, content);
		
		    SendGrid sg = new SendGrid(SENDGRID_API_KEY);
		    Request request = new Request();
		    try {
		      request.setMethod(Method.POST);
		      request.setEndpoint("mail/send");
		      request.setBody(mail.build());
		      Response response = sg.api(request);
		      log.info("Status code: {}", response.getStatusCode());
		      log.info("Headers: {}", response.getHeaders());
		      log.info("Body: {}", response.getBody());
		    } catch (IOException ex) {
		      log.error("Error al enviar correo de bienvenida: {}", ex.getMessage());
		    }	   
	}
}
