/** TODO <HEAD> */
package jp.nichicom.vr.plaf.metal;

import javax.swing.UIDefaults;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.InsetsUIResource;
import javax.swing.plaf.metal.MetalLookAndFeel;

//import sun.awt.AppContext;

/**
 * VRデザインを実装するLook&Feelです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Susumu Nakahara
 * @version 1.0 2005/10/31
 * @see MetalLookAndFeel
 * @see VRTheme
 */
public class VRLookAndFeel extends MetalLookAndFeel {

    //private static boolean METAL_LOOK_AND_FEEL_INITED = false;

    //private static VRTheme currentTheme;

    //private static boolean isOnlyOneContext = true;

    //private static AppContext cachedAppContext;

    private static VRTheme vrTheme;

    public String getID() {
        return "VR";
    }

    public String getName() {
        return "VR";
    }

    public String getDescription() {
        return "VRControls Look&Feel";
    }

    public boolean isNativeLookAndFeel() {
        return false;
    }

    public boolean isSupportedLookAndFeel() {
        return true;
    }

    public static void setCurrentVRTheme(VRTheme theme) {
        vrTheme = theme;
        MetalLookAndFeel.setCurrentTheme(theme);
    }

    public static VRTheme getCurrentVRTheme() {
        return vrTheme;
    }

    protected void initComponentDefaults(UIDefaults table) {
        super.initComponentDefaults(table);

        Object textFieldBorder = new UIDefaults.ProxyLazyValue(this.getClass()
                .getPackage().getName()
                + ".VRBorders", "getTextFieldBorder");

        Object buttonBorder = new UIDefaults.ProxyLazyValue(this.getClass()
                .getPackage().getName()
                + ".VRBorders", "getButtonBorder");
        Object spinnerButtonBorder = new UIDefaults.ProxyLazyValue(this
                .getClass().getPackage().getName()
                + ".VRBorders", "getSpinnerButtonBorder");

        Object scrollPaneBorder = new UIDefaults.ProxyLazyValue(this.getClass()
                .getPackage().getName()
                + ".VRBorders$ScrollPaneBorder");

        //            Object menuItemBorder = new
        // UIDefaults.ProxyLazyValue(this.getClass().getPackage().getName()+".VRBorders$MenuItemBorder");

        Object popupMenuBorder = new UIDefaults.ProxyLazyValue(this.getClass()
                .getPackage().getName()
                + ".VRBorders$PopupMenuBorder");
        Object menuBarBorder = new UIDefaults.ProxyLazyValue(this.getClass()
                .getPackage().getName()
                + ".VRBorders$MenuBarBorder");

        Object simpleButtonBorder = new UIDefaults.ProxyLazyValue(this
                .getClass().getPackage().getName()
                + ".VRBorders", "getSimpleButtonBorder");

        table.put("Menu.border", null);
        table.put("MenuItem.border", null);
        table.put("CheckBoxMenuItem.border", null);
        table.put("RadioButtonMenuItem.border", null);
        table.put("MenuBar.border", menuBarBorder);
        table.put("OptionPane.border", null);

        table.put("PopupMenu.border", popupMenuBorder);

        table.put("ScrollPane.border", scrollPaneBorder);
        table.put("Table.scrollPaneBorder", scrollPaneBorder);
        table.put("TableHeader.cellBorder", simpleButtonBorder);

        table.put("ToolTip.border", popupMenuBorder);

        table.put("TextField.border", textFieldBorder);
        table.put("FormattedTextField.border", textFieldBorder);
        table.put("Spinner.border", textFieldBorder);
        table.put("ComboBox.border", textFieldBorder);
        table.put("ProgressBar.border", textFieldBorder);
        table.put("PasswordField.border", textFieldBorder);

        // *** List value objects

        //        	Object listCellRendererActiveValue = new UIDefaults.ActiveValue() {
        //        	    public Object createValue(UIDefaults table) {
        //        		return new VRListCellRenderer.UIResource();
        //        	    }
        //        	};
        //            
        //            table.put("List.cellRenderer", listCellRendererActiveValue);

        table.put("Button.border", buttonBorder);
        table.put("ToggleButton.border", buttonBorder);

        table.put("ComboBox.background", table.get("window"));

        table.put("ScrollBar.thumb", getPrimaryControl());
        table.put("ScrollBar.thumbShadow", getPrimaryControlShadow());
        table.put("ScrollBar.thumbHighlight", getPrimaryControlHighlight());

        table.put("Slider.thumb", getPrimaryControl());

        table.put("ComboBox.selectionBackground", getTextHighlightColor());
        table.put("ComboBox.selectionForeground", getHighlightedTextColor());

        table.put("ToolTip.background", getTextFocusBackground());

        table.put("Spinner.arrowButtonBorder", spinnerButtonBorder);

        table.put("CheckBox.icon", new UIDefaults.ProxyLazyValue(this
                .getClass().getPackage().getName()
                + ".VRIconFactory", "getCheckBoxIcon"));
        table.put("RadioButton.icon", new UIDefaults.ProxyLazyValue(this
                .getClass().getPackage().getName()
                + ".VRIconFactory", "getRadioButtonIcon"));

        table.put("CheckBoxMenuItem.checkIcon", new UIDefaults.ProxyLazyValue(
                this.getClass().getPackage().getName() + ".VRIconFactory",
                "getCheckBoxIcon"));
        table.put("RadioButtonMenuItem.checkIcon",
                new UIDefaults.ProxyLazyValue(this.getClass().getPackage()
                        .getName()
                        + ".VRIconFactory", "getRadioButtonIcon"));

        //          table.put("CheckBoxMenuItem.arrowIcon",new
        // UIDefaults.ProxyLazyValue(this.getClass().getPackage().getName()+".VRIconFactory",
        // "getMenuItemArrowIcon"));
        //          table.put("RadioButtonMenuItem.arrowIcon",new
        // UIDefaults.ProxyLazyValue(this.getClass().getPackage().getName()+".VRIconFactory",
        // "getMenuItemArrowIcon"));
        //          table.put("Menu.checkIcon",new
        // UIDefaults.ProxyLazyValue(this.getClass().getPackage().getName()+".VRIconFactory",
        // "getMenuItemCheckIcon"));
        //          table.put("Menu.checkIcon",new
        // UIDefaults.ProxyLazyValue(this.getClass().getPackage().getName()+".VRIconFactory",
        // "getMenuArrowIcon"));

        // *** Shared Insets
        InsetsUIResource textInsets = new InsetsUIResource(0, 3, 0, 3);
        table.put("TextField.margin", textInsets);
        table.put("FormattedTextField.margin", textInsets);
        table.put("TextArea.margin", textInsets);
        table.put("PasswordField.margin", textInsets);

        //          table.put("FileView.directoryIcon",LookAndFeel.makeIcon(getClass(),
        // "icons/folder.png"));
        //          table.put("FileView.fileIcon",LookAndFeel.makeIcon(getClass(),
        // "icons/file.png"));
        //          table.put("FileView.computerIcon",LookAndFeel.makeIcon(getClass(),
        // "icons/computer.png"));
        //          table.put("FileView.hardDriveIcon",LookAndFeel.makeIcon(getClass(),
        // "icons/harddisk.png"));
        //          table.put("FileView.floppyDriveIcon",LookAndFeel.makeIcon(getClass(),
        // "icons/floppy.png"));
        //          

    }

