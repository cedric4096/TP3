package thomas.isima.tp2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class Tp2Application implements ApplicationRunner {

	public static Logger log = LoggerFactory.getLogger("Tp2Application");

	@Autowired
	TravelService tfs;

	public static void main(String[] args) {
		SpringApplication.run(Tp2Application.class, args);
	}


	@Override
	public void run(ApplicationArguments args)throws Exception {
		log.info("Application started with command-line arguments: {}", Arrays.toString(args.getSourceArgs()));
		log.info("NonOptionArgs: {}", args.getNonOptionArgs());
		log.info("OptionNames: {}", args.getOptionNames());

		for (String name : args.getOptionNames()){
			log.info("arg-" + name + "=" + args.getOptionValues(name));
		}

		boolean containsOption = args.containsOption("person.name");
		log.info("Contains person.name: " + containsOption);

		if( !args.getNonOptionArgs().isEmpty()) {
			float r = tfs.feeCalculator(Float.parseFloat(args.getNonOptionArgs().get(0)));
			log.warn("Result is {}", r);
		}

	}

}
