# MvpGenerator-Kotlin
基于dagger的全自动化MVP插件
Kotlin base DI classes将会生成基础的MVP配置，并且会在项目根目录下生成mvpgenerator.properties属性配置文件
MVP Kotlin Activity 创建MVP的Acitivity
MVP Kotlin Fragment 创建MVP的Fragment

项目中可以添加自定义属性（项目根下mvpgenerator.properties文件中）

layout.folders=layout,layout-land 来实现创建MVP的Activity或Fragment时，生成多套布局，手动配置

mvp.helper.package=mvphelper的包名   默认自动配置

mvp.activity.package=MvpActivity所在包 默认自动配置

mvp.fragment.package=MvpFragment所在包 默认自动配置

activity.injector.factory.file=ActivitiesInjectorFactories(Activity dagger绑定工厂)文件地址，$PROJECT_DIR$/app/src/main/java/com/..../ActivitiesInjectorFactories.kt 默认自动配置

fragment.injector.factory.file=FragmentsInjectorFactories(Fragment dagger绑定工厂)

manifest.activity.tag.attrs=[key1=value1,key2=value2] manifest为自动注册的activity添加额外属性值，手动配置