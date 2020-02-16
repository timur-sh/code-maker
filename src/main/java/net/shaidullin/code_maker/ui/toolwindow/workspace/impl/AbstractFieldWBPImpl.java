package net.shaidullin.code_maker.ui.toolwindow.workspace.impl;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.ui.components.JBLabel;
import net.shaidullin.code_maker.core.config.ApplicationState;
import net.shaidullin.code_maker.core.node.FieldNode;
import net.shaidullin.code_maker.core.type.Type;
import net.shaidullin.code_maker.core.type.TypeUtils;
import net.shaidullin.code_maker.ui.SearchBoxModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.List;
import java.util.UUID;

public abstract class AbstractFieldWBPImpl<T extends FieldNode> extends WorkspacePanelBody<T> {

    protected JComboBox<Type> typeJComboBox;
    private SearchBoxModel<Type> model;

    private SearchBoxModel<Type> genericModel;
    protected JComboBox<Type> typeArgumentCompoBox;

    public AbstractFieldWBPImpl(Project project, JTree tree, ApplicationState state) {
        super(project, tree, state);
    }

    protected void renderFieldTypeBrowse(GridBagConstraints constraints, UUID fieldTypeUID, UUID genericFieldTypeUID) {
        List<Type> fieldTypeList = state.getTypeManager().getTypes();

        typeJComboBox = new ComboBox<>();
        model = new SearchBoxModel<>(typeJComboBox);
        model.addElements(fieldTypeList);
        typeJComboBox.setModel(model);
        typeJComboBox.addItemListener(item -> {
            if (item.getStateChange() == ItemEvent.SELECTED) {
                Type selectedItem = (Type) typeJComboBox.getSelectedItem();
                typeArgumentCompoBox.setEnabled(selectedItem != null && TypeUtils.isGeneric(selectedItem.getUuid(), state));
            }
        });

        typeArgumentCompoBox = new ComboBox<>();
        genericModel = new SearchBoxModel<>(typeArgumentCompoBox);
        genericModel.addElements(fieldTypeList);
        typeArgumentCompoBox.setModel(genericModel);

        if (fieldTypeUID != null) {
            initializeType(fieldTypeUID);
        }

        this.add(new JBLabel("Type"), constraints);

        GridBagConstraints fieldConstraint = (GridBagConstraints) constraints.clone();
        fieldConstraint.gridx = 1;
        fieldConstraint.fill = GridBagConstraints.HORIZONTAL;
        typeJComboBox.setEditable(true);
        this.add(typeJComboBox, fieldConstraint);

        // generic typeJComboBox
        GridBagConstraints btnConstraint = (GridBagConstraints) constraints.clone();
        btnConstraint.gridx = 2;

        this.add(new JBLabel("Generic type argument"), btnConstraint);
        btnConstraint.gridx = 3;
        typeArgumentCompoBox.setEditable(true);
        this.add(typeArgumentCompoBox, btnConstraint);

        if (genericFieldTypeUID != null) {
            initializeGenericType(genericFieldTypeUID);
        }
    }

    @Override
    protected boolean validateModel() {
        return true;
    }

    protected void initializeType(UUID typeUID) {
        Type fieldType = state.getTypeManager().getTypeByUID(typeUID);
        model.setSelectedItem(fieldType);
        typeJComboBox.setSelectedItem(fieldType);
        typeJComboBox.getEditor().setItem(fieldType);

        if (TypeUtils.isGeneric(fieldType.getUuid(), state)) {
            typeArgumentCompoBox.setEnabled(true);
        } else {
            typeArgumentCompoBox.setEnabled(false);
        }
    }

    protected void initializeGenericType(UUID typeUID) {
        if (typeUID == null) {
            typeArgumentCompoBox.setSelectedItem(null);
            return;
        }

        Type fieldType = state.getTypeManager().getTypeByUID(typeUID);
        genericModel.setSelectedItem(fieldType);
        typeArgumentCompoBox.setSelectedItem(fieldType);
        typeArgumentCompoBox.getEditor().setItem(fieldType);
    }

    protected void setTypeNull() {
        typeJComboBox.setSelectedItem(null);
        typeArgumentCompoBox.setSelectedItem(null);
    }
}
