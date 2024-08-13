package core;

import config.enums.MobilePlatform;

public class MultipleXpathEvaluate {
    private String androidXpathEvaluate;
    private String iosXpathEvaluate;

    public MultipleXpathEvaluate android(String xpath) {
        this.androidXpathEvaluate = xpath;
        return this;
    }

    public MultipleXpathEvaluate ios(String xpath) {
        this.iosXpathEvaluate = xpath;
        return this;
    }

    public String getByPlatform() {
        return MobilePlatform.isIOS() ? iosXpathEvaluate : androidXpathEvaluate;
    }
}
