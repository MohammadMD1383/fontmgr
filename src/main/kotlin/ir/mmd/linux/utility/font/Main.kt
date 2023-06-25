package ir.mmd.linux.utility.font

import ir.mmd.linux.utility.font.cmd.HelpCommand

fun main(args: Array<String>) {
	// check if command line is empty then show help
	if (args.isEmpty()) {
		HelpCommand().execute()
		return
	}
	
	// ensure config folder exists
	configDirectoryPath.toFile().mkdirs()
	
	// parse command line
	val cli = CommandLine(args)
	
	// execute command
	cli.execute()
}
