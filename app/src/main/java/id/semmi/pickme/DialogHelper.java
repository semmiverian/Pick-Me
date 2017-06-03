package id.semmi.pickme;

import android.content.Context;
import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

/**
 * Created by Semmiverian on 4/15/17.
 */

public class DialogHelper {
    private Context context;

    public DialogHelper(Context context) {
        this.context = context;
    }

    public void singlePositiveDialog(String title, String content, String buttonText,MaterialDialog.SingleButtonCallback callback){
        new MaterialDialog.Builder(context)
                .title(title)
                .content(content)
                .positiveText(buttonText)
                .positiveColorRes(R.color.colorAccent)
                .onPositive(callback)
                .show();
    }

    public void positiveAndNegativeDialog(String title, String content, String buttonPositiveText, String buttonNegativeText, MaterialDialog.SingleButtonCallback callbackPositive, MaterialDialog.SingleButtonCallback callbackNegative){
        new MaterialDialog.Builder(context)
                .title(title)
                .content(content)
                .positiveText(buttonPositiveText)
                .negativeText(buttonNegativeText)
                .positiveColorRes(R.color.colorAccent)
                .negativeColorRes(R.color.colorAccent)
                .onPositive(callbackPositive)
                .onNegative(callbackNegative)
                .show();
    }

    public MaterialDialog loadingDialog(String title,String content){
        return new MaterialDialog.Builder(context)
                .title(title)
                .content(content)
                .progress(true, 0)
                .widgetColorRes(R.color.colorAccent)
                .show();
    }

    public void listDialog(String title, String[] items, MaterialDialog.SingleButtonCallback callbackPositive) {
        new MaterialDialog.Builder(context)
                .title(title)
                .items(items)
                .widgetColorRes(R.color.colorAccent)
                .positiveColorRes(R.color.colorPrimary)
                .positiveText("Ok")
                .onPositive(callbackPositive)
                .show();
    }

    public void inputDialog (String title, String content, int inputType, MaterialDialog.InputCallback callback) {
        new MaterialDialog.Builder(context)
                         .title(title)
                         .content(content)
                         .inputType(inputType)
                         .input(content, null, callback)
                         .show();
    }

    public MaterialDialog.SingleButtonCallback dismissDialog () {
        return new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                dialog.dismiss();
            }
        };
    }
}
