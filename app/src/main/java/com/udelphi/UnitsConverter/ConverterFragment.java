package com.udelphi.UnitsConverter;

import com.udelphi.UnitsConverter.Converters.ConverterBaseClass;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.util.Log;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/*
 * Created by ODiomin on 31.03.2015.
 */

public class ConverterFragment extends android.support.v4.app.Fragment
{
    private View m_View;
    private int m_ArrRadioGroupCaptionsId;
    private String m_converterTag;
    private EditText m_EditTextConvert;
    private EditText m_EditTextResult;
    private RadioGroup m_RadioGroupConvertFrom;
    private RadioGroup m_RadioGroupConvertTo;
    private Button m_BtnCalculate;
    private ConverterApplication m_ApplicationContext;

    // Keyboard types enum
    // Just number. Without "." and "-"
    private final int m_KeyboardNumberInputType = InputType.TYPE_CLASS_NUMBER;
    // Number with "." without "-"
    private final int m_KeyboardFloatInputType = m_KeyboardNumberInputType  | InputType.TYPE_NUMBER_FLAG_DECIMAL;
    // Number with "." and "-"
    private final int m_KeyboardFloatSignedInputType = m_KeyboardFloatInputType | InputType.TYPE_NUMBER_FLAG_SIGNED;

    // Log
    private String TAG = "Fragment";

    public static ConverterFragment GetInstance(int arrRadioGroupID, String converterTag)
    {
        ConverterFragment frame = new ConverterFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("ArrRadioGroupCaptionsId", arrRadioGroupID);
        bundle.putString("converterTag", converterTag);

        frame.setArguments(bundle);
        return frame;
    }

    private void FillRadioGroup(int containerId, int idOffset)
    {
        RadioGroup group = (RadioGroup) m_View.findViewById(containerId);
        // Clear RadioGroup
        group.removeAllViews();

        // Get RadioButtons list
        String[] stringList = this.getResources().getStringArray(m_ArrRadioGroupCaptionsId);

        // Fill RadioGroup
        int radioButtonIndex = idOffset;
        int radioButtonTag = 0;
        for(String item : stringList)
        {
            RadioButton button = new RadioButton(group.getContext());
            button.setText(item);
            button.setId(radioButtonIndex);
            button.setTag(radioButtonTag);

            group.addView(button);
            radioButtonIndex++;
            radioButtonTag++;
        }

        // Set 1st item as checked
        group.check(group.getChildAt(0).getId());
    }

    private void SetRadioGroupCheckedChangeListener(RadioGroup group)
    {
        group.setOnCheckedChangeListener(
        new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                // Clear edits
                m_EditTextResult.setText("");

                // Just for "Convert from" radioGroup
                if (group != m_RadioGroupConvertFrom)
                    return;

                if (m_converterTag.equals(m_ApplicationContext.tagConverterTemperature))
                {
                    return;
                }

                // For data and data speed
                switch (checkedId)
                {
                    case 0:
                    {
                        // Bits
                        m_EditTextConvert.setInputType(m_KeyboardNumberInputType);
                        break;
                    }
                    default:
                    {
                        // Other units
                        m_EditTextConvert.setInputType(m_KeyboardFloatInputType);
                        break;
                    }
                }
            }
        });
    }

    private void SetEditTextListener()
    {
        m_EditTextConvert.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                m_EditTextResult.setText("");
                String str = s.toString();
                boolean b = str.length() > 0;
                if (b)
                {
                    b = CheckNumber(str);
                }

                m_BtnCalculate.setEnabled(b);
            }
        });
    }

    private boolean CheckNumber(String s)
    {
        try
        {
            double d = Double.parseDouble(s.toString());
        }
        catch(Exception E)
        {
            return false;
        }
        return true;
    }

    private void SetCalculateButtonOnClickListener(View view)
    {
        Button button = (Button) view.findViewById(R.id.btnCalculate);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view)
            {
                ConverterBaseClass converter = m_ApplicationContext.GetConverter(m_converterTag);
                if (converter != null) {
                    int convertFrom = GetRadioGroupCheckedId(R.id.radioGroupUnitsFrom);
                    int convertTo = GetRadioGroupCheckedId(R.id.radioGroupUnitsTo);

                    Double f = Double.parseDouble(m_EditTextConvert.getText().toString());
                    Double result = converter.Calculate(convertFrom, convertTo, f);

                    // Generate displayed number format
                    int digitsCount = view.getResources().getInteger(R.integer.resultCalculatedDigitCount);
                    //"####################.####################";
                    StringBuilder sb = new StringBuilder(digitsCount * 2 + 1);
                    for(int i = 0; i < digitsCount; i++)
                        sb.append("#");
                    sb.append('.');
                    for(int i = 0; i < digitsCount; i++)
                        sb.append("#");

                    // Round the result and show it
                    DecimalFormat df = new DecimalFormat(sb.toString());
                    df.setRoundingMode(RoundingMode.HALF_UP);
                    String s = df.format(result).replace(',', '.');
                    m_EditTextResult.setText(s);
                }
                else
                {
                    AddLogString("===== Converter is null =====");
                }
            }
        });
    }

    private int GetRadioGroupCheckedId(int radioGroupId)
    {
        RadioGroup group = (RadioGroup) m_View.findViewById(radioGroupId);
        int selectedId = group.getCheckedRadioButtonId();
        RadioButton button = (RadioButton) group.findViewById(selectedId);
        return (int) button.getTag();
    }

    private void AddLogString(String s)
    {
        if (R.string.flDebug == '1')
        {
            Log.v(TAG, s);
        }
    }

    @Override
    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);

        if (getArguments() != null)
        {
            m_ArrRadioGroupCaptionsId = getArguments().getInt("ArrRadioGroupCaptionsId");
            m_converterTag = getArguments().getString("converterTag");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        AddLogString("OnCreateView start");

        m_View = inflater.inflate(R.layout.activity_tab_controls, container, false);

        // Get activity & application context
        Activity activity =  getActivity();
        m_ApplicationContext = (ConverterApplication) activity.getApplicationContext();

        FillRadioGroup(R.id.radioGroupUnitsFrom, 0);
        FillRadioGroup(R.id.radioGroupUnitsTo, 100);

        m_EditTextConvert = (EditText) m_View.findViewById(R.id.editTextConvert);
        m_EditTextResult = (EditText) m_View.findViewById(R.id.editTextResult);
        m_BtnCalculate = (Button) m_View.findViewById(R.id.btnCalculate);

        SetEditTextListener();
        SetCalculateButtonOnClickListener(m_View);

        String s = m_EditTextConvert.getText().toString();
        boolean b = (s == null) || s.isEmpty();
        m_BtnCalculate.setEnabled(!b);

        if (!m_converterTag.equals(m_ApplicationContext.tagConverterTemperature))
        {
            m_EditTextConvert.setInputType(m_KeyboardNumberInputType);
        }
        else
        {
            m_EditTextConvert.setInputType(m_KeyboardFloatSignedInputType);
        }

        m_RadioGroupConvertFrom = (RadioGroup) m_View.findViewById(R.id.radioGroupUnitsFrom);
        m_RadioGroupConvertTo = (RadioGroup) m_View.findViewById(R.id.radioGroupUnitsTo);
        SetRadioGroupCheckedChangeListener(m_RadioGroupConvertFrom);
        SetRadioGroupCheckedChangeListener(m_RadioGroupConvertTo);

        m_EditTextResult.setHorizontallyScrolling(true);
        m_EditTextConvert.setFocusable(true);
        AddLogString("OnCreateView finish");

        return m_View;
    }
}
