package app.aaps.core.data.db

import java.util.TimeZone

data class TT(
    var id: Long = 0,
    var version: Int = 0,
    var dateCreated: Long = -1,
    var isValid: Boolean = true,
    var referenceId: Long? = null,
    var ids: IDs = IDs(),
    var timestamp: Long,
    var utcOffset: Long = TimeZone.getDefault().getOffset(timestamp).toLong(),
    var reason: Reason,
    var highTarget: Double, // in mgdl
    var lowTarget: Double, // in mgdl
    var duration: Long // in millis
) {

    fun contentEqualsTo(other: TT): Boolean =
        timestamp == other.timestamp &&
            utcOffset == other.utcOffset &&
            reason == other.reason &&
            highTarget == other.highTarget &&
            lowTarget == other.lowTarget &&
            duration == other.duration &&
            isValid == other.isValid

    fun onlyNsIdAdded(previous: TT): Boolean =
        previous.id != id &&
            contentEqualsTo(previous) &&
            previous.ids.nightscoutId == null &&
            ids.nightscoutId != null

    enum class Reason(val text: String) {
        CUSTOM("Custom"),
        HYPOGLYCEMIA("Hypo"),
        ACTIVITY("Activity"),
        EATING_SOON("Eating Soon"),
        AUTOMATION("Automation"),
        WEAR("Wear")
        ;

        companion object {

            fun fromString(reason: String?) = entries.firstOrNull { it.text == reason }
                ?: CUSTOM
        }
    }

    companion object
}