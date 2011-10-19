
package hu.skzs.multiproperties.base.model.schema;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.skzs.multiproperties.base.model.schema package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.skzs.multiproperties.base.model.schema
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link MultiProperties }
     * 
     */
    public MultiProperties createMultiProperties() {
        return new MultiProperties();
    }

    /**
     * Create an instance of {@link MultiProperties.Records }
     * 
     */
    public MultiProperties.Records createMultiPropertiesRecords() {
        return new MultiProperties.Records();
    }

    /**
     * Create an instance of {@link MultiProperties.Records.Property }
     * 
     */
    public MultiProperties.Records.Property createMultiPropertiesRecordsProperty() {
        return new MultiProperties.Records.Property();
    }

    /**
     * Create an instance of {@link MultiProperties.Columns }
     * 
     */
    public MultiProperties.Columns createMultiPropertiesColumns() {
        return new MultiProperties.Columns();
    }

    /**
     * Create an instance of {@link MultiProperties.Records.Comment }
     * 
     */
    public MultiProperties.Records.Comment createMultiPropertiesRecordsComment() {
        return new MultiProperties.Records.Comment();
    }

    /**
     * Create an instance of {@link MultiProperties.Records.Empty }
     * 
     */
    public MultiProperties.Records.Empty createMultiPropertiesRecordsEmpty() {
        return new MultiProperties.Records.Empty();
    }

    /**
     * Create an instance of {@link MultiProperties.Records.Property.Value }
     * 
     */
    public MultiProperties.Records.Property.Value createMultiPropertiesRecordsPropertyValue() {
        return new MultiProperties.Records.Property.Value();
    }

    /**
     * Create an instance of {@link MultiProperties.Columns.Key }
     * 
     */
    public MultiProperties.Columns.Key createMultiPropertiesColumnsKey() {
        return new MultiProperties.Columns.Key();
    }

    /**
     * Create an instance of {@link MultiProperties.Columns.Column }
     * 
     */
    public MultiProperties.Columns.Column createMultiPropertiesColumnsColumn() {
        return new MultiProperties.Columns.Column();
    }

}
