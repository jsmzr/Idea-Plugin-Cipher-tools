package com.jsmzr.cipher.ui;

import com.intellij.openapi.ui.ComboBox;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jsmzr.cipher.model.CipherBaseModel;
import com.jsmzr.cipher.model.CipherModel;
import com.jsmzr.cipher.util.Base64Util;
import com.jsmzr.cipher.util.CommonsCipherUtil;
import com.jsmzr.cipher.util.HmacUtil;
import com.jsmzr.cipher.util.MessageDigestUtil;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.Map;

public class CipherForm extends JPanel{
    private final CellConstraints cc = new CellConstraints();
    private final JLabel inLabel = new JLabel("Input:");
    private final JLabel centerLabel = new JLabel("Option:");
    private final JLabel outLabel = new JLabel("Output:");
    private final JLabel keyLabel = new JLabel("key:");
    private final JLabel ivLabel = new JLabel("iv:");
    private final JPanel centerSonJP;

    private final static String[] supportED = new String[]{
            "AES/CBC/NoPadding(128)", //( 128) ctl 8*
            "AES/CBC/PKCS5Padding(128)", //( 128) key 16 iv 16   --
            "AES/ECB/NoPadding(128)",// (128)
            "AES/ECB/PKCS5Padding(128)",// (128) key 16 --
            "DES/CBC/NoPadding(56)",// (56)
            "DES/CBC/PKCS5Padding(56)",// (56) key 8 iv 8  --
            "DES/ECB/NoPadding(56)",// (56)
            "DES/ECB/PKCS5Padding(56)",// (56) key 8  --
            "DESede/CBC/NoPadding(168)",// (168)
            "DESede/CBC/PKCS5Padding(168)",// (168) key 24 iv 8
            "DESede/ECB/NoPadding(168)",// (168)
            "DESede/ECB/PKCS5Padding(168)"
    };
    private final static String[] supportMD = new String[] {
            "MD5",
            "SHA-1",
            "SHA-256"
    };
    private final static String[] supportBS = new String[] {
            "BASE64"
    };
    private final static String[] supportHm = new String[] {
            "HmacMD5",
            "HmacSHA1",
            "HmacSHA256"
    };

    private final String[] supportCipher;
    private final Map<String, CipherBaseModel> cipherModels;

    private final JComboBox cipherType;// = new ComboBox(new DefaultComboBoxModel(supportCipher));

    private final JTextField cipherKey = new JTextField();
    private final JTextField cipherIv = new JTextField();

    private final JTextArea input = new JTextArea(10, 32);
    private final JTextArea output = new JTextArea(10, 32);

    private final JButton encodeBtn = new JButton("Encode>>");
    private final JButton decodeBtn = new JButton("<<Decode");

    private static String histInputText;
    private static String histInputKey;
    private static String histInputIv;
    private static String histOption;

    private static String histInput;
    private static String histOutput;
    private static boolean histBtn;

    public CipherForm() {
        super(new FormLayout("f:d:g 10dlu 150dlu 10dlu f:d:g", "f:d:g"));
        cipherModels = new HashMap<>();
        supportCipher = new String[supportBS.length + supportED.length + supportHm.length + supportMD.length];
        for (int index=0,size=supportED.length; index < size; index++) {
            cipherModels.put(supportED[index], new CipherModel(supportED[index]));
            supportCipher[index] = supportED[index];
        }
        for (int index=0, size=supportMD.length; index < size; index++) {
            cipherModels.put(supportMD[index], new CipherBaseModel(2));
            supportCipher[index + supportED.length] = supportMD[index];
        }
        for (int index=0, size=supportHm.length; index < size; index++) {
            cipherModels.put(supportHm[index], new CipherBaseModel(4));
            supportCipher[index + supportED.length + supportMD.length] = supportHm[index];
        }
        for (int index=0, size=supportBS.length; index < size; index++) {
            cipherModels.put(supportBS[index], new CipherBaseModel(3));
            supportCipher[index + supportED.length + supportMD.length + supportHm.length] = supportBS[index];
        }
//        supportCipher = tmpSupportCipher;
        for (String tmps : supportCipher) {
            System.out.println(tmps);
        }
        cipherType = new ComboBox(new DefaultComboBoxModel(supportCipher));
        this.cc.insets.set(2, 2, 2, 2);
        JPanel inJP = new JPanel(new FormLayout("f:d:g", "p 5dlu f:d:g"));
        JPanel centerJP = new JPanel(new FormLayout("f:d:g", "p 5dlu 20dlu 5dlu 45dlu 5dlu f:d:g"));
        JPanel outJP = new JPanel(new FormLayout("f:d:g", "p 5dlu f:d:g"));

        this.centerSonJP = new JPanel(new FormLayout("20dlu 5dlu f:d:g","20dlu 5dlu 20dlu"));
        JPanel centerSonJP2 = new JPanel(new FormLayout("f:d:g 60dlu f:d:g", "20dlu 5dlu 20dlu"));

        inJP.add(this.inLabel, this.cc.xy(1, 1));
        inJP.add(new JScrollPane(this.input), this.cc.xy(1, 3));

        this.input.setBorder(new LineBorder(new Color(127, 157, 185),1, false));
        this.output.setBorder(new LineBorder(new Color(127, 157, 185),1, false));
        this.cipherKey.setBorder(new LineBorder(new Color(127, 157, 185),1, false));
        this.cipherIv.setBorder(new LineBorder(new Color(127, 157, 185),1, false));
        this.input.setLineWrap(true);
        this.input.setWrapStyleWord(true);
        this.output.setLineWrap(true);
        this.output.setWrapStyleWord(true);


        centerJP.add(this.centerLabel, this.cc.xy(1, 1));
        centerJP.add(this.cipherType, this.cc.xy(1, 3));

        centerJP.add(centerSonJP, this.cc.xy(1, 5));
        centerSonJP.add(this.keyLabel, this.cc.xy(1, 1));
        centerSonJP.add(this.cipherKey, this.cc.xy(3, 1));
        centerSonJP.add(this.ivLabel, this.cc.xy(1,3));
        centerSonJP.add(this.cipherIv, this.cc.xy(3, 3));
        centerSonJP2.add(this.encodeBtn, this.cc.xy(2, 1));
        centerSonJP2.add(this.decodeBtn, this.cc.xy(2, 3));
        centerJP.add(centerSonJP2, this.cc.xy(1, 7));

        outJP.add(this.outLabel, this.cc.xy(1, 1));
        outJP.add(new JScrollPane(this.output), this.cc.xy(1, 3));


        this.add(inJP, this.cc.xy(1, 1));
        this.add(centerJP, this.cc.xy(3, 1));
        this.add(outJP, this.cc.xy(5, 1));
        startListener();
    }

