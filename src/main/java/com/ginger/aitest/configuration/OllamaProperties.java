package com.ginger.aitest.configuration;

import org.springframework.ai.ollama.api.OllamaModel;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("spring.ai.ollama.settings")
public class OllamaProperties {

    //<editor-fold desc="==== Vars ====">
    /**
     * Ollama Model to load. <br>
     * Default value will be OllamaModel.LLAMA3.
     */
    private OllamaModel model = OllamaModel.LLAMA3;

    /**
     * Ollama Model to load. <br>
     * Default value will be null.
     */
    private String modelString = null;

    /**
     * <B>Unknown</B>.
     */
    private String keepAlive = "true";

    /**
     * <B>Unknown</B>.
     */
    private boolean truncate = false;

    /**
     * Whether to use NUMA.
     */
    private Boolean useNUMA = false;

    /**
     * Sets the size of the context window used to generate the next token.
     */
    private Integer numCtx = 2048;

    /**
     * Prompt processing maximum batch size.
     */
    private Integer numBatch = 512;

    /**
     * The number of layers to send to the GPU(s). <br>
     * On macOS, it defaults to 1 to enable metal support, 0 to disable. <br>
     * 1 here indicates that NumGPU should be set dynamically.
     */
    private Integer numGPU = -1;

    /**
     * When using multiple GPUs this option controls which GPU is used for small tensors for which the overhead of
     * splitting the computation across all GPUs is not worthwhile. <br> <br>
     * The GPU in question will use slightly more VRAM to store a scratch buffer for temporary results.
     */
    private Integer mainGPU = 0;

    /**
     * <B>Unknown</B>.
     */
    private Boolean lowVRAM = false;

    /**
     * <B>Unknown</B>.
     */
    private boolean f16KV = false;

    /**
     * Return logits for all the tokens, not just the last one. <br>
     * To enable completions to return logprobs, this must be true.
     */
    private Boolean logitsAll = false;

    /**
     * Load only the vocabulary, not the weights.
     */
    private Boolean vocabOnly = false;

    /**
     * By default, models are mapped into memory,
     * which allows the system to load only the necessary parts of the model as needed. <br>
     * However, if the model is larger than your total amount of RAM or if your system is low on available memory,
     * using mmap might increase the risk of pageouts, negatively impacting performance. <br>
     * Disabling mmap results in slower load times but may reduce pageouts if you’re not using mlock. <br>
     * Note that if the model is larger than the total amount of RAM,
     * turning off mmap would prevent the model from loading at all. <br>
     * <br>
     * Set to true for LARGE Models.
     */
    private Boolean useMMap = null;

    /**
     * Lock the model in memory, preventing it from being swapped out when memory-mapped. <br>
     * This can improve performance but trades away some of the advantages of memory-mapping by requiring more RAM to
     * run and potentially slowing down load times as the model loads into RAM. <br>
     * <br>
     * Set to false for LARGE Models
     */
    private Boolean useMLock = false;

    /**
     * Sets the number of threads to use during computation. <br>
     * By default, Ollama will detect this for optimal performance. <br>
     * It is recommended to set this value to the number of physical CPU cores your system has
     * (as opposed to the logical number of cores). <br>
     * <br>
     * 0 = let the runtime decide
     */
    private Integer numThread = 0;

    /**
     * <B>Unknown</B>.
     */
    private Integer numKeep = 4;

    /**
     * Sets the random number seed to use for generation. <br>
     * Setting this to a specific number will make the model generate the same text for the same prompt. <br>
     * <br>
     * Default value -1
     */
    private Integer seed = -1;

    /**
     * <pre> Maximum number of tokens to predict when generating text. <br>
     * 1 = infinite generation
     *-2 = fill context</pre>
     */
    private Integer numPredict = -1;

    /**
     * Reduces the probability of generating nonsense. <br>
     * A higher value (e.g., 100) will give more diverse answers. <br>
     * while a lower value (e.g., 10) will be more conservative.
     */
    private Integer topK = 40;

    /**
     * Works together with top-k. <br>
     * A higher value (e.g., 0.95) will lead to more diverse text. <br>
     * while a lower value (e.g., 0.5) will generate more focused and conservative text.
     */
    private Double topP = 0.9;

    /** Tail-free sampling is used to reduce the impact of less probable tokens from the output. <br>
     * A higher value (e.g., 2.0) will reduce the impact more. <br>
     * while a value of 1.0 disables this setting.
     */
    private Float tfsZ = 1.0f;

    /**
     * <B>Unknown</B>.
     */
    private Float typicalP = 1.0f;

    /**
     * Sets how far back for the model to look back to prevent repetition. <br>
     * <br>
     * Default: 64 <br>
     * 0 = disabled <br>
     * -1 = num_ctx
     */
    private Integer repeatLastN = 64;

    /**
     * The temperature of the model. <br>
     * Increasing the temperature will make the model answer more creatively.
     */
    private Double temperature = 0.8;

    /**
     * Sets how strongly to penalize repetitions. <br>
     * A higher value (e.g., 1.5) will penalize repetitions more strongly. <br>
     * while a lower value (e.g., 0.9) will be more lenient.
     */
    private Double repeatPenalty = 1.1;

    /**
     * <B>Unknown</B>.
     */
    private Double presencePenalty = 0.0;

    /**
     * <B>Unknown</B>.
     */
    private Double frequencyPenalty = 0.0;

    /**
     * Enable Mirostat sampling for controlling perplexity. <br>
     * default: 0 <br>
     * 0 = disabled <br>
     * 1 = Mirostat <br>
     * 2 = Mirostat 2.0
     */
    private Integer mirostat = 0;

