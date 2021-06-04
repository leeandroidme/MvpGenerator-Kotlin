package com.newland.mvp.generator;

import com.intellij.ide.fileTemplates.*;
import com.intellij.openapi.fileTypes.StdFileTypes;
import com.intellij.psi.PsiDirectory;

import java.util.Map;
import java.util.Properties;

public class FileTemplateProvider implements FileTemplateGroupDescriptorFactory {
    public static final String MVP_ACTIVITY = "Activity.kt";
    public static final String MVP_ACTIVITYBINDINJECTMETHOD = "ActivityBindInjectMethod.kt";
    public static final String MVP_ACTIVITY_CONTRACT = "ActivityContract.kt";
    public static final String MVP_ACTIVITY_MODULE = "ActivityModule.kt";
    public static final String MVP_ACTIVITY_PRESENTER = "ActivityPresenter.kt";
    public static final String MVP_ACTIVITY_SUB_COMPONENT = "ActivitySubComponent.kt";
    public static final String MVP_ACTIVITY_LAYOUT = "layout_activity.xml";
    public static final String MVP_FRAGMENT = "Fragment.kt";
    public static final String MVP_FRAGMENT_CONTRACT = "FragmentContract.kt";
    public static final String MVP_FRAGMENT_MODULE = "FragmentModule.kt";
    public static final String MVP_FRAGMENT_PRESENTER = "FragmentPresenter.kt";
    public static final String MVP_FRAGMENT_SUB_COMPONENT = "FragmentSubComponent.kt";
    public static final String MVP_FRAGMENTBINDINJECTMETHOD = "FragmentBindInjectMethod.kt";
    public static final String MVP_FRAGMENT_LAYOUT = "layout_fragment.xml";
    public static final String DI_BASE_APP = "App.kt";
    public static final String DI_BASE_APP_COMPONENT = "AppComponent.kt";
    public static final String DI_BASE_APP_MODULE = "AppModule.kt";
    public static final String DI_BASE_ACTIVITIES_INJECTOR_FACTORIES = "ActivitiesInjectorFactories.kt";
    public static final String DI_BASE_FRAGMENTS_INJECTOR_FACTORIES = "FragmentsInjectorFactories.kt";
    public static final String MVP_HELPER_IPRESENTER = "IPresenter.kt";
    public static final String MVP_HELPER_PRESENTER = "Presenter.kt";
    public static final String MVP_HELPER_IVIEW = "IView.kt";
    public static final String MVP_BASE_ACTIVITY = "BaseActivity.kt";
    public static final String MVP_BASE_FRAGMENT = "BaseFragment.kt";
    public static final String MVP_MVP_ACTIVITY = "MvpActivity.kt";
    public static final String MVP_MVP_FRAGMENT = "MvpFragment.kt";

    public FileTemplateGroupDescriptor getFileTemplatesDescriptor() {
        final FileTemplateGroupDescriptor group = new FileTemplateGroupDescriptor("MVP", StdFileTypes.JAVA.getIcon());
        group.addTemplate(new FileTemplateDescriptor(MVP_ACTIVITY, StdFileTypes.JAVA.getIcon()));
        group.addTemplate(new FileTemplateDescriptor(MVP_ACTIVITY_CONTRACT, StdFileTypes.JAVA.getIcon()));
        group.addTemplate(new FileTemplateDescriptor(MVP_ACTIVITY_MODULE, StdFileTypes.JAVA.getIcon()));
        group.addTemplate(new FileTemplateDescriptor(MVP_ACTIVITY_PRESENTER, StdFileTypes.JAVA.getIcon()));
        group.addTemplate(new FileTemplateDescriptor(MVP_ACTIVITY_SUB_COMPONENT, StdFileTypes.JAVA.getIcon()));
        group.addTemplate(new FileTemplateDescriptor(MVP_ACTIVITY_LAYOUT, StdFileTypes.XML.getIcon()));
        group.addTemplate(new FileTemplateDescriptor(MVP_FRAGMENT, StdFileTypes.JAVA.getIcon()));
        group.addTemplate(new FileTemplateDescriptor(MVP_FRAGMENT_CONTRACT, StdFileTypes.JAVA.getIcon()));
        group.addTemplate(new FileTemplateDescriptor(MVP_FRAGMENT_MODULE, StdFileTypes.JAVA.getIcon()));
        group.addTemplate(new FileTemplateDescriptor(MVP_FRAGMENT_PRESENTER, StdFileTypes.JAVA.getIcon()));
        group.addTemplate(new FileTemplateDescriptor(MVP_FRAGMENT_SUB_COMPONENT, StdFileTypes.JAVA.getIcon()));
        group.addTemplate(new FileTemplateDescriptor(MVP_FRAGMENT_LAYOUT, StdFileTypes.XML.getIcon()));
        group.addTemplate(new FileTemplateDescriptor(DI_BASE_APP, StdFileTypes.JAVA.getIcon()));
        group.addTemplate(new FileTemplateDescriptor(DI_BASE_APP_COMPONENT, StdFileTypes.JAVA.getIcon()));
        group.addTemplate(new FileTemplateDescriptor(DI_BASE_APP_MODULE, StdFileTypes.JAVA.getIcon()));
        group.addTemplate(new FileTemplateDescriptor(DI_BASE_ACTIVITIES_INJECTOR_FACTORIES, StdFileTypes.JAVA.getIcon()));
        group.addTemplate(new FileTemplateDescriptor(DI_BASE_FRAGMENTS_INJECTOR_FACTORIES, StdFileTypes.JAVA.getIcon()));
        group.addTemplate(new FileTemplateDescriptor(MVP_HELPER_IPRESENTER, StdFileTypes.JAVA.getIcon()));
        group.addTemplate(new FileTemplateDescriptor(MVP_HELPER_PRESENTER, StdFileTypes.JAVA.getIcon()));
        group.addTemplate(new FileTemplateDescriptor(MVP_HELPER_IVIEW, StdFileTypes.JAVA.getIcon()));
        group.addTemplate(new FileTemplateDescriptor(MVP_BASE_ACTIVITY, StdFileTypes.JAVA.getIcon()));
        group.addTemplate(new FileTemplateDescriptor(MVP_BASE_FRAGMENT, StdFileTypes.JAVA.getIcon()));
        return group;
    }

    public static void createPsiClass(final PsiDirectory directory, final String name, final FileTemplateManager fileTemplateManager, final String templateName, final Map<String, String> properties) {
        final FileTemplate template = fileTemplateManager.getJ2eeTemplate(templateName);
        final Properties props = fileTemplateManager.getDefaultProperties();
        FileUtils.checkTemplaeProperties(directory,props);
        props.putAll(properties);
        try {
            FileTemplateUtil.createFromTemplate(template, name, props, directory);
        } catch (Exception e) {
            throw new RuntimeException("Unable to create template for " + name + "Activity", e);
        }
    }
}
