package me.zirko.util;

import javax.swing.*;

/**
 * This class is an utility class that help developer
 * not to make redundant static code on UI calls
 *
 * @author Guillaume Fillon
 * @version 1.0
 * @since 1.0
 */
public class UIUtils {
    public static java.awt.Image createImageFromRes(String path,
                                                    String description) {
        return createImageIconFromRes(path, description).getImage();
    }

    public static ImageIcon createImageIconFromRes(String path,
                                                   String description) {
        java.net.URL imgURL = UIUtils.class.getResource("/assets/" + path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
}
