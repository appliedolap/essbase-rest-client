package com.appliedolap.essbase;

/**
 * The generative-AI capabilities a server can offer, each independently switchable.
 *
 * <p>{@code GET /about/instance} reports one flag per capability rather than one for AI as a whole,
 * so a server can perfectly well have semantic search on and the MDX generator off. Asking
 * {@link EssCubeAi#getReadiness(EssAiFeature)} about the one you are about to use beats asking
 * whether "AI" is available, which is not a question the server answers.
 */
public enum EssAiFeature {

    /** Writing an MDX query from a question asked in English. */
    MDX_GENERATOR("aiMdxEnabled", "MDX generator"),

    /** Free-form chat about a cube. */
    ASK_ESSBASE("aiAskEssbaseEnabled", "Ask Essbase"),

    /** Writing a calculation script from a description. */
    CALCULATION("aiCalcEnabled", "Calculation assistant"),

    /** Finding members by meaning rather than by name. */
    SEMANTIC_SEARCH("aiSemanticSearchEnabled", "Semantic search");

    /** The flag for AI as a whole; every capability is off when this is. */
    public static final String ENABLED_FLAG = "aiEnabled";

    private final String flag;

    private final String label;

    EssAiFeature(String flag, String label) {
        this.flag = flag;
        this.label = label;
    }

    /**
     * The key this capability is reported under in {@code /about/instance}.
     *
     * @return the flag name
     */
    public String getFlag() {
        return flag;
    }

    /**
     * The capability's name as a person would write it, for a caller putting it on screen. Worth
     * carrying here rather than deriving from the constant name, which would give "Mdx generator".
     *
     * @return the display name
     */
    public String getLabel() {
        return label;
    }

}
