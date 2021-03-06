package ge.framework.frame.core.command.dialog;

import com.jidesoft.dialog.ButtonPanel;
import com.jidesoft.dialog.StandardDialog;
import com.jidesoft.swing.PartialEtchedBorder;
import com.jidesoft.swing.PartialLineBorder;
import com.jidesoft.swing.PartialSide;
import ge.framework.frame.core.ApplicationFrame;
import ge.framework.frame.core.command.ApplicationCommandBarComponent;
import ge.framework.frame.core.command.dialog.panel.CommandBarPanel;
import ge.framework.frame.core.objects.FrameDefinition;
import ge.utils.bundle.Resources;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 01/03/13
 * Time: 17:20
 */
public class CommandBarDialog extends StandardDialog implements ActionListener
{
    private static Resources resources = Resources.getInstance(
            "ge.framework.frame.resources" );

    private boolean result = false;

    private final List<ApplicationCommandBarComponent> applicationCommandBarComponents;

    private ButtonPanel buttonPanel;

    private JButton okButton;

    private JButton cancelButton;

    private JPanel contentPanel;

    private CommandBarPanel commandBarPanel;

    private List<ApplicationCommandBarComponent> selectedValues;

    public CommandBarDialog( ApplicationFrame frame,
                             List<ApplicationCommandBarComponent> applicationCommandBarComponents )
    {
        super( frame );

        this.applicationCommandBarComponents = applicationCommandBarComponents;

        initializeDialog();
    }

    private void initializeDialog()
    {
        setModal( true );

        setDefaultCloseOperation( DISPOSE_ON_CLOSE );

        setSize( 450, 500 );
        setMinimumSize( new Dimension( 450, 500 ) );

        ApplicationFrame frame = ( ApplicationFrame ) getParent();
        FrameDefinition frameDefinition = frame.getFrameDefinition();

        setIconImage( frameDefinition.getSmallImage() );
        setTitle( resources.getResourceString( CommandBarDialog.class, "title" ) );
    }

    @Override
    public JComponent createBannerPanel()
    {
        return null;
    }

    @Override
    public JComponent createContentPanel()
    {
        if ( contentPanel == null )
        {
            commandBarPanel = new CommandBarPanel();
            commandBarPanel.setCommandBarComponents( applicationCommandBarComponents );

            contentPanel = new JPanel( new BorderLayout() );

            contentPanel.setBorder( new PartialLineBorder( Color.GRAY, 1, PartialSide.NORTH ) );

            contentPanel.add( BorderLayout.CENTER, commandBarPanel );
        }

        return contentPanel;
    }

    @Override
    public ButtonPanel createButtonPanel()
    {
        if ( buttonPanel == null )
        {
            buttonPanel = new ButtonPanel();

            okButton =
                    new JButton( resources.getResourceString( CommandBarDialog.class, "button", "ok" ) );
            okButton.addActionListener( this );

            buttonPanel.addButton( okButton, ButtonPanel.AFFIRMATIVE_BUTTON );

            cancelButton = new JButton(
                    resources.getResourceString( CommandBarDialog.class, "button", "cancel" ) );
            cancelButton.addActionListener( this );

            buttonPanel.addButton( cancelButton, ButtonPanel.CANCEL_BUTTON );

            buttonPanel.setSizeConstraint( ButtonPanel.NO_LESS_THAN );

            buttonPanel.setBorder(
                    BorderFactory.createCompoundBorder( new PartialEtchedBorder( PartialEtchedBorder.LOWERED,
                                                                                 PartialSide.NORTH ),
                                                        new EmptyBorder( 5, 5, 5, 5 ) ) );
        }

        return buttonPanel;
    }

    public boolean doModal()
    {
        setResizable( false );
        pack();
        setLocationRelativeTo( getOwner() );

        setVisible( true );

        dispose();

        return result;
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        Object source = e.getSource();

        if ( source == okButton )
        {
            selectedValues = commandBarPanel.getSelectedValues();

            result = true;

            dispose();
        }
        else if ( source == cancelButton )
        {
            result = false;
            dispose();
        }
    }

    public List<ApplicationCommandBarComponent> getSelectedValues()
    {
        return selectedValues;
    }
}