    protected void initClassDefaults(UIDefaults table) {
        super.initClassDefaults(table);
        putDefault(table, "ButtonUI");
        putDefault(table, "TextFieldUI");
        putDefault(table, "TextAreaUI");
        putDefault(table, "PanelUI");
        putDefault(table, "RadioButtonUI");
        putDefault(table, "CheckBoxUI");
        putDefault(table, "ComboBoxUI");
        putDefault(table, "SpinnerUI");
        putDefault(table, "ScrollBarUI");
        putDefault(table, "TabbedPaneUI");
        putDefault(table, "TableHeaderUI");
        putDefault(table, "ListUI");
        putDefault(table, "SliderUI");
        putDefault(table, "ToggleButtonUI");
        putDefault(table, "ProgressBarUI");
        putDefault(table, "EditorPaneUI");
        putDefault(table, "FormattedTextFieldUI");
        putDefault(table, "MenuBarUI");
        putDefault(table, "MenuItemUI");
        putDefault(table, "MenuUI");
        putDefault(table, "CheckBoxMenuItemUI");
        putDefault(table, "RadioButtonMenuItemUI");
        putDefault(table, "PasswordFieldUI");
        putDefault(table, "TextPaneUI");

        //        try {
        //          String className="VRCheckBoxIcon";
        //          table.put("CheckBox.icon",className);
        //        } catch(Exception e) {
        //          e.printStackTrace();
        //        }
    }

    protected void putDefault(UIDefaults table, String uiKey) {
        try {
            String className = this.getClass().getPackage().getName() + ".VR"
                    + uiKey;

            table.put(uiKey, className);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public UIDefaults getDefaults() {
        setCurrentVRTheme(new VRTheme());
        return super.getDefaults();
    }

    public static ColorUIResource getTextFocusBackground() {
        return getCurrentVRTheme().getFocus1();
    }

    public static ColorUIResource getHoverColor() {
        return getCurrentVRTheme().getFocus2();
    }

    public static ColorUIResource getCheckColor() {
        return getCurrentVRTheme().getFocus3();
    }

}