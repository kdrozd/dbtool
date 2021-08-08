package nl.drozd.tools.db.dbtool.commands;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nl.drozd.tools.db.dbtool.MainCommand;
import picocli.CommandLine;

class DeleteCommandTest {
	final PrintStream originalOut = System.out;
	final PrintStream originalErr = System.err;
	final ByteArrayOutputStream out = new ByteArrayOutputStream();
	final ByteArrayOutputStream err = new ByteArrayOutputStream();

	@Test
	void testConnection() {
		String[] args = {
				"delete",
				"-u=sa",
				"-p=sa",
				"-j=" + DBToolTestUtils.HTTESTDB,
				"DELETE FROM test.TBL_EMPLOYEES WHERE last_name='Gupta';" };

		new CommandLine(new MainCommand()).execute(args);
		assertAll("Check results for SQL Command",
				() -> assertEquals("", err.toString(), "No errors"),
				() -> assertEquals("", out.toString(), "No output"));

	}

	@BeforeEach
	public void setup() {
		out.reset();
		err.reset();
		System.setOut(new PrintStream(out));
		System.setErr(new PrintStream(err));
	}

	@AfterEach
	public void teardown() {
		System.setOut(originalOut);
		System.setErr(originalErr);
	}
}
