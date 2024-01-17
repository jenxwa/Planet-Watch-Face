package data

data class HorizonResponse(
    val signature: ApiSignature,
    val result: String
)

data class ApiSignature(
    val source: String,
    val version: String
)