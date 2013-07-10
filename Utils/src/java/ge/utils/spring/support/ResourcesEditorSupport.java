package ge.utils.spring.support;

import ge.utils.bundle.Resources;

import java.beans.PropertyEditorSupport;

public class ResourcesEditorSupport extends PropertyEditorSupport
{
    public void setAsText( String text ) throws
                                         IllegalArgumentException
    {
        setValue( Resources.getInstance( text ) );
    }
}