    /**
     * Controls the balance between coherence and diversity of the output. <br>
     * A lower value will result in more focused and coherent text.
     */
    private Float mirostatTau = 5.0f;

    /**
     * Influences how quickly the algorithm responds to feedback from the generated text. <br>
     * A lower learning rate will result in slower adjustments. <br>
     * while a higher learning rate will make the algorithm more responsive.
     */
    private Float mirostatEta = 0.1f;

    /**
     * <B>Unknown</B>.
     */
    private Boolean penalizeNewline = true;
    //</editor-fold>

    //<editor-fold desc="==== Getters ====">
    public OllamaModel getModel() {
        return model;
    }

    public String getModelString() {
        return modelString;
    }

    public String getKeepAlive() {
        return keepAlive;
    }

    public boolean isTruncate() {
        return truncate;
    }

    public Boolean getUseNUMA() {
        return useNUMA;
    }

    public Integer getNumCtx() {
        return numCtx;
    }

    public Integer getNumBatch() {
        return numBatch;
    }

    public Integer getNumGPU() {
        return numGPU;
    }

    public Integer getMainGPU() {
        return mainGPU;
    }

    public Boolean getLowVRAM() {
        return lowVRAM;
    }

    public boolean isF16KV() {
        return f16KV;
    }

    public Boolean getLogitsAll() {
        return logitsAll;
    }

    public Boolean getVocabOnly() {
        return vocabOnly;
    }

    public Boolean getUseMMap() {
        return useMMap;
    }

    public Boolean getUseMLock() {
        return useMLock;
    }

    public Integer getNumThread() {
        return numThread;
    }

    public Integer getNumKeep() {
        return numKeep;
    }

    public Integer getSeed() {
        return seed;
    }

    public Integer getNumPredict() {
        return numPredict;
    }

    public Integer getTopK() {
        return topK;
    }

    public Double getTopP() {
        return topP;
    }

    public Float getTfsZ() {
        return tfsZ;
    }

    public Float getTypicalP() {
        return typicalP;
    }

    public Integer getRepeatLastN() {
        return repeatLastN;
    }

    public Double getTemperature() {
        return temperature;
    }

    public Double getRepeatPenalty() {
        return repeatPenalty;
    }

    public Double getPresencePenalty() {
        return presencePenalty;
    }

    public Double getFrequencyPenalty() {
        return frequencyPenalty;
    }

    public Integer getMirostat() {
        return mirostat;
    }

    public Float getMirostatTau() {
        return mirostatTau;
    }

    public Float getMirostatEta() {
        return mirostatEta;
    }

    public Boolean getPenalizeNewline() {
        return penalizeNewline;
    }
    //</editor-fold>

    //<editor-fold desc="==== Setters ====">
    public void setModel(OllamaModel model) {
        this.model = model;
    }

    public void setModelString(String modelString) {
        this.modelString = modelString;
    }

    public void setKeepAlive(String keepAlive) {
        this.keepAlive = keepAlive;
    }

    public void setTruncate(boolean truncate) {
        this.truncate = truncate;
    }

    public void setUseNUMA(Boolean useNUMA) {
        this.useNUMA = useNUMA;
    }

    public void setNumCtx(Integer numCtx) {
        this.numCtx = numCtx;
    }

    public void setNumBatch(Integer numBatch) {
        this.numBatch = numBatch;
    }

    public void setNumGPU(Integer numGPU) {
        this.numGPU = numGPU;
    }

    public void setMainGPU(Integer mainGPU) {
        this.mainGPU = mainGPU;
    }

    public void setLowVRAM(Boolean lowVRAM) {
        this.lowVRAM = lowVRAM;
    }

    public void setF16KV(boolean f16KV) {
        this.f16KV = f16KV;
    }

    public void setLogitsAll(Boolean logitsAll) {
        this.logitsAll = logitsAll;
    }

    public void setVocabOnly(Boolean vocabOnly) {
        this.vocabOnly = vocabOnly;
    }

    public void setUseMMap(Boolean useMMap) {
        this.useMMap = useMMap;
    }

    public void setUseMLock(Boolean useMLock) {
        this.useMLock = useMLock;
    }

    public void setNumThread(Integer numThread) {
        this.numThread = numThread;
    }

    public void setNumKeep(Integer numKeep) {
        this.numKeep = numKeep;
    }

    public void setSeed(Integer seed) {
        this.seed = seed;
    }

    public void setNumPredict(Integer numPredict) {
        this.numPredict = numPredict;
    }

    public void setTopK(Integer topK) {
        this.topK = topK;
    }

    public void setTopP(Double topP) {
        this.topP = topP;
    }

    public void setTfsZ(Float tfsZ) {
        this.tfsZ = tfsZ;
    }

    public void setTypicalP(Float typicalP) {
        this.typicalP = typicalP;
    }

    public void setRepeatLastN(Integer repeatLastN) {
        this.repeatLastN = repeatLastN;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public void setRepeatPenalty(Double repeatPenalty) {
        this.repeatPenalty = repeatPenalty;
    }

    public void setPresencePenalty(Double presencePenalty) {
        this.presencePenalty = presencePenalty;
    }

    public void setFrequencyPenalty(Double frequencyPenalty) {
        this.frequencyPenalty = frequencyPenalty;
    }

    public void setMirostat(Integer mirostat) {
        this.mirostat = mirostat;
    }

    public void setMirostatTau(Float mirostatTau) {
        this.mirostatTau = mirostatTau;
    }

    public void setMirostatEta(Float mirostatEta) {
        this.mirostatEta = mirostatEta;
    }

    public void setPenalizeNewline(Boolean penalizeNewline) {
        this.penalizeNewline = penalizeNewline;
    }
    //</editor-fold>
}
