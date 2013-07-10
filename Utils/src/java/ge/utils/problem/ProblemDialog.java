package ge.utils.problem;

import com.jidesoft.dialog.ButtonPanel;
import com.jidesoft.dialog.StandardDialog;
import com.jidesoft.swing.PartialLineBorder;
import com.jidesoft.swing.PartialSide;
import com.jidesoft.swing.StyledLabel;
import ge.utils.bundle.Resources;
import ge.utils.problem.enums.ProblemType;
import ge.utils.problem.object.Problem;
import ge.utils.problem.table.ProblemTable;
import org.apache.log4j.Logger;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ProblemDialog extends StandardDialog implements ActionListener
{
    private static final Logger logger = Logger.getLogger( ProblemDialog.class );

    private static final Resources resources =
            Resources.getInstance( "ge.utils.resources" );

    private List<Problem> problems;

    private ProblemType problemType;

    private JButton okButton;

    private ButtonPanel buttonPanel = null;

    public ProblemDialog( List<Problem> problems )
    {
        super();

        initializeDialog( problems );
    }

    public ProblemDialog( Frame owner, List<Problem> problems )
    {
        super( owner, true );

        initializeDialog( problems );
    }

    public ProblemDialog( Dialog owner, List<Problem> problems )
    {
        super( owner, true );

        initializeDialog( problems );
    }

    private void initializeDialog( List<Problem> problems )
    {
        if ( ( problems == null ) || ( problems.isEmpty() == true ) )
        {
            logger.error( "problems cannot be null or empty." );
            throw new IllegalArgumentException( "problems cannot be null or empty." );
        }

        setModal( true );

        setSize( 350, 250 );
        setMaximumSize( new Dimension( 350, 250 ) );
        setPreferredSize( new Dimension( 350, 250 ) );

        this.problems = problems;

        setDefaultCloseOperation( DISPOSE_ON_CLOSE );

        problemType = ProblemType.INFORMATION;

        for ( Problem problem : this.problems )
        {
            if ( problem.getProblemType() != null )
            {
                if ( problem.getProblemType() == ProblemType.ERROR )
                {
                    problemType = ProblemType.ERROR;
                }
                else if ( ( problem.getProblemType() == ProblemType.WARNING ) && ( problemType != ProblemType.ERROR ) )
                {
                    problemType = ProblemType.WARNING;
                }
            }
        }

        String titleText = "";

        if ( problemType == ProblemType.ERROR )
        {
            titleText = resources.getResourceString( ProblemDialog.class, "text", "error" );
        }
        else if ( problemType == ProblemType.WARNING )
        {
            titleText = resources.getResourceString( ProblemDialog.class, "text", "warning" );
        }
        else if ( problemType == ProblemType.INFORMATION )
        {
            titleText = resources.getResourceString( ProblemDialog.class, "text", "information" );
        }

        if ( ( titleText != null ) && ( titleText.length() != 0 ) )
        {
            setTitle( titleText );
        }

        setIconImage( getImage() );
    }

    private Image getImage()
    {
        if ( problemType != null )
        {
            if ( problemType == ProblemType.ERROR )
            {
                return resources.getResourceImage( ProblemDialog.class, "icon", "error" );
            }
            else if ( problemType == ProblemType.WARNING )
            {
                return resources.getResourceImage( ProblemDialog.class, "icon", "warning" );
            }
            else if ( problemType == ProblemType.INFORMATION )
            {
                return resources.getResourceImage( ProblemDialog.class, "icon", "information" );
            }
        }

        return null;
    }

    private Icon getIcon()
    {
        if ( problemType != null )
        {
            if ( problemType == ProblemType.ERROR )
            {
                return resources.getResourceIcon( ProblemDialog.class, "icon", "error" );
            }
            else if ( problemType == ProblemType.WARNING )
            {
                return resources.getResourceIcon( ProblemDialog.class, "icon", "warning" );
            }
            else if ( problemType == ProblemType.INFORMATION )
            {
                return resources.getResourceIcon( ProblemDialog.class, "icon", "information" );
            }
        }

        return null;
    }

    public void doModal()
    {
        setLocationRelativeTo( getOwner() );
        setResizable( false );
        pack();

        setVisible( true );

        dispose();
    }

    @Override
    public JComponent createBannerPanel()
    {
        return null;
    }

    @Override
    public JComponent createContentPanel()
    {
        JPanel topPanel = new JPanel( new BorderLayout() );

        topPanel.setBorder( new EmptyBorder( 10, 10, 10, 10 ) );

        StyledLabel messageLabel =
                new StyledLabel( resources.getResourceString( ProblemDialog.class, "message" ), getIcon(),
                                 SwingConstants.LEADING );

        topPanel.add( BorderLayout.NORTH, messageLabel );
        ProblemTable problemTable = new ProblemTable( problems );

        JScrollPane scrollPane = new JScrollPane( problemTable );
        scrollPane.setBorder( new EmptyBorder( 10, 10, 10, 10 ) );

        topPanel.add( BorderLayout.CENTER, scrollPane );

        return topPanel;
    }

    @Override
    public ButtonPanel createButtonPanel()
    {
        if ( buttonPanel == null )
        {
            buttonPanel = new ButtonPanel();

            buttonPanel.setBorder( BorderFactory.createCompoundBorder(
                    new PartialLineBorder( Color.GRAY, 1, PartialSide.NORTH ), new EmptyBorder( 5, 5, 5, 5 ) ) );

            okButton = new JButton( resources.getResourceString( ProblemDialog.class, "button", "ok" ) );
            okButton.addActionListener( this );

            buttonPanel.addButton( okButton, ButtonPanel.AFFIRMATIVE_BUTTON );

            buttonPanel.setSizeConstraint( ButtonPanel.NO_LESS_THAN );
        }

        return buttonPanel;
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        Object source = e.getSource();

        if ( source == okButton )
        {
            setVisible( false );
        }
    }
}
