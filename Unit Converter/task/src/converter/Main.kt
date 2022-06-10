package converter

/** Regex for matching the allowed input */
val queryRegex = """-?\d++(\.\d++)? (degrees? )?\p{Lower}++ \p{Lower}++ (degrees? )?\p{Lower}++""".toRegex()

/** Main function. Controls the flow of the program */
fun main() {
    while (true) {
        println("Enter what you want to convert (or exit):")
        val input = readln().lowercase()
        if (input == "exit") return
        if (!queryRegex.matches(input)) {
            println("Parse error")
            println()
            continue
        }
        val (num, sourceUnit, targetUnit) = try {
            processInput(input)
        } catch (e: IllegalArgumentException) {
            println(e.message)
            println()
            continue
        }
        try {
            checkInputValue(num, sourceUnit)
        } catch (e: IllegalArgumentException) {
            println(e.message)
            println()
            continue
        }

        val targetNumber = targetUnit.fromBaseUnit(sourceUnit.inBaseUnit(num))
        val sourceStr = if (num == 1.0) sourceUnit.repr.singular else sourceUnit.repr.plural
        val targetStr = if (targetNumber == 1.0) targetUnit.repr.singular else targetUnit.repr.plural
        println("$num $sourceStr is $targetNumber $targetStr")
        println()
    }
}

/**
 * Check if the input unit can be converted among them
 *
 * @param sourceUnit string with a representation of the source unit
 * @param targetUnit string with a representation of the target unit
 *
 * @throws IllegalArgumentException if conversion is not possible
 */
private fun checkInputUnits(sourceUnit: String, targetUnit: String) {
    val errorSource = if (sourceUnit in MeasureUnit.fromRepr.keys) {
        MeasureUnit.fromRepr[sourceUnit]!!.repr.plural
    } else {
        "???"
    }

    val errorTarget = if (targetUnit in MeasureUnit.fromRepr.keys) {
        MeasureUnit.fromRepr[targetUnit]!!.repr.plural
    } else {
        "???"
    }

    if (errorSource == "???" || errorTarget == "???" ||
        MeasureUnit.fromRepr[sourceUnit]!!.magnitude
        != MeasureUnit.fromRepr[targetUnit]!!.magnitude) {
        throw IllegalArgumentException(
            "Conversion from $errorSource to $errorTarget is impossible"
        )
    }
}

/**
 * Processes the input to get the necessary data
 *
 * @param input the input in lower case
 *
 * @return Triple<Double, MeasureUnit, MeasureUnit> consisting of the number of units,
 * the source unit and the target unit respectively
 *
 * @throws IllegalStateException if a parsing error occurrs
 */
private fun processInput(input: String): Triple<Double, MeasureUnit, MeasureUnit> {
    val temperature = input.contains("degree")
    val input1 = input.replace("degrees? ".toRegex(), "")
    val (num, source, _, target) = input1.split(" ")
    checkInputUnits(source, target)
    val sourceUnit = MeasureUnit.fromRepr[source]!!
    val targetUnit = MeasureUnit.fromRepr[target]!!
    if (temperature && (sourceUnit.magnitude != Magnitude.TEMPERATURE
        || targetUnit.magnitude != Magnitude.TEMPERATURE)) {
        throw IllegalArgumentException("Parse error")
    }
    return  Triple(num.toDouble(), sourceUnit, targetUnit)
}

/**
 * Checks if the input value is allowed for the source unit
 *
 * @param num the input value to convert
 * @param sourceUnit the source unit
 *
 * @throws IllegalArgumentException if num is negative and sourceUnit is not temperature
 */
private fun checkInputValue(num: Double, sourceUnit: MeasureUnit) {
    if (num < 0 && sourceUnit.magnitude == Magnitude.LENGTH) {
        throw IllegalArgumentException("Length shouldn't be negative")
    }
    if (num < 0 && sourceUnit.magnitude == Magnitude.WEIGHT) {
        throw IllegalArgumentException("Weight shouldn't be negative")
    }
}
