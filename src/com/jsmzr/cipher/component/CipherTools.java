package com.jsmzr.cipher.component;

import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowAnchor;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.jsmzr.cipher.ui.CipherForm;
import org.jetbrains.annotations.NotNull;

public class CipherTools implements ProjectComponent {
    private final Project project;
    private CipherForm cipherForm;
    public CipherTools(Project project) {
        this.project = project;
    }

    @Override
    public void initComponent() {
    }

    @Override
    public void disposeComponent() {
    }

    @Override
    @NotNull
    public String getComponentName(){
        return "CipherTools";
    }

    @Override
    public void projectOpened() {
        ToolWindowManager twm = ToolWindowManager.getInstance(this.project);
        this.cipherForm = new CipherForm();
        ToolWindow cipherToolWindow = twm.registerToolWindow(getComponentName(), false, ToolWindowAnchor.BOTTOM);
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(this.cipherForm, "", false);
        cipherToolWindow.getContentManager().addContent(content);
        // called when project is opened
    }

    @Override
    public void projectClosed() {
    }
}
