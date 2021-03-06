package com.patrykandpatryk.liftapp.core.extension

import android.content.Context
import androidx.annotation.StringRes
import com.patrykandpatryk.liftapp.core.R
import com.patrykandpatryk.liftapp.domain.unit.DistanceUnit
import com.patrykandpatryk.liftapp.domain.unit.MassUnit

inline val DistanceUnit.stringResourceId: Int
    @StringRes
    get() = when (this) {
        DistanceUnit.Kilometers -> R.string.kilometer_unit
        DistanceUnit.Miles -> R.string.mile_unit
    }

inline val MassUnit.stringResourceId: Int
    @StringRes
    get() = when (this) {
        MassUnit.Kilograms -> R.string.kilogram_unit
        MassUnit.Pounds -> R.string.pound_unit
    }

fun DistanceUnit.formatValue(
    context: Context,
    value: Float,
    decimalPlaces: Int,
) = String.format(
    format = context.getString(R.string.distance_value_and_unit),
    value.round(decimalPlaces = decimalPlaces),
    context.getString(stringResourceId),
)

fun MassUnit.formatValue(
    context: Context,
    value: Float,
    decimalPlaces: Int,
) = String.format(
    format = context.getString(R.string.mass_value_and_unit),
    value.round(decimalPlaces = decimalPlaces),
    context.getString(stringResourceId),
)
