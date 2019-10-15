package com.rabobank.reports.writer;

import com.opencsv.bean.ColumnPositionMappingStrategy;

class CustomMappingStrategy<T> extends ColumnPositionMappingStrategy<T> {
    private static final String[] HEADER = new String[]{"Reference", "Description"};
    @Override
    public String[] generateHeader() {
        return HEADER;
    }
}
