package com.appliedolap.essbase.impl;

import com.appliedolap.essbase.EssMdxGrid;
import com.appliedolap.essbase.misc.MdxJson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The grid of an MDX result, with the dimension names from the result's metadata.
 *
 * @since 2.1.0
 */
public class EssMdxGridImpl extends EssGridImpl implements EssMdxGrid {

    private final List<String> rowDimensions;

    private final List<String> columnDimensions;

    private final List<String> pageDimensions;

    private final List<String> pageMembers;

    EssMdxGridImpl(MdxJson json) {
        super(json);
        MdxJson.Metadata metadata = json.getMetadata();
        rowDimensions = copy(metadata == null ? null : metadata.getRow());
        columnDimensions = copy(metadata == null ? null : metadata.getColumn());
        List<String> page = new ArrayList<>();
        List<String> members = new ArrayList<>();
        if (metadata != null && metadata.getPage() != null) {
            for (MdxJson.PageDimension dimension : metadata.getPage()) {
                page.add(dimension.getName());
                members.add(dimension.getPov());
            }
        }
        pageDimensions = Collections.unmodifiableList(page);
        pageMembers = Collections.unmodifiableList(members);
    }

    private static List<String> copy(List<String> names) {
        return names == null ? List.of() : Collections.unmodifiableList(new ArrayList<>(names));
    }

    @Override
    public List<String> getRowDimensions() {
        return rowDimensions;
    }

    @Override
    public List<String> getColumnDimensions() {
        return columnDimensions;
    }

    @Override
    public List<String> getPageDimensions() {
        return pageDimensions;
    }

    @Override
    public List<String> getPageMembers() {
        return pageMembers;
    }

}
