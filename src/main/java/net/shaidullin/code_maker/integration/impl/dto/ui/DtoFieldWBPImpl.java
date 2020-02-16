package net.shaidullin.code_maker.integration.impl.dto.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.ui.components.JBCheckBox;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBTextField;
import net.shaidullin.code_maker.core.config.ApplicationState;
import net.shaidullin.code_maker.core.metadata.FieldMetadata;
import net.shaidullin.code_maker.core.metadata.GenericMetadata;
import net.shaidullin.code_maker.core.node.FieldNode;
import net.shaidullin.code_maker.core.node.LeafNode;
import net.shaidullin.code_maker.core.type.Type;
import net.shaidullin.code_maker.core.type.TypeUtils;
import net.shaidullin.code_maker.integration.impl.dto.metadata.DtoMetadata;
import net.shaidullin.code_maker.integration.impl.dto.node.DtoNode;
import net.shaidullin.code_maker.ui.toolwindow.workspace.impl.AbstractFieldWBPImpl;
import net.shaidullin.code_maker.utils.NodeUtils;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DtoFieldWBPImpl extends AbstractFieldWBPImpl<FieldNode<DtoNode, FieldMetadata>> {
    private FieldMetadata fieldMetadata;
    private DtoNode dtoNode;

    private JBCheckBox nullableCheckBox;
    private JBCheckBox listableCheckbox;
    private JBCheckBox genericCheckBox;
    private JTextField typeParameterTextField;

    public DtoFieldWBPImpl(Project project, JTree tree, ApplicationState state) {
        super(project, tree, state);
    }

    @Override
    public DtoFieldWBPImpl initialize(FieldNode<DtoNode, FieldMetadata> fieldNode) {
        this.fieldMetadata = fieldNode.getMetadata();
        this.dtoNode = fieldNode.getParent();

        super.initialize(fieldMetadata.getSystemName(), fieldMetadata.getDescription());

        nullableCheckBox = new JBCheckBox("Nullable");
        nullableCheckBox.setSelected(fieldMetadata.isNullable());
        GridBagConstraints constraints = new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE, COMPONENT_INSETS, 0, 0);
        this.add(nullableCheckBox, constraints);

        listableCheckbox = new JBCheckBox("Is collection");
        listableCheckbox.setSelected(fieldMetadata.isList());
        constraints.gridx = 1;
        this.add(listableCheckbox, constraints);


        genericCheckBox = new JBCheckBox("Is genericCheckBox", fieldMetadata.isGeneric());
        constraints.gridy = 4;
        constraints.gridx = 0;
        this.add(genericCheckBox, constraints);

        typeParameterTextField = new JBTextField(fieldMetadata.getTypeParameter());
        typeParameterTextField.setEnabled(fieldMetadata.isGeneric());
        constraints.gridx = 1;
        this.add(new JBLabel("Generic type parameter"), constraints);

        constraints.gridx = 2;
        this.add(typeParameterTextField, constraints);

        genericCheckBox.addActionListener((e) -> {
            if (((JBCheckBox) e.getSource()).isSelected()) {
                typeJComboBox.setSelectedItem(null);
                typeJComboBox.getEditor().setItem(null);

                typeArgumentCompoBox.setSelectedItem(null);
                typeArgumentCompoBox.getEditor().setItem(null);
                typeArgumentCompoBox.setEnabled(false);
                typeJComboBox.setEnabled(false);
                typeParameterTextField.setEnabled(true);
            } else {
                typeJComboBox.setEnabled(true);
                typeParameterTextField.setEnabled(true);
                typeParameterTextField.setText(null);
                typeArgumentCompoBox.setEnabled(true);
            }
        });

        renderFieldTypeBrowse(
            new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE, COMPONENT_INSETS, 0, 0),
            fieldMetadata.getTypeUID(),
            fieldMetadata.getTypeArgumentUID()
        );

        renderToolbar(
            new GridBagConstraints(0, 6, 4, 1, 1.0, 0.2, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, COMPONENT_INSETS, 0, 0)
        );

        typeJComboBox.setEnabled(!fieldMetadata.isGeneric());
        typeArgumentCompoBox.setEnabled(!fieldMetadata.isGeneric());

        return this;
    }

    @Override
    public String getWorkspaceName() {
        return "Field metadata";
    }

    @Override
    public boolean isInProcess(FieldNode node) {
        LeafNode parentNode = node.getParent();
        return this.dtoNode.equals(parentNode);
    }

    @Override
    protected void save() {
        NodeUtils.writeLeafMetadata(dtoNode, dtoNode.getMetadata());
    }

    @Override
    protected boolean validateModel() {
        if (!super.validateModel()) {
            return false;
        }

        if (genericCheckBox.isSelected()) {
            if (StringUtils.isEmpty(typeParameterTextField.getText())) {
                Messages.showWarningDialog(project, "Generic alias can't be empty", "Error");
                return false;

            }

        } else if (typeJComboBox.getSelectedItem() == null) {
            Messages.showWarningDialog(project, "Type can't be empty ", "Error");
            return false;
        }

        if (typeJComboBox.getSelectedItem() instanceof String) {
            Messages.showWarningDialog(project, "Did you choose type? ", "Error");
            return false;
        }

        if (StringUtils.isEmpty(domainName.getText())) {
            Messages.showWarningDialog(project, "Name can't be empty", "Error");
            return false;
        }

        if (StringUtils.isEmpty(fieldDescription.getText())) {
            Messages.showWarningDialog(project, "Description can't be empty ", "Error");
            return false;
        }

        return true;
    }

    @Override
    protected void apply() {
        fieldMetadata.setSystemName(domainName.getText());
        fieldMetadata.setDescription(fieldDescription.getText());
        fieldMetadata.setList(listableCheckbox.getModel().isSelected());
        fieldMetadata.setNullable(nullableCheckBox.getModel().isSelected());

        if (typeJComboBox.getSelectedItem() != null) {
            fieldMetadata.setTypeUID(((Type) typeJComboBox.getSelectedItem()).getUuid());
        } else {
            fieldMetadata.setTypeUID(null);
        }

        fieldMetadata.setGeneric(genericCheckBox.getModel().isSelected());
        fieldMetadata.setTypeParameter(typeParameterTextField.getText());

        if (typeArgumentCompoBox.getSelectedItem() != null && typeArgumentCompoBox.getSelectedItem() instanceof Type) {
            fieldMetadata.setTypeArgumentUID(((Type) typeArgumentCompoBox.getSelectedItem()).getUuid());
        } else {
            fieldMetadata.setTypeArgumentUID(null);
        }

        DtoMetadata dtoMetadata = dtoNode.getMetadata();
        dtoMetadata.getFields().removeIf(m -> m.getUuid().equals(fieldMetadata.getUuid()));
        dtoMetadata.getFields().add(fieldMetadata);

        // DTO has generic fields or generic types
        boolean isGeneric = dtoMetadata.getFields().stream()
            .anyMatch(field -> field.isGeneric() || TypeUtils.isGeneric(field.getTypeUID(), state));

        if (isGeneric) {
            List<String> typeParameters = new ArrayList<>();
            for (FieldMetadata field : dtoMetadata.getFields()) {
                // field is marked as generic manually
                if (field.isGeneric()) {
                    // Type parameter must exist
                    typeParameters.add(field.getTypeParameter());
                }

                GenericMetadata genericMetadata = TypeUtils.getGenericMetadata(field.getTypeUID(), state);
                // field has generic type
                if (genericMetadata != null && genericMetadata.isGeneric()) {
                    // type argument is not a generic
                    if (field.getTypeArgumentUID() != null && !TypeUtils.isGeneric(field.getTypeArgumentUID(), state)) {
                        continue;
                    }

                    typeParameters.add(genericMetadata.getTypeParameter());
                }

            }

            if (typeParameters.isEmpty()) {
                dtoMetadata.setTypeParameter(null);
                dtoMetadata.setGeneric(false);
            } else {
                List<String> distinctTypeParameters = typeParameters.stream().distinct().collect(Collectors.toList());
                dtoMetadata.setTypeParameter(StringUtils.join(distinctTypeParameters, ", "));
                dtoMetadata.setGeneric(true);
            }

        } else {
            dtoMetadata.setTypeParameter(null);
        }
    }

    @Override
    protected void reset() {
        domainName.setText(fieldMetadata.getSystemName());
        fieldDescription.setText(fieldMetadata.getDescription());
        listableCheckbox.setSelected(fieldMetadata.isList());
        nullableCheckBox.setSelected(fieldMetadata.isNullable());

        if (fieldMetadata.getTypeUID() != null) {
            initializeType(fieldMetadata.getTypeUID());
            initializeGenericType(fieldMetadata.getTypeArgumentUID());
        } else {
            setTypeNull();
        }

        genericCheckBox.setSelected(fieldMetadata.isGeneric());
        typeParameterTextField.setText(fieldMetadata.getTypeParameter());
    }
}
