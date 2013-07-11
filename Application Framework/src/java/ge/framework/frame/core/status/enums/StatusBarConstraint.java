package ge.framework.frame.core.status.enums;

import com.jidesoft.swing.JideBoxLayout;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 29/06/2012
 * Time: 07:36
 */
public enum StatusBarConstraint
{
    FLEXIBLE( JideBoxLayout.FLEXIBLE ),
    FIX( JideBoxLayout.FIX ),
    VARY( JideBoxLayout.VARY );

    private String constraint;

    StatusBarConstraint( String constraint )
    {
        this.constraint = constraint;
    }

    public String getConstraint()
    {
        return constraint;
    }
}
