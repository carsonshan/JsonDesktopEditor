package ge.utils.properties;

import com.jidesoft.dialog.ButtonPanel;
import com.jidesoft.dialog.ScrollableButtonPanel;
import com.jidesoft.dialog.StandardDialog;
import com.jidesoft.swing.PartialEtchedBorder;
import com.jidesoft.swing.PartialLineBorder;
import com.jidesoft.swing.PartialSide;
import ge.utils.bundle.Resources;
import ge.utils.problem.ProblemBannerPanel;
import ge.utils.problem.enums.ProblemType;
import ge.utils.problem.object.Problem;
import ge.utils.problem.object.ProblemList;
import ge.utils.properties.event.PropertyValueChangedEvent;
import ge.utils.properties.event.PropertyValueChangedListener;
import ge.utils.properties.object.PropertyDialogObject;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 18/02/13
 * Time: 11:07
 */
public abstract class PropertiesDialog<PROPERTIES extends PropertyDialogObject> extends StandardDialog implements
                                                                                                       ActionListener,
                                                                                                       PropertyValueChangedListener
{
    private static final Resources resources =
            Resources.getInstance( "ge.utils.resources" );

    private PROPERTIES properties;

    private ButtonPanel buttonPanel;

    private JButton okButton;

    private JButton cancelButton;

    private List<PropertiesDialogPage> pages = new ArrayList<PropertiesDialogPage>();

    private Map<String, PropertiesDialogPage<PROPERTIES>> pageMap =
            new HashMap<String, PropertiesDialogPage<PROPERTIES>>();

    private Map<String, PropertyPageButton> buttonMap =
            new HashMap<String, PropertyPageButton>();

    private ButtonGroup indexButtonGroup;

    private CardLayout cardLayout;

    private ScrollableButtonPanel indexPanel;

    private JPanel pagesPanel;

    private ProblemBannerPanel bannerPanel;

    private JPanel contentPanel;

    private JButton applyButton;

    public PropertiesDialog( PROPERTIES properties )
    {
        super();
        this.properties = properties;
    }

    public PropertiesDialog( Frame owner, PROPERTIES properties )
    {
        super( owner, true );
        this.properties = properties;
    }

    public PropertiesDialog( Dialog owner, PROPERTIES properties )
    {
        super( owner, true );
        this.properties = properties;
    }

    public final void doModal()
    {
        setLocationRelativeTo( getOwner() );
        pack();

        setVisible( true );

        dispose();
    }

    @Override
    public final JComponent createBannerPanel()
    {
        if ( bannerPanel == null )
        {
            bannerPanel = new ProblemBannerPanel();
            bannerPanel.setTitle( getBannerTitle() );
            bannerPanel.setSubtitle( getBannerSubTitle() );
            bannerPanel.setTitleIcon( getBannerIcon() );
            bannerPanel.setTitleIconLocation( SwingConstants.WEST );
            bannerPanel.setBackground( getBackground() );
            bannerPanel.setBorder( new PartialLineBorder( Color.GRAY, 1, PartialSide.SOUTH ) );
        }
        return bannerPanel;
    }

    protected abstract String getBannerTitle();

    protected abstract String getBannerSubTitle();

    protected abstract ImageIcon getBannerIcon();

    @Override
    public final JComponent createContentPanel()
    {
        if ( contentPanel == null )
        {
            pages = getPages();
            contentPanel = new JPanel( new BorderLayout( 10, 10 ) );

            contentPanel.setBorder( new EmptyBorder( 10, 10, 10, 10 ) );

            contentPanel.add( BorderLayout.WEST, createIndexPanel() );
            contentPanel.add( BorderLayout.CENTER, createPagesPanel() );

            if ( ( pages != null ) && ( pages.isEmpty() == false ) )
            {
                for ( PropertiesDialogPage<PROPERTIES> page : pages )
                {
                    if ( pageMap.containsKey( page.getId() ) == false )
                    {
                        PropertyPageButton iconButton =
                                new PropertyPageButton( page.getId(), page.getPageTitle(), page.getPageIcon() );
                        iconButton.addActionListener( this );

                        pagesPanel.add( page, page.getId() );
                        indexButtonGroup.add( iconButton );
                        indexPanel.add( iconButton );

                        pageMap.put( page.getId(), page );
                        buttonMap.put( page.getId(), iconButton );

                        page.addPropertyValueChangedListener( this );
                    }
                    else
                    {
                        throw new IllegalStateException( "duplicate id: " + page.getId() );
                    }
                }

                String id = properties.getLastPage();

                if ( id != null )
                {
                    showPage( id );
                }
                else
                {
                    PropertiesDialogPage<PROPERTIES> propertiesPropertiesDialogPage = pages.get( 0 );

                    if ( propertiesPropertiesDialogPage != null )
                    {
                        showPage( propertiesPropertiesDialogPage.getId() );
                    }
                }
            }
        }

        return contentPanel;
    }

    protected abstract List<PropertiesDialogPage> getPages();

    private Component createIndexPanel()
    {
        indexButtonGroup = new ButtonGroup();

        indexPanel = new ScrollableButtonPanel( 1, 0 );
        indexPanel.setBorder( BorderFactory.createEmptyBorder( 10, 10, 10, 10 ) );
        indexPanel.setGroupGap( 0 );
        indexPanel.setButtonGap( 0 );
        indexPanel.setOpaque( true );
        indexPanel.setBackground( getBackground() );

        ButtonPanelScrollPane scrollPane = new ButtonPanelScrollPane( indexPanel );

        scrollPane.setBorder( new LineBorder( Color.GRAY, 1 ) );
        scrollPane.getViewport().setOpaque( true );
        scrollPane.getViewport().setBackground( getBackground() );
        scrollPane.setHorizontalScrollBarPolicy( ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER );
        scrollPane.setVerticalScrollBarPolicy( ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED );

        return scrollPane;
    }

    private Component createPagesPanel()
    {
        cardLayout = new CardLayout();

        pagesPanel = new JPanel( cardLayout );

        return pagesPanel;
    }

    @Override
    public final ButtonPanel createButtonPanel()
    {
        if ( buttonPanel == null )
        {
            okButton = new JButton( resources.getResourceString( PropertiesDialog.class, "button", "ok" ) );
            okButton.addActionListener( this );

            cancelButton = new JButton( resources.getResourceString( PropertiesDialog.class, "button", "cancel" ) );
            cancelButton.addActionListener( this );

            applyButton = new JButton( resources.getResourceString( PropertiesDialog.class, "button", "apply" ) );
            applyButton.addActionListener( this );

            buttonPanel = new ButtonPanel();
            buttonPanel.addButton( okButton, ButtonPanel.AFFIRMATIVE_BUTTON );
            buttonPanel.addButton( cancelButton, ButtonPanel.CANCEL_BUTTON );
            buttonPanel.addButton( applyButton, ButtonPanel.AFFIRMATIVE_BUTTON );

            buttonPanel.setSizeConstraint( ButtonPanel.NO_LESS_THAN );

            buttonPanel.setBorder(
                    BorderFactory.createCompoundBorder( new PartialEtchedBorder( PartialEtchedBorder.LOWERED,
                                                                                 PartialSide.NORTH ),
                                                        new EmptyBorder( 5, 5, 5, 5 ) ) );
        }

        return buttonPanel;
    }

    @Override
    public final void actionPerformed( ActionEvent e )
    {
        Object source = e.getSource();

        if ( source == okButton )
        {
            if ( validateProperties() == true )
            {
                updateProperties( properties );
                dispose();
            }
        }
        else if ( source == cancelButton )
        {
            dispose();
        }
        else if ( source == applyButton )
        {
            if ( validateProperties() == true )
            {
                updateProperties( properties );
            }
        }
        else if ( source instanceof PropertyPageButton )
        {
            PropertyPageButton button = ( PropertyPageButton ) source;

            showPage( button.getId() );
        }
    }

    private void showPage( String id )
    {
        PropertiesDialogPage<PROPERTIES> propertiesPropertiesDialogPage = pageMap.get( id );

        PropertyPageButton button = buttonMap.get( id );

        propertiesPropertiesDialogPage.setupPage( properties );

        indexButtonGroup.setSelected( button.getModel(), true );

        cardLayout.show( pagesPanel, button.getId() );

        properties.setLastPage( id );
    }

    private boolean validateProperties()
    {
        ProblemList problems = new ProblemList();

        if ( ( pages != null ) && ( pages.isEmpty() == false ) )
        {
            for ( PropertiesDialogPage<PROPERTIES> page : pages )
            {
                if ( page.isSetup() == true )
                {
                    List<Problem> pageProblems = page.validateProperties();

                    if ( ( pageProblems != null ) && ( pageProblems.isEmpty() == false ) )
                    {
                        for ( Problem pageProblem : pageProblems )
                        {
                            Problem newProblem =
                                    new Problem( resources, PropertiesDialog.class, pageProblem.getProblemType(),
                                                 "pageProblem" );
                            newProblem.putArgument( "pageName", page.getPageTitle() );
                            newProblem.putArgument( "problem", pageProblem );

                            problems.add( newProblem );
                        }
                    }
                }
            }
        }

        bannerPanel.setProblems( problems );

        boolean valid;

        if ( problems.getHighestProblemType() == ProblemType.ERROR )
        {
            valid = false;
        }
        else
        {
            valid = true;
        }

        okButton.setEnabled( valid );
        applyButton.setEnabled( valid );

        return valid;
    }

    @Override
    public void propertyChanged( PropertyValueChangedEvent event )
    {
        validateProperties();
    }

    private void updateProperties( PROPERTIES properties )
    {
        if ( ( pages != null ) && ( pages.isEmpty() == false ) )
        {
            for ( PropertiesDialogPage<PROPERTIES> page : pages )
            {
                if ( page.isSetup() == true )
                {
                    page.updateProperties( properties );
                }
            }
        }
    }
}
