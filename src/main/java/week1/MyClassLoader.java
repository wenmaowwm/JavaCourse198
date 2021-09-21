package week1;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;

public class MyClassLoader extends ClassLoader{
    public static void main(String[] args) throws Exception {
        Class<?> myClazz = new MyClassLoader().loadClass("Hello");
        Method method = myClazz.getDeclaredMethod("hello");
        method.invoke(myClazz.newInstance());
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        File file = new File("src/main/resources/Hello.xlass");
        FileInputStream fileInput = null;
        byte[] buffer ;
        try {
            fileInput = new FileInputStream(file);
            buffer = new byte[fileInput.available()];
            fileInput.read(buffer, 0, fileInput.available());
            for(int i = 0;i<buffer.length;i++){
                buffer[i] = (byte) (255 - buffer[i]);
            }
            return defineClass(name, buffer, 0, buffer.length);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (fileInput != null) {
                    fileInput.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
