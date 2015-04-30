package com.udelphi.UnitsConverter.Converters;

/*
 * Created by ODiomin on 25.03.2015.
 */

public final class ConvertSpeed implements ConverterBaseClass
{
    private int m_ConvertBaseFactor = 1024;

    private double GetConvertFactor(int radioGroupIndex) {
        double convertFactor;
        switch (radioGroupIndex) {
            // Bit/s
            case 0: {
                convertFactor = (double) 1;
                break;
            }
            // Byte/s
            case 1: {
                convertFactor = (double) 8 * 1;
                break;
            }
            // Kilobyte/s
            case 2: {
                convertFactor = (double) m_ConvertBaseFactor * 8 * 1;
                break;
            }
            // Megabyte/s
            case 3: {
                convertFactor = (double) m_ConvertBaseFactor * m_ConvertBaseFactor * 8 * 1;
                break;
            }
            // Gigabyte/s
            case 4: {
                convertFactor = (double) m_ConvertBaseFactor * m_ConvertBaseFactor * m_ConvertBaseFactor * 8 * 1;
                break;
            }

            // =====================================================================================
            // Kilobit/s
            case 5: {
                convertFactor = (double) m_ConvertBaseFactor * 8;
                break;
            }
            // Megabit/s
            case 6: {
                convertFactor = (double) m_ConvertBaseFactor * m_ConvertBaseFactor * 8;
                break;
            }
            // Gigabit/s
            case 7: {
                convertFactor = (double) m_ConvertBaseFactor * m_ConvertBaseFactor * m_ConvertBaseFactor * 8;
                break;
            }
            // =====================================================================================

            default: {
                convertFactor = 0;
            }
        }
        return convertFactor;
    }

    public double Calculate(int from, int to, double factor)
    {
        // kbit/s to kbit/s ect...
        if(from  == to)
        {
            return factor;
        }

        if((from < 0) || (to < 0))
        {
            return Double.NaN;
        }

        double convertFromFactor = GetConvertFactor(from);
        double convertToFactor = GetConvertFactor(to);
        double result = factor * convertFromFactor / convertToFactor;

        // From kilobytes/s to kilobits/s etc
        if ((from >= 0 && from <= 4) && (to >= 5 && to <= 7)) {
            result = result * 8;
        }

        // From kilobits/s to kilobytes/s etc
        if ((from >= 5 && from <= 7) && (to >= 0 && to <= 4)) {
            result = result / 8;
        }

        return result;
    }
}
