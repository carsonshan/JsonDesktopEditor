package ge.utils.controls.breadcrumb;

import com.jidesoft.swing.JidePopupMenu;
import com.jidesoft.swing.PartialLineBorder;
import com.jidesoft.swing.PartialSide;
import com.jidesoft.swing.StyleRange;
import com.jidesoft.swing.StyledLabel;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 17/05/13
 * Time: 14:16
 */
public abstract class BreadcrumbBar extends JPanel implements TreeSelectionListener
{
    private TreeModel model;

    private TreeSelectionModel treeSelectionModel;

    private boolean rootVisible;

    private JidePopupMenu popupMenu = new JidePopupMenu();

    public BreadcrumbBar()
    {
        this( new DefaultTreeModel( new DefaultMutableTreeNode() ) );
    }

    public BreadcrumbBar( TreeModel model )
    {
        super( new FlowLayout( FlowLayout.LEADING ) );
        this.model = model;

        initialise();
    }

    private void initialise()
    {
        setBackground( new Color( 220, 220, 220 ) );

        setBorder( new PartialLineBorder( Color.GRAY, 1, PartialSide.NORTH | PartialSide.SOUTH ) );

        treeSelectionModel = new DefaultTreeSelectionModel();
        treeSelectionModel.setSelectionMode( DefaultTreeSelectionModel.SINGLE_TREE_SELECTION );
        treeSelectionModel.addTreeSelectionListener( this );

        addMouseListener( new BreadcrumbBar_MouseListener( this ) );

        popupMenu.setBackground( Color.WHITE );
    }

    public void addTreeSelectionListener( TreeSelectionListener x )
    {
        listenerList.add( TreeSelectionListener.class, x );
    }

    public void removeTreeSelectionListener( TreeSelectionListener x )
    {
        listenerList.remove( TreeSelectionListener.class, x );
    }

    public TreeModel getModel()
    {
        return model;
    }

    public void setModel( TreeModel model )
    {
        treeSelectionModel.clearSelection();
        this.model = model;

        reset();
    }

    private void reset()
    {
        TreePath selectionPath = treeSelectionModel.getSelectionPath();

        clearBar();

        if ( selectionPath != null )
        {
            Object[] path = selectionPath.getPath();

            int index = 0;

            if ( rootVisible == false )
            {
                index = 1;
            }

            for (; index < path.length; index++ )
            {
                Object object = path[ index ];

                if ( object instanceof TreeNode )
                {
                    BreadcrumbBarLabel label = new BreadcrumbBarLabel( this, ( TreeNode ) object );

                    add( label );
                }
            }
        }

        revalidate();
        repaint();
    }

    private void clearBar()
    {
        Component[] components = getComponents();

        for ( Component component : components )
        {
            if ( component instanceof BreadcrumbBarLabel )
            {
                BreadcrumbBarLabel label = ( BreadcrumbBarLabel ) component;

                label.dispose();
            }
        }

        removeAll();
    }

    protected abstract String getNodeText( TreeNode treeNode );

    protected abstract Icon getNodeIcon( TreeNode treeNode );

    protected abstract StyleRange[] getNodeStyleRanges( TreeNode treeNode );

    protected abstract void showContextMenuForNode( TreeNode treeNode, Component component, int x, int y );

    @Override
    public void valueChanged( TreeSelectionEvent e )
    {
        reset();
        fireValueChanged( ( TreeSelectionEvent ) e.cloneWithSource( this ) );
    }

    protected void fireValueChanged( TreeSelectionEvent e )
    {
        TreeSelectionListener[] treeSelectionListeners = getTreeSelectionListeners();

        for ( TreeSelectionListener treeSelectionListener : treeSelectionListeners )
        {
            treeSelectionListener.valueChanged( e );
        }
    }

    public TreeSelectionListener[] getTreeSelectionListeners()
    {
        return listenerList.getListeners( TreeSelectionListener.class );
    }

    public TreePath getSelectionPath()
    {
        return treeSelectionModel.getSelectionPath();
    }

