package com.example.dhagrafis.design;

import android.icu.text.DecimalFormat;
import android.icu.text.NumberFormat;
import android.icu.util.Currency;

import java.util.Locale;

public class RupiahConvert {
    public static String convertToRupiah(int value) {

        Locale locale = new Locale("id", "ID");

        // Buat format angka tanpa strip koma
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(locale);
        formatter.applyPattern("#,###");

        // Set currency ke Rupiah
        Currency currency = Currency.getInstance("IDR");

        // Mengubah nilai integer menjadi format mata uang Rupiah tanpa strip koma
        String rupiah = currency.getSymbol(locale) + " " + formatter.format(value);

        // Mengembalikan hasil
        return rupiah;
    }
}
