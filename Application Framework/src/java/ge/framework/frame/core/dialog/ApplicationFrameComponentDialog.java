package ge.framework.frame.core.dialog;

import com.jidesoft.dialog.ButtonPanel;
import com.jidesoft.dialog.StandardDialog;
import com.jidesoft.swing.PartialEtchedBorder;
import com.jidesoft.swing.PartialLineBorder;
import com.jidesoft.swing.PartialSide;
import ge.framework.frame.core.ApplicationFrame;
import ge.framework.frame.core.ApplicationFrameComponent;
import ge.framework.frame.core.dialog.panel.ApplicationFrameComponentPanel;
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
 * Date: 27/02/13
 * Time: 12:13
 */
public abstract class ApplicationFrameComponentDialog extends StandardDialog implements ActionListener
{
    private static Resources resources = Resources.getInstance(
            "ge.framework.frame.resources" );

    private List<ApplicationFrameComponent> applicationDocumentComponents;

    private JPanel contentPanel;

    private ApplicationFrameComponent applicationFrameComponent;

    private boolean result = false;

    private ButtonPanel buttonPanel;

    private JButton okButton;

    private JButton cancelButton;

    private ApplicationFrameComponentPanel applicationFrameComponentPanel;

    public ApplicationFrameComponentDialog( ApplicationFrame applicationFrame, List<ApplicationFrameComponent> applicationFrameComponents )
    {
        super( applicationFrame );

        this.applicationDocumentComponents = applicationFrameComponents;

        initializeDialog();
    }

    private void initializeDialog()
    {
        setModal( true );

        setDefaultCloseOperation( DISPOSE_ON_CLOSE );

        setSize( 450, 500 );
        setMinimumSize( new Dimension( 450, 500 ) );

        ApplicationFrame applicationFrame = ( ApplicationFrame ) getParent();
        FrameDefinition frameDefinition = applicationFrame.getFrameDefinition();

        setIconImage( frameDefinition.getSmallImage() );
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
            applicationFrameComponentPanel = createApplicationFrameComponentPanel();
            applicationFrameComponentPanel.setApplicationFrameComponents( applicationDocumentComponents );

            contentPanel = new JPanel( new BorderLayout() );

            contentPanel.setBorder( new PartialLineBorder( Color.GRAY, 1, PartialSide.NORTH ) );

            contentPanel.add( BorderLayout.CENTER, applicationFrameComponentPanel );
        }

        return contentPanel;
    }

    protected abstract ApplicationFrameComponentPanel createApplicationFrameComponentPanel();

    @Override
    public ButtonPanel createButtonPanel()
    {
        if ( buttonPanel == null )
        {
            buttonPanel = new ButtonPanel();

            okButton =
                    new JButton( resources.getResourceString( ApplicationFrameComponentDialog.class, "button", "ok" ) );
            okButton.addActionListener( this );

            buttonPanel.addButton( okButton, ButtonPanel.AFFIRMATIVE_BUTTON );

            cancelButton = new JButton(
                    resources.getResourceString( ApplicationFrameComponentDialog.class, "button", "cancel" ) );
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
            applicationFrameComponent = applicationFrameComponentPanel.getSelectedValue();

            if ( applicationFrameComponent != null )
            {
                result = true;

                dispose();
            }
        }
        else if ( source == cancelButton )
        {
            result = false;
            dispose();
        }
    }

    public ApplicationFrameComponent getApplicationFrameComponent()
    {
        return applicationFrameComponent;
    }
}
