package com.fangxuele.tool.push.ui.dialog;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.fangxuele.tool.push.App;
import com.fangxuele.tool.push.ui.UiConsts;
import com.fangxuele.tool.push.util.ComponentUtil;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

@Slf4j
public class AppreciateDialog extends JDialog {
    private JPanel contentPane;
    private JLabel qrCodeLabel;

    public AppreciateDialog() {
        super(App.mainFrame, "鼓励一下");
        ComponentUtil.setPreferSizeAndLocateToCenter(this, 400, 400);
        setContentPane(contentPane);
        setModal(true);

        ThreadUtil.execute(this::initQrCode);

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_FOCUSED);
    }

    /**
     * 初始化二维码
     */
    public void initQrCode() {
        String qrCodeContent = HttpUtil.get(UiConsts.QR_CODE_URL);
        if (StringUtils.isNotEmpty(qrCodeContent)) {
            Map<String, String> urlMap = JSONUtil.toBean(qrCodeContent, Map.class);
            try {
                URL url = new URL(urlMap.get("url"));
                BufferedImage image = ImageIO.read(url);
                qrCodeLabel.setIcon(new ImageIcon(image));
            } catch (IOException e) {
                e.printStackTrace();
                log.error(ExceptionUtils.getStackTrace(e));
            }

        }
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        contentPane = new JPanel();
        contentPane.setLayout(new GridLayoutManager(1, 1, new Insets(10, 10, 10, 10), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        qrCodeLabel = new JLabel();
        qrCodeLabel.setIcon(new ImageIcon(getClass().getResource("/icon/weixin-qrcode.png")));
        qrCodeLabel.setText("");
        qrCodeLabel.setToolTipText("感谢您的鼓励和支持！");
        panel1.add(qrCodeLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }

}
