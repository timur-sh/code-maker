package net.shaidullin.code_maker.ui.toolwindow;

import com.intellij.openapi.project.Project;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.treeStructure.SimpleTree;
import net.shaidullin.code_maker.core.config.ApplicationState;
import net.shaidullin.code_maker.core.node.*;
import net.shaidullin.code_maker.integration.IntegrationElement;
import net.shaidullin.code_maker.ui.IconManager;
import net.shaidullin.code_maker.ui.toolwindow.tree.NodeTreeMenu;
import net.shaidullin.code_maker.ui.toolwindow.tree.impl.ElementNodeTreeMenuImpl;
import net.shaidullin.code_maker.ui.toolwindow.tree.impl.FieldNodeTreeMenuImpl;
import net.shaidullin.code_maker.ui.toolwindow.tree.impl.PackageNodeTreeMenuImpl;
import net.shaidullin.code_maker.ui.toolwindow.workspace.WorkspacePanel;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NodeTreePanel {

    private final ApplicationState state;
    private JPanel mainPanel;

    private final List<NodeTreeMenu<?>> nodeTreeMenus = new ArrayList<>();
    private JTree tree;
    private DefaultMutableTreeNode nodes;
    private DefaultTreeModel treeModel;


    public NodeTreePanel(ApplicationState state) {
        this.state = state;
    }

    public JPanel createPanel(ElementNode elementNode, Project project, WorkspacePanel workspacePanel) {
        nodes = new DefaultMutableTreeNode(elementNode);
        treeModel = new DefaultTreeModel(nodes);

        state.refreshState();
        assembleNodes(elementNode);
        mainPanel = new JPanel(new BorderLayout());
        tree = new SimpleTree(treeModel);
        tree.setCellRenderer(new CellRenderer());
        this.registerNodeTreeMenus(project, workspacePanel);

        tree.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

            /* if nothing is selected */
            if (node == null) {
                return;
            }

            resetSelectedNodes();
            Object selectedNode = node.getUserObject();

            boolean selected = false;
            for (NodeTreeMenu<?> nodeTreeMenu : this.nodeTreeMenus) {
                if (nodeTreeMenu.isNodeAllowed(selectedNode)) {
                    nodeTreeMenu.setSelectedNode(selectedNode);
                    selected = true;
                    break;
                }
            }
            if (!selected) {
                throw new RuntimeException("There is no suitable menu for node=" + selectedNode);
            }
        });

        tree.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    for (NodeTreeMenu<?> nodeTreeMenu : NodeTreePanel.this.nodeTreeMenus) {
                        if (nodeTreeMenu.getSelectedNode() != null) {
                            nodeTreeMenu.show((JComponent) e.getSource(), e.getX(), e.getY());
                            break;
                        }
                    }
                }
            }
        });


        JScrollPane scrollPane = new JBScrollPane(tree);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.setMinimumSize(new Dimension(150, 50));
        return mainPanel;
    }

    private void registerNodeTreeMenus(Project project, WorkspacePanel workspacePanel) {
        // Assemble and initialize NodeTreeMenu impl
        nodeTreeMenus.add(new ElementNodeTreeMenuImpl(state));
        nodeTreeMenus.add(new PackageNodeTreeMenuImpl(state));
        nodeTreeMenus.add(new FieldNodeTreeMenuImpl(state));

        for (IntegrationElement<?> integrationElement : state.getIntegrationElements()) {
            nodeTreeMenus.add(integrationElement.createNodeTreeMenu(state));
        }
        for (NodeTreeMenu<?> nodeTreeMenu : nodeTreeMenus) {
            nodeTreeMenu.initialize(tree, project, workspacePanel);
        }

    }

    private void resetSelectedNodes() {
        for (NodeTreeMenu<?> nodeTreeMenu : nodeTreeMenus) {
            nodeTreeMenu.setSelectedNode(null);
        }

        state.setUsedLeaf(null);
        state.setUsedElement(null);
        state.setUsedPackage(null);
    }

    @SuppressWarnings("unchecked")
    private void assembleNodes(ElementNode elementNode) {
        List<PackageNode> packages = state.getPackages().get(elementNode);
        if (packages == null) {
            return;
        }

        for (PackageNode packageNode : packages) {
            DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode(packageNode);

            List<LeafNode> leafNodes = state.getLeaves().get(packageNode);
            if (leafNodes != null) {
                for (LeafNode leafNode : leafNodes) {
                    DefaultMutableTreeNode leafTreeNode = new DefaultMutableTreeNode(leafNode);

                    leafNode.getIntegrationElement()
                        .assembleTreeNodeByTreeNode(leafTreeNode, leafNode);
                    treeNode.add(leafTreeNode);
                }
            }

            nodes.add(treeNode);
        }
    }

    public void refresh(ElementNode elementNode) {
        tree.removeAll();
        nodes.removeAllChildren();
        assembleNodes(elementNode);
        nodes.setUserObject(elementNode);
        treeModel.reload(nodes);
    }

    private class CellRenderer implements TreeCellRenderer {

        private JBLabel label;

        public CellRenderer() {
            label = new JBLabel();
        }

        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
            Object o = ((DefaultMutableTreeNode) value).getUserObject();

            if (o instanceof Node) {
                ImageIcon icon = IconManager.getInstance()
                    .getIcon((Node) o);
                label.setIcon(icon);
            } else {
                label.setIcon(null);
            }

            if (o != null) {
                label.setText(o.toString());
            }

            return label;
        }
    }
}