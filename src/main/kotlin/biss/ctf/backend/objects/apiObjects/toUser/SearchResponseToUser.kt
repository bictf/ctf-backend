package biss.ctf.back.objects.apiObjects.toUser

import biss.ctf.back.entities.FileEntity

data class SearchResponseToUser(
    var totalResults: Long,
    val title: String,
    val content: String,
    val isBinaryFile: Boolean
) {
    constructor(totalResults: Long, file: FileEntity) :
            this(totalResults, file.title, file.content, file.isBinaryFile)
}
