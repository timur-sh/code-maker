package net.shaidullin.code_maker.ui;

import net.shaidullin.code_maker.core.type.FieldType;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;


public class SearchBoxModel<T> extends AbstractListModel implements ComboBoxModel, KeyListener, ItemListener {

    private final ArrayList<T> elements = new ArrayList<>();
    private final ArrayList<T> data = new ArrayList<>();
    private T selection;

    private JComboBox comboBox;
    private ComboBoxEditor editor;
    private int currPos = 0;


    public SearchBoxModel(JComboBox jcb) {
        comboBox = jcb;
        editor = jcb.getEditor();
        //here we add the key listener to the text field that the combobox is wrapped around
        editor.getEditorComponent().addKeyListener(this);
    }

    public void addElements(List<T> elements) {
        //set up our "database" of items - in practice you will usuallu have a proper elements.
        this.elements.addAll(elements);
    }

    private void updateModel(String in) {
        data.clear();
        //lets find any items which start with the string the user typed, and add it to the popup list
        //here you would usually get your items from a database, or some other storage...
        for (T element : elements) {
            String lcElementName = element.toString().toLowerCase();
            String lcSearchable = in.toLowerCase();
            if (lcElementName.contains(lcSearchable) || lcElementName.equals(lcSearchable)) {
                data.add(element);
            }
        }


        super.fireContentsChanged(this, 0, data.size());

        //this is a hack to get around redraw problems when changing the list length of the displayed popups
        comboBox.hidePopup();
        comboBox.showPopup();
        if (data.size() != 0) {
            comboBox.setSelectedIndex(0);
        }
    }

    @Override
    public int getSize() {
        return data.size();
    }

    @Override
    public T getElementAt(int index) {
        return data.get(index);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void setSelectedItem(Object anItem) {
        selection = (T) anItem;
    }

    @Override
    public Object getSelectedItem() {
        return selection;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        String str = editor.getItem().toString();
        JTextField jtf = (JTextField) editor.getEditorComponent();
        currPos = jtf.getCaretPosition();

        if (e.getKeyChar() == KeyEvent.CHAR_UNDEFINED) {
            if (e.getKeyCode() != KeyEvent.VK_ENTER) {
                editor.setItem(str);
                jtf.setCaretPosition(currPos);
            }
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_TAB) {
            editor.setItem(comboBox.getSelectedItem());
            comboBox.setSelectedIndex(comboBox.getSelectedIndex());

        } else {
            updateModel(comboBox.getEditor().getItem().toString());
            editor.setItem(str);
            jtf.setCaretPosition(currPos);
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        editor.setItem(e.getItem().toString());
        comboBox.setSelectedItem(e.getItem());
    }

    public void setEditorItem(FieldType fieldType) {
        editor.setItem(fieldType.getName());
    }

}
