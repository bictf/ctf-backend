package biss.ctf.backend.entities

import java.nio.file.Path

/**
 * A data class to hold picture data for the picture CAPTCHAs
 */
data class ImageData(
    val image: Path, val imageName: String
)