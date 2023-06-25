package ir.mmd.linux.utility.font.cmd

import ir.mmd.linux.utility.font.service.LocalIndexService
import kotlinx.coroutines.runBlocking

class UpdateCommand : Command {
	override fun execute() = runBlocking {
		LocalIndexService.update()
	}
}
