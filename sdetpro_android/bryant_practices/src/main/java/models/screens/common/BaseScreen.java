package models.screens.common;

import core.ConciseApi;
import core.MultipleXpathEvaluate;
import utils.WaitUtilities;

public class BaseScreen extends ConciseApi {
    public boolean waitUntilTheLabelDisplayed(String label) {
        int retry = 0;
        boolean isDisplay = isLabelDisplayed(label);
        while (!isDisplay && retry < 30) {
            WaitUtilities.wait(300);
            isDisplay = isLabelDisplayed(label);
            retry++;
        }
        return isDisplay;
    }
    public NavigationBar getNavigation() {
        return new NavigationBar();
    }
    protected String lblEvaluateOf(String label) {
        return new MultipleXpathEvaluate()
                .android(String.format("//*[contains(translate(@text, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '%s')]", label.toLowerCase()))
                .ios(String.format("//*[contains(translate(@label, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '%s')]", label.toLowerCase()))
                .getByPlatform();
    }
    protected boolean isLabelDisplayed(String label) {
        try {
            return cmd.getMiddlePointOfElementFromDOM(lblEvaluateOf(label)) != null;
        } catch (Exception ex) {
            return false;
        }
    }
}