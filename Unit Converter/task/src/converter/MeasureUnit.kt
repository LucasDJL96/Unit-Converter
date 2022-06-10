package converter

/**
 * Enum class representing the units we convert to/from
 *
 * @param magnitude the magnitude this unit measures
 * @param repr the representations of this unit as strings
 * @param inBaseUnit a function describing how to convert this unit to the base unit
 * @param fromBaseUnit a function describing how to convert the base unit to this unit
 */
enum class MeasureUnit(val magnitude: Magnitude,
                       val repr: UnitRepresentation,
                       val inBaseUnit: (Double) -> Double,
                       val fromBaseUnit: (Double) -> Double) {
    METER(
        Magnitude.LENGTH,
        UnitRepresentation("meter", "meters", "m"),
        { it },
        { it }
    ),
    KILOMETER(
        Magnitude.LENGTH,
        UnitRepresentation("kilometer", "kilometers", "km"),
        { it * 1000.0},
        { it / 1000.0}
    ),
    CENTIMETER(
        Magnitude.LENGTH,
        UnitRepresentation("centimeter", "centimeters", "cm"),
        { it * 0.01 },
        { it / 0.01 }
    ),
    MILLIMETER(
        Magnitude.LENGTH,
        UnitRepresentation("millimeter", "millimeters", "mm"),
        { it * 0.001 },
        { it / 0.001 }
    ),
    MILE(
        Magnitude.LENGTH,
        UnitRepresentation("mile", "miles", "mi"),
        { it * 1609.35 },
        { it / 1609.35 }
    ),
    YARD(
        Magnitude.LENGTH,
        UnitRepresentation("yard", "yards", "yd"),
        { it * 0.9144 },
        { it / 0.9144 }
    ),
    FOOT(
        Magnitude.LENGTH,
        UnitRepresentation("foot", "feet", "ft"),
        { it * 0.3048 },
        { it / 0.3048 }
    ),
    INCH(
        Magnitude.LENGTH,
        UnitRepresentation("inch", "inches", "in"),
        { it * 0.0254 },
        { it / 0.0254 }
    ),

    GRAM(
        Magnitude.WEIGHT,
        UnitRepresentation("gram", "grams", "g"),
        { it },
        { it }
    ),
    KILOGRAM(
        Magnitude.WEIGHT,
        UnitRepresentation("kilogram", "kilograms", "kg"),
        { it * 1000.0 },
        { it / 1000.0 }
    ),
    CENTIGRAM(
        Magnitude.WEIGHT,
        UnitRepresentation("centigram", "centigrams", "cg"),
        { it * 0.01 },
        { it / 0.01 }
    ),
    MILLIGRAM(
        Magnitude.WEIGHT,
        UnitRepresentation("milligram", "milligrams", "mg"),
        { it * 0.001 },
        { it / 0.001 }
    ),
    POUND(
        Magnitude.WEIGHT,
        UnitRepresentation("pound", "pounds", "lb"),
        { it * 453.592 },
        { it / 453.592 }
    ),
    OUNCE(
        Magnitude.WEIGHT,
        UnitRepresentation("ounce", "ounces", "oz"),
        { it * 28.3495 },
        { it / 28.3495 },
    ),

    KELVIN(
        Magnitude.TEMPERATURE,
        UnitRepresentation("kelvin", "kelvins", "k"),
        { it },
        { it }
    ),
    CELSIUS(
        Magnitude.TEMPERATURE,
        UnitRepresentation("degree Celsius", "degrees Celsius",
            "c", "dc", "celsius"),
        { it + 273.15 },
        { it - 273.15 }
    ),
    FAHRENHEIT(
        Magnitude.TEMPERATURE,
        UnitRepresentation("degree Fahrenheit", "degrees Fahrenheit",
            "f", "df", "fahrenheit"),
        { (it + 459.67) * 5 / 9 },
        { it * 9 / 5 - 459.67 }
    ),
    ;

    companion object {
        /** Map from the string representations of the units to the corresponding units */
        val fromRepr = buildMap {
            for (unit in MeasureUnit.values()) {
                put(unit.repr.singular.lowercase(), unit)
                put(unit.repr.plural.lowercase(), unit)
                for (other in unit.repr.others) put(other.lowercase(), unit)
            }
        }

        /**
         * Gets all units available for a magnitude
         *
         * @param magnitude the magnitude
         *
         * @return List<MeasureUnit>
         */
        fun ofMagnitude(magnitude: Magnitude): List<MeasureUnit> {
            return MeasureUnit.values().filter { it.magnitude == magnitude }
        }
    }
}
