package UnitTests;

import junit.framework.TestCase;

import com.udelphi.UnitsConverter.Converters.ConvertInformation;
import com.udelphi.UnitsConverter.Converters.ConvertSpeed;
import com.udelphi.UnitsConverter.Converters.ConverterBaseClass;
import com.udelphi.UnitsConverter.Converters.ConverterTemperature;

/*
 * Created by ODiomin on 10.04.2015.
 */

public class myUnitTests extends TestCase
{
    private ConverterBaseClass m_converter;

    // Check convertors create
    //@SmallTest
    public void testCheckConvertersCreate()
    {
        m_converter = new ConvertInformation();
        assertNotNull(m_converter);

        m_converter = new ConvertSpeed();
        assertNotNull(m_converter);

        m_converter = new ConverterTemperature();
        assertNotNull(m_converter);
    }

    // test calculate with normal indexes and factor < 0
    // =============================================================================================
    public void testConvertInformationCalculateFactorBelowZero()
    {
        m_converter = new ConvertInformation();

        int from = 3;
        int to = 2;
        double convertFactor = -1;
        double result = m_converter.Calculate(from, to, convertFactor);

        assertEquals(result, -1024.0);
    }

    public void testConvertSpeedCalculateFactorBelowZero()
    {
        m_converter = new ConvertSpeed();

        int from = 4;
        int to = 3;
        double convertFactor = -1;
        double result = m_converter.Calculate(from, to, convertFactor);

        assertEquals(result, -1024.0);
    }

    public void testConvertTemperatureFactorBelowZero()
    {
        m_converter = new ConverterTemperature();

        int from = 2;
        int to = 3;
        double convertFactor = -1;
        double result = m_converter.Calculate(from, to, convertFactor);

        assertEquals(result, -219.32);
    }
    // =============================================================================================

    // test calculate with normal indexes and factor == 0
    // =============================================================================================
    public void testConvertInformationCalculateZeroFactor()
    {
        m_converter = new ConvertInformation();

        int from = 3;
        int to = 2;
        double convertFactor = 0;
        double result = m_converter.Calculate(from, to, convertFactor);

        assertEquals(result, 0.0);
    }

    public void testConvertSpeedCalculateZeroFactor()
    {
        m_converter = new ConvertSpeed();

        int from = 4;
        int to = 3;
        double convertFactor = 0;
        double result = m_converter.Calculate(from, to, convertFactor);

        assertEquals(result, 0.0);
    }

    public void testConvertTemperatureZeroFactor()
    {
        m_converter = new ConverterTemperature();

        int from = 2;
        int to = 3;
        double convertFactor = 0;
        double result = m_converter.Calculate(from, to, convertFactor);

        //assertEquals(result, -218.51999999999998);
        assertEquals(-218.519, result , 1E-3);
    }
    // =============================================================================================

    // test calculate with normal indexes and factor == 1
    // =============================================================================================
    public void testConvertInformationCalculate()
    {
        m_converter = new ConvertInformation();

        int from = 3;
        int to = 2;
        double convertFactor = 1;
        double result = m_converter.Calculate(from, to, convertFactor);

        assertEquals(result, 1024.0);
    }

    public void testConvertSpeedCalculate()
    {
        m_converter = new ConvertSpeed();

        int from = 4;
        int to = 3;
        double convertFactor = 1;
        double result = m_converter.Calculate(from, to, convertFactor);

        assertEquals(result, 1024.0);
    }

    public void testConvertTemperature()
    {
        m_converter = new ConverterTemperature();

        int from = 2;
        int to = 3;
        double convertFactor = 1;
        double result = m_converter.Calculate(from, to, convertFactor);

        assertEquals(result, -217.72);
    }
    // =============================================================================================

    // test calculate with normal indexes and factor > 1
    // =============================================================================================
    public void testConvertInformationCalculateFactor()
    {
        m_converter = new ConvertInformation();

        int from = 3;
        int to = 2;
        double convertFactor = 2;
        double result = m_converter.Calculate(from, to, convertFactor);

        assertEquals(result, 2048.0);
    }

    public void testConvertSpeedCalculateFactor()
    {
        m_converter = new ConvertSpeed();

        int from = 4;
        int to = 3;
        double convertFactor = 3;
        double result = m_converter.Calculate(from, to, convertFactor);

        assertEquals(result, 3072.0);
    }

    public void testConvertTemperatureFactor()
    {
        m_converter = new ConverterTemperature();

        int from = 0;
        int to = 1;
        double convertFactor = 2;
        double result = m_converter.Calculate(from, to, convertFactor);

        assertEquals(result, 35.6);
    }
    // =============================================================================================

    // test calculate with Convert from indexes < 0, Convert to index >=0, factor == 1
    // =============================================================================================
    public void testConvertInformationCalculateFirstIndexBelowZero()
    {
        m_converter = new ConvertInformation();

        int from = -1;
        int to = 0;
        double convertFactor = 2;
        double result = m_converter.Calculate(from, to, convertFactor);

        assertEquals(result, Double.NaN);
    }

    public void testConvertSpeedCalculateFirstIndexBelowZero()
    {
        m_converter = new ConvertSpeed();

        int from = -1;
        int to = 0;
        double convertFactor = 3;
        double result = m_converter.Calculate(from, to, convertFactor);

        assertEquals(result, Double.NaN);
    }

    public void testConvertTemperatureFirstIndexBelowZero()
    {
        m_converter = new ConverterTemperature();

        int from = -1;
        int to = 1;
        double convertFactor = 2;
        double result = m_converter.Calculate(from, to, convertFactor);

        assertEquals(result, Double.NaN);
    }
}
