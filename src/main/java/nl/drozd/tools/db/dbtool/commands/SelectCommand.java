package nl.drozd.tools.db.dbtool.commands;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(
		name = "select",
		description = "Execute SQL Select command",
		mixinStandardHelpOptions = true,
		showAtFileInUsageHelp = true,
		defaultValueProvider = picocli.CommandLine.PropertiesDefaultProvider.class)
public class SelectCommand extends DBCommand {

	@Option(names = { "--as-table" }, description = "Print as table", defaultValue = "false", fallbackValue = "false")
	boolean printAsTable = false;

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

			if (this.printAsTable) {
				printReultsAsTable(rs);
			} else {
				printResult(rs);
			}

		} catch (SQLException e) {
			logger.error("SQL SELECT Exception: {}", e.getMessage());
			return 1;
		}
		return 0;
	}

	private void printResult(ResultSet rs) throws SQLException {
		int columnCount = rs.getMetaData().getColumnCount();
		PrintWriter out = this.commandSpec.commandLine().getOut();

		String[] printRow = new String[columnCount];

		// Print header
		for (int i = 0; i < columnCount; i++) {
			printRow[i] = rs.getMetaData().getColumnName(i + 1);
		}
		out.println(String.join(separator, printRow));

		// Print rows
		while (rs.next()) {
			for (int i = 0; i < columnCount; i++) {
				Object cell = rs.getObject(i + 1);
				printRow[i] = (cell != null) ? cell.toString() : "null";
			}
			out.println(String.join(separator, printRow));
		}

	}

	private void printReultsAsTable(ResultSet rs) throws SQLException {
		this.printResult(rs);
	}

}
