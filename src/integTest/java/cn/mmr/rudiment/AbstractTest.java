package cn.mmr.rudiment;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Resources;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.Charsets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.codec.multipart.MultipartHttpMessageReader;


public class AbstractTest {

  @Autowired
  private ObjectMapper objectMapper;


  public ClassPathResource getClassPathResource(String path) {
    return new ClassPathResource(path, MultipartHttpMessageReader.class);
  }

  /**
   * 获取文件流.
   * @param path 文件路径
   * @return
   */
  public String getStringFromJson(String path, Charset charset) throws IOException {
    return Resources.toString(Resources.getResource(path), charset);
  }

  /**
   * 将json转换为对象.
   * @param path 文件路径
   */
  public <T> T fromJson(String path, Class<T> cls) throws IOException {
    return objectMapper
        .readValue(this.getStringFromJson(path, Charsets.UTF_8), cls);
  }

  /**
   * 将json数组转换为对象列表.
   * @param path 文件路径
   */
  public <T> List<T> fromJsonArray(String path, TypeReference typeReference) throws IOException {
    return objectMapper
        .readValue(getStringFromJson(path, Charsets.UTF_8), typeReference);
  }

  /**
   * 获取测试资源文件.
   * @param name 文件名
   */
  public static File getResource(String name) {
    ClassLoader classLoader = AbstractTest.class.getClassLoader();
    File file = new File(classLoader.getResource(name).getFile());
    return file;
  }

  public static interface PassableRunnable extends Runnable {

    boolean isPassed();
  }

  /**
   * 循环执行线程.
   */
  protected void periodicCheck(PassableRunnable runnable) throws InterruptedException {
    while (true) {
      runnable.run();
      if (runnable.isPassed()) {
        return;
      }
      TimeUnit.MILLISECONDS.sleep(50);
    }
  }

}
