package nl.drozd.tools.db.dbtool.commands;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import picocli.CommandLine.Command;

@Command(
		name = "insert",
		description = "Execute SQL Insert command",
		mixinStandardHelpOptions = true,
		showAtFileInUsageHelp = true,
		defaultValueProvider = picocli.CommandLine.PropertiesDefaultProvider.class)
public class InsertCommand extends DBCommand {
	@Override
	protected Integer excute(DataSource ds) {

		try (Connection conn = ds.getConnection();
				Statement stmt = conn.createStatement()) {

			int executions = stmt.executeUpdate(this.sqlCommand);

			logger.debug("Executed {} rows", executions);

		} catch (SQLException e) {
			logger.error("SQL INSERT Exception: {}", e.getMessage());
			return 1;
		}
		return 0;
	}
}
