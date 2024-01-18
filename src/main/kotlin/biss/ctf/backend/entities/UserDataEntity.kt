package biss.ctf.back.entities

data class UserDataEntity(
    val UUID: String,
    var password: String,
    var hasLoggedIn: Boolean,
    var path: HashMap<String, Boolean>,
) {
    fun isTruePath(): Boolean {
        for (levelResult in this.path.values) {
            if (!levelResult) {
                return false
            }
        }

        return true
    }
}