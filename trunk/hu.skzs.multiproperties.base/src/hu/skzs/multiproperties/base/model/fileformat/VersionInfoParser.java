package hu.skzs.multiproperties.base.model.fileformat;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * The <code>VersionInfoParser</code> is a {@link DefaultHandler} implementation for parsing only the version information from a
 * MultiProperties file.
 * 
 * @author Krisztian_Zsolt_Sall
 * @see SchemaConverterFactory
 * 
 */
class VersionInfoParser extends DefaultHandler {

    private static final String VERSION_TAG = "Version";
    private boolean insideVersionElement;
    private String version;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (VERSION_TAG.equals(localName))
        {
            insideVersionElement = true;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (VERSION_TAG.equals(localName))
        {
            insideVersionElement = false;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (insideVersionElement)
        {
            version = new String(ch);
        }
    }

    @Override
    public void endDocument() throws SAXException {
        if (version == null)
        {
            throw new SAXException("Missing version information");
        }
    }

    /**
     * Returns the version information
     * 
     * @return the version information
     */
    public String getVersion()
    {
        return version;
    }
}
