package com.midream.sheep.swcj.util.classLoader;

import com.midream.sheep.swcj.util.Compiler.DynamicCompiler;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author midreamSheep
 * 自定义编译类
 */
public class SWCJClassLoader extends ClassLoader {
    //通过文件加载一个class
    public Class<?> loadData(String className, String file) {
        byte[] data = loderClassData(file);
        if (data != null) {
            return super.defineClass(className, data, 0, data.length);
        }
        return null;
    }

    public Class<?> loadData(String className, byte[] datas) {
        if (datas != null) {
            return super.defineClass(className, datas, 0, datas.length);
        }
        return null;
    }

    @Override
    public Class<?> findClass(String name) {
        return super.findLoadedClass(name);
    }

    //通过字节加载一个class
    private byte[] loderClassData(String file) {
        byte[] datas = null;
        try {
            datas = Files.readAllBytes(Paths.get(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return datas;
    }

    //java编译器,编译一个java文件为class
    public String compileJavaFile(File tofile) {
        //获取系统Java编译器
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        //获取Java文件管理器
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
        //通过源文件获取到要编译的Java类源码迭代器，包括所有内部类，其中每个类都是一个 JavaFileObject，也被称为一个汇编单元
        Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjects(tofile);
        //生成编译任务
        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, null, null, null, compilationUnits);
        //执行编译任务
        task.call();
        return tofile.getPath().replace(".java", ".class");
    }
    public Class<?> compileJavaFile(String className,String allClass) throws IOException, ClassNotFoundException {
        DynamicCompiler compiler = new DynamicCompiler();
        Class<?> clz = compiler.compileAndLoad(className,allClass);
        compiler.close();
        return clz;
    }
}