    public void setSelectionPath( TreePath path )
    {
        treeSelectionModel.setSelectionPath( path );
        reset();
    }

    public boolean isRootVisible()
    {
        return rootVisible;
    }

    public void setRootVisible( boolean rootVisible )
    {
        this.rootVisible = rootVisible;
        reset();
    }

    private void setSelected( TreeNode treeNode )
    {
        List<TreeNode> nodes = new ArrayList<TreeNode>();

        while ( treeNode != null )
        {
            nodes.add( 0, treeNode );

            treeNode = treeNode.getParent();
        }

        setSelectionPath( new TreePath( nodes.toArray( new TreeNode[ nodes.size() ] ) ) );
    }

    private void showChildMenu( BreadcrumbBarLabel label )
    {
        clearPopupMenu();
        TreeNode treeNode = label.getTreeNode();

        for ( int i = 0; i < treeNode.getChildCount(); i++ )
        {
            TreeNode childAt = treeNode.getChildAt( i );

            popupMenu.add( new BreadcrumbBarMenuItem( this, childAt ) );
        }

        if ( popupMenu.getComponentCount() != 0 )
        {
            popupMenu.show( this, label.getX(), getHeight() );
        }
    }

    private void clearPopupMenu()
    {
        Component[] components = popupMenu.getComponents();

        for ( Component component : components )
        {
            if ( component instanceof BreadcrumbBarMenuItem )
            {
                BreadcrumbBarMenuItem menuItem = ( BreadcrumbBarMenuItem ) component;

                menuItem.dispose();
            }
        }

        popupMenu.removeAll();
    }

    public class BreadcrumbBarLabel extends StyledLabel implements MouseListener
    {
        private BreadcrumbBar breadcrumbBar;

        private TreeNode treeNode;

        public BreadcrumbBarLabel( BreadcrumbBar breadcrumbBar, TreeNode treeNode )
        {
            this.breadcrumbBar = breadcrumbBar;
            this.treeNode = treeNode;

            initialise();
        }

        private void initialise()
        {
            addMouseListener( this );
            setText( breadcrumbBar.getNodeText( treeNode ) );
            setIcon( breadcrumbBar.getNodeIcon( treeNode ) );
            setStyleRanges( breadcrumbBar.getNodeStyleRanges( treeNode ) );

            EmptyBorder insideBorder = new EmptyBorder( 0, 0, 0, 5 );
            PartialLineBorder outsideBorder = new PartialLineBorder( Color.GRAY, 1, PartialSide.EAST );

            CompoundBorder compoundBorder = BorderFactory.createCompoundBorder( outsideBorder, insideBorder );

            setBorder( compoundBorder );
        }

        public void dispose()
        {
            removeMouseListener( this );
            this.breadcrumbBar = null;
            this.treeNode = null;
        }

        @Override
        public void mouseClicked( MouseEvent e )
        {
            if ( e.getSource() == this )
            {
                if ( e.getButton() == MouseEvent.BUTTON1 )
                {
                    if ( e.getClickCount() == 2 )
                    {
                        breadcrumbBar.setSelected( treeNode );
                    }
                    else
                    {
                        breadcrumbBar.showChildMenu( this );
                    }
                }
                else if ( ( e.getButton() == MouseEvent.BUTTON3 ) || ( ( e.getButton() == MouseEvent.BUTTON1 ) &&
                                                                               ( e.getModifiersEx() ==
                                                                                         InputEvent.CTRL_DOWN_MASK ) ) )
                {
                    breadcrumbBar.showContextMenuForNode( treeNode, breadcrumbBar, getX(), breadcrumbBar.getHeight() );
                }
            }
        }

        public TreeNode getTreeNode()
        {
            return treeNode;
        }

        @Override
        public void mousePressed( MouseEvent e )
        {
        }

        @Override
        public void mouseReleased( MouseEvent e )
        {
        }

        @Override
        public void mouseEntered( MouseEvent e )
        {
        }

        @Override
        public void mouseExited( MouseEvent e )
        {
        }
    }

