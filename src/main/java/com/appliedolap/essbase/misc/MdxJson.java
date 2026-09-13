package com.appliedolap.essbase.misc;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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

        private List<PageDimension> page;

        private List<String> column;

        private List<String> row;

        public List<PageDimension> getPage() {
            return page;
        }

        public void setPage(List<PageDimension> page) {
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

    /**
     * A dimension that the query left off both axes, so it contributes a single point-of-view
     * member to every cell.
     *
     * <p>Two server versions write this two different ways, and both arrive here: 21.7 writes a
     * bare dimension name ({@code "Product"}), 26.1 an object carrying the dimension name plus the
     * POV member selected and whether the client last had it expanded ({@code {"id":0,
     * "name":"Product","pov":"Product","expand":"0"}}). Only {@link #getName()} is populated in
     * both cases.
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PageDimension {

        private Integer id;

        private String name;

        private String pov;

        private String expand;

        public PageDimension() {
        }

        /**
         * Reads the 21.7 form, where the whole entry is the dimension name.
         */
        @JsonCreator
        public static PageDimension of(String name) {
            PageDimension dimension = new PageDimension();
            dimension.setName(name);
            return dimension;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        /**
         * The member this dimension is pinned to, or null against a server that doesn't report one.
         */
        public String getPov() {
            return pov;
        }

        public void setPov(String pov) {
            this.pov = pov;
        }

        public String getExpand() {
            return expand;
        }

        public void setExpand(String expand) {
            this.expand = expand;
        }

        @Override
        public String toString() {
            return pov == null || pov.equals(name) ? String.valueOf(name) : name + ":" + pov;
        }

    }

}
