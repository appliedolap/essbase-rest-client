package com.appliedolap.essbase.misc;

import java.util.List;

public class MdxJson {

    private List<List<String>> data;

    private Metadata metadata;

    public List<List<String>> getData() {
        return data;
    }

    public void setData(List<List<String>> data) {
        this.data = data;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public static class Metadata {

        private List<String> page;

        private List<String> column;

        private List<String> row;

        public List<String> getPage() {
            return page;
        }

        public void setPage(List<String> page) {
            this.page = page;
        }

        public List<String> getColumn() {
            return column;
        }

        public void setColumn(List<String> column) {
            this.column = column;
        }

        public List<String> getRow() {
            return row;
        }

        public void setRow(List<String> row) {
            this.row = row;
        }

    }

}