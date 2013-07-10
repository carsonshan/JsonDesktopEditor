package ge.utils.xml.w3c;

import org.apache.log4j.Logger;
import org.apache.xerces.dom.CDATASectionImpl;
import org.apache.xerces.dom.CoreDocumentImpl;
import org.apache.xerces.dom.ElementImpl;
import org.jdom2.Attribute;
import org.jdom2.CDATA;
import org.jdom2.Content;
import org.jdom2.Element;
import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 11/01/13
 * Time: 18:22
 */
public class XmlHelper
{
    private static Logger logger = Logger.getLogger( XmlHelper.class );

    public static Content nodeToContent( org.w3c.dom.Node node )
    {
        Content retVal = null;

        if ( node instanceof org.w3c.dom.Element )
        {
            retVal = OrgW3cDomElementToElement( ( org.w3c.dom.Element ) node );
        }
        else if ( node instanceof CDATASection )
        {
            retVal = CDATASectionToCDATA( ( CDATASection ) node );
        }
        else
        {
            logger.debug( "Failed to convert object to jDom type: " + node.getClass().getSimpleName() );
        }

        return retVal;
    }

    private static Content CDATASectionToCDATA( CDATASection cdataSection )
    {
        return new CDATA( cdataSection.getTextContent() );
    }

    private static Element OrgW3cDomElementToElement( org.w3c.dom.Element element )
    {
        Element retVal = new Element( element.getTagName() );

        NamedNodeMap namedNodeMap = element.getAttributes();

        for ( int i = 0; i < namedNodeMap.getLength(); i++ )
        {
            Node node = namedNodeMap.item( i );

            if ( node instanceof Attr )
            {
                Attr attr = ( Attr ) node;

                Attribute attribute = new Attribute( attr.getName(), attr.getValue() );
                retVal.setAttribute( attribute );
            }
        }

        NodeList nodeList = element.getChildNodes();

        for ( int i = 0; i < nodeList.getLength(); i++ )
        {
            Content content = nodeToContent( nodeList.item( i ) );

            if ( content != null )
            {
                retVal.addContent( content );
            }
        }

        return retVal;
    }

    public static org.w3c.dom.Node contentToNode( Document document, Content content )
    {
        org.w3c.dom.Node retVal = null;

        if ( content instanceof Element )
        {
            retVal = ElementToOrgW3cDomElement( document, ( Element ) content );
        }
        else if ( content instanceof CDATA )
        {
            retVal = CDATAToOrgW3cDomElement( document, ( CDATA ) content );
        }
        else
        {
            logger.debug( "Failed to convert object to w3c type: " + content.getClass().getSimpleName() );
        }

        return retVal;
    }

    private static Node CDATAToOrgW3cDomElement( Document document, CDATA content )
    {
        return new CDATASectionImpl( ( CoreDocumentImpl ) document, content.getText() );
    }

    private static org.w3c.dom.Element ElementToOrgW3cDomElement( Document document, Element element )
    {
        org.w3c.dom.Element retVal = new ElementImpl( ( CoreDocumentImpl ) document, element.getName() );

        List<Attribute> attributes = element.getAttributes();

        for ( Attribute attribute : attributes )
        {
            retVal.setAttribute( attribute.getName(), attribute.getValue() );
        }

        List<Content> contents = element.getContent();

        for ( Content content : contents )
        {
            Node node = contentToNode( document, content );

            if ( node != null )
            {
                retVal.appendChild( node );
            }
        }

        return retVal;
    }
}