    private void showOptional(boolean keyState, boolean ivState, boolean btnState) {
        keyLabel.setVisible(keyState);
        cipherKey.setVisible(keyState);
        ivLabel.setVisible(ivState);
        cipherIv.setVisible(ivState);
        decodeBtn.setVisible(btnState);
    }

    private void initHistText() {
        histInputIv = null;
        histInputKey = null;
        histInputText = null;
    }

    private void startListener() {

        cipherType.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == 2) {
                    return;
                }
                String currCmd = (String)e.getItem();
                if (histOption != null && histOption.equals(currCmd)) {
                    return;
                }else {
                    initHistText();
                    histOption = currCmd;
                }
                String currOption = (String)cipherType.getSelectedItem();
                CipherBaseModel cb = cipherModels.get(currOption);
                switch (cb.getType()){
                    case 1:
                        if ("CBC".equals(((CipherModel)cb).getWork())) {
                            showOptional(true, true, true);
                        }else{
                            showOptional(true, false, true);
                        }
                        break;
                    case 2:
                        showOptional(false, false, false);
                        break;
                    case 3:
                        showOptional(false, false, true);
                        break;
                    case 4:
                        showOptional(true, false, false);
                        break;
                    default:
                        break;
                }
            }
        });

        encodeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonAction(true);
            }
        });

        decodeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonAction(false);
            }
        });
    }

    private void buttonAction(boolean isEncode) {
        String content = isEncode ? input.getText() : output.getText();
        String currKey = cipherKey.getText();
        currKey = currKey == null || "".equals(currKey = currKey.trim()) ? null : currKey;
        String currIv = cipherIv.getText();
        currIv = currIv == null || "".equals(currIv = currIv.trim()) ? null : currIv;
        String currOutput = !isEncode ? input.getText() : output.getText();
        currOutput = currOutput == null || "".equals(currOutput=currOutput.trim()) ? null : currOutput;

        if (content == null || "".equals(content.trim())) {
            return;
        }
        content = content.trim();
        String currOption = (String)cipherType.getSelectedItem();
        if (!(isEncode ^ histBtn) && currOption.equals(histOption) &&
                content.equals(histInputText) &&
                (histInputKey != null? histInputKey.equals(currKey) : histInputKey == currKey) &&
                (histInputIv != null ? histInputIv.equals(currIv) : histInputIv == currIv) &&
                (isEncode ? histOutput != null : histInput != null)) {
            if (isEncode && !histOutput.equals(currOutput)) {
                output.setText(histOutput);
            }else if(!isEncode && !histInput.equals(currOutput)) {
                input.setText(currOutput);
            }
            return;
        }

        String result = null;
        CipherBaseModel cb = cipherModels.get(currOption);
        try {
            switch (cb.getType()) {
                case 1:
                    result = isEncode ?
                            CommonsCipherUtil.encrypt((CipherModel) cb, content, currKey, currIv) :
                            CommonsCipherUtil.decrypt((CipherModel) cb, content, currKey, currIv);
                    break;
                case 2:
                    result = MessageDigestUtil.commons(currOption, content);
                    break;
                case 3:
                    result = isEncode ?
                            Base64Util.encode(content) :
                            Base64Util.decode(content);
                    break;
                case 4:
                    result = HmacUtil.encrypt(currOption, content, currKey);
                    break;
                default:
                    result = "";
            }
        }catch (Exception e) {
            result = "error: " + e.getMessage();
        }
        if (isEncode) {
            output.setText(result);
        }else {
            input.setText(result);
        }
        histBtn = isEncode;
        histOutput = isEncode ? result : content;
        histInput = isEncode ? content : result;
        histInputIv = currIv;
        histInputKey = currKey;
    }
}
