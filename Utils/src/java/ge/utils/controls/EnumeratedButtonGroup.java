package ge.utils.controls;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 18/02/13
 * Time: 10:27
 */
public class EnumeratedButtonGroup<ENUM extends Enum> extends ButtonGroup
{
    private Map<ENUM, AbstractButton> enumMap = new HashMap<ENUM, AbstractButton>();

    public void add( ENUM value, AbstractButton button )
    {
        if ( enumMap.containsKey( value ) == true )
        {
            remove( value );
        }

        enumMap.put( value, button );

        super.add( button );
    }

    private void remove( ENUM value )
    {
        if ( enumMap.containsKey( value ) == true )
        {
            super.remove( enumMap.get( value ) );
            enumMap.remove( value );
        }
    }

    public void setSelected( ENUM value, boolean b )
    {
        if ( enumMap.containsKey( value ) == true )
        {
            AbstractButton abstractButton = enumMap.get( value );

            super.setSelected( abstractButton.getModel(), b );
        }
    }

    public ENUM getSelectionEnum()
    {
        ButtonModel selection = super.getSelection();

        for ( Map.Entry<ENUM, AbstractButton> enumAbstractButtonEntry : enumMap.entrySet() )
        {
            AbstractButton value = enumAbstractButtonEntry.getValue();

            if ( value == selection )
            {
                return enumAbstractButtonEntry.getKey();
            }
        }

        return null;
    }
}
