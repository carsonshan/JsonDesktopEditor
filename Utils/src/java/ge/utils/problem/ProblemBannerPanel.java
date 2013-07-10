package ge.utils.problem;

import com.jidesoft.dialog.BannerPanel;
import com.jidesoft.swing.JideButton;
import com.jidesoft.swing.StyledLabel;
import ge.utils.bundle.Resources;
import ge.utils.problem.object.Problem;
import ge.utils.problem.object.ProblemList;
import ge.utils.text.StringArgumentMessageFormat;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class ProblemBannerPanel extends BannerPanel implements ActionListener
{
    private static final Resources resources =
            Resources.getInstance( "ge.utils.resources" );

    private JPanel problemPanel = null;

    private ProblemList problems;

    private String moreLabelResource = resources.getResourceString( ProblemBannerPanel.class, "more", "label" );

    private JideButton moreButton;

    public ProblemBannerPanel()
    {
        super();

        initialisePanel();
    }

    public ProblemBannerPanel( String title )
    {
        super( title );

        initialisePanel();
    }

    public ProblemBannerPanel( String title, String subTitle )
    {
        super( title, subTitle );

        initialisePanel();
    }

    public ProblemBannerPanel( String title, String subTitle, ImageIcon titleIcon )
    {
        super( title, subTitle, titleIcon );

        initialisePanel();
    }

    public ProblemBannerPanel( String title, String subTitle, JComponent iconComponent )
    {
        super( title, subTitle, iconComponent );

        initialisePanel();
    }

    private void initialisePanel()
    {
        moreButton = new JideButton();
        moreButton.setButtonStyle( JideButton.HYPERLINK_STYLE );
        moreButton.setIcon( resources.getResourceIcon( ProblemBannerPanel.class, "more", "icon" ) );
        moreButton.setHorizontalAlignment( SwingConstants.LEADING );
        moreButton.setFocusable( false );
        moreButton.addActionListener( this );
    }

    @Override
    public void lazyInitialize()
    {
        super.lazyInitialize();

        if ( ( problems != null ) && ( problems.isEmpty() == false ) )
        {
            problems.sort();

            problemPanel = new JPanel();
            problemPanel.setBorder( new EmptyBorder( 10, 10, 2, 10 ) );
            problemPanel.setOpaque( false );

            if ( problems.size() > 2 )
            {
                for ( int i = 0; i < 2; i++ )
                {
                    Problem problem = problems.get( i );

                    problemPanel.add( new StyledLabel( problem.getResourceString(),
                                                       problem.getResourceIcon(),
                                                       JLabel.LEADING ) );
                }

                Map<String, Object> arguments = new HashMap<String, Object>();
                arguments.put( "moreCount", problems.size() - 2 );
                String label = StringArgumentMessageFormat.format( moreLabelResource, arguments );
                moreButton.setText( label );

                problemPanel.add( moreButton );
            }
            else
            {
                for ( Problem problem : problems )
                {
                    problemPanel.add( new StyledLabel( problem.getResourceString(),
                                                       problem.getResourceIcon(),
                                                       JLabel.LEADING ) );
                }
            }

            problemPanel.setLayout( new GridLayout( problemPanel.getComponentCount(), 1 ) );

            problemPanel.validate();

            add( problemPanel, "Last" );
        }
        else if ( problemPanel != null )
        {
            remove( problemPanel );
            problemPanel = null;
        }

        validate();

        Container parent = getParent();

        while ( parent != null )
        {
            parent.validate();

            parent = parent.getParent();
        }
    }

    public ProblemList getProblems()
    {
        return problems;
    }

    public void setProblems( ProblemList problems )
    {
        this.problems = problems;
        lazyInitialize();
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        if ( e.getSource() == moreButton )
        {
            Container topLevelAncestor = getTopLevelAncestor();

            ProblemDialog problemDialog = null;

            if ( topLevelAncestor instanceof Dialog )
            {
                Dialog dialog = ( Dialog ) topLevelAncestor;
                problemDialog = new ProblemDialog( dialog, problems );
            }
            else if ( topLevelAncestor instanceof Frame )
            {
                Frame frame = ( Frame ) topLevelAncestor;
                problemDialog = new ProblemDialog( frame, problems );
            }
            else
            {
                problemDialog = new ProblemDialog( problems );
            }

            problemDialog.doModal();
        }
    }
}
