package net.shaidullin.code_maker.ui;

import com.intellij.ui.components.JBList;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.components.JBTextField;
import com.intellij.ui.treeStructure.SimpleTree;
import com.intellij.util.ui.JBUI;
import net.shaidullin.code_maker.core.Pair;
import net.shaidullin.code_maker.core.config.ApplicationState;
import net.shaidullin.code_maker.core.metadata.ModuleMetadata;
import net.shaidullin.code_maker.core.node.ElementNode;
import net.shaidullin.code_maker.core.node.LeafNode;
import net.shaidullin.code_maker.core.node.ModuleNode;
import net.shaidullin.code_maker.core.node.PackageNode;
import org.apache.commons.collections.CollectionUtils;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.List;

import static javax.swing.ListSelectionModel.SINGLE_SELECTION;

/**
 * Select class that will be extended by {@link ChooseParentDialog#leafNode}
 */
public class ChooseParentDialog {
    protected static final Insets COMPONENT_INSETS = JBUI.insets(4);

    private final LeafNode leafNode;
    private final Frame frame;
    private final ApplicationState state;

    private ModuleNode selectedModuleNode;
    private DefaultMutableTreeNode nodes = new DefaultMutableTreeNode();

    private LeafNode selectedLeafNode;
    private JBTextField textField;
    private Pair<String, String> parentData;

    public ChooseParentDialog(LeafNode leafNode, Frame frame, ApplicationState state) {
        this.leafNode = leafNode;
        this.frame = frame;
        this.state = state;
    }

    public JDialog createDialog(JBTextField textField, Pair<String, String> parentData) {
        this.textField = textField;
        this.parentData = parentData;
        JDialog dialog = new JDialog(frame);
        dialog.setLayout(new BorderLayout());
        dialog.setMinimumSize(new Dimension(500, 400));
        dialog.setVisible(true);
        dialog.setModal(false);
        dialog.setTitle("Choose parent class");

        initialize(dialog);

        addEscapeListener(dialog);
        dialog.pack();

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        dialog.setLocation(
            (toolkit.getScreenSize().width - dialog.getSize().width) / 2,
            (toolkit.getScreenSize().height - dialog.getSize().height) / 2
        );
        return dialog;
    }

    private void initialize(JDialog dialog) {
        JPanel jPanel = new JPanel(new GridBagLayout());
        JTree tree = new SimpleTree();

        state.refreshState();

        DefaultListModel<ModuleNode> listModel = new DefaultListModel<>();
        state.getModules().forEach(listModel::addElement);
        JBList<ModuleNode> moduleNodeJBList = new JBList<>(listModel);
        moduleNodeJBList.setSelectionMode(SINGLE_SELECTION);
        moduleNodeJBList.addListSelectionListener((ListSelectionEvent e) -> {
            if (!e.getValueIsAdjusting()) {
                selectedModuleNode = moduleNodeJBList.getSelectedValue();

                // element node of edited leaf
                ElementNode elementNode = leafNode.getParent()
                    .getParent();

                // select element node, whose leaves may be a parent of the leafNode
                ElementNode selectedElement = state.getElements().get(selectedModuleNode).stream()
                    .filter(element -> element.getSystemName().equals(elementNode.getSystemName()))
                    .findFirst()
                    .orElse(null);

                ModuleMetadata moduleMetadata = elementNode.getParent().getMetadata();
                if (!moduleMetadata.isModuleAllowed(selectedModuleNode) || selectedElement == null) {
                    tree.removeAll();
                    nodes.removeAllChildren();
                    ((DefaultTreeModel) tree.getModel()).reload();
                    return;
                }

                nodes = buildTreeNodeWithParentLeaf(selectedElement, state);

                if (nodes != null) {
                    DefaultTreeModel treeModel = new DefaultTreeModel(nodes);
                    tree.setModel(treeModel);
                    ((DefaultTreeModel) tree.getModel()).reload();
                }
            }
        });

        jPanel.add(new JLabel("Select module:"),
            new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, COMPONENT_INSETS, 0, 0));

