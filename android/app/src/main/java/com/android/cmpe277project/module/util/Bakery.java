package com.android.cmpe277project.module.util;


import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

/**
 * @author Aaditya
 *
 * A utility class which makes showing toast and snackbar messages easier.
 */
public class Bakery {

    private Context context;

    public Bakery(Context context) {
        this.context = context;
    }

    /**
     * Shows a LENGTH_SHORT toast message.
     *
     * @param message
     */
    public void toastShort(String message) {
        toast(message, Toast.LENGTH_SHORT);
    }

    /**
     * Shows a LENGTH_LONG toast message.
     *
     * @param message
     */
    public void toastLong(String message) {
        toast(message, Toast.LENGTH_LONG);
    }

    /**
     * Shows a toast message.
     *
     * @param message
     * @param length
     */
    public void toast(String message, int length) {
        Toast.makeText(context, message, length).show();
    }

    /**
     * Shows a LENGTH_SHORT snack message.
     *
     * @param view
     * @param message
     */
    public void snackShort(View view, String message) {
        snack(view, message, Snackbar.LENGTH_SHORT);
    }

    /**
     * Shows a LENGTH_SHORT snack message with action.
     *
     * @param view
     * @param message
     */
    public void snackShort(
            View view, String message, String action, View.OnClickListener actionListener) {
        snack(view, message, Snackbar.LENGTH_SHORT, action, actionListener);
    }

    /**
     * Shows a LENGTH_LONG snack message.
     *
     * @param view
     * @param message
     */
    public void snackLong(View view, String message) {
        snack(view, message, Snackbar.LENGTH_LONG);
    }

    /**
     * Shows a LENGTH_LONG snack message with action.
     *
     * @param view
     * @param message
     */
    public void snackLong(
            View view, String message, String action, View.OnClickListener actionListener) {
        snack(view, message, Snackbar.LENGTH_LONG, action, actionListener);
    }

    /**
     * Shows a LENGTH_INDEFINITE snack message.
     *
     * @param view
     * @param message
     */
    public void snackIndefinite(View view, String message) {
        snack(view, message, Snackbar.LENGTH_INDEFINITE);
    }

    /**
     * Shows a LENGTH_INDEFINITE snack message with action.
     *
     * @param view
     * @param message
     */
    public void snackIndefinite(
            View view, String message, String action, View.OnClickListener actionListener) {
        snack(view, message, Snackbar.LENGTH_INDEFINITE, action, actionListener);
    }

    /**
     * Shows a snack message.
     *
     * @param view
     * @param message
     */
    public void snack(View view, String message, int length) {
        Snackbar.make(view, message, length).show();
    }

    /**
     * Shows a snack message with action.
     *
     * @param view
     * @param message
     */
    public void snack(
            View view, String message, int length, String action,
            View.OnClickListener actionListener) {
        Snackbar.make(view, message, length)
                .setAction(action, actionListener)
                .show();
    }

}