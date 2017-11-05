package com.fsd.supportservces.edgeserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.net.ssl.HttpsURLConnection;

@SpringBootApplication
@Controller
@EnableZuulProxy
public class EdgeServerApplication {

	private static final Logger LOG = LoggerFactory.getLogger(EdgeServerApplication.class);

	static {
		// for localhost testing only
		LOG.warn("Will now disable hostname check in SSL, only to be used during development");
		HttpsURLConnection.setDefaultHostnameVerifier((hostname, sslSession) -> true);
	}

	public static void main(String[] args) {
		int buildNo = 15;
		LOG.info("Edge-server, starting build no. {}...", buildNo);

		new SpringApplicationBuilder(EdgeServerApplication.class).web(true).run(args);

		LOG.info("Edge-server, build no. {} started", buildNo);
	}


	// to allows Cross-Origin-Requests
	@Bean
	public CorsFilter corsFilter() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		final CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("OPTIONS");
		config.addAllowedMethod("HEAD");
		config.addAllowedMethod("GET");
		config.addAllowedMethod("PUT");
		config.addAllowedMethod("POST");
		config.addAllowedMethod("DELETE");
		config.addAllowedMethod("PATCH");
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}
}