        jPanel.add(new JBScrollPane(moduleNodeJBList),
            new GridBagConstraints(0, 1, 2, 1, 0.0, 0.0, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, COMPONENT_INSETS, 0, 0));

        tree.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

            if (selectedNode == null || !(selectedNode.getUserObject() instanceof LeafNode)) {
                return;
            }

            selectedLeafNode = (LeafNode) selectedNode.getUserObject();
        });

        jPanel.add(new JLabel("Select class:"),
            new GridBagConstraints(0, 1, 2, 1, 0.0, 0.0, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, COMPONENT_INSETS, 0, 0));
        JBScrollPane scrollPane = new JBScrollPane(tree);
        jPanel.add(scrollPane,
            new GridBagConstraints(0, 2, 2, 1, 1.0, 1.0, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, COMPONENT_INSETS, 0, 0));

        JButton btnOk = new JButton(new OkAction(dialog));
        btnOk.setText("Ok");

        JButton btnCancel = new JButton(new CancelAction(dialog));
        btnCancel.setText("Cancel");

        JToolBar toolBar = new JToolBar(JToolBar.HORIZONTAL);
        toolBar.add(Box.createHorizontalStrut(4));
        toolBar.add(btnOk);
        toolBar.add(Box.createHorizontalStrut(4));
        toolBar.add(btnCancel);
        toolBar.add(Box.createHorizontalGlue());
        toolBar.setFloatable(false);
        toolBar.setOpaque(false);
        toolBar.setBorder(null);
        jPanel.add(toolBar,
            new GridBagConstraints(0, 3, 1, 1, 1.0, 1.0, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, COMPONENT_INSETS, 0, 0));


        dialog.add(jPanel, BorderLayout.CENTER);
    }

    /**
     * Got leafs available for {@code elementNode}, then put them to the nodes
     *
     * @param elementNode
     * @param state
     * @return
     */
    private DefaultMutableTreeNode buildTreeNodeWithParentLeaf(ElementNode elementNode, ApplicationState state) {
        DefaultMutableTreeNode nodes = new DefaultMutableTreeNode(elementNode);

        List<PackageNode> packageNodeList = state.getPackages().get(elementNode);
        for (PackageNode packageNode : packageNodeList) {
            DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode(packageNode);

            List<LeafNode> leafNodes = state.getLeaves().get(packageNode);
            if (CollectionUtils.isNotEmpty(leafNodes)) {
                for (LeafNode leafNode : leafNodes) {
                    // exclude the current leafNode
                    if (leafNode.getMetadata().equals(this.leafNode.getMetadata())) {
                        continue;
                    }
                    treeNode.add(new DefaultMutableTreeNode(leafNode));
                }
            }

            nodes.add(treeNode);
        }

        return nodes;
    }

    private void addEscapeListener(JDialog dialog) {
        dialog.getRootPane().registerKeyboardAction((event) -> dialog.setVisible(false),
            KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
            JComponent.WHEN_IN_FOCUSED_WINDOW);
    }


    private class OkAction extends AbstractAction {
        private final JDialog jDialog;

        OkAction(JDialog jDialog) {
            this.jDialog = jDialog;
        }

        @Override
        public void actionPerformed(final ActionEvent e) {
            if (selectedLeafNode != null) {
                textField.setText(selectedLeafNode.getSystemName());
                parentData.setFirst(selectedLeafNode.getSystemName());
                parentData.setSecond(selectedLeafNode.getMetadata().getUuid().toString());
            }

            jDialog.setVisible(false);
        }
    }

    private class CancelAction extends AbstractAction {
        private final JDialog jDialog;

        CancelAction(JDialog jDialog) {
            this.jDialog = jDialog;
        }

        @Override
        public void actionPerformed(final ActionEvent e) {
            jDialog.setVisible(false);
        }
    }
}
