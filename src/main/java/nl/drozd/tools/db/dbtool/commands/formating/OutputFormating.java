package nl.drozd.tools.db.dbtool.commands.formating;

import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import nl.drozd.tools.db.dbtool.commands.OutputFormatEnum;
import picocli.CommandLine.Model.CommandSpec;

public class OutputFormating {

	public static void printResult(OutputFormatEnum outputFormat, ResultSet rs, String separator, CommandSpec commandSpec) throws SQLException {

		switch (outputFormat) {
		case CSV:
			printResult(rs, ",", commandSpec);
			break;
		case TSV:
			printResult(rs, "\t", commandSpec);
			break;
		case TABLE:
			printTable(rs, commandSpec);
			break;
		case JSONARRAY:
			printJsonArray(rs, commandSpec);
			break;
		case INSERT:
			printSQLInsert(rs, commandSpec);
			break;
		default:
			// SIMPLE format
			printResult(rs, separator, commandSpec);
			break;
		}

	}

	/**
	 * Prints results as XSV. First row will contain header 
	 * Next rows will contain result rows, culumns are separated by 'separtor' param
	 * 
	 * Function used to print PSV, CSV, TSV, etc formated outputs
	 * @param rs
	 * @param separator
	 * @param commandSpec
	 * @throws SQLException
	 */
	private static void printResult(ResultSet rs, String separator, CommandSpec commandSpec) throws SQLException {
		int columnCount = rs.getMetaData().getColumnCount();
		PrintWriter out = commandSpec.commandLine().getOut();

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

	private static void printSQLInsert(ResultSet rs, CommandSpec commandSpec) {
		// TODO Auto-generated method stub

	}

	/**
	 *  Prints results as formated JSON Array. As objects and numbers of row can be big
	 *  Output is not buffered to save memory.
	 *  
	 * @param rs
	 * @param commandSpec
	 * @throws SQLException
	 */
	private static void printJsonArray(ResultSet rs, CommandSpec commandSpec) throws SQLException {
		int columnCount = rs.getMetaData().getColumnCount();
		PrintWriter out = commandSpec.commandLine().getOut();

		String[] keys = new String[columnCount];

		// Get column = keys names
		for (int i = 0; i < columnCount; i++) {
			keys[i] = rs.getMetaData().getColumnName(i + 1);
		}

		out.println("[");
		// Print rows as objects
		while (rs.next()) {
			out.println("\t{");

			for (int i = 0; i < columnCount; i++) {
				var value = rs.getObject(i + 1);

				var valueStr = (value == null) ? "null" : value.toString();
				if (value instanceof String) {
					valueStr = "\"" + valueStr + "\"";
				}

				out.println(String.format("\t\t\"%s\" : %s%s", keys[i], valueStr, i < (columnCount - 1) ? "," : ""));

			}

			out.println(String.format("\t}%s", !rs.isLast() ? "," : ""));
		}
		out.println("]");

	}

	private static void printTable(ResultSet rs, CommandSpec commandSpec) {
		// TODO Auto-generated method stub

	}
}
