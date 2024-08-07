package biss.ctf.backend.entities

data class CompilationResponse(
    val doesCompile: Boolean,
    val errorMessage: String
)
