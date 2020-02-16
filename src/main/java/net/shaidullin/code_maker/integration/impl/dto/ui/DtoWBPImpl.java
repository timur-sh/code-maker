package net.shaidullin.code_maker.integration.impl.dto.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.ui.Messages;
import com.intellij.ui.components.JBCheckBox;
import com.intellij.ui.components.JBLabel;
import net.shaidullin.code_maker.core.config.ApplicationState;
import net.shaidullin.code_maker.core.type.Type;
import net.shaidullin.code_maker.integration.impl.dto.metadata.DtoMetadata;
import net.shaidullin.code_maker.integration.impl.dto.node.DtoNode;
import net.shaidullin.code_maker.ui.SearchBoxModel;
import net.shaidullin.code_maker.ui.toolwindow.workspace.impl.LeafWBPImpl;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.util.UUID;

public class DtoWBPImpl extends LeafWBPImpl<DtoNode> {

    private DtoMetadata metadata;
    private JBCheckBox cachable;
    protected JComboBox<Type> cacheType;
    private SearchBoxModel<Type> model;


    public DtoWBPImpl(Project project, JTree tree, ApplicationState state) {
        super(project, tree, state);
    }

    @Override
    public DtoWBPImpl initialize(DtoNode dtoNode) {
        this.leafNode = dtoNode;
        this.metadata = dtoNode.getMetadata();

        oldName = metadata.getSystemName();

        super.initialize(metadata.getSystemName(), metadata.getDescription());

        cachable = new JBCheckBox("Is cached");
        cachable.setSelected(metadata.isCachable());
        GridBagConstraints constraints = new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE, COMPONENT_INSETS, 0, 0);
        this.add(cachable, constraints);
        constraints.gridx = 1;
        this.add(new JBLabel("Type of cache key"), constraints);
        cachable.addActionListener((e) -> {
            boolean selected = ((JBCheckBox) e.getSource()).isSelected();
            cacheType.setEnabled(selected);

            if (!selected) {
                model.setSelectedItem(null);
                cacheType.setSelectedItem(null);
                cacheType.getEditor().setItem(null);
            }
        });

        constraints.gridx = 2;

        cacheType = new ComboBox<>();
        model = new SearchBoxModel<>(cacheType);
        model.addElements(state.getTypeManager().getTypes());
        cacheType.setModel(model);
//        cacheType.addItemListener(item -> {
//            if (item.getStateChange() == ItemEvent.SELECTED) {
//                FieldType selectedItem = (FieldType) cacheType.getSelectedItem();
//                genericParameter.setEnabled(selectedItem != null && selectedItem.getDomainMetadata() != null && selectedItem.getDomainMetadata().isGeneric());
//            }
//        });

        if (metadata.getCacheKeyTypeUID() != null) {
            initializeType(metadata.getCacheKeyTypeUID());
        }

        cacheType.setEditable(true);
        this.add(cacheType, constraints);

        UUID parentUID = metadata.getParentUID();
        renderParentBrowse(
            new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE, COMPONENT_INSETS, 0, 0),
            parentUID
        );

        renderToolbar(
            new GridBagConstraints(0, 5, 4, 1, 1.0, 0.2, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, COMPONENT_INSETS, 0, 0)
        );

        return this;
    }

    @Override
    public String getWorkspaceName() {
        return "Class metadata";
    }

    @Override
    protected void afterSuccessSave() {
        oldName = metadata.getSystemName();
    }

    @Override
    protected void apply() {
        metadata.setSystemName(domainName.getText());
        metadata.setDescription(fieldDescription.getText());

        leafNode.setSystemName(metadata.getSystemName());

        // if parent is null, parent is not activated
        if (parent != null && parentData != null) {
            UUID parentUID = StringUtils.isNotEmpty(parentData.getSecond())
                ? UUID.fromString(parentData.getSecond())
                : null;

            metadata.setParentUID(parentUID);
        }

        metadata.setCachable(cachable.getModel().isSelected());

        if (cacheType.getSelectedItem() != null && cacheType.getSelectedItem() instanceof Type) {
            metadata.setCacheKeyTypeUID(((Type) cacheType.getSelectedItem()).getUuid());
        } else {
            metadata.setCacheKeyTypeUID(null);
        }
    }

    private void initializeType(UUID uuid) {
        Type fieldType = state.getTypeManager().getTypeByUID(uuid);

        model.setSelectedItem(fieldType);
        cacheType.setSelectedItem(fieldType);
        cacheType.getEditor().setItem(fieldType);
    }

    @Override
    protected boolean validateModel() {
        if (!super.validateModel()) {
            return false;
        }

        String fileName = domainName.getText();
        String lcFileName = fileName.toLowerCase();
        boolean objectIsPresent = state.getTypeManager().getTypes().stream()
            .anyMatch(f -> f.getName().toLowerCase().equals(lcFileName));

        if (objectIsPresent) {
            if (!oldName.equals(fileName)) {
                Messages.showWarningDialog(project, "Class with this name exists", "Validation");
                return false;
            }
        }

        if (cachable.isSelected() && (cacheType.getSelectedItem() == null || !(cacheType.getSelectedItem() instanceof Type))) {
            Messages.showWarningDialog(project, "Type of cache key not specified", "Validation");
            return false;
        }

        return true;
    }

    @Override
    protected void reset() {
        domainName.setText(metadata.getSystemName());
        fieldDescription.setText(metadata.getDescription());

        if (parent != null) {
            parentData = null;
            UUID parentUID = metadata.getParentUID();


            if (parentUID != null) {
                initializeParent(parentUID);

            } else {
                parent.setText("");
            }
        }

        cachable.setSelected(metadata.isCachable());

        if (metadata.getCacheKeyTypeUID() != null) {
            initializeType(metadata.getCacheKeyTypeUID());
        } else {
            model.setSelectedItem(null);
            cacheType.setSelectedItem(null);
            cacheType.getEditor().setItem(null);
        }

    }
}
