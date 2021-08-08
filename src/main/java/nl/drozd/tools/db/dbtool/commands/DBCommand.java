package nl.drozd.tools.db.dbtool.commands;

import java.util.concurrent.Callable;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Spec;
import picocli.CommandLine.ParameterException;

/**
 * Base class used in
 */
public abstract class DBCommand extends BasicCommand implements Callable<Integer> {

	@Spec
	CommandSpec commandSpec;
	
	HikariConfig config = new HikariConfig();

	@Option(names = { "--user", "--username", "-u" }, description = "Database username", paramLabel = "USER")
	public String dbUser;

	@Option(names = { "--pass", "--password", "-p" }, description = "Database password", paramLabel = "PASSWORD")
	public String dbPassword;

	@Option(names = { "--url", "-j" }, description = "Database url", paramLabel = "URL", required = true)
	public String dbURL;

	@Parameters(arity = "1", defaultValue = "", description = "SQL Command to execute", paramLabel = "SQL_COMMAND")
	String sqlCommand;

	@Override
	public Integer call() throws Exception {
		this.changeLogLevel(this.logLevel.name().toUpperCase());

		// validate SQL if it's technicaly valid
		
		if(!isStatementValid(this.sqlCommand)) {
			//TODO: Implement sql statement validation with sql parser
		}
		
		
		
		// Simple data source configuration
		
		config.setJdbcUrl(this.dbURL);
		
		config.setDriverClassName(org.h2.Driver.class.getName());

		if (this.authRequired()) {

			config.setUsername(this.dbUser);
			config.setPassword(this.dbPassword);
		}

		// This data source is closable :)
		try (HikariDataSource ds = new HikariDataSource(config)) {
			return this.excute(ds);
		}

	}

	private boolean isStatementValid(String sqlCommand2) {
		return true;
	}

	private boolean authRequired() {

		if (this.dbURL.startsWith("jdbc:sqlite:")) {
			return false;
		}

		if (this.dbUser == null || this.dbUser.isBlank()) {
			throw new ParameterException(commandSpec.commandLine(),
					"Missing database username");
		}

		if (this.dbPassword == null || this.dbPassword.isBlank()) {
			throw new ParameterException(commandSpec.commandLine(),
					"Missing database password");
		}
		return true;
	}

	/**
	 * For all the work this method needs to be implemented
	 * 
	 * @param ds Data source to use in DB communication
	 * @return int value for Syste.exit
	 */
	protected abstract Integer excute(DataSource ds);
}
