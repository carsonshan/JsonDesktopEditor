package ge.framework.application.multi.dialog.properties;

import com.jidesoft.swing.PartialLineBorder;
import com.jidesoft.swing.PartialSide;
import ge.framework.application.multi.objects.MultiApplicationConfiguration;
import ge.framework.application.multi.objects.enums.OpenLocationEnum;
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
public class GeneralMultiApplicationPropertiesPage extends PropertiesDialogPage<MultiApplicationConfiguration> implements
                                                                                                     ActionListener,
                                                                                                     ChangeListener
{
    private static final Resources resources =
            Resources.getInstance( "ge.framework.application.resources" );

    private JCheckBox reopenLast;

    private JCheckBox confirmExit;

    private JCheckBox exitOnLastWindowClose;

    private JSpinner allowedRecently;

    private EnumeratedButtonGroup<OpenLocationEnum> openingButtonGroup;

    private JRadioButton openInNewWindow;

    private JRadioButton openInSameWindow;

    private JRadioButton confirmWindow;

    public GeneralMultiApplicationPropertiesPage()
    {
        super( "general" );
    }

    @Override
    public String getPageTitle()
    {
        return resources.getResourceString( GeneralMultiApplicationPropertiesPage.class, "title" );
    }

    @Override
    public Icon getPageIcon()
    {
        return resources.getResourceIcon( GeneralMultiApplicationPropertiesPage.class, "icon" );
    }

    @Override
    protected JComponent createContentPanel()
    {
        JPanel content = new JPanel( new BorderLayout() );

        JPanel componentPanel = new JPanel( new GridLayout( 3, 1 ) );

        componentPanel.add( createStartupShutdownSection() );
        componentPanel.add( createOpeningSection() );
        componentPanel.add( createRecentlyOpened() );

        content.add( BorderLayout.NORTH, componentPanel );

        return content;
    }

    private Component createStartupShutdownSection()
    {
        reopenLast =
                new JCheckBox( resources.getResourceString( GeneralMultiApplicationPropertiesPage.class, "reopenLast" ) );
        reopenLast.addActionListener( this );

        confirmExit =
                new JCheckBox( resources.getResourceString( GeneralMultiApplicationPropertiesPage.class, "confirmExit" ) );
        confirmExit.addActionListener( this );

        exitOnLastWindowClose =
                new JCheckBox( resources.getResourceString( GeneralMultiApplicationPropertiesPage.class,
                                                            "exitOnLastWindowClose" ) );
        exitOnLastWindowClose.addActionListener( this );

        JPanel startupShutdownPanel = new JPanel( new GridLayout( 3, 1 ) );
        startupShutdownPanel.setBorder(
                new TitledBorder( new PartialLineBorder( Color.GRAY, 1, PartialSide.NORTH ),
                                  resources.getResourceString( GeneralMultiApplicationPropertiesPage.class,
                                                               "startupShutdown" ) ) );

        startupShutdownPanel.add( reopenLast );
        startupShutdownPanel.add( confirmExit );
        startupShutdownPanel.add( exitOnLastWindowClose );

        return startupShutdownPanel;
    }

    private Component createOpeningSection()
    {
        openInNewWindow = new JRadioButton(
                resources.getResourceString( GeneralMultiApplicationPropertiesPage.class, "openInNewWindow" ) );
        openInNewWindow.addActionListener( this );

        openInSameWindow = new JRadioButton(
                resources.getResourceString( GeneralMultiApplicationPropertiesPage.class, "openInSameWindow" ) );
        openInSameWindow.addActionListener( this );

        confirmWindow = new JRadioButton(
                resources.getResourceString( GeneralMultiApplicationPropertiesPage.class, "confirmWindow" ) );
        confirmWindow.addActionListener( this );

        openingButtonGroup = new EnumeratedButtonGroup<OpenLocationEnum>();
        openingButtonGroup.add( OpenLocationEnum.NEW, openInNewWindow );
        openingButtonGroup.add( OpenLocationEnum.EXISTING, openInSameWindow );
        openingButtonGroup.add( OpenLocationEnum.ASK, confirmWindow );

        JPanel openingPanel = new JPanel( new GridLayout( 3, 1 ) );
        openingPanel.setBorder(
                new TitledBorder( new PartialLineBorder( Color.GRAY, 1, PartialSide.NORTH ),
                                  resources.getResourceString( GeneralMultiApplicationPropertiesPage.class,
                                                               "openingFrame" ) ) );

        openingPanel.add( openInNewWindow );
        openingPanel.add( openInSameWindow );
        openingPanel.add( confirmWindow );

        return openingPanel;
    }

    private Component createRecentlyOpened()
    {
        JPanel recentlyOpenedPanel = new JPanel( new FlowLayout( FlowLayout.LEADING ) );
        recentlyOpenedPanel.setBorder(
                new TitledBorder( new PartialLineBorder( Color.GRAY, 1, PartialSide.NORTH ),
                                  resources.getResourceString( GeneralMultiApplicationPropertiesPage.class,
                                                               "recentlyOpened" ) ) );

        SpinnerModel spinnerModel = new SpinnerNumberModel( 0, 0, 99, 1 );
        allowedRecently = new JSpinner( spinnerModel );
        allowedRecently.addChangeListener( this );

        recentlyOpenedPanel.add( new JLabel(
                resources.getResourceString( GeneralMultiApplicationPropertiesPage.class, "allowedRecently" ) ) );
        recentlyOpenedPanel.add( allowedRecently );

        return recentlyOpenedPanel;
    }

    @Override
    public void setCurrentValues( MultiApplicationConfiguration applicationConfiguration )
    {
        reopenLast.setSelected( applicationConfiguration.isReOpenLast() );
        confirmExit.setSelected( applicationConfiguration.isAskBeforeExit() );
        exitOnLastWindowClose.setSelected( applicationConfiguration.isExitOnFinalWindowClose() );
        openingButtonGroup.setSelected( applicationConfiguration.getOpenLocation(), true );
        allowedRecently.setValue( applicationConfiguration.getAllowedRecentlyOpenedCount() );
    }

    @Override
    public List<Problem> validateProperties()
    {
        return null;
    }

    @Override
    public void updateProperties( MultiApplicationConfiguration applicationConfiguration )
    {
        applicationConfiguration.setReOpenLast( reopenLast.isSelected() );
        applicationConfiguration.setAskBeforeExit( confirmExit.isSelected() );
        applicationConfiguration.setExitOnFinalWindowClose( exitOnLastWindowClose.isSelected() );
        applicationConfiguration.setOpenLocation( openingButtonGroup.getSelectionEnum() );
        applicationConfiguration.setAllowedRecentlyOpenedCount( ( Integer ) allowedRecently.getValue() );
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        Object source = e.getSource();

        if ( ( source == reopenLast ) || ( source == confirmExit ) || ( source == openInSameWindow ) ||
                ( source == openInNewWindow ) || ( source == confirmWindow ) )
        {
            firePropertyValueChangedEvent();
        }
    }

    @Override
    public void stateChanged( ChangeEvent e )
    {
        Object source = e.getSource();

        if ( source == allowedRecently )
        {
            firePropertyValueChangedEvent();
        }
    }
}
