package nl.drozd.tools.db.dbtool.commands;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import nl.drozd.tools.db.dbtool.commands.formating.OutputFormating;
import picocli.CommandLine.Command;

@Command(
		name = "search",
		description = "Execute search command",
		mixinStandardHelpOptions = true,
		showAtFileInUsageHelp = true,
		defaultValueProvider = picocli.CommandLine.PropertiesDefaultProvider.class)
public class SearchCommand extends SelectCommand {

	@Override
	protected Integer excute(DataSource ds) {

		try (Connection conn = ds.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(this.sqlCommand)) {

			OutputFormating.printResult(outputFormat, rs, separator, this.commandSpec);

		} catch (SQLException e) {
			logger.error("SQL SEARCH Exception: {}", e.getMessage());
			return 1;
		}
		return 0;
	}
}
