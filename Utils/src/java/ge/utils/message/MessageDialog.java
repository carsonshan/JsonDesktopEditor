package ge.utils.message;

import com.jidesoft.dialog.ButtonPanel;
import com.jidesoft.dialog.StandardDialog;
import com.jidesoft.swing.PartialEtchedBorder;
import com.jidesoft.swing.PartialSide;
import com.jidesoft.swing.StyledLabel;
import ge.utils.bundle.Resources;
import ge.utils.message.enums.MessageLevel;
import ge.utils.message.enums.MessageResult;
import ge.utils.message.enums.MessageType;
import org.apache.log4j.Logger;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Map;

public class MessageDialog extends StandardDialog implements ActionListener,
                                                             WindowListener
{
    private static final Logger logger = Logger.getLogger( MessageDialog.class );

    private static final Resources resources =
            Resources.getInstance( "ge.utils.resources" );

    private boolean displayCheckBox;

    private String alternateCheckBoxText;

    private MessageType type;

    private MessageLevel level;

    private String message;

    private String title;

    private ButtonPanel buttonPanel;

    private JPanel contentPanel;

    private JButton abortButton;

    private JButton retryButton;

    private JButton ignoreButton;

    private JButton okButton;

    private JButton cancelButton;

    private JButton yesButton;

    private JButton noButton;

    private MessageResult result;

    private JCheckBox checkbox;

    private Map<MessageResult, String> alternateButtonText;

    public MessageDialog( String message ) throws
                                           HeadlessException
    {
        this( message, null, null, null, false, null, null );
    }

    public MessageDialog( String message,
                          Map<MessageResult, String> alternateButtonText ) throws
                                                                           HeadlessException
    {
        this( message, null, null, null, false, null, alternateButtonText );
    }

    public MessageDialog( String message, String title ) throws
                                                         HeadlessException
    {
        this( message, title, null, null, false, null, null );
    }

    public MessageDialog( String message, String title,
                          Map<MessageResult, String> alternateButtonText ) throws
                                                                           HeadlessException
    {
        this( message, title, null, null, false, null, alternateButtonText );
    }

    public MessageDialog( String message, MessageType type ) throws
                                                             HeadlessException
    {
        this( message, null, type, null, false, null, null );
    }

    public MessageDialog( String message, MessageType type,
                          Map<MessageResult, String> alternateButtonText ) throws
                                                                           HeadlessException
    {
        this( message, null, type, null, false, null, alternateButtonText );
    }

    public MessageDialog( String message, MessageLevel level ) throws
                                                               HeadlessException
    {
        this( message, null, null, level, false, null, null );
    }

    public MessageDialog( String message, MessageLevel level,
                          Map<MessageResult, String> alternateButtonText ) throws
                                                                           HeadlessException
    {
        this( message, null, null, level, false, null, alternateButtonText );
    }

    public MessageDialog( String message, boolean displayCheckBox ) throws
                                                                    HeadlessException
    {
        this( message, null, null, null, displayCheckBox, null, null );
    }

    public MessageDialog( String message, boolean displayCheckBox,
                          Map<MessageResult, String> alternateButtonText ) throws
                                                                           HeadlessException
    {
        this( message, null, null, null, displayCheckBox, null, alternateButtonText );
    }

    public MessageDialog( String message, boolean displayCheckBox, String alternateCheckBoxText ) throws
                                                                                                  HeadlessException
    {
        this( message, null, null, null, displayCheckBox, alternateCheckBoxText, null );
    }

    public MessageDialog( String message, boolean displayCheckBox, String alternateCheckBoxText,
                          Map<MessageResult, String> alternateButtonText ) throws
                                                                           HeadlessException
    {
        this( message, null, null, null, displayCheckBox, alternateCheckBoxText, alternateButtonText );
    }

    public MessageDialog( String message, String title, MessageType type ) throws
                                                                           HeadlessException
    {
        this( message, title, type, null, false, null, null );
    }

    public MessageDialog( String message, String title, MessageType type,
                          Map<MessageResult, String> alternateButtonText ) throws
                                                                           HeadlessException
    {
        this( message, title, type, null, false, null, alternateButtonText );
    }

    public MessageDialog( String message, String title, MessageLevel level ) throws
                                                                             HeadlessException
    {
        this( message, title, null, level, false, null, null );
    }

    public MessageDialog( String message, String title, MessageLevel level,
                          Map<MessageResult, String> alternateButtonText ) throws
                                                                           HeadlessException
    {
        this( message, title, null, level, false, null, alternateButtonText );
    }

    public MessageDialog( String message, String title, boolean displayCheckBox ) throws
                                                                                  HeadlessException
    {
        this( message, title, null, null, displayCheckBox, null, null );
    }

    public MessageDialog( String message, String title, boolean displayCheckBox,
                          Map<MessageResult, String> alternateButtonText ) throws
                                                                           HeadlessException
    {
        this( message, title, null, null, displayCheckBox, null, alternateButtonText );
    }

    public MessageDialog( String message, String title, boolean displayCheckBox, String alternateCheckBoxText ) throws
                                                                                                                HeadlessException
    {
        this( message, title, null, null, displayCheckBox, alternateCheckBoxText, null );
    }

    public MessageDialog( String message, String title, boolean displayCheckBox, String alternateCheckBoxText,
                          Map<MessageResult, String> alternateButtonText ) throws
                                                                           HeadlessException
    {
        this( message, title, null, null, displayCheckBox, alternateCheckBoxText, alternateButtonText );
    }

    public MessageDialog( String message, String title, MessageType type, MessageLevel level ) throws
                                                                                               HeadlessException
    {
        this( message, title, type, level, false, null, null );
    }

    public MessageDialog( String message, String title, MessageType type, MessageLevel level,
                          Map<MessageResult, String> alternateButtonText ) throws
                                                                           HeadlessException
    {
        this( message, title, type, level, false, null, alternateButtonText );
    }

    public MessageDialog( String message, String title, MessageType type, boolean displayCheckBox ) throws
                                                                                                    HeadlessException
    {
        this( message, title, type, null, displayCheckBox, null, null );
    }

    public MessageDialog( String message, String title, MessageType type, boolean displayCheckBox,
                          Map<MessageResult, String> alternateButtonText ) throws
                                                                           HeadlessException
    {
        this( message, title, type, null, displayCheckBox, null, alternateButtonText );
    }

    public MessageDialog( String message,
                          String title,
                          MessageType type,
                          boolean displayCheckBox,
                          String alternateCheckBoxText ) throws
                                                         HeadlessException
    {
        this( message, title, type, null, displayCheckBox, alternateCheckBoxText, null );
    }

    public MessageDialog( String message,
                          String title,
                          MessageType type,
                          boolean displayCheckBox,
                          String alternateCheckBoxText,
                          Map<MessageResult, String> alternateButtonText ) throws
                                                                           HeadlessException
    {
        this( message, title, type, null, displayCheckBox, alternateCheckBoxText, alternateButtonText );
    }

    public MessageDialog( String message,
                          String title,
                          MessageType type,
                          MessageLevel level,
                          boolean displayCheckBox ) throws
                                                    HeadlessException
    {
        this( message, title, type, level, displayCheckBox, null, null );
    }

    public MessageDialog( String message,
                          String title,
                          MessageType type,
                          MessageLevel level,
                          boolean displayCheckBox,
                          Map<MessageResult, String> alternateButtonText ) throws
                                                                           HeadlessException
    {
        this( message, title, type, level, displayCheckBox, null, alternateButtonText );
    }

    public MessageDialog( String message,
                          String title,
                          MessageType type,
                          MessageLevel level,
                          boolean displayCheckBox,
                          String alternateCheckBoxText ) throws
                                                         HeadlessException
    {
        this( message, title, type, level, displayCheckBox, alternateCheckBoxText, null );
    }

    public MessageDialog( String message,
                          String title,
                          MessageType type,
                          MessageLevel level,
                          boolean displayCheckBox,
                          String alternateCheckBoxText,
                          Map<MessageResult, String> alternateButtonText ) throws
                                                                           HeadlessException
    {
        super();

        setModal( true );

        if ( message == null )
        {
            logger.error( "message cannot be null." );
            throw new IllegalArgumentException( "message cannot be null." );
        }

        this.type = type;
        this.level = level;
        this.message = message;
        this.title = title;
        this.displayCheckBox = displayCheckBox;
        this.alternateCheckBoxText = alternateCheckBoxText;
        this.alternateButtonText = alternateButtonText;

        setDefaultCloseOperation( DO_NOTHING_ON_CLOSE );

        addWindowListener( this );

        initializeDialog();
    }

    public MessageDialog( Frame owner, String message ) throws
                                                        HeadlessException
    {
        this( owner, message, null, null, null, false, null, null );
    }

    public MessageDialog( Frame owner, String message,
                          Map<MessageResult, String> alternateButtonText ) throws
                                                                           HeadlessException
    {
        this( owner, message, null, null, null, false, null, alternateButtonText );
    }

    public MessageDialog( Frame owner, String message, String title ) throws
                                                                      HeadlessException
    {
        this( owner, message, title, null, null, false, null, null );
    }

    public MessageDialog( Frame owner, String message, String title,
                          Map<MessageResult, String> alternateButtonText ) throws
                                                                           HeadlessException
    {
        this( owner, message, title, null, null, false, null, alternateButtonText );
    }

    public MessageDialog( Frame owner, String message, MessageType type ) throws
                                                                          HeadlessException
    {
        this( owner, message, null, type, null, false, null, null );
    }

    public MessageDialog( Frame owner, String message, MessageType type, MessageLevel level )
    {
        this( owner, message, null, type, level, false, null, null );
    }

    public MessageDialog( Frame owner, String message, MessageType type,
                          Map<MessageResult, String> alternateButtonText ) throws
                                                                           HeadlessException
    {
        this( owner, message, null, type, null, false, null, alternateButtonText );
    }

    public MessageDialog( Frame owner, String message, MessageLevel level ) throws
                                                                            HeadlessException
    {
        this( owner, message, null, null, level, false, null, null );
    }

    public MessageDialog( Frame owner, String message, MessageLevel level,
                          Map<MessageResult, String> alternateButtonText ) throws
                                                                           HeadlessException
    {
        this( owner, message, null, null, level, false, null, alternateButtonText );
    }

    public MessageDialog( Frame owner, String message, boolean displayCheckBox ) throws
                                                                                 HeadlessException
    {
        this( owner, message, null, null, null, displayCheckBox, null, null );
    }

    public MessageDialog( Frame owner, String message, boolean displayCheckBox,
                          Map<MessageResult, String> alternateButtonText ) throws
                                                                           HeadlessException
    {
        this( owner, message, null, null, null, displayCheckBox, null, alternateButtonText );
    }

    public MessageDialog( Frame owner, String message, boolean displayCheckBox, String alternateCheckBoxText ) throws
                                                                                                               HeadlessException
    {
        this( owner, message, null, null, null, displayCheckBox, alternateCheckBoxText, null );
    }

    public MessageDialog( Frame owner, String message, boolean displayCheckBox, String alternateCheckBoxText,
                          Map<MessageResult, String> alternateButtonText ) throws
                                                                           HeadlessException
    {
        this( owner, message, null, null, null, displayCheckBox, alternateCheckBoxText, alternateButtonText );
    }

    public MessageDialog( Frame owner, String message, String title, MessageType type ) throws
                                                                                        HeadlessException
    {
        this( owner, message, title, type, null, false, null, null );
    }

    public MessageDialog( Frame owner, String message, String title, MessageType type,
                          Map<MessageResult, String> alternateButtonText ) throws
                                                                           HeadlessException
    {
        this( owner, message, title, type, null, false, null, alternateButtonText );
    }

    public MessageDialog( Frame owner, String message, String title, MessageLevel level ) throws
                                                                                          HeadlessException
    {
        this( owner, message, title, null, level, false, null, null );
    }

    public MessageDialog( Frame owner, String message, String title, MessageLevel level,
                          Map<MessageResult, String> alternateButtonText ) throws
                                                                           HeadlessException
    {
        this( owner, message, title, null, level, false, null, alternateButtonText );
    }

    public MessageDialog( Frame owner, String message, String title, boolean displayCheckBox ) throws
                                                                                               HeadlessException
    {
        this( owner, message, title, null, null, displayCheckBox, null, null );
    }

    public MessageDialog( Frame owner, String message, String title, boolean displayCheckBox,
                          Map<MessageResult, String> alternateButtonText ) throws
                                                                           HeadlessException
    {
        this( owner, message, title, null, null, displayCheckBox, null, alternateButtonText );
    }

    public MessageDialog( Frame owner,
                          String message,
                          String title,
                          boolean displayCheckBox,
                          String alternateCheckBoxText ) throws
                                                         HeadlessException
    {
        this( owner, message, title, null, null, displayCheckBox, alternateCheckBoxText, null );
    }

    public MessageDialog( Frame owner,
                          String message,
                          String title,
                          boolean displayCheckBox,
                          String alternateCheckBoxText,
                          Map<MessageResult, String> alternateButtonText ) throws
                                                                           HeadlessException
    {
        this( owner, message, title, null, null, displayCheckBox, alternateCheckBoxText, alternateButtonText );
    }

    public MessageDialog( Frame owner, String message, String title, MessageType type, MessageLevel level ) throws
                                                                                                            HeadlessException
    {
        this( owner, message, title, type, level, false, null, null );
    }

    public MessageDialog( Frame owner, String message, String title, MessageType type, MessageLevel level,
                          Map<MessageResult, String> alternateButtonText ) throws
                                                                           HeadlessException
    {
        this( owner, message, title, type, level, false, null, alternateButtonText );
    }

    public MessageDialog( Frame owner, String message, String title, MessageType type, boolean displayCheckBox ) throws
                                                                                                                 HeadlessException
    {
        this( owner, message, title, type, null, displayCheckBox, null, null );
    }

    public MessageDialog( Frame owner, String message, String title, MessageType type, boolean displayCheckBox,
                          Map<MessageResult, String> alternateButtonText ) throws
                                                                           HeadlessException
    {
        this( owner, message, title, type, null, displayCheckBox, null, alternateButtonText );
    }

    public MessageDialog( Frame owner,
                          String message,
                          String title,
                          MessageType type,
                          boolean displayCheckBox,
                          String alternateCheckBoxText ) throws
                                                         HeadlessException
    {
        this( owner, message, title, type, null, displayCheckBox, alternateCheckBoxText, null );
    }

    public MessageDialog( Frame owner,
                          String message,
                          String title,
                          MessageType type,
                          boolean displayCheckBox,
                          String alternateCheckBoxText,
                          Map<MessageResult, String> alternateButtonText ) throws
                                                                           HeadlessException
    {
        this( owner, message, title, type, null, displayCheckBox, alternateCheckBoxText, alternateButtonText );
    }

    public MessageDialog( Frame owner,
                          String message,
                          String title,
                          MessageType type,
                          MessageLevel level,
                          boolean displayCheckBox ) throws
                                                    HeadlessException
    {
        this( owner, message, title, type, level, displayCheckBox, null, null );
    }

    public MessageDialog( Frame owner,
                          String message,
                          String title,
                          MessageType type,
                          MessageLevel level,
                          boolean displayCheckBox,
                          Map<MessageResult, String> alternateButtonText ) throws
                                                                           HeadlessException
    {
        this( owner, message, title, type, level, displayCheckBox, null, alternateButtonText );
    }

    public MessageDialog( Frame owner,
                          String message,
                          String title,
                          MessageType type,
                          MessageLevel level,
                          boolean displayCheckBox,
                          String alternateCheckBoxText ) throws
                                                         HeadlessException
    {
        this( owner, message, title, type, level, displayCheckBox, alternateCheckBoxText, null );
    }

    public MessageDialog( Frame owner,
                          String message,
                          String title,
                          MessageType type,
                          MessageLevel level,
                          boolean displayCheckBox,
                          String alternateCheckBoxText,
                          Map<MessageResult, String> alternateButtonText ) throws
                                                                           HeadlessException
    {
        super( owner, true );

        if ( message == null )
        {
            logger.error( "message cannot be null." );
            throw new IllegalArgumentException( "message cannot be null." );
        }

        this.type = type;
        this.level = level;
        this.message = message;
        this.title = title;
        this.displayCheckBox = displayCheckBox;
        this.alternateCheckBoxText = alternateCheckBoxText;
        this.alternateButtonText = alternateButtonText;

        setDefaultCloseOperation( DO_NOTHING_ON_CLOSE );

        addWindowListener( this );

        initializeDialog();
    }

    public MessageDialog( Dialog owner, String message ) throws
                                                         HeadlessException
    {
        this( owner, message, null, null, null, false, null, null );
    }

    public MessageDialog( Dialog owner, String message,
                          Map<MessageResult, String> alternateButtonText ) throws
                                                                           HeadlessException
    {
        this( owner, message, null, null, null, false, null, alternateButtonText );
    }

    public MessageDialog( Dialog owner, String message, String title ) throws
                                                                       HeadlessException
    {
        this( owner, message, title, null, null, false, null, null );
    }

    public MessageDialog( Dialog owner, String message, String title,
                          Map<MessageResult, String> alternateButtonText ) throws
                                                                           HeadlessException
    {
        this( owner, message, title, null, null, false, null, alternateButtonText );
    }

    public MessageDialog( Dialog owner, String message, MessageType type ) throws
                                                                           HeadlessException
    {
        this( owner, message, null, type, null, false, null, null );
    }

    public MessageDialog( Dialog owner, String message, MessageType type,
                          Map<MessageResult, String> alternateButtonText ) throws
                                                                           HeadlessException
    {
        this( owner, message, null, type, null, false, null, alternateButtonText );
    }

    public MessageDialog( Dialog owner, String message, MessageLevel level ) throws
                                                                             HeadlessException
    {
        this( owner, message, null, null, level, false, null, null );
    }

    public MessageDialog( Dialog owner, String message, MessageLevel level,
                          Map<MessageResult, String> alternateButtonText ) throws
                                                                           HeadlessException
    {
        this( owner, message, null, null, level, false, null, alternateButtonText );
    }

    public MessageDialog( Dialog owner, String message, MessageType type, MessageLevel level ) throws
                                                                                               HeadlessException
    {
        this( owner, message, null, type, level, false, null, null );
    }

    public MessageDialog( Dialog owner, String message, MessageType type, MessageLevel level,
                          Map<MessageResult, String> alternateButtonText ) throws
                                                                           HeadlessException
    {
        this( owner, message, null, type, level, false, null, alternateButtonText );
    }

    public MessageDialog( Dialog owner, String message, boolean displayCheckBox ) throws
                                                                                  HeadlessException
    {
        this( owner, message, null, null, null, displayCheckBox, null, null );
    }

    public MessageDialog( Dialog owner, String message, boolean displayCheckBox,
                          Map<MessageResult, String> alternateButtonText ) throws
                                                                           HeadlessException
    {
        this( owner, message, null, null, null, displayCheckBox, null, alternateButtonText );
    }

    public MessageDialog( Dialog owner, String message, boolean displayCheckBox, String alternateCheckBoxText ) throws
                                                                                                                HeadlessException
    {
        this( owner, message, null, null, null, displayCheckBox, alternateCheckBoxText, null );
    }

    public MessageDialog( Dialog owner, String message, boolean displayCheckBox, String alternateCheckBoxText,
                          Map<MessageResult, String> alternateButtonText ) throws
                                                                           HeadlessException
    {
        this( owner, message, null, null, null, displayCheckBox, alternateCheckBoxText, alternateButtonText );
    }

    public MessageDialog( Dialog owner, String message, String title, MessageType type ) throws
                                                                                         HeadlessException
    {
        this( owner, message, title, type, null, false, null, null );
    }

    public MessageDialog( Dialog owner, String message, String title, MessageType type,
                          Map<MessageResult, String> alternateButtonText ) throws
                                                                           HeadlessException
    {
        this( owner, message, title, type, null, false, null, alternateButtonText );
    }

    public MessageDialog( Dialog owner, String message, String title, MessageLevel level ) throws
                                                                                           HeadlessException
    {
        this( owner, message, title, null, level, false, null, null );
    }

    public MessageDialog( Dialog owner, String message, String title, MessageLevel level,
                          Map<MessageResult, String> alternateButtonText ) throws
                                                                           HeadlessException
    {
        this( owner, message, title, null, level, false, null, alternateButtonText );
    }

    public MessageDialog( Dialog owner, String message, String title, boolean displayCheckBox ) throws
                                                                                                HeadlessException
    {
        this( owner, message, title, null, null, displayCheckBox, null, null );
    }

    public MessageDialog( Dialog owner, String message, String title, boolean displayCheckBox,
                          Map<MessageResult, String> alternateButtonText ) throws
                                                                           HeadlessException
    {
        this( owner, message, title, null, null, displayCheckBox, null, alternateButtonText );
    }

    public MessageDialog( Dialog owner,
                          String message,
                          String title,
                          boolean displayCheckBox,
                          String alternateCheckBoxText ) throws
                                                         HeadlessException
    {
        this( owner, message, title, null, null, displayCheckBox, alternateCheckBoxText, null );
    }

    public MessageDialog( Dialog owner,
                          String message,
                          String title,
                          boolean displayCheckBox,
                          String alternateCheckBoxText,
                          Map<MessageResult, String> alternateButtonText ) throws
                                                                           HeadlessException
    {
        this( owner, message, title, null, null, displayCheckBox, alternateCheckBoxText, alternateButtonText );
    }

    public MessageDialog( Dialog owner, String message, String title, MessageType type, MessageLevel level ) throws
                                                                                                             HeadlessException
    {
        this( owner, message, title, type, level, false, null, null );
    }

    public MessageDialog( Dialog owner, String message, String title, MessageType type, MessageLevel level,
                          Map<MessageResult, String> alternateButtonText ) throws
                                                                           HeadlessException
    {
        this( owner, message, title, type, level, false, null, alternateButtonText );
    }

    public MessageDialog( Dialog owner, String message, String title, MessageType type, boolean displayCheckBox ) throws
                                                                                                                  HeadlessException
    {
        this( owner, message, title, type, null, displayCheckBox, null, null );
    }

    public MessageDialog( Dialog owner, String message, String title, MessageType type, boolean displayCheckBox,
                          Map<MessageResult, String> alternateButtonText ) throws
                                                                           HeadlessException
    {
        this( owner, message, title, type, null, displayCheckBox, null, alternateButtonText );
    }

    public MessageDialog( Dialog owner,
                          String message,
                          String title,
                          MessageType type,
                          boolean displayCheckBox,
                          String alternateCheckBoxText ) throws
                                                         HeadlessException
    {
        this( owner, message, title, type, null, displayCheckBox, alternateCheckBoxText, null );
    }

    public MessageDialog( Dialog owner,
                          String message,
                          String title,
                          MessageType type,
                          boolean displayCheckBox,
                          String alternateCheckBoxText,
                          Map<MessageResult, String> alternateButtonText ) throws
                                                                           HeadlessException
    {
        this( owner, message, title, type, null, displayCheckBox, alternateCheckBoxText, alternateButtonText );
    }

    public MessageDialog( Dialog owner,
                          String message,
                          String title,
                          MessageType type,
                          MessageLevel level,
                          boolean displayCheckBox ) throws
                                                    HeadlessException
    {
        this( owner, message, title, type, level, displayCheckBox, null, null );
    }

    public MessageDialog( Dialog owner,
                          String message,
                          String title,
                          MessageType type,
                          MessageLevel level,
                          boolean displayCheckBox,
                          Map<MessageResult, String> alternateButtonText ) throws
                                                                           HeadlessException
    {
        this( owner, message, title, type, level, displayCheckBox, null, alternateButtonText );
    }

    public MessageDialog( Dialog owner,
                          String message,
                          String title,
                          MessageType type,
                          MessageLevel level,
                          boolean displayCheckBox,
                          String alternateCheckBoxText ) throws
                                                         HeadlessException
    {
        this( owner, message, title, type, level, displayCheckBox, alternateCheckBoxText, null );
    }

    public MessageDialog( Dialog owner,
                          String message,
                          String title,
                          MessageType type,
                          MessageLevel level,
                          boolean displayCheckBox,
                          String alternateCheckBoxText,
                          Map<MessageResult, String> alternateButtonText ) throws
                                                                           HeadlessException
    {
        super( owner, true );

        if ( message == null )
        {
            logger.error( "message cannot be null." );
            throw new IllegalArgumentException( "message cannot be null." );
        }

        this.type = type;
        this.level = level;
        this.message = message;
        this.title = title;
        this.displayCheckBox = displayCheckBox;
        this.alternateCheckBoxText = alternateCheckBoxText;
        this.alternateButtonText = alternateButtonText;

        setDefaultCloseOperation( DO_NOTHING_ON_CLOSE );

        addWindowListener( this );

        initializeDialog();
    }

    private void initializeDialog()
    {
        setIconImage( getImage() );

        String titleText = "";

        if ( ( level != null ) && ( level != MessageLevel.NONE ) )
        {
            if ( level == MessageLevel.ERROR )
            {
                titleText = resources.getResourceString( MessageDialog.class, "text", "error" );
            }
            else if ( level == MessageLevel.WARNING )
            {
                titleText = resources.getResourceString( MessageDialog.class, "text", "warning" );
            }
            else if ( level == MessageLevel.INFORMATION )
            {
                titleText = resources.getResourceString( MessageDialog.class, "text", "information" );
            }
            else if ( level == MessageLevel.QUESTION )
            {
                titleText = resources.getResourceString( MessageDialog.class, "text", "question" );
            }

            if ( ( title != null ) && ( title.length() != 0 ) )
            {
                titleText += ": " + title;
            }
        }
        else
        {
            titleText = title;
        }

        if ( ( titleText != null ) && ( titleText.length() != 0 ) )
        {
            setTitle( titleText );
        }
    }

    public MessageResult doModal()
    {
        setVisible( true );

        dispose();

        return result;
    }

    public boolean isCheckBoxSelected()
    {
        if ( checkbox == null )
        {
            return false;
        }
        else
        {
            return checkbox.isSelected();
        }
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
            contentPanel = new JPanel( new BorderLayout() );

            if ( ( level != null ) && ( level != MessageLevel.NONE ) )
            {
                StyledLabel messageLabel = new StyledLabel( message, getIcon(), SwingConstants.LEADING );

                contentPanel.add( BorderLayout.CENTER, messageLabel );
            }
            else
            {
                StyledLabel messageLabel = new StyledLabel( message, SwingConstants.LEADING );

                contentPanel.add( BorderLayout.CENTER, messageLabel );
            }

            contentPanel.setBorder(
                    BorderFactory.createCompoundBorder( new PartialEtchedBorder( PartialEtchedBorder.LOWERED,
                                                                                 PartialSide.SOUTH ),
                                                        new EmptyBorder( 10, 10, 10, 20 ) ) );
        }

        return contentPanel;
    }

    private Image getImage()
    {
        if ( ( level != null ) && ( level != MessageLevel.NONE ) )
        {
            if ( level == MessageLevel.ERROR )
            {
                return resources.getResourceImage( MessageDialog.class, "icon", "error" );
            }
            else if ( level == MessageLevel.WARNING )
            {
                return resources.getResourceImage( MessageDialog.class, "icon", "warning" );
            }
            else if ( level == MessageLevel.INFORMATION )
            {
                return resources.getResourceImage( MessageDialog.class, "icon", "information" );
            }
            else if ( level == MessageLevel.QUESTION )
            {
                return resources.getResourceImage( MessageDialog.class, "icon", "question" );
            }
        }

        return null;
    }

    private Icon getIcon()
    {
        if ( ( level != null ) && ( level != MessageLevel.NONE ) )
        {
            if ( level == MessageLevel.ERROR )
            {
                return resources.getResourceIcon( MessageDialog.class, "icon", "error" );
            }
            else if ( level == MessageLevel.WARNING )
            {
                return resources.getResourceIcon( MessageDialog.class, "icon", "warning" );
            }
            else if ( level == MessageLevel.INFORMATION )
            {
                return resources.getResourceIcon( MessageDialog.class, "icon", "information" );
            }
            else if ( level == MessageLevel.QUESTION )
            {
                return resources.getResourceIcon( MessageDialog.class, "icon", "question" );
            }
        }

        return null;
    }

    @Override
    public ButtonPanel createButtonPanel()
    {
        if ( buttonPanel == null )
        {
            buttonPanel = new ButtonPanel();

            buttonPanel.setBorder(
                    BorderFactory.createCompoundBorder( new PartialEtchedBorder( PartialEtchedBorder.LOWERED,
                                                                                 PartialSide.NORTH ),
                                                        new EmptyBorder( 5, 5, 5, 5 ) ) );

            if ( displayCheckBox == true )
            {
                if ( ( alternateCheckBoxText != null ) && ( alternateCheckBoxText.length() != 0 ) )
                {
                    checkbox = new JCheckBox( alternateCheckBoxText );
                }
                else
                {
                    checkbox =
                            new JCheckBox( resources.getResourceString( MessageDialog.class, "checkbox", "default" ) );
                }

                buttonPanel.addButton( checkbox, ButtonPanel.HELP_BUTTON );
            }

            JButton defaultButton;
            JButton defaultCancelButton = null;

            String abortButtonText = resources.getResourceString( MessageDialog.class, "button", "abort" );
            String cancelButtonText = resources.getResourceString( MessageDialog.class, "button", "cancel" );
            String ignoreButtonText = resources.getResourceString( MessageDialog.class, "button", "ignore" );
            String noButtonText = resources.getResourceString( MessageDialog.class, "button", "no" );
            String okButtonText = resources.getResourceString( MessageDialog.class, "button", "ok" );
            String retryButtonText = resources.getResourceString( MessageDialog.class, "button", "retry" );
            String yesButtonText = resources.getResourceString( MessageDialog.class, "button", "yes" );

            if ( alternateButtonText != null )
            {
                if ( alternateButtonText.containsKey( MessageResult.ABORT ) == true )
                {
                    abortButtonText = alternateButtonText.get( MessageResult.ABORT );
                }
                if ( alternateButtonText.containsKey( MessageResult.CANCEL ) == true )
                {
                    cancelButtonText = alternateButtonText.get( MessageResult.CANCEL );
                }
                if ( alternateButtonText.containsKey( MessageResult.IGNORE ) == true )
                {
                    ignoreButtonText = alternateButtonText.get( MessageResult.IGNORE );
                }
                if ( alternateButtonText.containsKey( MessageResult.NO ) == true )
                {
                    noButtonText = alternateButtonText.get( MessageResult.NO );
                }
                if ( alternateButtonText.containsKey( MessageResult.OK ) == true )
                {
                    okButtonText = alternateButtonText.get( MessageResult.OK );
                }
                if ( alternateButtonText.containsKey( MessageResult.RETRY ) == true )
                {
                    retryButtonText = alternateButtonText.get( MessageResult.RETRY );
                }
                if ( alternateButtonText.containsKey( MessageResult.YES ) == true )
                {
                    yesButtonText = alternateButtonText.get( MessageResult.YES );
                }
            }

            if ( type != null )
            {
                switch ( type )
                {
                    case ABORT_RETRY_IGNORE:
                    {
                        abortButton = new JButton( abortButtonText );
                        abortButton.addActionListener( this );

                        retryButton = new JButton( retryButtonText );
                        retryButton.addActionListener( this );

                        ignoreButton =
                                new JButton( ignoreButtonText );
                        ignoreButton.addActionListener( this );

                        buttonPanel.addButton( abortButton, ButtonPanel.CANCEL_BUTTON );
                        buttonPanel.addButton( retryButton, ButtonPanel.AFFIRMATIVE_BUTTON );
                        buttonPanel.addButton( ignoreButton, ButtonPanel.OTHER_BUTTON );

                        defaultButton = retryButton;
                        defaultCancelButton = abortButton;
                        break;
                    }
                    case OK_CANCEL:
                    {
                        okButton = new JButton( okButtonText );
                        okButton.addActionListener( this );

                        cancelButton =
                                new JButton( cancelButtonText );
                        cancelButton.addActionListener( this );

                        buttonPanel.addButton( okButton, ButtonPanel.AFFIRMATIVE_BUTTON );
                        buttonPanel.addButton( cancelButton, ButtonPanel.CANCEL_BUTTON );

                        defaultButton = okButton;
                        defaultCancelButton = cancelButton;
                        break;
                    }
                    case RETRY_CANCEL:
                    {

                        retryButton = new JButton( retryButtonText );
                        retryButton.addActionListener( this );

                        cancelButton =
                                new JButton( cancelButtonText );
                        cancelButton.addActionListener( this );

                        buttonPanel.addButton( retryButton, ButtonPanel.AFFIRMATIVE_BUTTON );
                        buttonPanel.addButton( cancelButton, ButtonPanel.CANCEL_BUTTON );

                        defaultButton = retryButton;
                        defaultCancelButton = cancelButton;
                        break;
                    }
                    case YES_NO:
                    {
                        yesButton = new JButton( yesButtonText );
                        yesButton.setOpaque( true );
                        yesButton.addActionListener( this );

                        noButton = new JButton( noButtonText );
                        noButton.setOpaque( true );
                        noButton.addActionListener( this );

                        buttonPanel.addButton( yesButton, ButtonPanel.AFFIRMATIVE_BUTTON );
                        buttonPanel.addButton( noButton, ButtonPanel.CANCEL_BUTTON );

                        defaultButton = yesButton;
                        defaultCancelButton = noButton;
                        break;
                    }
                    case YES_NO_CANCEL:
                    {
                        cancelButton =
                                new JButton( cancelButtonText );
                        cancelButton.addActionListener( this );

                        yesButton = new JButton( yesButtonText );
                        yesButton.setOpaque( true );
                        yesButton.addActionListener( this );

                        noButton = new JButton( noButtonText );
                        noButton.setOpaque( true );
                        noButton.addActionListener( this );

                        buttonPanel.addButton( yesButton, ButtonPanel.AFFIRMATIVE_BUTTON );
                        buttonPanel.addButton( noButton, ButtonPanel.CANCEL_BUTTON );
                        buttonPanel.addButton( cancelButton, ButtonPanel.CANCEL_BUTTON );

                        defaultButton = yesButton;
                        defaultCancelButton = cancelButton;
                        break;
                    }
                    case OK:
                    default:
                    {
                        okButton = new JButton( okButtonText );
                        okButton.addActionListener( this );

                        buttonPanel.addButton( okButton, ButtonPanel.AFFIRMATIVE_BUTTON );

                        defaultButton = okButton;
                        break;
                    }
                }
            }
            else
            {
                okButton = new JButton( okButtonText );
                okButton.addActionListener( this );

                buttonPanel.addButton( okButton, ButtonPanel.AFFIRMATIVE_BUTTON );

                defaultButton = okButton;
            }

            if ( defaultCancelButton != null )
            {
                setDefaultCancelAction( defaultCancelButton.getAction() );
            }

            if ( defaultButton != null )
            {
                setDefaultAction( defaultButton.getAction() );
                getRootPane().setDefaultButton( defaultButton );
            }

            buttonPanel.setSizeConstraint( ButtonPanel.NO_LESS_THAN );
        }

        return buttonPanel;
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        Object source = e.getSource();

        if ( source == abortButton )
        {
            result = MessageResult.ABORT;
            setVisible( false );
        }
        else if ( source == retryButton )
        {
            result = MessageResult.RETRY;
            setVisible( false );
        }
        else if ( source == ignoreButton )
        {
            result = MessageResult.IGNORE;
            setVisible( false );
        }
        else if ( source == okButton )
        {
            result = MessageResult.OK;
            setVisible( false );
        }
        else if ( source == cancelButton )
        {
            result = MessageResult.CANCEL;
            setVisible( false );
        }
        else if ( source == yesButton )
        {
            result = MessageResult.YES;
            setVisible( false );
        }
        else if ( source == noButton )
        {
            result = MessageResult.NO;
            setVisible( false );
        }
    }

    @Override
    public void windowOpened( WindowEvent e )
    {
        setLocationRelativeTo( getOwner() );
        setResizable( false );
        pack();
    }

    @Override
    public void windowClosing( WindowEvent e )
    {
    }

    @Override
    public void windowClosed( WindowEvent e )
    {
    }

    @Override
    public void windowIconified( WindowEvent e )
    {
    }

    @Override
    public void windowDeiconified( WindowEvent e )
    {
    }

    @Override
    public void windowActivated( WindowEvent e )
    {
    }

    @Override
    public void windowDeactivated( WindowEvent e )
    {
    }
}
