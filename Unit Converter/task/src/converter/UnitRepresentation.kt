package converter

/**
 * Class representing the representations as strings of a unit
 *
 * @param singular the representation as a singular name
 * @param plural the representation as a plural name
 * @param _others other representations
 */
class UnitRepresentation(val singular: String, val plural: String, vararg _others: String) {
    /** Other representations. Same as _others parameter in constructor */
    val others = _others
}
