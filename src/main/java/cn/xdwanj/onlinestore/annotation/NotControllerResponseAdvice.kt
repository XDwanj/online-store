package cn.xdwanj.onlinestore.annotation

/**
 * 关闭统一返回对象切面包装
 *
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class NotControllerResponseAdvice
