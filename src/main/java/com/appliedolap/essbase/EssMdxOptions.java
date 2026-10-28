package com.appliedolap.essbase;

import java.time.Duration;

/**
 * How the server should present an MDX query's result: whether to include the data, and how to
 * name the members.
 *
 * <p>Every option starts unset, and an unset option isn't sent, so the server applies its own
 * default - which is what a query got before this class existed. {@link #defaults()} is therefore
 * exactly the plain query.
 *
 * <p>Only {@link #dataless(boolean) dataless} and the member naming options have been checked against a
 * server (26.1); the others pass the server's preference of the same name through as they are. The
 * member naming options are the useful ones for reporting. Against Sample.Basic,
 * {@code memberIdentifierType(ALIAS)} turns the row member {@code 100-10} into {@code Cola}, and adding
 * {@code aliasTableName("Long Names")} turns the column member {@code Jan} into {@code January}. Both
 * the axes and the point of view are renamed; the dimension names in {@link EssMdxGrid} are not.
 *
 * @since 2.1.0
 */
public final class EssMdxOptions {

    private Boolean dataless;

    private Boolean hideRestrictedData;

    private Boolean cellAttributes;

    private Boolean formatString;

    private Boolean formatValues;

    private Boolean meaninglessCells;

    private Boolean textList;

    private Boolean urlDrillThrough;

    private EssCube.MdxIdentifierType memberIdentifierType;

    private String aliasTableName;

    private Duration timeout;

    /** No options set: the server's defaults, as for a plain query. */
    public static EssMdxOptions defaults() {
        return new EssMdxOptions();
    }

    /**
     * Whether to leave the data out and return only the members. The grid keeps its shape, with
     * every data cell empty, which makes this a cheap way to resolve a member set.
     */
    public EssMdxOptions dataless(boolean dataless) {
        this.dataless = dataless;
        return this;
    }

    /** Sets the server's {@code hideRestrictedData} preference. */
    public EssMdxOptions hideRestrictedData(boolean hideRestrictedData) {
        this.hideRestrictedData = hideRestrictedData;
        return this;
    }

    /** Sets the server's {@code cellAttributes} preference. */
    public EssMdxOptions cellAttributes(boolean cellAttributes) {
        this.cellAttributes = cellAttributes;
        return this;
    }

    /** Sets the server's {@code formatString} preference. */
    public EssMdxOptions formatString(boolean formatString) {
        this.formatString = formatString;
        return this;
    }

    /** Sets the server's {@code formatValues} preference. */
    public EssMdxOptions formatValues(boolean formatValues) {
        this.formatValues = formatValues;
        return this;
    }

    /** Sets the server's {@code meaninglessCells} preference. */
    public EssMdxOptions meaninglessCells(boolean meaninglessCells) {
        this.meaninglessCells = meaninglessCells;
        return this;
    }

    /** Sets the server's {@code textList} preference. */
    public EssMdxOptions textList(boolean textList) {
        this.textList = textList;
        return this;
    }

    /** Sets the server's {@code urlDrillThrough} preference. */
    public EssMdxOptions urlDrillThrough(boolean urlDrillThrough) {
        this.urlDrillThrough = urlDrillThrough;
        return this;
    }

    /**
     * How to name the members in the result: by name (the server's default), by alias, or by unique
     * name. Unique names only differ from names in an outline that allows duplicate member names.
     */
    public EssMdxOptions memberIdentifierType(EssCube.MdxIdentifierType memberIdentifierType) {
        this.memberIdentifierType = memberIdentifierType;
        return this;
    }

    /**
     * Which alias table to take aliases from, when members are named by alias. Unset, the server uses
     * the default table.
     */
    public EssMdxOptions aliasTableName(String aliasTableName) {
        this.aliasTableName = aliasTableName;
        return this;
    }

    /**
     * How long to wait for the server's answer before giving up, which fails the query with an
     * {@link EssApiException} caused by a {@link java.net.http.HttpTimeoutException}. This isn't sent to
     * the server, which goes on with the query regardless. Unset, the query waits as long as the client's
     * other requests do.
     */
    public EssMdxOptions timeout(Duration timeout) {
        this.timeout = timeout;
        return this;
    }

    /** @return whether to leave the data out, or null to leave it to the server */
    public Boolean getDataless() {
        return dataless;
    }

    /** @return the {@code hideRestrictedData} preference, or null to leave it to the server */
    public Boolean getHideRestrictedData() {
        return hideRestrictedData;
    }

    /** @return the {@code cellAttributes} preference, or null to leave it to the server */
    public Boolean getCellAttributes() {
        return cellAttributes;
    }

    /** @return the {@code formatString} preference, or null to leave it to the server */
    public Boolean getFormatString() {
        return formatString;
    }

    /** @return the {@code formatValues} preference, or null to leave it to the server */
    public Boolean getFormatValues() {
        return formatValues;
    }

    /** @return the {@code meaninglessCells} preference, or null to leave it to the server */
    public Boolean getMeaninglessCells() {
        return meaninglessCells;
    }

    /** @return the {@code textList} preference, or null to leave it to the server */
    public Boolean getTextList() {
        return textList;
    }

    /** @return the {@code urlDrillThrough} preference, or null to leave it to the server */
    public Boolean getUrlDrillThrough() {
        return urlDrillThrough;
    }

    /** @return how to name the members, or null to leave it to the server */
    public EssCube.MdxIdentifierType getMemberIdentifierType() {
        return memberIdentifierType;
    }

    /** @return the alias table to take aliases from, or null for the default table */
    public String getAliasTableName() {
        return aliasTableName;
    }

    /** @return how long to wait for the answer, or null to wait as long as other requests do */
    public Duration getTimeout() {
        return timeout;
    }

    /**
     * @return whether any of the server's preferences is set; if none is, the query is sent without
     * preferences, as a plain query is
     */
    public boolean hasPreferences() {
        return dataless != null || hideRestrictedData != null || cellAttributes != null || formatString != null
                || formatValues != null || meaninglessCells != null || textList != null || urlDrillThrough != null
                || memberIdentifierType != null || aliasTableName != null;
    }

    /** @return whether nothing is set at all: no preference and no timeout */
    public boolean isEmpty() {
        return !hasPreferences() && timeout == null;
    }

}
