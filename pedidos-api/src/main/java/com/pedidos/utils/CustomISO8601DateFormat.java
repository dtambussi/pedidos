package com.pedidos.utils;

import java.text.FieldPosition;
import java.util.Date;
import java.util.TimeZone;

import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import com.fasterxml.jackson.databind.util.ISO8601Utils;

public class CustomISO8601DateFormat extends ISO8601DateFormat {

	private static final long serialVersionUID = 1L;

	@Override
    public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition) {
        String value = ISO8601Utils.format(date, false,  TimeZone.getDefault());
        toAppendTo.append(value);
        return toAppendTo;
    }
}
