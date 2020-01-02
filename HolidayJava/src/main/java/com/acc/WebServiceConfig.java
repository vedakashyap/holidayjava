package com.acc;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;


@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter{

	@Bean
	public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext){
		MessageDispatcherServlet servlet = new MessageDispatcherServlet();
		servlet.setApplicationContext(applicationContext);
		servlet.setTransformWsdlLocations(true);
		return new ServletRegistrationBean(servlet,"/ws/*");
	}
	@Bean(name = "holiday") 
	public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema hrSchema) {
	    DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
	    wsdl11Definition.setPortTypeName("HolidayRequest");
	    wsdl11Definition.setLocationUri("/ws");
	    wsdl11Definition.setTargetNamespace("http://mycompany.com/hr/schemas");
	    wsdl11Definition.setSchema(hrSchema);
	    return wsdl11Definition;
	}
	 
	@Bean
	public XsdSchema hrSchema() {
	    return new SimpleXsdSchema(new ClassPathResource("hr2.xsd"));
	}
}
