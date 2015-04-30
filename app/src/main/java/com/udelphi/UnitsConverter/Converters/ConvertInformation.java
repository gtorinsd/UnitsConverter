package com.udelphi.UnitsConverter.Converters;

/*
 * Created by ODiomin on 26.03.2015.
 */

public final class ConvertInformation implements ConverterBaseClass
{
    private int m_ConvertBaseFactor = 1024;

    private double GetConvertFactor(int radioGroupIndex)
    {
        double convertFactor;
        switch (radioGroupIndex) {
            // Bit
            case 0: {
                convertFactor = (double) 1;
                break;
            }
            // Byte
            case 1: {
                convertFactor = (double) 8 * 1;
                break;
            }
            // Kilobyte
            case 2: {
                convertFactor = (double) m_ConvertBaseFactor * 8 * 1;
                break;
            }
            // Megabyte
            case 3: {
                convertFactor = (double) m_ConvertBaseFactor * m_ConvertBaseFactor * 8 * 1;
                break;
            }
            // Gigabyte
            case 4: {
                convertFactor = (double) m_ConvertBaseFactor * m_ConvertBaseFactor * m_ConvertBaseFactor * 8 * 1;
                break;
            }

            // =====================================================================================
            // Kilobit
            case 5: {
                convertFactor = (double) m_ConvertBaseFactor * 8;
                break;
            }
            // Megabit
            case 6: {
                convertFactor = (double) m_ConvertBaseFactor * m_ConvertBaseFactor * 8;
                break;
            }
            // Gigabit
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
        // bit to bit ect...
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

        // From kilobytes to kilobits etc
        if ((from >= 0 && from <= 4) && (to >= 5 && to <= 7)) {
            return result * 8;
        }

        // From kilobits to kilobytes etc
        if ((from >= 5 && from <= 7) && (to >= 0 && to <= 4)) {
            return result / 8;
        }

        return result;
    }
}
