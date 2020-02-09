package net.shaidullin.code_maker.ui.settings;


import net.shaidullin.code_maker.core.config.ApplicationState;
import net.shaidullin.code_maker.core.node.ModuleNode;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SettingsTableModel extends AbstractTableModel {
    private static final long serialVersionUID = -7914774770821623832L;

    private static final int COLUMN_DESCRIPTION = 0;
    private static final int PACKAGE_NAME = 1;
    private static final int COLUMN_FILE = 2;
    private static final int NUMBER_OF_COLUMNS = 3;

    private final String[] columnNames;

    private final List<ModuleNode> moduleNodes = new ArrayList<>();

    /**
     * Create a new empty table model.
     */
    public SettingsTableModel(ApplicationState state) {
        moduleNodes.addAll(state.getModules());
        columnNames = new String[NUMBER_OF_COLUMNS];
        columnNames[COLUMN_DESCRIPTION] = "System name";
        columnNames[COLUMN_FILE] = "Location of module";
        columnNames[PACKAGE_NAME] = "Package name";
    }

    public List<ModuleNode> getModuleNodes() {
        return moduleNodes;
    }

    public void addModule(ModuleNode moduleNode) {
        moduleNodes.add(moduleNode);
    }

    public void removeModuleAt(int index) {
        moduleNodes.remove(index);
    }

    public void apply(ApplicationState state) {
        state.getModules().clear();
        state.getModules().addAll(moduleNodes);
    }

    public void reset(ApplicationState state) {
        moduleNodes.clear();
        moduleNodes.addAll(state.getModules());
    }

    public boolean isModified(ApplicationState state) {
        return !Objects.deepEquals(moduleNodes, state.getModules());
    }

    @Override
    public int getColumnCount() {
        return NUMBER_OF_COLUMNS;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }


    @Override
    public int getRowCount() {
        return moduleNodes.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ModuleNode moduleNode = moduleNodes.get(rowIndex);
        switch (columnIndex) {
            case COLUMN_DESCRIPTION:
                return moduleNode.getSystemName();

            case COLUMN_FILE:
                return moduleNode.getRootMetadataPath();

            case PACKAGE_NAME:
                return moduleNode.getMetadata().getFqnPackage();

            default:
                throw new IllegalArgumentException("Invalid column: " + columnIndex);
        }
    }

}
