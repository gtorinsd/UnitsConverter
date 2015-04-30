package com.udelphi.UnitsConverter.Converters;

/*
 * Created by ODiomin on 07.04.2015.
 */
public final class ConverterTemperature implements ConverterBaseClass
{
    public double Calculate(int from, int to, double factor)
    {

        if((from < 0) || (to < 0))
        {
            return Double.NaN;
        }

        // Itself to itself
        if (from == to)
        {
            return factor;
        }

        // C-F
        if ((from == 0) && (to == 1)) return (9.0/5 * factor + 32);

        // C-K
        if ((from == 0) && (to == 2)) return (273.15 + factor);

        // C-RE
        if ((from == 0) && (to == 3)) return (4.0/5 * factor);

        // F-C
        if ((from == 1) && (to == 0)) return (5.0/9) * (factor - 32);

        // F-K
        if ((from == 1) && (to == 2)) return (5.0/9) * (factor - 32) + 273.15;

        // F-RE
        if ((from == 1) && (to == 3)) return (4.0/9) * (factor - 32);

        // K-C
        if ((from == 2) && (to == 0)) return (factor - 273.15);

        // K-F
        if ((from == 2) && (to == 1)) return (9.0 / 5 * (factor - 273.15) + 32);

        // K-RE
        if ((from == 2) && (to == 3)) return 4.0 / 5 * (factor - 273.15);

        // RE-C
        if ((from == 3) && (to == 0)) return 5.0 / 4 * factor;

        // RE-F
        if ((from == 3) && (to == 1)) return (9.0/4 * factor + 32);

        // RE-K
        if ((from == 3) && (to == 2)) return 5.0 / 4 * factor + 273.15;

        // Other way returns NAN
        return Double.NaN;

    }
}
