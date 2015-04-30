package com.udelphi.UnitsConverter;

import android.app.AlertDialog;
import android.content.Context;

/*
 * Created by ODiomin on 29.04.2015.
 */

public class ExceptionMessageBox
{
    private Context m_Context;

    public ExceptionMessageBox(Context context)
    {
        this.m_Context = context;
    }

    public void Show(String caption, String message)
    {
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(m_Context);
        dlgAlert.setMessage(message);
        dlgAlert.setTitle(caption);
        dlgAlert.setIcon(R.drawable.error);
        dlgAlert.setPositiveButton("OK", null);
        dlgAlert.setCancelable(false);
        dlgAlert.create().show();
    }
}
