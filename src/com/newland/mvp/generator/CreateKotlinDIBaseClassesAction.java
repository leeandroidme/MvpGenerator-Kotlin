package com.newland.mvp.generator;

import com.intellij.ide.fileTemplates.FileTemplate;
import com.intellij.ide.fileTemplates.FileTemplateManager;
import com.intellij.ide.fileTemplates.FileTemplateUtil;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class CreateKotlinDIBaseClassesAction extends AnAction {
    private static final String NAME_DIR_ROOT = "di";
    private static final String NAME_DIR_COMPONENTS = "components";
    private static final String NAME_DIR_FACTORIES = "factories";
    private static final String NAME_DIR_MODULES = "modules";
    private static final String NAME_DIR_MVPHELPER = "mvphelper";
    private static final String NAME_DIR_BASE = "base";
    private static final String NAME_FILE_APP = "App";
    private static final String NAME_FILE_APP_COMPONENT = "AppComponent";
    private static final String NAME_FILE_APP_MODULE = "AppModule";
    private static final String NAME_FILE_ACTIVITIES_INJECTOR_FACTORIES = "ActivitiesInjectorFactories";
    private static final String NAME_FILE_FRAGMENTS_INJECTOR_FACTORIES = "FragmentsInjectorFactories";
    private static final String NAME_FILE_IPRESENTER = "IPresenter";
    private static final String NAME_FILE_PRESENTER = "Presenter";
    private static final String NAME_FILE_IVIEW = "IView";
    private static final String NAME_FILE_BASEACTIVITY = "BaseActivity";
    private static final String NAME_FILE_BASEFRAGMENT = "BaseFragment";
    private static final String NAME_FILE_MVPACTIVITY = "MvpActivity";
    private static final String NAME_FILE_MVPFRAGMENT = "MvpFragment";

    public void actionPerformed(final AnActionEvent e) {
        final Project project = e.getProject();
        if (project == null) {
            return;
        }
        final DataContext dataContext = e.getDataContext();
        final Module module = LangDataKeys.MODULE.getData(dataContext);
        if (module == null) {
            return;
        }
        final VirtualFile targetFile = CommonDataKeys.VIRTUAL_FILE.getData(dataContext);
        PsiDirectory psiDirectory = FileUtils.validateSelectedDirectory(project, targetFile);
        if (psiDirectory == null) {
            return;
        }
        psiDirectory = psiDirectory.createSubdirectory(NAME_DIR_ROOT);
        this.create(psiDirectory, module);
    }

    private void create(final PsiDirectory directory, final Module module) {
        final FileTemplateManager fileTemplateManager = FileTemplateManager.getDefaultInstance();
        final String rootPackage = FileUtils.getSelectedDirectoryPath(fileTemplateManager, directory);
        this.createFile(directory, NAME_FILE_APP, rootPackage, fileTemplateManager, FileTemplateProvider.DI_BASE_APP);
        this.createFile(directory.createSubdirectory(NAME_DIR_COMPONENTS), NAME_FILE_APP_COMPONENT, rootPackage, fileTemplateManager, FileTemplateProvider.DI_BASE_APP_COMPONENT);
        this.createFile(directory.createSubdirectory(NAME_DIR_MODULES), NAME_FILE_APP_MODULE, rootPackage, fileTemplateManager, FileTemplateProvider.DI_BASE_APP_MODULE);
        this.createFile(directory.createSubdirectory(NAME_DIR_FACTORIES), NAME_FILE_ACTIVITIES_INJECTOR_FACTORIES, rootPackage, fileTemplateManager, FileTemplateProvider.DI_BASE_ACTIVITIES_INJECTOR_FACTORIES);
        this.createFile(directory.findSubdirectory(NAME_DIR_FACTORIES), NAME_FILE_FRAGMENTS_INJECTOR_FACTORIES, rootPackage, fileTemplateManager, FileTemplateProvider.DI_BASE_FRAGMENTS_INJECTOR_FACTORIES);

        this.createFile(directory.createSubdirectory(NAME_DIR_MVPHELPER), NAME_FILE_IPRESENTER, rootPackage, fileTemplateManager, FileTemplateProvider.MVP_HELPER_IPRESENTER);
        this.createFile(directory.findSubdirectory(NAME_DIR_MVPHELPER), NAME_FILE_PRESENTER, rootPackage, fileTemplateManager, FileTemplateProvider.MVP_HELPER_PRESENTER);
        this.createFile(directory.findSubdirectory(NAME_DIR_MVPHELPER), NAME_FILE_IVIEW, rootPackage, fileTemplateManager, FileTemplateProvider.MVP_HELPER_IVIEW);

        PsiDirectory psiDirectory = directory.findSubdirectory(NAME_DIR_MVPHELPER);
        String mvpHelperPackage = FileUtils.getPackage(psiDirectory.getVirtualFile().getPath() + File.separator + FileTemplateProvider.MVP_HELPER_IVIEW);


        this.createFile(directory.createSubdirectory(NAME_DIR_BASE), NAME_FILE_BASEACTIVITY, rootPackage, fileTemplateManager, FileTemplateProvider.MVP_BASE_ACTIVITY);
        this.createFile(directory.findSubdirectory(NAME_DIR_BASE), NAME_FILE_BASEFRAGMENT, rootPackage, fileTemplateManager, FileTemplateProvider.MVP_BASE_FRAGMENT);

        FileTemplateProvider.createPsiClass(directory.findSubdirectory(NAME_DIR_BASE), NAME_FILE_MVPACTIVITY, fileTemplateManager, FileTemplateProvider.MVP_MVP_ACTIVITY, new HashMap<String, String>() {{
            this.put("COMMON_PACKAGE", mvpHelperPackage);
        }});
        FileTemplateProvider.createPsiClass(directory.findSubdirectory(NAME_DIR_BASE), NAME_FILE_MVPFRAGMENT, fileTemplateManager, FileTemplateProvider.MVP_MVP_FRAGMENT, new HashMap<String, String>() {{
            this.put("COMMON_PACKAGE", mvpHelperPackage);
        }});

        PsiDirectory baseDirectory = directory.findSubdirectory(NAME_DIR_BASE);
        String basePackage = FileUtils.getPackage(baseDirectory.getVirtualFile().getPath() + File.separator + FileTemplateProvider.MVP_BASE_ACTIVITY);

        String factoryPath = directory.findSubdirectory(NAME_DIR_FACTORIES).getVirtualFile().getPath().replace(module.getProject().getBasePath(), "$PROJECT_DIR$");

        Map<String, String> map = new HashMap<>();
        map.put("mvp.helper.package", mvpHelperPackage);
        map.put("mvp.activity.package", basePackage);
        map.put("mvp.fragment.package", basePackage);
        map.put("activity.injector.factory.file", factoryPath + "/" + FileTemplateProvider.DI_BASE_ACTIVITIES_INJECTOR_FACTORIES);
        map.put("fragment.injector.factory.file", factoryPath + "/" + FileTemplateProvider.DI_BASE_FRAGMENTS_INJECTOR_FACTORIES);
        MvpGeneratorManager.getInstance().writeMvpProperties(module, map);
    }

    private void createFile(final PsiDirectory directory, final String name, final String rootPackage, final FileTemplateManager fileTemplateManager, final String templateName) {
        final FileTemplate template = fileTemplateManager.getJ2eeTemplate(templateName);
        final Properties props = fileTemplateManager.getDefaultProperties();
        FileUtils.checkTemplaeProperties(directory, props);
        props.put("ROOT_NAME", rootPackage);
        try {
            FileTemplateUtil.createFromTemplate(template, name, props, directory);
        } catch (Exception e) {
            throw new RuntimeException("Unable to create template for injection classes :(", e);
        }
    }
}
