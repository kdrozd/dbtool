package nl.drozd.tools.db.dbtool.commands;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import nl.drozd.tools.db.dbtool.commands.formating.OutputFormating;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(
		name = "select",
		description = "Execute SQL Select command",
		mixinStandardHelpOptions = true,
		showAtFileInUsageHelp = true,
		defaultValueProvider = picocli.CommandLine.PropertiesDefaultProvider.class)
public class SelectCommand extends DBCommand {

	@Option(
			names = { "--output-format" },			
			description = "Format output in specific way, available options: ${COMPLETION-CANDIDATES}",
			defaultValue = "SIMPLE",
			fallbackValue = "SIMPLE",
			paramLabel = "FORMAT"
			)
	OutputFormatEnum outputFormat;

	@Option(
			names = { "--separator" },
			description = "String used as column separator, default: ${DEFAULT-VALUE}",
			defaultValue = "|",
			fallbackValue = "|")
	String separator;

	@Override
	protected Integer excute(DataSource ds) {

		try (Connection conn = ds.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(this.sqlCommand)) {

			OutputFormating.printResult(outputFormat, rs, separator, this.commandSpec);

		} catch (SQLException e) {
			logger.error("SQL SELECT Exception: {}", e.getMessage());
			return 1;
		}
		return 0;
	}

}
