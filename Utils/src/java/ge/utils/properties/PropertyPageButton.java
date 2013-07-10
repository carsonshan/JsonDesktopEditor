package ge.utils.properties;

import com.jidesoft.swing.JideButton;

import javax.swing.Icon;
import javax.swing.SwingConstants;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 21/02/13
 * Time: 15:28
 */
public class PropertyPageButton extends JideButton
{
    private String id;

    public PropertyPageButton( String id, String pageTitle, Icon pageIcon )
    {
        super( pageTitle, pageIcon );
        this.id = id;
        setRequestFocusEnabled( false );
        setFocusable( false );
        setHorizontalAlignment( SwingConstants.CENTER );
        setVerticalTextPosition( SwingConstants.BOTTOM );
        setHorizontalTextPosition( SwingConstants.CENTER );
    }

    public String getId()
    {
        return id;
    }

    public void setId( String id )
    {
        this.id = id;
    }
}
