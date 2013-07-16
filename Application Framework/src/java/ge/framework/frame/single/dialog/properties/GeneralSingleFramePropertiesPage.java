package ge.framework.frame.single.dialog.properties;

import com.jidesoft.swing.PartialLineBorder;
import com.jidesoft.swing.PartialSide;
import ge.framework.application.multi.dialog.properties.GeneralApplicationPropertiesPage;
import ge.framework.frame.core.dialog.properties.AbstractFramePropertiesPage;
import ge.framework.frame.core.objects.FrameConfiguration;
import ge.framework.frame.single.objects.SingleFrameConfiguration;
import ge.utils.bundle.Resources;
import ge.utils.problem.object.Problem;
import ge.utils.properties.PropertiesDialogPage;

import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 15/02/13
 * Time: 18:34
 */
public class GeneralSingleFramePropertiesPage extends AbstractFramePropertiesPage implements ActionListener
{
    private static final Resources resources =
            Resources.getInstance( "ge.framework.frame.resources" );

    private JCheckBox confirmExit;

    public GeneralSingleFramePropertiesPage()
    {
        super( "general" );
    }

    @Override
    public String getPageTitle()
    {
        return resources.getResourceString( GeneralSingleFramePropertiesPage.class, "title" );
    }

    @Override
    public Icon getPageIcon()
    {
        return resources.getResourceIcon( GeneralSingleFramePropertiesPage.class, "icon" );
    }

    @Override
    protected JComponent createContentPanel()
    {
        JPanel content = new JPanel( new BorderLayout() );

        JPanel componentPanel = new JPanel( new GridLayout( 3, 1 ) );

        componentPanel.add( createStartupShutdownSection() );

        content.add( BorderLayout.NORTH, componentPanel );

        return content;
    }

    private Component createStartupShutdownSection()
    {
        confirmExit =
                new JCheckBox( resources.getResourceString( GeneralSingleFramePropertiesPage.class, "confirmExit" ) );
        confirmExit.addActionListener( this );

        JPanel startupShutdownPanel = new JPanel( new GridLayout( 3, 1 ) );
        startupShutdownPanel.setBorder(
                new TitledBorder( new PartialLineBorder( Color.GRAY, 1, PartialSide.NORTH ),
                                  resources.getResourceString( GeneralSingleFramePropertiesPage.class,
                                                               "startupShutdown" ) ) );

        startupShutdownPanel.add( confirmExit );

        return startupShutdownPanel;
    }

    @Override
    public void setCurrentValues( FrameConfiguration configuration )
    {
        SingleFrameConfiguration singleFrameConfiguration = ( SingleFrameConfiguration ) configuration;
        confirmExit.setSelected( singleFrameConfiguration.isAskBeforeExit() );
    }

    @Override
    public List<Problem> validateProperties()
    {
        return null;
    }

    @Override
    public void updateProperties( FrameConfiguration configuration )
    {
        SingleFrameConfiguration singleFrameConfiguration = ( SingleFrameConfiguration ) configuration;
        singleFrameConfiguration.setAskBeforeExit( confirmExit.isSelected() );
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        Object source = e.getSource();

        if ( source == confirmExit )
        {
            firePropertyValueChangedEvent();
        }
    }
}
