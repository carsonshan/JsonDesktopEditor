package ge.framework.application.single.dialog.properties;

import com.jidesoft.swing.PartialLineBorder;
import com.jidesoft.swing.PartialSide;
import ge.framework.application.multi.objects.MultiApplicationConfiguration;
import ge.framework.application.multi.objects.enums.OpenLocationEnum;
import ge.framework.application.single.objects.SingleApplicationConfiguration;
import ge.utils.bundle.Resources;
import ge.utils.controls.EnumeratedButtonGroup;
import ge.utils.problem.object.Problem;
import ge.utils.properties.PropertiesDialogPage;

import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
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
public class GeneralSingleApplicationPropertiesPage extends PropertiesDialogPage<SingleApplicationConfiguration> implements
                                                                                                     ActionListener
{
    private static final Resources resources =
            Resources.getInstance( "ge.framework.application.resources" );

    private JCheckBox confirmExit;

    public GeneralSingleApplicationPropertiesPage()
    {
        super( "general" );
    }

    @Override
    public String getPageTitle()
    {
        return resources.getResourceString( GeneralSingleApplicationPropertiesPage.class, "title" );
    }

    @Override
    public Icon getPageIcon()
    {
        return resources.getResourceIcon( GeneralSingleApplicationPropertiesPage.class, "icon" );
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
                new JCheckBox( resources.getResourceString( GeneralSingleApplicationPropertiesPage.class, "confirmExit" ) );
        confirmExit.addActionListener( this );

        JPanel startupShutdownPanel = new JPanel( new GridLayout( 3, 1 ) );
        startupShutdownPanel.setBorder(
                new TitledBorder( new PartialLineBorder( Color.GRAY, 1, PartialSide.NORTH ),
                                  resources.getResourceString( GeneralSingleApplicationPropertiesPage.class,
                                                               "startupShutdown" ) ) );

        startupShutdownPanel.add( confirmExit );

        return startupShutdownPanel;
    }

    @Override
    public void setCurrentValues( SingleApplicationConfiguration applicationConfiguration )
    {
        confirmExit.setSelected( applicationConfiguration.isAskBeforeExit() );
    }

    @Override
    public List<Problem> validateProperties()
    {
        return null;
    }

    @Override
    public void updateProperties( SingleApplicationConfiguration applicationConfiguration )
    {
        applicationConfiguration.setAskBeforeExit( confirmExit.isSelected() );
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
