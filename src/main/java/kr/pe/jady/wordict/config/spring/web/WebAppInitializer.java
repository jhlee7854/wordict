package kr.pe.jady.wordict.config.spring.web;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import kr.pe.jady.wordict.config.logging.LogbackConfig;
import kr.pe.jady.wordict.config.tiles.TilesConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] {LogbackConfig.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] {WebConfig.class, TilesConfig.class};
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] {"/"};
	}
	
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		super.onStartup(servletContext);
		// logback설정을 xml을 이용할 경우 아래의 주석을 해제한다. 현재는 Java 기반 설정이다.
		//servletContext.addListener(new LogbackConfigListener());
		//servletContext.setInitParameter("logbackConfigLocation", "classpath:kr/pe/jady/wordict/config/logging/logback.xml");
	}

}
