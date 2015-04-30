package com.udelphi.UnitsConverter;

import android.app.Application;
import com.udelphi.UnitsConverter.Converters.ConvertInformation;
import com.udelphi.UnitsConverter.Converters.ConvertSpeed;
import com.udelphi.UnitsConverter.Converters.ConverterBaseClass;
import com.udelphi.UnitsConverter.Converters.ConverterTemperature;

/*
 * Created by ODiomin on 06.04.2015.
 */

public class ConverterApplication extends Application
{
    // Converter tags list
    final public String tagConverterInfo = "tagTabDataUnits";
    final public String tagConverterSpeed = "tagTabSpeedUnits";
    final public String tagConverterTemperature = "tagTabTempUnits";

    public ConverterBaseClass GetConverter(String key)
    {
        ConverterBaseClass converter = null;

        switch (key) {
            case tagConverterInfo:
                converter = new ConvertInformation();
                break;
            case tagConverterSpeed:
                converter = new ConvertSpeed();
                break;
            case tagConverterTemperature:
                converter = new ConverterTemperature();
                break;
        }

        return converter;
    }
}