    public class BreadcrumbBarMenuItem extends JMenuItem implements ActionListener
    {
        private BreadcrumbBar breadcrumbBar;

        private TreeNode treeNode;

        public BreadcrumbBarMenuItem( BreadcrumbBar breadcrumbBar, TreeNode treeNode )
        {
            this.breadcrumbBar = breadcrumbBar;
            this.treeNode = treeNode;

            initialise();
        }

        private void initialise()
        {
            addActionListener( this );
            setText( breadcrumbBar.getNodeText( treeNode ) );
            setIcon( breadcrumbBar.getNodeIcon( treeNode ) );

            EmptyBorder insideBorder = new EmptyBorder( 0, 0, 0, 5 );
            PartialLineBorder outsideBorder = new PartialLineBorder( Color.GRAY, 1, PartialSide.EAST );

            CompoundBorder compoundBorder = BorderFactory.createCompoundBorder( outsideBorder, insideBorder );

            setBorder( compoundBorder );

            setBackground( Color.WHITE );
        }

        public void dispose()
        {
            removeActionListener( this );

            this.breadcrumbBar = null;
            this.treeNode = null;
        }

        @Override
        public void actionPerformed( ActionEvent e )
        {
            breadcrumbBar.setSelected( treeNode );
        }
    }

    private class BreadcrumbBar_MouseListener implements MouseListener
    {
        private BreadcrumbBar breadcrumbBar;

        public BreadcrumbBar_MouseListener( BreadcrumbBar breadcrumbBar )
        {
            this.breadcrumbBar = breadcrumbBar;
        }

        @Override
        public void mouseClicked( MouseEvent e )
        {
            if ( e.getSource() == breadcrumbBar )
            {
                if ( e.getButton() == MouseEvent.BUTTON1 )
                {
                    Component componentAt = breadcrumbBar.getComponentAt( e.getX(), breadcrumbBar.getHeight() / 2 );

                    if ( componentAt instanceof BreadcrumbBarLabel )
                    {
                        if ( e.getClickCount() == 2 )
                        {
                            BreadcrumbBarLabel label = ( BreadcrumbBarLabel ) componentAt;
                            breadcrumbBar.setSelected( label.getTreeNode() );
                        }
                        else
                        {
                            BreadcrumbBarLabel label = ( BreadcrumbBarLabel ) componentAt;
                            breadcrumbBar.showChildMenu( label );
                        }
                    }
                }
                else if ( ( e.getButton() == MouseEvent.BUTTON3 ) || ( ( e.getButton() == MouseEvent.BUTTON1 ) &&
                                                                               ( e.getModifiersEx() ==
                                                                                         InputEvent.CTRL_DOWN_MASK ) ) )
                {
                    BreadcrumbBarLabel label = null;
                    int x;
                    int y;

                    Component componentAt = breadcrumbBar.getComponentAt( e.getX(), breadcrumbBar.getHeight() / 2 );

                    if ( componentAt instanceof BreadcrumbBarLabel )
                    {
                        label = ( BreadcrumbBarLabel ) componentAt;
                        x = label.getX();
                        y = breadcrumbBar.getHeight();
                    }
                    else
                    {
                        x = e.getX();
                        y = e.getY();

                        for ( int i = e.getX(); i > 0; i-- )
                        {
                            Component component = breadcrumbBar.getComponentAt( i, breadcrumbBar.getHeight() / 2 );

                            if ( component instanceof BreadcrumbBarLabel )
                            {
                                label = ( BreadcrumbBarLabel ) component;
                                break;
                            }
                        }
                    }

                    if ( label != null )
                    {
                        breadcrumbBar.showContextMenuForNode( label.getTreeNode(), breadcrumbBar, x, y );
                    }
                }
            }
        }

        @Override
        public void mousePressed( MouseEvent e )
        {
        }

        @Override
        public void mouseReleased( MouseEvent e )
        {
        }

        @Override
        public void mouseEntered( MouseEvent e )
        {
        }

        @Override
        public void mouseExited( MouseEvent e )
        {
        }
    }
}
