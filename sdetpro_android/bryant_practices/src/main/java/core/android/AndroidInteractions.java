package core.android;

import core.MobileInteractions;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.offset.PointOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.StringReader;
import java.lang.invoke.MethodHandles;
import java.util.Optional;

public class AndroidInteractions extends MobileInteractions {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private AndroidDriver driver;

    public AndroidInteractions(AndroidDriver driver) {
        this.driver = driver;
    }

    @Override
    public void pressEnterKeyboard() {
        driver.pressKey(new KeyEvent(AndroidKey.ENTER));
    }

    @Override
    public PointOption getMiddlePointOfElementFromDOM(String xpathExpression) {
        Document document = convertStringToXMLDocument(driver.getPageSource());
        PointOption pointOption = null;
        XPath xPath = XPathFactory.newInstance().newXPath();
        try {
            NodeList nodes = (NodeList) xPath.compile(xpathExpression).evaluate(document, XPathConstants.NODESET);
            Element element = (Element) nodes.item(0);
            if (element == null) {
                return pointOption;
            }
            pointOption = convertElementBoundsToPoint(element);
            String strBounds = element.getAttribute("bounds");
            LOGGER.info("Boundary of element: " + strBounds);
        } catch (XPathExpressionException e) {
            LOGGER.error("Cannot find any element which matches with Xpath Expression: {}", xpathExpression);
        }

        Optional.ofNullable(pointOption).orElseThrow(() -> {
            LOGGER.error("Cannot find any element which matches with Xpath Expression: {}", xpathExpression);
            return new IllegalArgumentException("Coordinate values must be defined");
        });

        return pointOption;
    }

    private Document convertStringToXMLDocument(String xmlString) {
        //Parser that produces DOM object trees from XML content
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        //API to obtain DOM Document instance
        DocumentBuilder builder = null;
        try {
            //Create DocumentBuilder with default configuration
            builder = factory.newDocumentBuilder();

            //Parse the content to Document object
            return builder.parse(new InputSource(new StringReader(xmlString)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public PointOption convertElementBoundsToPoint(Element element) {
        System.out.println("[DEBUG] Android - text: " + element.getAttribute("text"));
        String bounds = element.getAttribute("bounds");
        bounds = bounds.replace("][", ",").replace("[", "").replace("]", "");
        String[] coordinates = bounds.split(",");
        int x = Integer.parseInt(coordinates[0]);
        int y = Integer.parseInt(coordinates[1]);
        int width = Integer.parseInt(coordinates[2]) - x;
        int height = Integer.parseInt(coordinates[3]) - y;
        return PointOption.point(x + width / 2, y + height / 2);
    }
}
