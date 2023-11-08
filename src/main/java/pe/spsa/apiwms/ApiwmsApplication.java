package pe.spsa.apiwms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import pe.spsa.apiwms.repository.RepoApi;

@SpringBootApplication

public class ApiwmsApplication implements CommandLineRunner {
	private final Logger log = LoggerFactory.getLogger(ApiwmsApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(ApiwmsApplication.class, args);
	}
	@Autowired
	RepoApi repoApi;

	@Override
	public void run(String... args) throws Exception {

		repoApi.getHead("OS94900017755","1432");

		log.info("fin process...");
	}
}
