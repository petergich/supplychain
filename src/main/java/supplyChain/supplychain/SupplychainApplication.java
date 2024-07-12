package supplyChain.supplychain;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import springfox.documentation.oas.annotations.EnableOpenApi;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
//@EnableSwagger2
//@EnableOpenApi
@OpenAPIDefinition(
		info = @Info(
				title = "Loans microservice REST API Documentation",
				description = "SupplyChain",
				version = "v1",
				contact = @Contact(
						name = "Manasses chege",
						email = "petergich80@gmail.com",
						url = "https://www.linkedin.com/in/petergich/?originalSubdomain=ke"
				),
				license = @License(
						name = "Apache 2.0",
						url="https://www.linkedin.com/in/petergich/?originalSubdomain=ke"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Supplychain",
				url = "https://www.linkedin.com/in/manasses-chege-b133b3269/?originalSubdomain=ke"
		)
)
public class SupplychainApplication {

	public static void main(String[] args) {
		SpringApplication.run(SupplychainApplication.class, args);
	}

}
