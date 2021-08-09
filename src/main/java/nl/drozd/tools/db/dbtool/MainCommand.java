package nl.drozd.tools.db.dbtool;

import java.util.concurrent.Callable;

import nl.drozd.tools.db.dbtool.cli.PrintExceptionMessageHandler;
import nl.drozd.tools.db.dbtool.commands.DeleteCommand;
import nl.drozd.tools.db.dbtool.commands.InsertCommand;
import nl.drozd.tools.db.dbtool.commands.SearchCommand;
import nl.drozd.tools.db.dbtool.commands.SelectCommand;
import nl.drozd.tools.db.dbtool.commands.UpdateCommand;
import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(
		name = "dbtool",
		description = "Database helper",
		mixinStandardHelpOptions = true,
		versionProvider = nl.drozd.tools.db.dbtool.cli.DBToolVersionProvider.class,
		subcommands = {
				SelectCommand.class,
				InsertCommand.class,
				DeleteCommand.class,
				UpdateCommand.class,
				SearchCommand.class
		})
public class MainCommand implements Callable<Integer> {

	public static void main(String[] args) {
		int exitCode = new CommandLine(new MainCommand())
				.setExecutionExceptionHandler(new PrintExceptionMessageHandler())
				// This will disable comments in @files as any character can be used in passwords
				// And whitechars are something unintuitive as comments characters ;)
				.setAtFileCommentChar(null)				
				.execute(args);
		System.exit(exitCode);
	}

	@Override
	public Integer call() throws Exception {
		// Call without sub command is wrong at least till default command will be
		// chosen
		return 1;
	}

}
