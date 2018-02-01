package com.andyadc.scaffold.util.base;

import com.google.common.base.Throwables;

import java.lang.reflect.UndeclaredThrowableException;

/**
 * 关于异常的工具类.
 * <p>
 * 1. 若干常用函数.
 * <p>
 * 2. StackTrace性能优化相关，尽量使用静态异常避免异常生成时获取StackTrace，及打印StackTrace的消耗
 *
 * @author calvin
 */
public abstract class ExceptionUtils {

    private static final StackTraceElement[] EMPTY_STACK_TRACE = new StackTraceElement[0];

    /**
     * 将CheckedException转换为RuntimeException重新抛出, 可以减少函数签名中的CheckExcetpion定义.
     * <p>
     * CheckedException会用UndeclaredThrowableException包裹，RunTimeException和Error则不会被转变.
     * <p>
     * from Commons Lange 3.5 ExceptionUtils.
     * <p>
     * 虽然unchecked()里已直接抛出异常，但仍然定义返回值，方便欺骗Sonar。因此本函数也改变了一下返回值
     * <p>
     * 示例代码:
     * <p>
     * <pre>
     * try {
     *      ...
     * } catch (Exception e) {
     *      throw unchecked(t);
     *  }
     * </pre>
     *
     * @see org.apache.commons.lang3.exception.ExceptionUtils#wrapAndThrow(Throwable)
     */
    public static RuntimeException unchecked(final Throwable t) {

        Throwable unwrapped = unwrap(t);
        if (unwrapped instanceof RuntimeException) {
            throw (RuntimeException) unwrapped;
        }
        if (unwrapped instanceof Error) {
            throw (Error) unwrapped;
        }
        throw new UndeclaredThrowableException(unwrapped);
    }

    /**
     * 如果是著名的包裹类，从cause中获得真正异常. 其他异常则不变.
     * <p>
     * Future中使用的ExecutionException 与 反射时定义的InvocationTargetException， 真正的异常都封装在Cause中
     * <p>
     * 前面 unchecked() 使用的UndeclaredThrowableException同理.
     * <p>
     * from Quasar and Tomcat's ExceptionUtils
     */
    public static Throwable unwrap(final Throwable t) {
        if (t instanceof java.util.concurrent.ExecutionException
                || t instanceof java.lang.reflect.InvocationTargetException
                || t instanceof java.lang.reflect.UndeclaredThrowableException) {
            return t.getCause();
        }
        return t;
    }

    /**
     * 将StackTrace[]转换为String, 供Logger或e.printStackTrace()外的其他地方使用.
     *
     * @see Throwables#getStackTraceAsString(Throwable)
     */
    public static String stackTraceText(final Throwable t) {
        return Throwables.getStackTraceAsString(t);
    }

    /**
     * 获取异常的Root Cause.
     *
     * @see Throwables#getRootCause(Throwable)
     */
    public static Throwable getRootCause(final Throwable t) {
        return Throwables.getRootCause(t);
    }

    /**
     * 判断异常是否由某些底层的异常引起.
     */
    @SuppressWarnings("unchecked")
    public static boolean isCausedBy(final Throwable t, final Class<? extends Exception>... causeExceptionClasses) {
        Throwable cause = t;

        while (cause != null) {
            for (Class<? extends Exception> causeClass : causeExceptionClasses) {
                if (causeClass.isInstance(cause)) {
                    return true;
                }
            }
            cause = cause.getCause();
        }
        return false;
    }

    /**
     * 拼装异常类名与异常信息
     *
     * @see org.apache.commons.lang3.exception.ExceptionUtils#getMessage(Throwable)
     */
    public static String getMessageWithExceptionName(final Throwable t) {
        return org.apache.commons.lang3.exception.ExceptionUtils.getMessage(t);
    }

    /////////// StackTrace 性能优化相关////////

    /**
     * from Netty, 为静态异常设置StackTrace.
     * <p>
     * 对某些已知且经常抛出的异常, 不需要每次创建异常类并很消耗性能的并生成完整的StackTrace. 此时可使用静态声明的异常.
     * <p>
     * 如果异常可能在多个地方抛出，使用本函数设置抛出的类名和方法名.
     * <p>
     * <pre>
     * private static RuntimeException TIMEOUT_EXCEPTION = ExceptionUtils.setStackTrace(new RuntimeException("Timeout"),
     * 		MyClass.class, "mymethod");
     *
     * </pre>
     */
    public static <T extends Throwable> T setStackTrace(T exception, Class<?> throwClass, String throwClazz) {
        exception.setStackTrace(
                new StackTraceElement[]{new StackTraceElement(throwClass.getName(), throwClazz, null, -1)});
        return exception; // NOSONAR
    }

    /**
     * 清除StackTrace. 假设StackTrace已生成, 但把它打印出来也有不小的消耗.
     * <p>
     * 如果不能控制StackTrace的生成，也不能控制它的打印端(如logger)，可用此方法暴力清除Trace.
     * <p>
     * 但Cause链依然不能清除, 只能清除每一个Cause的StackTrace.
     */
    public static <T extends Throwable> T clearStackTrace(final T exception) {
        Throwable cause = exception;
        while (cause != null) {
            cause.setStackTrace(EMPTY_STACK_TRACE);
            cause = cause.getCause();
        }
        return exception; // NOSONAR
    }

    /**
     * 适用于Message经常变更的异常, 可通过clone()不经过构造函数的构造异常再设定新的异常信息.
     */
    public static class CloneableException extends Exception implements Cloneable {

        private static final long serialVersionUID = -6270471689928560417L;
        protected String message;

        public CloneableException() {
            super((Throwable) null);
        }

        public CloneableException(String message) {
            super((Throwable) null);
            this.message = message;
        }

        public CloneableException(String message, Throwable cause) {
            super(cause);
            this.message = message;
        }

        @Override
        public CloneableException clone() {
            try {
                return (CloneableException) super.clone();
            } catch (CloneNotSupportedException e) { // NOSONAR
                return null;
            }
        }

        public CloneableException clone(String message) {
            CloneableException newException = this.clone();
            newException.setMessage(message);
            return newException;
        }

        @Override
        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public CloneableException setStackTrace(Class<?> throwClazz, String throwMethod) {
            ExceptionUtils.setStackTrace(this, throwClazz, throwMethod);
            return this;
        }
    }

    /**
     * 重载fillInStackTrace()方法，不生成StackTrace.
     * <p>
     * 适用于Message经常变更，不能使用静态异常时
     */
    public static class CloneableRuntimeException extends RuntimeException implements Cloneable {

        private static final long serialVersionUID = 3984796576627959400L;

        protected String message;

        public CloneableRuntimeException() {
            super((Throwable) null);
        }

        public CloneableRuntimeException(String message) {
            super((Throwable) null);
            this.message = message;
        }

        public CloneableRuntimeException(String message, Throwable cause) {
            super(cause);
            this.message = message;
        }

        @Override
        public CloneableRuntimeException clone() {
            try {
                return (CloneableRuntimeException) super.clone();
            } catch (CloneNotSupportedException e) { // NOSONAR
                return null;
            }
        }

        public CloneableRuntimeException clone(String message) {
            CloneableRuntimeException newException = this.clone();
            newException.setMessage(message);
            return newException;
        }

        @Override
        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public CloneableRuntimeException setStackTrace(Class<?> throwClazz, String throwMethod) {
            ExceptionUtils.setStackTrace(this, throwClazz, throwMethod);
            return this;
        }
    }
}

